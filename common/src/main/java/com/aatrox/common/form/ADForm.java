package com.aatrox.common.form;


import com.aatrox.common.enums.StausEnum;

import java.util.List;

/**
 * @Author chenjc
 * @Date 2019-03-03 10:09
 */
public class ADForm {
    private List<AD> adList;

    private Integer adId;

    /**
     * 状态
     */
    private StausEnum status;

    public List<AD> getAdList() {
        return adList;
    }

    public ADForm setAdList(List<AD> adList) {
        this.adList = adList;
        return this;
    }


    public Integer getAdId() {
        return adId;
    }

    public ADForm setAdId(Integer adId) {
        this.adId = adId;
        return this;
    }

    public StausEnum getStatus() {
        return status;
    }

    public ADForm setStatus(StausEnum status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "ADForm{" +
                "adList=" + adList +
                ", adId=" + adId +
                ", status=" + status +
                '}';
    }
}
