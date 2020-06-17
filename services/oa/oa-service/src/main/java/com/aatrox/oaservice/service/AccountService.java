package com.aatrox.oaservice.service;

import com.aatrox.oa.apilist.model.AccountModel;
import com.baomidou.mybatisplus.extension.service.IService;
/**
* <p>
    * 账户信息表 服务类
    * </p>
*
* @author Aatrox
* @since 2020-06-16
*/
public interface AccountService extends IService<AccountModel> {

    void deduction(AccountModel record);
}

