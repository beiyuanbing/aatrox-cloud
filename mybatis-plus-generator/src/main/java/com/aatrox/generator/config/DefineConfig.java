package com.aatrox.generator.config;

/**
 * @author aatrox
 * @desc
 * @date 2019/8/28
 */
public class DefineConfig {

    /**
     * 是否开启mybaitsplus的注解
     */
    private boolean openMybaitsPlusAnnotion = true;

    /**是否使用mybaits plus**/
    private boolean openMybatisPlus=false;
    /**使用spring cloud**/
    private boolean springCloud=false;
    /*****使用form的模式***/
    private boolean openForm=false;
    public boolean isOpenMybaitsPlusAnnotion() {
        return openMybaitsPlusAnnotion;
    }

    public DefineConfig setOpenMybaitsPlusAnnotion(boolean openMybaitsPlusAnnotion) {
        this.openMybaitsPlusAnnotion = openMybaitsPlusAnnotion;
        return this;
    }

    public boolean isOpenMybatisPlus() {
        return openMybatisPlus;
    }

    public DefineConfig setOpenMybatisPlus(boolean openMybatisPlus) {
        this.openMybatisPlus = openMybatisPlus;
        return this;
    }

    public boolean isSpringCloud() {
        return springCloud;
    }

    public DefineConfig setSpringCloud(boolean springCloud) {
        this.springCloud = springCloud;
        return this;
    }

    public boolean isOpenForm() {
        return openForm;
    }

    public DefineConfig setOpenForm(boolean openForm) {
        this.openForm = openForm;
        return this;
    }
}
