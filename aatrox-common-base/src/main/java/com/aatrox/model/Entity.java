package com.aatrox.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class Entity<T> implements Serializable, Comparable<Entity<T>> {
    private static final long serialVersionUID = 1L;
    private T id;
    @ApiModelProperty(
            hidden = true
    )
    private Date createTime;
    @ApiModelProperty(
            hidden = true
    )
    private Date updateTime;
    @ApiModelProperty(
            hidden = true
    )
    private String createOperatorId;
    @ApiModelProperty(
            hidden = true
    )
    private String updateOperatorId;

    public Entity() {
    }

    public T getId() {
        return this.id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public int compareTo(Entity<T> o) {
        return 0;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setCreateOperatorId(String createOperatorId) {
        this.createOperatorId = createOperatorId;
    }

    public String getCreateOperatorId() {
        return this.createOperatorId;
    }

    public void setUpdateOperatorId(String updateOperatorId) {
        this.updateOperatorId = updateOperatorId;
    }

    public String getUpdateOperatorId() {
        return this.updateOperatorId;
    }

    public void addOperatorInfo(String operatorId, Boolean update) {
        Date now = new Date();
        if (update == null) {
            this.updateTime = now;
            this.updateOperatorId = operatorId;
            this.createOperatorId = operatorId;
            this.createTime = now;
        } else if (update) {
            this.updateTime = now;
            this.updateOperatorId = operatorId;
        } else {
            this.createOperatorId = operatorId;
            this.createTime = now;
        }

    }

    public boolean idEqual(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        } else {
            Entity objP = (Entity) obj;
            return this.getId() != null && objP.getId() != null ? this.getId().equals(objP.getId()) : false;
        }
    }
}

