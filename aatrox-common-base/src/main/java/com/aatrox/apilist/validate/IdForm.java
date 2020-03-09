package com.aatrox.apilist.validate;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class IdForm extends OperateForm {
    @ApiModelProperty(
            value = "主键id",
            required = true
    )
    @NotEmpty(
            message = "ID不得为空"
    )
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdForm() {
    }

    public IdForm(String id) {
        this.id = id;
    }
}
