package com.aatrox.component.solr.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class SolrUpdateParam {
    private String idName;
    private String idValue;
    private List<UpdateParam> paramList = new ArrayList();

    public String getIdName() {
        return this.idName;
    }

    public String getIdValue() {
        return this.idValue;
    }

    public List<UpdateParam> getParamList() {
        return this.paramList;
    }

    public SolrUpdateParam(String idName, String idValue) {
        this.idName = idName;
        this.idValue = idValue;
    }

    public void addParam(SolrUpdateParam.OperatorType type, String field, Object value) {
        this.paramList.add(new SolrUpdateParam.UpdateParam(type, field, value));
    }

    public static enum OperatorType {
        add,
        set,
        inc;

        private OperatorType() {
        }
    }

    public static class UpdateParam {
        private String field;
        private SolrUpdateParam.OperatorType operatorType;
        private Object value;

        public UpdateParam(SolrUpdateParam.OperatorType operatorType, String field, Object value) {
            this.field = field;
            this.operatorType = operatorType;
            this.value = value;
        }

        public String getField() {
            return this.field;
        }

        public Map<String, Object> pickUpdateInfo() {
            Map<String, Object> info = new HashMap();
            info.put(this.operatorType.name(), this.value);
            return info;
        }
    }
}

