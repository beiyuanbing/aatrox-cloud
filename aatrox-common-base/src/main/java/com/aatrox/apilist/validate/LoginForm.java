package com.aatrox.apilist.validate;

import com.aatrox.apilist.form.ValidationForm;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class LoginForm implements ValidationForm {
    @ApiModelProperty(
            value = "用户名",
            required = true,
            example = "user"
    )
    @NotEmpty(
            message = "用户名不得为空"
    )
    private String userName;
    @ApiModelProperty(
            value = "密码",
            example = "password"
    )
    private String password;

    public LoginForm() {
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

