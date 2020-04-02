package com.aatrox.common.utils;

/**
 * @author aatrox
 * @desc 表单填充工具
 * @date 2019-08-09
 */

import com.aatrox.common.form.LoginModel;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * form表单工具
 *
 * @author ch
 * @create 2019-04-15 11:38
 */
public class FormUtil {
    /**
     * @param object        需要封装 OperateForm对象，或者子OperateForm对象
     * @param currentPerson 操作人
     */
    public static void fillForm(Object object, LoginModel currentPerson) {
        fillForm(object, currentPerson, true);
    }

    /**
     * @param object        需要封装 OperateForm对象，或者子OperateForm对象
     * @param currentPerson 操作人
     * @param overwrite     是否覆盖
     */
    public static void fillForm(Object object, LoginModel currentPerson, boolean overwrite) {
        if (object == null) {
            return;
        }
        fillFormPropery(object, currentPerson, overwrite);
        Arrays.stream(object.getClass()
                .getDeclaredFields())
                .filter(Objects::nonNull)
                .forEach(field -> {
                            field.setAccessible(true);
                            try {
                                Type genericType = field.getGenericType();
                                //ParameterizedType 表示参数化的类型，比如Collection
                                if (genericType instanceof ParameterizedType) {
                                    if (((ParameterizedType) genericType).getRawType().getTypeName().equals(List.class.getTypeName())) {
                                        iterator(field.get(object), currentPerson);
                                    }
                                } else if (genericType instanceof TypeVariable) {
                                    //TypeVariable: 是各种类型变量的公共父接口
                                } else {
                                    Class clz = Class.forName(genericType.getTypeName());
                                    //if (isExtends(clz, OperateForm.class)) {
                                    fillFormPropery(field.get(object), currentPerson, overwrite);
                                    // }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                );
    }

    private static List<String> fieldNameList = Arrays.asList("operatorId", "operatorName", "operatorOrgId", "operatorCompanyId", "companyId", "cuid", "operatorCuid");

    /**
     * 填充当前登录人的信息
     *
     * @param obj
     * @param currentPerson
     */
    private static void fillFormPropery(Object obj, LoginModel currentPerson) {
        fillFormPropery(obj, currentPerson, true);
    }


    /**
     * 填充当前登录人的信息
     *
     * @param obj
     * @param currentPerson
     */
    private static void fillFormPropery(Object obj, LoginModel currentPerson, boolean overwrite) {
        try {
            /**基础类型的对象不做操作复制操作，减少遍历次数***/
            if (obj == null || currentPerson == null || ReflectionUtils.isBaseType(obj)) {
                return;
            }
            List<Object> valueList = Arrays.asList(currentPerson.getId(), currentPerson.getName(), currentPerson.getOrgId(), currentPerson.getCompanyId(),
                    currentPerson.getCompanyId(), currentPerson.getCuid(), currentPerson.getCuid());
            ReflectionUtils.setFieldValues(obj, fieldNameList, valueList, overwrite);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 循环遍历
     *
     * @param object
     * @param currentPerson
     * @throws Exception
     */

    private static void iterator(Object object, LoginModel currentPerson) throws Exception {
        if (object == null) {
            return;
        }
        Method m = object.getClass().getDeclaredMethod("size");
        int size = (Integer) m.invoke(object);
        for (int i = 0; i < size; i++) {
            //可能list包含了多个对象，因为加上这个进行递归调用
            fillForm(((List) object).get(i), currentPerson);
            //fillFormPropery(((List) object).get(i), currentPerson);
        }
    }


    public static void main(String[] args) throws Exception {
        //获取参数类

/*        ContractSenseInsertForm insertForm = new ContractSenseInsertForm().setContacterForm(new ContacterInsertForm().setEntityType(ContacterTypeEnum.PLAN)).setCommissionList(new ArrayList<>(Arrays.asList(new ExpandCommissionSchemeInsertForm().setCommissionCashType(CommissionCashType.CommissionIn), new ExpandCommissionSchemeInsertForm().setCommissionCashType(CommissionCashType.CommissionIn))));
        //Object object = ReflectionUtils.toObjectAddEnumStr(insertForm);
        //System.out.println(object);
        System.out.println(EnumStrUtils.toObjectEnumStrJson(insertForm, true));*/
        /*if(insertForm instanceof OperateForm){
            System.out.println("yes");
        }*/
    }
}
