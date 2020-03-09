package com.aatrox.common.utils.objectdiff;

import com.aatrox.common.utils.ListUtil;
import com.aatrox.common.utils.ReflectionUtils;
import com.aatrox.common.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;

/**
 * 用于比较list的内容不一致，仅用于对象型的非基础类型的list对象
 *
 * @author aatrox
 * @date 2019-06-13
 */
public class ListDiffUtil {

    //private static final String DIFF_TEMPLATE = " [%s]:{\r\n%s}";
    private static final String DIFF_TEMPLATE = " %s:{%s},";

    /**
     * 获取list的比较结果
     *
     * @param param
     * @return
     */
    /**
     * 获取list的比较结果
     *
     * @return
     */
    public static ListDiffResult diff(ListDiffParam listParam) {
        ListDiffResult result = new ListDiffResult();
        List historyEntity = (List) listParam.getHistoryEntity();
        //新的数据数据
        if (ListUtil.isEmpty(historyEntity)) {
            result.setNewList((List) listParam.getEntity());
        }
        //删除的数据组数据
        List entity = (List) listParam.getEntity();
        if (ListUtil.isEmpty(entity)) {
            result.setRemoveList((List) listParam.getHistoryEntity());
        }
        ergodicEntity(listParam, result);
        if (StringUtils.isNotEmpty(result.getData())) {
            result.setData(String.format(DIFF_TEMPLATE, listParam.getEntityDesc(), result.getData()));
        }
        return result;
    }

    /**
     * 进行遍历entity得到结果
     **/
    private static void ergodicEntity(ListDiffParam param, ListDiffResult result) {
        if (ListUtil.isEmpty((List) param.getEntity()) || ListUtil.isEmpty((List) param.getHistoryEntity())) {
            return;
        }
        Map<String, Object> newEntityMap = getEntityMap((List) param.getEntity(), param.getIdentityField());
        Map<String, Object> historyEntityMap = getEntityMap((List) param.getHistoryEntity(), param.getIdentityField());
        if (newEntityMap == null || historyEntityMap == null) {
            return;
        }
        if (ListUtil.isEmpty(result.getNewList())) {
            result.setNewList(new ArrayList());
        }
        if (ListUtil.isEmpty(result.getRemoveList())) {
            result.setRemoveList(new ArrayList());
        }
        newEntityMap.entrySet().stream().forEach(entity -> {
            Object oldEntity = historyEntityMap.get(entity.getKey());
            if (oldEntity == null) {
                result.getNewList().add(entity.getValue());
            } else {
                result.setData(getElemDiffInfo(entity.getValue(), oldEntity, result.getData(), param));
            }
        });
        historyEntityMap.entrySet().stream().forEach(oldEntity -> {
            Object entity = newEntityMap.get(oldEntity.getKey());
            if (entity == null) {
                result.getRemoveList().add(oldEntity.getValue());
            }
        });

    }

    /**
     * 获取每个元素的比较信息
     */
    private static String getElemDiffInfo(Object entity, Object oldEntity, String dataIfno, ListDiffParam param) {
        //说明大家都有
        DiffResult diffResult = EntityDiffUtil.doDiff(new DiffParam()
                .setEntity(entity)
                .setHistoryEntity(oldEntity)
                .setExcludeField(ListUtil.isEmpty(param.getExcludeField()) ? ListUtil.NEW(param.getIdentityField()) : param.getExcludeField())
                .setIncludeField(param.getIncludeField()));
        if (StringUtils.isNotEmpty(diffResult.getData())) {
            String tempInfo = StringUtils.isEmpty(dataIfno) ? "" : dataIfno;
            dataIfno = tempInfo + (param.isTableSymbol() ? "\t" : "") + diffResult.getData();
        }
        return dataIfno;
    }

    /**
     * 获取list对应的entityMap
     *
     * @param entity
     * @param identityField
     * @return
     */
    private static Map<String, Object> getEntityMap(List entity, String identityField) {

        if (StringUtils.isEmpty(identityField) || ReflectionUtils.getDeclaredField(entity.get(0), identityField) == null) {
            return null;
        }
        Map<String, Object> entityMap = new LinkedHashMap<>();
        for (int i = 0; i < entity.size(); i++) {
            Object obj = entity.get(i);
            entityMap.put(String.valueOf(ReflectionUtils.getFieldValue(obj, identityField)), obj);
        }
        return entityMap;
    }

    static class Test {
        @ApiModelProperty(value = "id")
        private Integer id;
        @ApiModelProperty(value = "用户姓名")
        private String name;

        public Integer getId() {
            return id;
        }

        public Test setId(Integer id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Test setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<Test> entity = Arrays.asList(new Test().setId(1).setName("你好1"), new Test().setId(3).setName("你好"));
        List<Test> entity2 = Arrays.asList(new Test().setId(1).setName("你好"), new Test().setId(2).setName("你好11"));
        ListDiffParam diffParam = new ListDiffParam()
                .setEntityDesc("测试类信息")
                .setIdentityField("id").setEntity(entity).setHistoryEntity(entity2);
        ListDiffResult result = ListDiffUtil.diff(diffParam);
        System.out.println(result);
        System.out.println(result.getData());
    }

}
