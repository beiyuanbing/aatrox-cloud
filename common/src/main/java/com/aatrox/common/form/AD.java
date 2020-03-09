package com.aatrox.common.form;


import java.util.Date;

/**
 * 广告实体Bean
 */
public class AD {

    private static final long serialVersionUID = 1L;
    /**
     * 投放城市Ids（支持多个城市，用逗号分割）
     */
    private String cityId;

    /**
     * 投放城市名称（支持多个城市，用逗号分割）
     */
    private String cityName;

    /**
     * 广告创建人
     */
    private String creater;

    /**
     * 广告名称
     */
    private String name;

    /**
     * 广告地址 图片地址
     */
    private String picUrl;

    /**
     * 广告地址 图片FDFS地址
     **/
    private String picFdfsUrl;

    /**
     * 广告有效期开始时间
     */
    private Date startTime;

    /**
     * 广告有效期结束时间
     */
    private Date endTime;


    private Integer isGarden;
    private Integer terminal;

    /**
     * 广告楼盘拓展ID
     */
    private Integer expandId;

    /**
     * 广告跳转楼盘Id
     */
    private Integer gardenId;

    /**
     * 广告跳转楼盘URL
     */
    private String gardenName;

    private String webUrl;
    private String appUrl;

    /**
     * 广告权重
     */
    private int order;

    /**
     * 广告创建时间
     */
    private Date createTime;

    /**
     * 广告更新时间
     */
    private Date updateTime;

    public Integer getExpandId() {
        return expandId;
    }

    public void setExpandId(Integer expandId) {
        this.expandId = expandId;
    }

    public String getCityId() {
        return cityId;
    }

    public AD setCityId(String cityId) {
        this.cityId = cityId;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public AD setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicFdfsUrl() {
        return picFdfsUrl;
    }

    public void setPicFdfsUrl(String picFdfsUrl) {
        this.picFdfsUrl = picFdfsUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsGarden() {
        return isGarden;
    }

    public void setIsGarden(Integer isGarden) {
        this.isGarden = isGarden;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Integer getGardenId() {
        return gardenId;
    }

    public void setGardenId(Integer gardenId) {
        this.gardenId = gardenId;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AD{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", creater='" + creater + '\'' +
                ", name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", picFdfsUrl='" + picFdfsUrl + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isGarden=" + isGarden +
                ", terminal=" + terminal +
                ", expandId=" + expandId +
                ", gardenId=" + gardenId +
                ", gardenName='" + gardenName + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", order=" + order +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
