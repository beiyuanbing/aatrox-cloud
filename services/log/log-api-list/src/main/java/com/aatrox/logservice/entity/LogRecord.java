package com.aatrox.logservice.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 日志记录表
 * </p>
 *
 * @author aatrox
 * @since 2019-06-05
 */
@TableName("t_log_record")
public class LogRecord extends Model<LogRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "fid", type = IdType.AUTO)
    private Integer id;

    /**
     * 内容
     */

    @TableField(value = "fcontent")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "id=" + id +
                ", content=" + content +
                "}";
    }
}
