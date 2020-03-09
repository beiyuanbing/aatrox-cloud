package com.aatrox.oaservice.api;

import com.aatrox.oa.apilist.fegin.UserInfoFegin;
import com.aatrox.oa.apilist.form.UserInfoQueryPageForm;
import com.aatrox.oa.apilist.model.UserInfoModel;
import com.aatrox.oaservice.service.UserInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-21
 */
@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoApi implements UserInfoFegin {

    @Resource
    private UserInfoService userInfoService;

    @Override
    @PostMapping("/selectById")
    public UserInfoModel selectById(Integer id){
        return userInfoService.getById(id);
    }

    @Override
    @PostMapping("/selectAll")
    public List<UserInfoModel> selectAll(){
        return userInfoService.list();
    }

    @Override
    @PostMapping("/insertUserInfo")
    public int insertUserInfo(@RequestBody UserInfoModel record){
        return userInfoService.save(record)?1:0;
    }

    @Override
    @PostMapping("/updateUserInfo")
    public int updateUserInfo(@RequestBody UserInfoModel record){
        return userInfoService.updateById(record)?1:0;
    }

    @Override
    @PostMapping("/deleteById")
    public int deleteById(@RequestBody Integer id){
        return userInfoService.removeById(id)?1:0;
    }

    @Override
    @PostMapping("/selectPage")
    public Page<UserInfoModel> selectPage(UserInfoQueryPageForm queryForm) {
        return userInfoService.selectPage(queryForm);
    }
}