package com.aatrox.common.utils.objectdiff;

import com.aatrox.common.utils.objectdiff.actor.DiffActorInterface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入口对象：
 * Created by yoara on 2018/1/4.
 */
public class DiffParam {
    /**
     * 当前对象
     */
    private Object entity;

    /**
     * 历史对象
     */
    private Object historyEntity;

    /**
     * 包含对象字段
     */
    private List<String> includeField;
    /**
     * 不包含对象字段
     */
    private List<String> excludeField;
    /**
     * diff实现类，null则使用默认
     */
    private DiffActorInterface actor;

    /**
     * 获取可用的历史class反射对象的参数集合
     *
     * @return
     */
    public List<Field> pickHistoryEntryFieldList() {
        Field[] fields = historyEntity.getClass().getDeclaredFields();
        List<Field> list = new ArrayList<>();
        for (Field field : fields) {
            //如果参数include不为空，则只判断这些字段
            if (includeField != null) {
                if (includeField.contains(field.getName())) {
                    field.setAccessible(true);
                    list.add(field);
                }
            } else {
                //如果参数exclude不为空，则排除这些字段
                if (excludeField != null) {
                    if (!excludeField.contains(field.getName())) {
                        field.setAccessible(true);
                        list.add(field);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 通过将反射集合的参数形成map
     *
     * @return
     */
    public Map<String, Field> pickEntryFieldMap(List<Field> diffFieldList) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Map<String, Field> map = new HashMap<>();
        for (Field field : diffFieldList) {
            for (Field fh : fields) {
                if (field.getName().equals(fh.getName())) {
                    fh.setAccessible(true);
                    map.put(fh.getName(), fh);
                    break;
                }
            }
        }
        return map;
    }

    public Object getEntity() {
        return entity;
    }

    public DiffParam setEntity(Object entity) {
        this.entity = entity;
        return this;
    }


    public Object getHistoryEntity() {
        return historyEntity;
    }

    public DiffParam setHistoryEntity(Object historyEntity) {
        this.historyEntity = historyEntity;
        return this;
    }

    public List<String> getIncludeField() {
        return includeField;
    }

    public DiffParam setIncludeField(List<String> includeField) {
        this.includeField = includeField;
        return this;
    }

    public List<String> getExcludeField() {
        return excludeField;
    }

    public DiffParam setExcludeField(List<String> excludeField) {
        this.excludeField = excludeField;
        return this;
    }

    public DiffActorInterface getActor() {
        return actor;
    }

    public DiffParam setActor(DiffActorInterface actor) {
        this.actor = actor;
        return this;
    }
}
