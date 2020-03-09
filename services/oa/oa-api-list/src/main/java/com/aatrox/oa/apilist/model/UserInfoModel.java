package com.aatrox.oa.apilist.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author aatrox
 * @since 2019-08-21
 */
@Data
@ToString
@Accessors(chain = true)
@TableName("t_user_info")
@ApiModel(value = "UserInfo对象", description = "用户表")
public class UserInfoModel extends Model<UserInfoModel> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "fid", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    @TableField("fname")
    private String name;

    @ApiModelProperty(value = "密码")
    @TableField("fpassword")
    private String password;

    @ApiModelProperty(value = "手机号")
    @TableField("fmobile")
    private String mobile;

    @ApiModelProperty(value = "出生日期")
    @TableField("fbirthday")
    private Date birthday;

    @ApiModelProperty(value = "启用状态 0:禁用 1:启用")
    @TableField("fstatus")
    private Integer status;

    @ApiModelProperty(value = "地址")
    @TableField("faddress")
    private String address;

    @TableField("fcreateDate")
    @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    private Date createDate;
}
