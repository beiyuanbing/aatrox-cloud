package com.aatrox.web.remote;

import javax.annotation.Resource;

import com.aatrox.oa.apilist.fegin.AccountFegin;
import com.aatrox.oa.apilist.model.AccountModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
* <p>
    * 账户信息表 服务实现类
    * </p>
*
* @author Aatrox
* @since 2020-06-16
*/
@Service
public class AccountRemote{

    @Resource
    private AccountFegin accountFegion;

    public AccountModel selectById(Integer id){
        return accountFegion.selectById(id);
    }

    public List<AccountModel> selectAll(){
        return accountFegion.selectAll();

    }
    public AccountModel insertUserAccount(AccountModel record){
        return accountFegion.insertUserAccount(record);
    }

    public AccountModel updateUserAccount(AccountModel record){
        return accountFegion.updateUserAccount(record);
    }

    public int deleteById(Integer id){
        return accountFegion.deleteById(id);
    }

    public void deduction(AccountModel record){
         accountFegion.deduction(record);
    }

}
