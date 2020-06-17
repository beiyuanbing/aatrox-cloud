package com.aatrox.oaservice.api;

import com.aatrox.oa.apilist.fegin.AccountFegin;
import javax.annotation.Resource;
import java.util.List;

import com.aatrox.oa.apilist.model.AccountModel;
import com.aatrox.oaservice.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* <p>
    * 账户信息表 服务实现类
    * </p>
*
* @author Aatrox
* @since 2020-06-16
*/
@RestController
@RequestMapping(value = "/userAccount")
public class AccountApi implements AccountFegin {

    @Resource
    private AccountService accountService;

    @Override
    @PostMapping("/selectById")
    public AccountModel selectById(@RequestBody Integer id){
            return accountService.getById(id);
    }

    @Override
    @PostMapping("/selectAll")
    public List<AccountModel> selectAll(){
            return accountService.list();
    }

    @Override
    @PostMapping("/insertUserAccount")
    public AccountModel insertUserAccount(@RequestBody AccountModel record){
        accountService.updateById(record);
        return record;
    }

    @Override
    @PostMapping("/updateUserAccount")
    public AccountModel updateUserAccount(@RequestBody AccountModel record){
        accountService.updateById(record);
        return record;
    }

    @Override
    @PostMapping("/deleteById")
    public int deleteById(@RequestBody Integer id){
            return accountService.removeById(id)?1:0;
    }

    @Override
    @PostMapping("/deduction")
    public void deduction(@RequestBody AccountModel record) {
        accountService.deduction(record);
    }

}
