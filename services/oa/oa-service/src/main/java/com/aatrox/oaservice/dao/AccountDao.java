package com.aatrox.oaservice.dao;

import java.util.List;

import com.aatrox.oa.apilist.model.AccountModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* <p>
    * 账户信息表 Mapper 接口
    * </p>
*
* @author Aatrox
* @since 2020-06-16
*/
@Mapper
public interface AccountDao extends BaseMapper<AccountModel>{
}

