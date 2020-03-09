package com.aatrox.common.utils.objectdiff;

import java.util.List;

public class ListDiffResult extends DiffResult {

    /**
     * 新的数据
     **/
    private List newList;
    /**
     * 删除了的数据
     **/
    private List removeList;


    public List getNewList() {
        return newList;
    }

    public ListDiffResult setNewList(List newList) {
        this.newList = newList;
        return this;
    }

    public List getRemoveList() {
        return removeList;
    }

    public ListDiffResult setRemoveList(List removeList) {
        this.removeList = removeList;
        return this;
    }

    @Override
    public String toString() {
        return "ListDiffResult{" +
                "newList=" + newList +
                ", removeList=" + removeList +
                ", data='" + getData() + '\'' +
                '}';
    }
}
