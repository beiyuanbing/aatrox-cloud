package com.aatrox.generator.bean;

import com.aatrox.generator.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc 自定义模板的内容
 * @date 2019/8/28
 */
public class DefineTemplateConfig {
    private DefineBean model;

    private DefineBean dao;

    private DefineBean xml;

    private DefineBean service;

    private DefineBean serviceImpl;

    private DefineBean controller;

    private DefineBean remote;

    private DefineBean fegin;

    private DefineBean api;

    private String entityName;

    public DefineBean getModel() {
        return model;
    }

    public DefineTemplateConfig setModel(DefineBean model) {
        this.model = model;
        return this;
    }

    public DefineBean getDao() {
        return dao;
    }

    public DefineTemplateConfig setDao(DefineBean dao) {
        this.dao = dao;
        return this;
    }

    public DefineBean getXml() {
        return xml;
    }

    public DefineTemplateConfig setXml(DefineBean xml) {
        this.xml = xml;
        return this;
    }

    public DefineBean getService() {
        return service;
    }

    public DefineTemplateConfig setService(DefineBean service) {
        this.service = service;
        return this;
    }

    public DefineBean getServiceImpl() {
        return serviceImpl;
    }

    public DefineTemplateConfig setServiceImpl(DefineBean serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }

    public DefineBean getController() {
        return controller;
    }

    public DefineTemplateConfig setController(DefineBean controller) {
        this.controller = controller;
        return this;
    }

    public DefineBean getRemote() {
        return remote;
    }

    public DefineTemplateConfig setRemote(DefineBean remote) {
        this.remote = remote;
        return this;
    }

    public DefineBean getFegin() {
        return fegin;
    }

    public DefineTemplateConfig setFegin(DefineBean fegin) {
        this.fegin = fegin;
        return this;
    }

    public DefineBean getApi() {
        return api;
    }

    public DefineTemplateConfig setApi(DefineBean api) {
        this.api = api;
        return this;
    }
    public String getEntityName() {
        return entityName;
    }

    public DefineTemplateConfig setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public Map<String, Object> getTemplateMap(){
        Map<String, Object> map = new HashMap<>();
        List<Field> declaredFields = ReflectionUtils.getDeclaredFields(this);
        declaredFields.stream()
        .filter(field->DefineBean.class.equals(field.getType()))
        .forEach(field -> {
            try {
                Object value = field.get(this);
                if(value==null){
                    return;
                }
                String key = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                map.put(key,value);
                //ReflectionUtils.setFieldValue(templateConfig,field.getName(),null,true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        /*if(model!=null){
            templateConfig.setEntity(null).setEntityKt(null);
        }
        if(dao!=null){
            templateConfig.setMapper(null);
        }*/
        return map;
    }

    public List<DefineBean> getDefineBeanList(){
        List<DefineBean> defineBeanList=new ArrayList<>();
        List<Field> declaredFields = ReflectionUtils.getDeclaredFields(this);
        declaredFields.stream()
                .filter(field->DefineBean.class.equals(field.getType()))
                .forEach(field -> {
                    try {
                        DefineBean value = (DefineBean)field.get(this);
                    if(value==null){
                        return;
                    }
                    value.setBeanType(field.getName());
                    defineBeanList.add((DefineBean)value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                });
        return defineBeanList;
    }
}
