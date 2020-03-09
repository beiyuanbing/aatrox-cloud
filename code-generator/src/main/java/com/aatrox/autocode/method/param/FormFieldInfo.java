package com.aatrox.autocode.method.param;

public class FormFieldInfo {
    private String fieldName;
    private String fieldType;
    private String desc;
    private boolean canNull;

    public FormFieldInfo(String fieldName, String fieldType, String desc, boolean canNull) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.desc = desc;
        this.canNull = canNull;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getDesc() {
        return this.desc;
    }

    public boolean isCanNull() {
        return this.canNull;
    }

    public String getFieldType() {
        return this.fieldType;
    }
}
