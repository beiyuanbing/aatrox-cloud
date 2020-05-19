package com.aatrox.common.utils;


import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author aatrox
 * @desc 多字段的一一对应的填充工具
 * @date 2020/5/11
 */
public class ObjectFillActor {
    private List<String> sourceFieldList = null;
    private List<String> targetFieldList = null;

    private boolean checkFillParams(Object sourceObject, Object targetObject) {
        if (sourceObject == null || targetObject == null || ListUtil.isEmpty(sourceFieldList)
                || ListUtil.isEmpty(targetFieldList) || sourceFieldList.size() != targetFieldList.size()) {
            return false;
        }
        return true;
    }

    /**
     * @param targetObject 需要封装 OperateForm对象，或者子OperateForm对象
     * @param sourceObject 操作人
     */
    public void fillForm(Object sourceObject, Object targetObject) {
        if (!checkFillParams(sourceObject, targetObject)) {
            return;
        }
        fillForm(sourceObject, targetObject, true);
    }

    /**
     * @param targetObject 需要封装 OperateForm对象，或者子OperateForm对象
     * @param sourceObject 操作人
     * @param overwrite    是否覆盖
     */
    public void fillForm(Object sourceObject, Object targetObject, boolean overwrite) {
        if (!checkFillParams(sourceObject, targetObject)) {
            return;
        }
        fillFormPropery(sourceObject, targetObject, overwrite);
        Arrays.stream(targetObject.getClass()
                .getDeclaredFields())
                .filter(Objects::nonNull)
                .forEach(field -> {
                            field.setAccessible(true);
                            try {
                                Type genericType = field.getGenericType();
                                //ParameterizedType 表示参数化的类型，比如Collection
                                if (genericType instanceof ParameterizedType) {
                                    if (((ParameterizedType) genericType).getRawType().getTypeName().equals(List.class.getTypeName())) {
                                        iterator(field.get(targetObject), sourceObject);
                                    }
                                } else if (genericType instanceof TypeVariable) {
                                    //TypeVariable: 是各种类型变量的公共父接口
                                } else {
                                    Class clz = Class.forName(genericType.getTypeName());
                                    //if (isExtends(clz, OperateForm.class)) {
                                    fillFormPropery(sourceObject, field.get(targetObject), overwrite);
                                    // }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                );
    }


    /**
     * 填充信息
     *
     * @param targetObject
     * @param sourceObject
     */
    private void fillFormPropery(Object sourceObject, Object targetObject) {
        if (!checkFillParams(sourceObject, targetObject)) {
            return;
        }
        fillFormPropery(sourceObject, targetObject, true);
    }


    /**
     * 填充信息
     *
     * @param targetObject
     * @param sourceObject
     */
    private void fillFormPropery(Object sourceObject, Object targetObject, boolean overwrite) {
        if (!checkFillParams(sourceObject, targetObject)) {
            return;
        }
        try {
            /**基础类型的对象不做操作复制操作，减少遍历次数***/
            if (targetObject == null || sourceObject == null || ReflectionUtils.isBaseType(targetObject)) {
                return;
            }
            List<Object> valueList = getValueList(sourceObject);
            ReflectionUtils.setFieldValues(targetObject, targetFieldList, valueList, overwrite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Object> getValueList(Object sourceObject) {
        List<Object> valueList = new ArrayList<>();
        sourceFieldList.forEach(fieldName -> {
            valueList.add(ReflectionUtils.getFieldValue(sourceObject, fieldName));
        });
        return valueList;
    }


    /**
     * 循环遍历
     *
     * @param targetObject
     * @param sourceObject
     * @throws Exception
     */

    private void iterator(Object sourceObject, Object targetObject) throws Exception {
        if (!checkFillParams(sourceObject, targetObject)) {
            return;
        }
        Method m = targetObject.getClass().getDeclaredMethod("size");
        int size = (Integer) m.invoke(targetObject);
        for (int i = 0; i < size; i++) {
            fillFormPropery(sourceObject, ((List) targetObject).get(i));
        }
    }

    public List<String> getSourceFieldList() {
        return sourceFieldList;
    }

    public ObjectFillActor setSourceFieldList(List<String> sourceFieldList) {
        this.sourceFieldList = sourceFieldList;
        return this;
    }

    public List<String> getTargetFieldList() {
        return targetFieldList;
    }

    public ObjectFillActor setTargetFieldList(List<String> targetFieldList) {
        this.targetFieldList = targetFieldList;
        return this;
    }

    public static void main(String[] args) throws Exception {
        //获取参数类

/*        ContractSenseInsertForm insertForm = new ContractSenseInsertForm().setContacterForm(new ContacterInsertForm
().setEntityType(ContacterTypeEnum.PLAN)).setCommissionList(new ArrayList<>(Arrays.asList(new
ExpandCommissionSchemeInsertForm().setCommissionCashType(CommissionCashType.CommissionIn), new
ExpandCommissionSchemeInsertForm().setCommissionCashType(CommissionCashType.CommissionIn))));
        //Object object = ReflectionUtils.toObjectAddEnumStr(insertForm);
        //System.out.println(object);
        System.out.println(EnumStrUtils.toObjectEnumStrJson(insertForm, true));*/
        /*if(insertForm instanceof OperateForm){
            System.out.println("yes");
        }*/
    }

}
