package com.aatrox.apilist.validate;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class OperateActionForm extends IdForm {
    @ApiModelProperty(
            value = "操作指令",
            required = true,
            example = "add"
    )
    @NotEmpty(
            message = "操作指令不得为空"
    )
    private String operateAction;

    public OperateActionForm() {
    }

    public String getOperateAction() {
        return this.operateAction;
    }

    public void setOperateAction(String operateAction) {
        this.operateAction = operateAction;
    }
}
