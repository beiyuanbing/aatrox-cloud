package com.aatrox.common.utils.objectdiff.actor;


import com.aatrox.common.utils.objectdiff.DiffParam;
import com.aatrox.common.utils.objectdiff.DiffResult;

/**
 * 根据比较对象，返回指定格式比较内容
 * Created by yoara on 2018/1/4.
 */
public interface DiffActorInterface {
    /**
     * 当前方法需要根据Act行为，设置result的值
     */
    DiffResult doAct(DiffParam param);
}
