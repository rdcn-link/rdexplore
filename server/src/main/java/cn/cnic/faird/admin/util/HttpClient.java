package cn.cnic.faird.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.List;
import java.util.Map;


@Slf4j
public class HttpClient {
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    /**
     * GET---有参 (将参数放入键值对类中,再放入URI中,从而通过URI得到HttpGet实例)
     *
     * @date 2020年8月17日 下午4:19:23
     */
    public static String doGetWayTwo(List<NameValuePair> params, String url, Map<String, String> header) {
        // 参数
        URI uri = null;
        try {
            url = url.trim();
            int i = url.indexOf(":");
            String scheme = url.substring(0, i);
            String substring = url.substring(i + 3);
            int i1 = substring.indexOf("/");
            String host = substring.substring(0, i1);
            String path = substring.substring(i1);
            uri = new URIBuilder().setScheme(scheme).setHost(host).setPath(path).setParameters(params).build();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        // 创建Get请求
        HttpGet httpGet = new HttpGet(uri);
        if (header != null && header.size() > 0) {
            for (String key : header.keySet()) {
                httpGet.setHeader(key, header.get(key));
            }
        }
        return doGetResult(httpGet);
    }


    public static String doGet(String url, Map<String, String> header) {
        HttpGet httpGet = new HttpGet(url.trim());
        if (header != null && header.size() > 0) {
            for (String key : header.keySet()) {
                httpGet.setHeader(key, header.get(key));
            }
        }
        return doGetResult(httpGet);
    }


    public static String doGetWayTwo(String url) throws MalformedURLException, URISyntaxException {
        // 创建Get请求
        URL contr = new URL(url.trim());
        URI uri = new URI(contr.getProtocol(), contr.getAuthority(), contr.getPath(), contr.getQuery(), null);

        HttpGet httpGet = new HttpGet(uri);
        return doGetResult(httpGet);
    }

    public static String doGetWayTwo(List<NameValuePair> params, String url) {
        URI uri = null;
        try {
            url = url.trim();
            int i = url.indexOf(":");
            String scheme = url.substring(0, i);
            String substring = url.substring(i + 3);
            int i1 = substring.indexOf("/");
            String host = substring.substring(0, i1);
            String path = substring.substring(i1);
            uri = new URIBuilder().setScheme(scheme).setHost(host).setPath(path).setParameters(params).build();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        HttpGet httpGet = new HttpGet(uri);
        return doGetResult(httpGet);
    }


    /**
     * 传 cookie
     *
     * @param url
     * @param cookie
     * @return
     */
    public static String doGetCookie(String url, String cookie) {
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url.trim());
        httpGet.setHeader("Cookie", cookie);
        return doGetResult(httpGet);
    }

    public static String doGetHeader(String url, String header) {
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url.trim());
        httpGet.setHeader("secretKey", header);
        return doGetResult(httpGet);
    }

    public static int doGetRetStatus(String url) {
        HttpGet httpGet = new HttpGet(url);

        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(2 * 1000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(2 * 1000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(2 * 1000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            return response.getStatusLine().getStatusCode();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    private static String doGetResult(HttpGet httpGet) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String result = "";
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(120000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(120000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(120000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            // 将上面的配置信息 运用到这个Get请求里
            httpGet.setConfig(requestConfig);

            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * post 表单传值
     *
     * @param params
     * @return
     */
    public static String doPostJsonWayTwo(List<NameValuePair> params, String url) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url.trim());
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return doPostSend(httpPost);
    }

    /**
     * post 表单传值 + 认真请求头
     *
     * @param params
     * @return
     */
    public static String doPostJsonWayTwo(List<NameValuePair> params, String url, String token) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url.trim());
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Authorization", token);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return doPostSend(httpPost);
    }


    /**
     * post 请求  传递JSON
     *
     * @param param
     * @return
     */
    public static String doPostJsonWayTwo(String param, String url) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url.trim());
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity se = new StringEntity(param, "UTF-8");
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        httpPost.setEntity(se);
        return doPostSend(httpPost);
    }

    /**
     * post 请求  传递JSON + 认证请求头
     *
     * @param param
     * @return
     */
    public static String doPostJsonAuth(String param, String url, String token) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url.trim());
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Authorization", token);
        StringEntity se = new StringEntity(param, "UTF-8");
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        httpPost.setEntity(se);
        return doPostSend(httpPost);
    }

    public static String doPostHeader(List<NameValuePair> params, String url, Map<String, String> header) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url.trim());
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        if (header != null && header.size() > 0) {
            for (String key : header.keySet()) {
                httpPost.setHeader(key, header.get(key));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return doPostSend(httpPost);
    }

    public static String doPostHeader(String param, String url, Map<String, String> header) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url.trim());
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        if (header != null && header.size() > 0) {
            for (String key : header.keySet()) {
                httpPost.setHeader(key, header.get(key));
            }
        }
        StringEntity se = new StringEntity(param, "UTF-8");
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        httpPost.setEntity(se);
        return doPostSend(httpPost);
    }


    private static String doPostSend(HttpPost httpPost) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String result = "";
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(150000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(150000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(150000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();
            // 将上面的配置信息 运用到这个Post请求里
            httpPost.setConfig(requestConfig);
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 科技云请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
     * @return 所代表远程资源的响应结果
     */
    public String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url.trim());
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
