package com.aatrox.oaservice.service.impl;

import com.aatrox.oa.apilist.model.AccountModel;
import com.aatrox.oaservice.dao.AccountDao;
import com.aatrox.oaservice.service.AccountService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.ObjectUtils;

/**
* <p>
    * 账户信息表 服务实现类
    * </p>
*
* @author Aatrox
* @since 2020-06-16
*/
@Service
public class AccountServiceimpl extends ServiceImpl<AccountDao, AccountModel> implements AccountService {
    @Override
    public void deduction(AccountModel record) {
        AccountModel accountModel = this.baseMapper.selectById(record.getId());
        if (ObjectUtils.isEmpty(accountModel.getMoney())) {
            throw new RuntimeException("账户：" + accountModel.getId() + "，不存在.");
        }
        if (accountModel.getMoney() - record.getMoney() < 0) {
            throw new RuntimeException("账户：" + accountModel.getId()  + "，余额不足.");
        }
        record.setMoney(accountModel.getMoney() - record.getMoney());
        this.updateById(record);
    }
}
