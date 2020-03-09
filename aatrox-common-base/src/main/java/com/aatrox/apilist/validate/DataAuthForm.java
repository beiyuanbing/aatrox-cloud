package com.aatrox.apilist.validate;

import com.aatrox.apilist.form.ValidationForm;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DataAuthForm implements ValidationForm {
    @ApiModelProperty(
            value = "是否为当前组织管理层",
            hidden = true
    )
    private boolean orgManager;
    @ApiModelProperty(
            value = "指定数据类型可用的数据组织长编码",
            hidden = true
    )
    private List<String> datas;
    @ApiModelProperty(
            value = "数据权限：当前人员组织长编码",
            hidden = true
    )
    private String personOrgLongNumber;
    @ApiModelProperty(
            value = "数据权限：当前人员ID",
            hidden = true
    )
    private String personId;
    @ApiModelProperty(
            value = "数据权限：当前组织分公司ID",
            hidden = true
    )
    private String cuid;
    @ApiModelProperty(
            value = "数据权限：拥有人字段名",
            hidden = true
    )
    private String managerColumnName = "fcreate_operator_id";
    @ApiModelProperty(
            value = "数据权限：拥有组织字段名",
            hidden = true
    )
    private String orgColumnName = "forg_id";
    @ApiModelProperty(
            value = "数据权限：erp所在库名",
            hidden = true
    )
    private String erpDbName;
    @ApiModelProperty(
            value = "数据权限：erp组织表名",
            hidden = true
    )
    private String erpOrgTableName = "t_erp_org";
    @ApiModelProperty(
            value = "数据权限：数据记录别称",
            hidden = true
    )
    private String dataAuthRecord = "data_auth_record";
    @ApiModelProperty(
            value = "数据权限：人员关联的权限",
            hidden = true
    )
    private boolean personData = true;

    public DataAuthForm() {
    }

    public boolean isOrgManager() {
        return this.orgManager;
    }

    public void setOrgManager(boolean orgManager) {
        this.orgManager = orgManager;
    }

    public List<String> getDatas() {
        return this.datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public String getPersonOrgLongNumber() {
        return this.personOrgLongNumber;
    }

    public void setPersonOrgLongNumber(String personOrgLongNumber) {
        this.personOrgLongNumber = personOrgLongNumber;
    }

    public String getPersonId() {
        return this.personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getManagerColumnName() {
        return this.managerColumnName;
    }

    public void renameManagerColumn(String managerColumnName) {
        this.managerColumnName = managerColumnName;
    }

    public String getOrgColumnName() {
        return this.orgColumnName;
    }

    public void renameOrgColumnName(String orgColumnName) {
        this.orgColumnName = orgColumnName;
    }

    public String getErpDbName() {
        return this.erpDbName;
    }

    public void setErpDbName(String erpDbName) {
        this.erpDbName = erpDbName;
    }

    public void setManagerColumnName(String managerColumnName) {
        this.managerColumnName = managerColumnName;
    }

    public void setOrgColumnName(String orgColumnName) {
        this.orgColumnName = orgColumnName;
    }

    public String getErpOrgTableName() {
        return this.erpOrgTableName;
    }

    public void setErpOrgTableName(String erpOrgTableName) {
        this.erpOrgTableName = erpOrgTableName;
    }

    public String getDataAuthRecord() {
        return this.dataAuthRecord;
    }

    public void setDataAuthRecord(String dataAuthRecord) {
        this.dataAuthRecord = dataAuthRecord;
    }

    public boolean isPersonData() {
        return this.personData;
    }

    public void setPersonData(boolean personData) {
        this.personData = personData;
    }

    public String getCuid() {
        return this.cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }
}

