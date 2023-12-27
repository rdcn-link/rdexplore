package cn.cnic.faird.admin.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yaxuan
 * @create 2023/8/1 10:57
 */
public interface ResourceService {


    String getResourceByUri(HttpServletRequest request,String uri);

    String open(HttpServletRequest request, int firstIndex, int secondIndex, String uri, String parser);

    String getSampleDataAndSchema(HttpServletRequest request, String dataframeId, String uri);

    String test(HttpServletRequest request, String uri, String dfId);

    String perform(HttpServletRequest request, String uri, String dataframeId, String sql);

    String performJoin(HttpServletRequest request, String uri,String sql);

}
