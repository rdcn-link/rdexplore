package cn.cnic.faird.admin.service.impl;

import cn.cnic.faird.FairdConnection;
import cn.cnic.faird.admin.enums.ResourceType;
import cn.cnic.faird.admin.service.ResourceService;
import cn.cnic.faird.admin.util.HttpClient;
import cn.cnic.faird.admin.util.JsonUtils;
import cn.cnic.faird.admin.util.ReturnMapUtils;
import cn.cnic.faird.admin.vo.CustomDataFrameVo;
import cn.cnic.faird.admin.vo.DataFrameSchemaVO;
import cn.cnic.faird.admin.vo.Resource;
import cn.cnic.protocol.model.DataFrame;
import cn.cnic.protocol.model.MetaData;
import cn.cnic.protocol.vo.UnionAskMethod;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import scala.Option;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResourceServiceImpl implements ResourceService {


    public static final String LIST_PARSERS_URL = "http://127.0.0.1:5000/list_parsers";


    @Override
    public String getResourceByUri(HttpServletRequest request, String uri) {

        String remoteAddr = "localhost";
        if (request != null) {
            remoteAddr = request.getRemoteAddr();
        }

        String serverIp = uri.split("/")[2];

        FairdConnection connect = FairdConnection.connect(serverIp, 3101, remoteAddr);

        MetaData meta = connect.meta(uri);

        // model
        List<DataFrame> dataFrames = connect.model(uri);

        String resourceId = "";
        List<MetaData.Identifier> identifiers = meta.getIdentifier();
        for (MetaData.Identifier identifier : identifiers) {
            if ("CUSTOM".equals(identifier.getType())) {
                resourceId = identifier.getId();
            }
        }

        String titleCN = "";
        List<MetaData.Title> titles = meta.getTitle();
        for (MetaData.Title title : titles) {
            if ("zh".equals(title.getLanguage())) titleCN = title.getTitle();
        }

        Resource resource = new Resource();
        resource.setId(resourceId);
        resource.setUri(uri);

        resource.setName(titleCN);
        resource.setResourceType(ResourceType.DATA);

        List<CustomDataFrameVo> dataFrameVos = new ArrayList<>();
        for (int i = 0; i < dataFrames.size(); i++) {
            DataFrame dataFrame = dataFrames.get(i);
            CustomDataFrameVo customDataFrameVo = new CustomDataFrameVo();
            BeanUtils.copyProperties(dataFrame, customDataFrameVo);

            customDataFrameVo.setDataframeId(dataFrame.getId());

            if (i == 0) {
                String s = dataFrame.toJsonStr();
                JSONArray objects = JSONArray.parseArray(s);
                for (int i1 = 0; i1 < objects.size(); i1++) {
                    JSONObject jsonObject = objects.getJSONObject(i1);
                    jsonObject.put("content", "");

                    String fileName = jsonObject.getString("fileName");
                    String extension = FilenameUtils.getExtension(fileName);

                    String url = null;
                    try {
                        //url = LIST_PARSERS_URL + "?Format=" + URLEncoder.encode("Format 中文", StandardCharsets.UTF_8.toString());
                        url = LIST_PARSERS_URL + "?Format=" + extension;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String ret = HttpClient.doGet(url , null);
                    JSONArray parsers = JSONArray.parseArray(ret);
                    jsonObject.put("parsers", parsers);
                }

                customDataFrameVo.setJsonData(JSONArray.parseArray(objects.toJSONString()));

            } else {
                customDataFrameVo.setJsonData(JSONArray.parseArray(dataFrame.limit(25).toJsonStr()));
            }
            dataFrameVos.add(customDataFrameVo);
        }

        resource.setDataFrameVos(dataFrameVos);

        resource.setResourceMetaData(meta);

        connect.close();

        return JsonUtils.toFormatJsonNoException(ReturnMapUtils.setSucceededCustomParam("data", resource));
    }


    @Override
    public String open(HttpServletRequest request, int firstIndex, int secondIndex, String uri, String parser) {

        String remoteAddr = "localhost";
        if (request != null) {
            remoteAddr = request.getRemoteAddr();
        }

        String serverIp = uri.split("/")[2];

        FairdConnection connect = FairdConnection.connect(serverIp, 3101, remoteAddr);

        List<DataFrame> model = connect.model(uri);

        DataFrame dataFrame = model.get(firstIndex);

        DataFrame open = null;
        if (StringUtils.isNotBlank(parser)) {
            open = dataFrame.open(secondIndex, parser);
        } else {
            open = dataFrame.open(secondIndex);
        }

        connect.close();

        return JsonUtils.toFormatJsonNoException(ReturnMapUtils.setSucceededCustomParam("data", open.getId()));
    }

    @Override
    public String getSampleDataAndSchema(HttpServletRequest request, String dataframeId, String uri) {

        String remoteAddr = "localhost";
        if (request != null) {
            remoteAddr = request.getRemoteAddr();
        }

        String serverIp = uri.split("/")[2];

        FairdConnection connect = FairdConnection.connect(serverIp, 3101, remoteAddr);

        DataFrame open = new DataFrame();
        open.setId(dataframeId);

        List<DataFrameSchemaVO> dataFrameSchemaVOS = new ArrayList<>();

        StructType schema = open.getSchema();

        StructField[] fields = schema.fields();
        for (StructField field : fields) {
            String name = field.name();
            String typeName = field.dataType().typeName();
            Option<String> comment = field.getComment();

            DataFrameSchemaVO dataFrameSchemaVO = new DataFrameSchemaVO();
            dataFrameSchemaVO.setName(name).setType(typeName).setComment("");

            dataFrameSchemaVOS.add(dataFrameSchemaVO);
        }


        Map<String, Object> ret = new HashMap<>();
        ret.put("schema", dataFrameSchemaVOS);
        ret.put("data", JSONArray.parseArray(open.limit(25).toJsonStr()));

        connect.close();

        return JsonUtils.toFormatJsonNoException(ReturnMapUtils.setSucceededCustomParam("data", ret));

    }

    @Override
    public String test(HttpServletRequest request, String uri, String dfId) {
        String remoteAddr = "localhost";
        if (request != null) {
            remoteAddr = request.getRemoteAddr();
        }

        String serverIp = uri.split("/")[2];
        FairdConnection connect = FairdConnection.connect(serverIp, 3101, remoteAddr);

        DataFrame open = new DataFrame();
        open.setId(dfId);

        List<DataFrameSchemaVO> dataFrameSchemaVOS = new ArrayList<>();

        StructType schema = open.getSchema();

        StructField[] fields = schema.fields();
        for (StructField field : fields) {
            String name = field.name();
            String typeName = field.dataType().typeName();
            Option<String> comment = field.getComment();

            DataFrameSchemaVO dataFrameSchemaVO = new DataFrameSchemaVO();
            dataFrameSchemaVO.setName(name).setType(typeName).setComment("");

            dataFrameSchemaVOS.add(dataFrameSchemaVO);
        }


        Map<String, Object> ret = new HashMap<>();
        ret.put("schema", dataFrameSchemaVOS);
        ret.put("data", JSONArray.parseArray(open.limit(25).toJsonStr()));

        connect.close();

        return JsonUtils.toFormatJsonNoException(ReturnMapUtils.setSucceededCustomParam("data", ret));
    }

    @Override
    public String perform(HttpServletRequest request, String uri, String dataframeId, String sql) {

        String remoteAddr = "localhost";
        if (request != null) {
            remoteAddr = request.getRemoteAddr();
        }

        String serverIp = uri.split("/")[2];
        FairdConnection connect = FairdConnection.connect(serverIp, 3101, remoteAddr);

        DataFrame dataFrame = new DataFrame();
        dataFrame.setId(dataframeId);

        String retData = "";

        if ("SCHEMA".equalsIgnoreCase(sql.trim())) {
            retData = dataFrame.printSchema();
        }
        if ("LENGTH".equalsIgnoreCase(sql.trim())) {
            retData = "" + dataFrame.getLength();
        }
        if ("SAMPLE".equalsIgnoreCase(sql.trim())) {
            retData = dataFrame.limit(25).toJsonStr();
            retData = JsonUtils.formatJsonToTable(retData, 25);
        }

        if (sql.toLowerCase(Locale.ROOT).startsWith("python")) {
            return "开发中";
        }

        if (sql.toLowerCase(Locale.ROOT).startsWith("sql")) {

            String sqlRegex = "SQL\\s+(.*+)";
            Pattern pattern = Pattern.compile(sqlRegex, Pattern.CASE_INSENSITIVE);
            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql);

            if (matcher.find()) {
                String group = matcher.group(1);

                String realSql = group.replace("\"", "");

                retData = dataFrame.ask(realSql).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("limit")) {
            // 定义正则表达式模式
            String regex = "limit\\s+(\\d+)";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String group = matcher.group(1);
                retData = dataFrame.limit(Integer.valueOf(group.trim())).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("select") && !sql.toLowerCase(Locale.ROOT).contains("from")) {
            // 定义正则表达式模式
            String regex = "(?i)select\\s+(.*)";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));

            if (matcher.find()) {
                String group = matcher.group(1);
                String[] split = group.split(",");
                String[] splitNoSpace = new String[split.length];
                for (int i = 0; i < split.length; i++) {
                    splitNoSpace[i] = split[i].trim();
                }

                if (splitNoSpace.length > 1) {
                    String[] sub = new String[splitNoSpace.length - 1];
                    for (int i = 1; i < splitNoSpace.length; i++) {
                        sub[i - 1] = splitNoSpace[i];
                    }
                    retData = dataFrame.select(splitNoSpace[0], sub).limit(25).toJsonStr();
                } else {
                    retData = dataFrame.select(splitNoSpace[0]).limit(25).toJsonStr();
                }
                retData = JsonUtils.formatJsonToTable(retData, 25);

            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("join") && !sql.toLowerCase(Locale.ROOT).contains("select")) {
            // 定义正则表达式模式
            String regex = "join\\s+(.*+)";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String group = matcher.group(1).trim();

                retData = dataFrame.ask(group, UnionAskMethod.LEFT_JOIN).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }


        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("open") && !sql.toLowerCase(Locale.ROOT).contains("select")) {
            // 定义正则表达式模式
            String regex = "open\\s+(.*+)";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String group = matcher.group(1).trim();

                retData = dataFrame.open(Integer.valueOf(group)).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        connect.close();
        return JsonUtils.toFormatJsonNoException(ReturnMapUtils.setSucceededCustomParam("data", retData));
    }

    @Override
    public String performJoin(HttpServletRequest request, String uri, String sql) {

        String remoteAddr = "localhost";
        if (request != null) {
            remoteAddr = request.getRemoteAddr();
        }

        String serverIp = uri.split("/")[2];
        FairdConnection connect = FairdConnection.connect(serverIp, 3101, remoteAddr);

        String retData = "";


        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("inner_join")) {
            // 定义正则表达式模式
            String regex = "inner_join\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.INNER_JOIN).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }


        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("left_join")) {
            // 定义正则表达式模式
            String regex = "left_join\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.LEFT_JOIN).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("right_join")) {
            // 定义正则表达式模式
            String regex = "right_join\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.RIGHT_JOIN).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("full_join")) {
            // 定义正则表达式模式
            String regex = "full_join\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.FULL_JOIN).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("cross_join")) {
            // 定义正则表达式模式
            String regex = "cross_join\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.CROSS_JOIN).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }


        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("union_all")) {
            // 定义正则表达式模式
            String regex = "union_all\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.UNION_ALL).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("union") && !sql.toLowerCase(Locale.ROOT).trim().startsWith("union_all")) {
            // 定义正则表达式模式
            String regex = "union\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.UNION).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("intersect_all")) {
            // 定义正则表达式模式
            String regex = "intersect_all\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.INTERSECT_ALL).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }


        if (sql.toLowerCase(Locale.ROOT).trim().startsWith("intersect") && !sql.toLowerCase(Locale.ROOT).trim().startsWith("intersect_all")) {
            // 定义正则表达式模式
            String regex = "intersect\\s+([a-z_0-9]+)\\s+([a-z_0-9]+)\\s*";

            // 创建Pattern对象
            Pattern pattern = Pattern.compile(regex);

            // 创建Matcher对象
            Matcher matcher = pattern.matcher(sql.toLowerCase(Locale.ROOT));
            if (matcher.find()) {
                String left = matcher.group(1).trim();
                String right = matcher.group(2).trim();

                DataFrame leftDF = new DataFrame();
                leftDF.setId(left);

                DataFrame rightDF = new DataFrame();
                rightDF.setId(right);

                retData = leftDF.ask(right, UnionAskMethod.INTERSECT).limit(25).toJsonStr();
                retData = JsonUtils.formatJsonToTable(retData, 25);
            }
        }

        connect.close();

        return JsonUtils.toFormatJsonNoException(ReturnMapUtils.setSucceededCustomParam("data", retData));

    }
}
