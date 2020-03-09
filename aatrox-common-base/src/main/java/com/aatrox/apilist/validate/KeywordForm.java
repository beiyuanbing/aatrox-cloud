package com.aatrox.apilist.validate;

import io.swagger.annotations.ApiModelProperty;

public class KeywordForm extends PageForm {
    @ApiModelProperty("keyword关键字")
    private String keyword;

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public KeywordForm(String keyword) {
        this.keyword = keyword;
    }

    public KeywordForm() {
    }
}
