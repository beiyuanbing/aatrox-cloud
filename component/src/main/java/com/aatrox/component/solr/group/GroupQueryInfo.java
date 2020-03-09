package com.aatrox.component.solr.group;

import org.apache.solr.client.solrj.SolrQuery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class GroupQueryInfo {
    private List<SolrGroupBean> groupFields;
    private Integer groupLimit;
    private boolean groupNGroups;

    public GroupQueryInfo(List<SolrGroupBean> groupFields, Integer groupLimit) {
        this.groupFields = groupFields;
        this.groupLimit = groupLimit;
        this.groupNGroups = true;
    }

    public Map<String, SolrGroupBean> getGroupFieldsMap() {
        if (this.groupFields == null) {
            return null;
        } else {
            Map<String, SolrGroupBean> beanMap = new HashMap();
            Iterator var2 = this.groupFields.iterator();

            while (var2.hasNext()) {
                SolrGroupBean bean = (SolrGroupBean) var2.next();
                beanMap.put(bean.getGroupField(), bean);
            }

            return beanMap;
        }
    }

    public List<SolrGroupBean> getGroupFields() {
        return this.groupFields;
    }

    public Integer getGroupLimit() {
        return this.groupLimit;
    }

    public boolean isGroupNGroups() {
        return this.groupNGroups;
    }

    public void setGroupNGroups(boolean groupNGroups) {
        this.groupNGroups = groupNGroups;
    }

    public void makeGroupIndex(SolrQuery solrQuery) {
        solrQuery.add("group", new String[]{"true"});
        if (this.groupFields != null) {
            Iterator var2 = this.groupFields.iterator();

            while (var2.hasNext()) {
                SolrGroupBean groupField = (SolrGroupBean) var2.next();
                solrQuery.add("group.field", new String[]{groupField.getGroupField()});
            }
        }

        if (this.groupLimit != null && this.groupLimit > 0) {
            solrQuery.add("group.limit", new String[]{String.valueOf(this.getGroupLimit())});
        }

        if (this.groupNGroups) {
            solrQuery.add("group.ngroups", new String[]{String.valueOf(this.isGroupNGroups())});
        }

    }
}

