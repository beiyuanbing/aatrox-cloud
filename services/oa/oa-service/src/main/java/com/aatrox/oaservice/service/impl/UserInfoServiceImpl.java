package com.aatrox.oaservice.service.impl;

import com.aatrox.oa.apilist.form.UserInfoQueryPageForm;
import com.aatrox.oa.apilist.model.UserInfoModel;
import com.aatrox.oaservice.dao.UserInfoDao;
import com.aatrox.oaservice.service.UserInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author aatrox
 * @since 2019-08-21
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoModel> implements UserInfoService {

    @Override
    public Page<UserInfoModel> selectPage(UserInfoQueryPageForm queryForm) {
        Page<UserInfoModel> page = new Page<>(queryForm.getCurrentPage(), queryForm.getPageSize());
        page.setRecords(baseMapper.selectPage(queryForm, page));
        return page;
    }
}
