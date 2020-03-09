package com.aatrox.oaservice.dao;

import com.aatrox.oa.apilist.form.UserInfoQueryPageForm;
import com.aatrox.oa.apilist.model.UserInfoModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author aatrox
 * @since 2019-08-21
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfoModel> {

    List<UserInfoModel> selectPage(UserInfoQueryPageForm queryForm, Page<UserInfoModel> page);

}
