package com.aatrox.autocode.method.param;

import java.util.ArrayList;
import java.util.List;

public class FormInfo {
    private String formName;
    private boolean isCustom;
    private String extendsForm;
    private List<FormFieldInfo> fieldList = new ArrayList();

    public FormInfo(String formName, boolean isCustom) {
        this.formName = formName;
        this.isCustom = isCustom;
    }

    public String getFormName() {
        return this.formName;
    }

    public String getExtendsForm() {
        return this.extendsForm;
    }

    public void setExtendsForm(String extendsForm) {
        this.extendsForm = extendsForm;
    }

    public List<FormFieldInfo> getFieldList() {
        return this.fieldList;
    }

    public void addField(FormFieldInfo fieldInfo) {
        this.fieldList.add(fieldInfo);
    }

    public boolean isCustom() {
        return this.isCustom;
    }
}
