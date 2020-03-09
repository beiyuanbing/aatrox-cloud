package com.aatrox.componentsolr.core.group;

import com.aatrox.componentsolr.core.callback.SolrCallbackHandler;
import com.aatrox.componentsolr.core.criteria.SearchCriteria;
import com.aatrox.componentsolr.core.template.SolrTemplate;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class GroupCallbackHandler<T> extends SolrCallbackHandler<T> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private GroupSolrPagination pagination;

    public GroupCallbackHandler(SolrTemplate<T> solrTemplate, SearchCriteria criteria, GroupSolrPagination<T> pagination) {
        super(solrTemplate, criteria, pagination);
        this.pagination = pagination;
    }

    public List<T> parseQuerResponse(QueryResponse response) {
        Map<String, SolrGroupBean> groupFieldsMap = this.criteria.getGroupQueryInfo().getGroupFieldsMap();
        this.pagination.setGroupFields(this.criteria.getGroupQueryInfo().getGroupFields());
        if (response.getGroupResponse() != null) {
            List<GroupCommand> values = response.getGroupResponse().getValues();
            this.pagination.setRecordCount(values.size());
            this.pagination.setItemMap(new HashMap());
            Iterator var4 = values.iterator();

            while (var4.hasNext()) {
                GroupCommand groupCommand = (GroupCommand) var4.next();
                String groupName = groupCommand.getName();
                SolrGroupBean groupBean = (SolrGroupBean) groupFieldsMap.get(groupName);
                groupBean.setDocsCount(groupCommand.getMatches());
                groupBean.setNgCount(groupCommand.getNGroups());
                Map<String, SolrGroupResultBean<T>> groupResult = new HashMap();
                groupBean.setGroupResult(groupResult);
                List<T> itemAll = new ArrayList();
                Iterator var10 = groupCommand.getValues().iterator();

                while (var10.hasNext()) {
                    Group group = (Group) var10.next();

                    try {
                        SolrGroupResultBean resultBean = new SolrGroupResultBean();
                        resultBean.setGroupValue(group.getGroupValue());
                        resultBean.setGroupResult(this.solrTemplate.solrDocumentsToEntities(group.getResult()));
                        resultBean.setNumFound(group.getResult().getNumFound());
                        resultBean.setStart(group.getResult().getStart());
                        groupResult.put(group.getGroupValue(), resultBean);
                        itemAll.addAll(resultBean.getGroupResult());
                    } catch (Exception var13) {
                        var13.printStackTrace();
                        this.logger.error(var13.getMessage());
                    }
                }

                this.pagination.getItemMap().put(groupName, itemAll);
            }
        }

        return null;
    }
}

