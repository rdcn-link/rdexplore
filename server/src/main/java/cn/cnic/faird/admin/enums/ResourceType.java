package cn.cnic.faird.admin.enums;

import cn.cnic.base.vo.TextureEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author yaxuan
 * @create 2023/8/1 11:14
 */
@JsonSerialize(using = TextureEnumSerializer.class)
public enum ResourceType {
    DATA("DATA","DATA"),
    OPERATOR("OPERATOR", "OPERATOR");

    private final String value;
    private final String text;

    private ResourceType(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public static ResourceType selectGender(String name) {
        for (ResourceType type : ResourceType.values()) {
            if (name.equalsIgnoreCase(type.name())) {
                return type;
            }
        }
        return null;
    }
}
