package com.aatrox.apilist.validate;

import com.aatrox.apilist.form.ValidationForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class OperateForm implements ValidationForm {
    @ApiModelProperty(
            value = "操作人ID",
            hidden = true
    )
    private String operatorId;
    @ApiModelProperty(
            value = "操作人名",
            hidden = true
    )
    private String operatorName;
    @ApiModelProperty(
            value = "操作人组织ID",
            hidden = true
    )
    private String operatorOrgId;
    @ApiModelProperty(
            value = "更新时间",
            hidden = true
    )
    private Date operatorTime;
    @ApiModelProperty(
            value = "操作人公司关联组织ID",
            hidden = true
    )
    private String operatorCompanyId;
    @ApiModelProperty(
            value = "操作人城市公司ID",
            hidden = true
    )
    private String operatorCuid;

    public OperateForm() {
    }

    public String getOperatorCompanyId() {
        return this.operatorCompanyId;
    }

    public void setOperatorCompanyId(String operatorCompanyId) {
        this.operatorCompanyId = operatorCompanyId;
    }

    public String getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorOrgId() {
        return this.operatorOrgId;
    }

    public void setOperatorOrgId(String operatorOrgId) {
        this.operatorOrgId = operatorOrgId;
    }

    public Date getOperatorTime() {
        return this.operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCuid() {
        return this.operatorCuid;
    }

    public void setOperatorCuid(String operatorCuid) {
        this.operatorCuid = operatorCuid;
    }
}
