package cn.cnic.faird.admin.vo;


import cn.cnic.faird.admin.enums.ResourceType;
import cn.cnic.protocol.model.DataFrame;
import cn.cnic.protocol.model.MetaData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author yaxuan
 * @create 2023/8/1 11:04
 */
@Getter
@Setter
public class Resource {

    private String id;
    private String uri;
    private String name;
    private MetaData resourceMetaData;
    //private FairData fairData;
    private List<CustomDataFrameVo> dataFrameVos;
    private List<DataFrame> dataFrames;
    private ResourceType resourceType;
}
