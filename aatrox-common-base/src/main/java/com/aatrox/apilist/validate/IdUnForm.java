package com.aatrox.apilist.validate;

import io.swagger.annotations.ApiModelProperty;

public class IdUnForm extends OperateForm {
    @ApiModelProperty("主键id，非必填")
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdUnForm() {
    }

    public IdUnForm(String id) {
        this.id = id;
    }
}
