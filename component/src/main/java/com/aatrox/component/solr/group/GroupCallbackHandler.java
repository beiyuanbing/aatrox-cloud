package com.aatrox.component.solr.group;

import com.aatrox.component.solr.callback.SolrCallbackHandler;
import com.aatrox.component.solr.criteria.SearchCriteria;
import com.aatrox.component.solr.template.SolrTemplate;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.*;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-12
 */
public class GroupCallbackHandler<T> extends SolrCallbackHandler<T> {
    private Logger logger = Logger.getLogger(this.getClass());
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
                        this.logger.error(var13);
                    }
                }

                this.pagination.getItemMap().put(groupName, itemAll);
            }
        }

        return null;
    }
}

