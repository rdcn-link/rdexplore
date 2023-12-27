package cn.cnic.faird.admin.controller;

import cn.cnic.faird.admin.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
@Api("/资源")
public class ResourceController {
    @Resource
    private ResourceService resourceService;


    @ApiOperation(value = "获取元数据信息")
    @RequestMapping(value = "/getResourceByUri", method = RequestMethod.POST)
    @ResponseBody
    public String getResourceByUri(HttpServletRequest request, String uri) {

        return resourceService.getResourceByUri(request, uri);
    }

    @ApiOperation(value = "open")
    @GetMapping("/open")
    public String open(HttpServletRequest request, int firstIndex, int secondIndex, String uri, String parser) {
        return resourceService.open(request, firstIndex, secondIndex, uri, parser);
    }

    @ApiOperation(value = "getSampleDataAndSchema")
    @GetMapping("/getSampleDataAndSchema")
    public String getSampleDataAndSchema(HttpServletRequest request, String dataframeId, String uri) {
        return resourceService.getSampleDataAndSchema(request, dataframeId, uri);
    }

    @ApiOperation(value = "test")
    @GetMapping("/test")
    public String test(HttpServletRequest request,String uri, String dfId) {
        return resourceService.test(request,uri, dfId);
    }

    @ApiOperation(value = "执行计算")
    @PostMapping("/perform")
    public String perform(HttpServletRequest request, String uri,  String dataframeId, String sql) {
        return resourceService.perform(request, uri, dataframeId, sql);
    }

    @ApiOperation(value = "执行联合查询")
    @PostMapping("/performJoin")
    public String performJoin(HttpServletRequest request, String uri,  String sql) {
        return resourceService.performJoin(request, uri, sql);
    }

    @ApiOperation(value = "获取使用说明")
    @GetMapping("/getDesc")
    public String getDesc() {

        String desc = "# 提示：请使用faird协议方法进行ASK操作。示例：<br>" +
                "1.获取schema：SCHEMA<br>" +
                "2.获取数据长度：LENGTH<br>" +
                "3.获取数据样例：SAMPLE<br>" +
                "4.获取前n数据：LIMIT {numRows}<br>" +
                "5.获取某几列数据：SELECT {col1, col2...}<br>" +
                "6.执行SQL语句：SQL \"{sqlQuery}\"<br>" +
                "7.执行PYTHON语句：PYTHON \"{script}\"<br>" +
                "8.联合查询：JOIN {dataframeId} <br>" +
                "9.打开某一行：OPEN {index}<br>";
        return desc;

    }

    @ApiOperation(value = "获取使用说明2")
    @GetMapping("/getDesc2")
    public String getDesc2() {

        String desc = "# 提示：请使用faird协议方法对已打开的DataFrames进行联合查询ASK操作。示例：<br>" +
                "1.INNER_JOIN {dataframe_id1} {dataframe_id2}<br>" +
                "2.LEFT_JOIN {dataframe_id1} {dataframe_id2}<br>" +
                "3.RIGHT_JOIN {dataframe_id1} {dataframe_id2}<br>" +
                "4.FULL_JOIN {dataframe_id1} {dataframe_id2} <br>" +
                "5.CROSS_JOIN {dataframe_id1} {dataframe_id2}<br>" +
                "6.UNION {dataframe_id1} {dataframe_id2}<br>" +
                "7.UNION_ALL {dataframe_id1} {dataframe_id2}<br>" +
                "8.INTERSECT {dataframe_id1} {dataframe_id2}<br>" +
                "9.INTERSECT_ALL {dataframe_id1} {dataframe_id2}<br>";
        return desc;
    }
}
