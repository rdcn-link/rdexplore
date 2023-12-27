package cn.cnic.faird.admin.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomDataFrameVo {
    private String dataframeId;
    private String refUri;
    private JSONArray jsonData;
}
