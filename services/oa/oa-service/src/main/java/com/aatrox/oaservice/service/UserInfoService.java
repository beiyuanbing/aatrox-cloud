package com.aatrox.oaservice.service;

import com.aatrox.oa.apilist.form.UserInfoQueryPageForm;
import com.aatrox.oa.apilist.model.UserInfoModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author aatrox
 * @since 2019-08-21
 */
public interface UserInfoService extends IService<UserInfoModel> {

    Page<UserInfoModel> selectPage(UserInfoQueryPageForm queryForm);
}
