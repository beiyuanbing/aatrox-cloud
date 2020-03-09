package com.aatrox.common.utils.objectdiff;

import com.aatrox.common.utils.objectdiff.actor.DefaultActor;
import com.aatrox.common.utils.objectdiff.actor.DiffActorInterface;

/**
 * 1.通过反射比较对象内容是否一致，那么class不一定是一致的
 * Created by yoara on 2018/1/4.
 */
public class EntityDiffUtil {
    public static DiffResult doDiff(DiffParam param) {
        DiffResult result = new DiffResult();
        if (param.getEntity() == null || param.getHistoryEntity() == null) {
            result.setErrMessage("wrong entity");
            return result;
        }
        DiffActorInterface actor = param.getActor() == null ? new DefaultActor() : param.getActor();
        result = actor.doAct(param);
        return result;
    }

    //测试DEMO
//    static class Test {
//        @ApiModelProperty(value = "id")
//        private Integer id;
//        @ApiModelProperty(value = "用户姓名")
//        private String name;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public Test setId(Integer id) {
//            this.id = id;
//            return this;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public Test setName(String name) {
//            this.name = name;
//            return this;
//        }
//    }

//    public static void main(String[] args) {
//        Test entity = new Test().setId(1).setName("123");
//        Test entity2 = new Test().setId(2).setName(null);
//        DiffResult diffResult = EntityDiffUtil.doDiff(new DiffParam()
//                .setEntity(entity)
//                .setHistoryEntity(entity2)
//                .setIncludeField(ListUtil.NEW("id", "name"))
//        );
//        System.out.println();
//    }
}
