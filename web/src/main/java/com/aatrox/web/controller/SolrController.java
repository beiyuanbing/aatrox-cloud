package com.aatrox.web.controller;

import com.aatrox.common.bean.Pagination;
import com.aatrox.common.utils.ListUtil;
import com.aatrox.common.utils.StringUtils;
import com.aatrox.componentsolr.core.SolrUpdateParam;
import com.aatrox.componentsolr.core.criteria.SearchCriteria;
import com.aatrox.web.base.controller.BaseController;
import com.aatrox.web.dto.OrderSolrQueryForm;
import com.aatrox.web.solr.OrderSolrSerach;
import com.aatrox.web.solr.model.OrderSolrField;
import com.aatrox.web.solr.model.OrderSolrModel;
import com.aatrox.web.solr.util.SolrUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

/**
 * @author aatrox
 * @desc
 * @date 2019/11/1
 */
@RestController
@RequestMapping("/solr")
public class SolrController extends BaseController {
    @Resource
    private OrderSolrSerach orderSolrSerach;

    @PostMapping("/add")
    public Object add(){
        OrderSolrModel orderSolrModel = new OrderSolrModel().setId(UUID.randomUUID().toString()).setName("测试")
                .setRatio(ListUtil.NEW(RandomUtils.nextDouble(), RandomUtils.nextDouble()))
                .setSubway(ListUtil.NEW(RandomUtils.nextInt(10) + "号线", RandomUtils.nextInt(10) + "号线"));
        orderSolrSerach.save(orderSolrModel);
        return returnSuccessInfo();
    }

    @PostMapping("/query")
    public Object query(@RequestBody OrderSolrQueryForm orderSolrQueryForm){
        SearchCriteria criteria=new SearchCriteria();
        //增加自定义的solr查询str
        if(StringUtils.isNotEmpty(orderSolrQueryForm.getSearchQStr())){
            criteria.addQuery(orderSolrQueryForm.getSearchQStr(),SearchCriteria.OpeType.AND);
        }
        SolrUtil.fillCriteria(criteria,orderSolrQueryForm, SearchCriteria.OpeType.AND);
        Pagination<OrderSolrModel> pagination=new Pagination<> ();
        pagination= orderSolrSerach.query(criteria, pagination);
        return pagination;
    }

    @PostMapping("/edit")
    public Object edit(@RequestBody OrderSolrModel orderSolrModel){
        SolrUpdateParam solrUpdateParam = new SolrUpdateParam(OrderSolrField.ID, orderSolrModel.getId());
        solrUpdateParam.addParam(SolrUpdateParam.OperatorType.set
                , OrderSolrField.NAME
                , Optional.ofNullable(orderSolrModel.getName()).orElse("测试"));
        if(ListUtil.isNotEmpty(orderSolrModel.getRatio())) {
            solrUpdateParam.addParam(SolrUpdateParam.OperatorType.set
                    , OrderSolrField.RATIO
                    , Optional.ofNullable(orderSolrModel.getRatio()).orElse(ListUtil.NEW(1.0,2.0)));
        }
        if(ListUtil.isNotEmpty(orderSolrModel.getSubway())) {
            solrUpdateParam.addParam(SolrUpdateParam.OperatorType.set
                    , OrderSolrField.SUBWAY
                    , Optional.ofNullable(orderSolrModel.getSubway()).orElse(ListUtil.NEW(RandomUtils.nextInt(10) + "号线",
                            RandomUtils.nextInt(10) + "号线")));
        }

        orderSolrSerach.update(solrUpdateParam);
        return returnSuccessInfo();
    }

    @PostMapping("/removeAll")
    public Object removeAll(){
        orderSolrSerach.deleteAll();
        return returnSuccessInfo();
    }
}
