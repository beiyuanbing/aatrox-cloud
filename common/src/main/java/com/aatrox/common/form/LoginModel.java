package com.aatrox.common.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-09
 */
public class LoginModel {
    @ApiModelProperty("主键")
    private String id;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("当前使用岗位上级组织id")
    private String orgId;
    @ApiModelProperty("当前使用岗位上级组织name")
    private String orgName;
    @ApiModelProperty("当前使用岗位上级组织长编码")
    private String orgLongNumber;
    @ApiModelProperty("公司Id")
    private String companyId;
    @ApiModelProperty("城市公司Id")
    private String cuid;

    public String getId() {
        return this.id;
    }

    public LoginModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LoginModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getOrgId() {
        return orgId;
    }

    public LoginModel setOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public LoginModel setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getOrgLongNumber() {
        return orgLongNumber;
    }

    public LoginModel setOrgLongNumber(String orgLongNumber) {
        this.orgLongNumber = orgLongNumber;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public LoginModel setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCuid() {
        return cuid;
    }

    public LoginModel setCuid(String cuid) {
        this.cuid = cuid;
        return this;
    }
}
