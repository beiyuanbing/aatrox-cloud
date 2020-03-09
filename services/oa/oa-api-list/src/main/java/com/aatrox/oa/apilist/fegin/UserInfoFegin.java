package com.aatrox.oa.apilist.fegin;

import com.aatrox.oa.apilist.form.UserInfoQueryPageForm;
import com.aatrox.oa.apilist.model.UserInfoModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
/**
* <p>
    * 用户表 服务类
    * </p>
*
* @author Aatrox
* @since 2019-09-09
*/
@FeignClient(value = "service-oa", contextId = "UserInfoFegin")
public interface UserInfoFegin  {

    @PostMapping("/userInfo/selectById")
    UserInfoModel selectById(Integer id);

    @PostMapping("/userInfo/selectAll")
    List<UserInfoModel> selectAll();

    @PostMapping("/userInfo/insertUserInfo")
    int insertUserInfo(@RequestBody UserInfoModel record);

    @PostMapping("/userInfo/updateUserInfo")
    int updateUserInfo(@RequestBody UserInfoModel record);

    @PostMapping("/userInfo/deleteById")
    int deleteById(Integer id);

    @PostMapping("/userInfo/selectPage")
    Page<UserInfoModel> selectPage(UserInfoQueryPageForm queryForm);

}

