package com.aatrox.web.solr.util;

import com.aatrox.common.utils.ListUtil;
import com.aatrox.common.utils.ReflectionUtils;
import com.aatrox.common.utils.StringUtils;
import com.aatrox.componentsolr.core.criteria.SearchCriteria;
import com.aatrox.web.dto.OrderSolrQueryForm;
import com.aatrox.web.solr.model.OrderSolrField;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.jetty.util.StringUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @desc
 * @auth beiyuanbing
 * @date 2019-04-18 14:04
 **/
public class SolrUtil {

    static Set<String> staticField;

    static {
        staticField = Arrays.stream(OrderSolrField.class.getFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .collect(HashSet::new, (s, f) -> {
                    try {
                        s.add(String.valueOf(f.get(OrderSolrField.class)));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }, HashSet::addAll);
        staticField = Optional.of(staticField).orElse(new HashSet<>());
    }

    /**
     * 第一层封装
     *
     * @param criteria          封装条件
     * @param orderSolrQueryForm 搜索条件
     * @param opeType           内部封装条件
     */
    public static void fillCriteria(SearchCriteria criteria, OrderSolrQueryForm orderSolrQueryForm, SearchCriteria.OpeType opeType) {
        fillCriteria(criteria, orderSolrQueryForm, opeType, SearchCriteria.OpeType.AND);
    }

    /**
     * 第二层封装
     *
     * @param criteria          封装条件
     * @param orderSolrQueryForm 搜索条件
     * @param innerOpeType      内部封装条件
     * @param opeType           外部封装条件
     */
    public static void fillCriteria(SearchCriteria criteria, OrderSolrQueryForm orderSolrQueryForm, SearchCriteria.OpeType innerOpeType, SearchCriteria.OpeType opeType) {
        List<Field> fieldList = Arrays.stream(orderSolrQueryForm.getClass().getDeclaredFields()).filter(field -> {
            field.setAccessible(true);
            try {
                return field.get(orderSolrQueryForm) != null;
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
        List<SearchCriteria.FieldValuePair> params = ListUtil.NEW();
        fieldList.forEach(field -> {
            try {
                String fieldName = field.getName();
                if (staticField.contains(fieldName)) {

                    Object valObj = field.get(orderSolrQueryForm);
                    if(ReflectionUtils.isCollectionField(field)){
                        System.out.println(true);
                        return;
                    }
                    String value = valObj + "";
                    if (StringUtils.isNotEmpty(value)) {
                        params.add(new SearchCriteria.FieldValuePair(fieldName, value));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //获取字段名;
        });
        if (innerOpeType == null) {
            innerOpeType = SearchCriteria.OpeType.AND;
        }
        if (params.size() > 0) {
            criteria.addQuerys(params, innerOpeType, opeType);
        } else {
            criteria.addQuery("*:*", SearchCriteria.OpeType.AND);
        }
    }

    /**
     * 获取组别的拼装值
     * @param fieldName
     * @param valueList
     * @return
     */
    public static String getMultiSearchQStr(String fieldName, List<?> valueList){
        return getMultiSearcQStrhWithPrefix(fieldName,null, valueList);
    }
    /**
     * 获取组别的拼装值
     * @param fieldName
     * @param valueList
     * @return
     */
    public static String getMultiSearchQStr(String fieldName,String prefixQStr, List<?> valueList){
        if(ListUtil.isEmpty(valueList)){
            return "";
        }
        StringBuffer  searchQStrBuffer=new StringBuffer();
        valueList.stream().forEach(value->{
            searchQStrBuffer.append("("+prefixQStr+" "+SearchCriteria.OpeType.AND+" "+fieldName+":"+value+")"+ SearchCriteria.OpeType.OR);
        });
        String searchQStr = searchQStrBuffer.toString();
        return searchQStr.substring(0,searchQStr.length()-2);
    }

    /**
     * 获取组别的拼装值
     * @param fieldName
     * @param valueList
     * @return
     */
    public static String getMultiSearcQStrhWithPrefix(String fieldName,String prefixStr, List<?> valueList){
        if(ListUtil.isEmpty(valueList)){
            return "";
        }
        StringBuffer  searchQStrBuffer=new StringBuffer();
        valueList.stream().forEach(value->{
            searchQStrBuffer.append(fieldName+":"+ (StringUtil.isEmpty(prefixStr)?"":prefixStr)+value+" "+ SearchCriteria.OpeType.OR+" ");
        });
        String searchQStr = searchQStrBuffer.toString();
        return searchQStr.substring(0,searchQStr.length()-4);
    }

    @Test
    public void test1() {
        SearchCriteria criteria = new SearchCriteria();
        //第一层封装
        OrderSolrQueryForm planSolrQueryForm = new OrderSolrQueryForm().setSubway(ListUtil.NEW("2号线","1号线")).setName("1");
        SolrUtil.fillCriteria(criteria, planSolrQueryForm, SearchCriteria.OpeType.AND);
        //第二层封装 内部and 外部or
//        SolrUtil.fillCriteria(criteria, new PlanSolrQueryForm().setAvgPrice(1.0).setExpandName("2"), SearchCriteria.OpeType.AND, SearchCriteria.OpeType.OR);
        //              内部              外部             内部
//        (expandName:2 AND avgPrice:1.0) OR (expandName:2 AND avgPrice:1.0)
        String s = JSONObject.toJSONString(planSolrQueryForm);
        System.out.println(s);
    }
}
