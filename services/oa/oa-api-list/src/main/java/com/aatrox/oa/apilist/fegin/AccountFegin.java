package com.aatrox.oa.apilist.fegin;

import com.aatrox.oa.apilist.form.UserInfoQueryPageForm;
import com.aatrox.oa.apilist.model.AccountModel;
import com.aatrox.oa.apilist.model.UserInfoModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* <p>
    * 用户表 服务类
    * </p>
*
* @author Aatrox
* @since 2019-09-09
*/
@FeignClient(value = "service-oa", contextId = "AccountFegin")
public interface AccountFegin  {

    @PostMapping("/userAccount/selectById")
    AccountModel selectById(Integer id);

    @PostMapping("/userAccount/selectAll")
    List<AccountModel> selectAll();

    @PostMapping("/userAccount/insertUserAccount")
    AccountModel insertUserAccount(AccountModel record);


    @PostMapping("/userAccount/updateUserAccount")
    AccountModel updateUserAccount(AccountModel record);

    @PostMapping("/userAccount/deleteById")
    int deleteById(Integer id);

    @PostMapping("/userAccount/deduction")
    void deduction(AccountModel record);

}

