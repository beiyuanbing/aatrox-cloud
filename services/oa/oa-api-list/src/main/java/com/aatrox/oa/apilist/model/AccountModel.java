package com.aatrox.oa.apilist.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
* <p>
    * 账户信息表
    * </p>
*
* @author Aatrox
* @since 2020-06-16
*/
@ApiModel(value="AccountModel对象", description="账户信息表")
@Data
@Accessors(chain = true)
@TableName(value = "t_user_account")
public class AccountModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "余额")
    @TableField("money")
    private Double money;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "备注信息")
    @TableField("mark")
    private String mark;
}
