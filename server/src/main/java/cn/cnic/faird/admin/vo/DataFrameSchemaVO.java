package cn.cnic.faird.admin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DataFrameSchemaVO {
    private String name;
    private String type;
    private String comment;
}
