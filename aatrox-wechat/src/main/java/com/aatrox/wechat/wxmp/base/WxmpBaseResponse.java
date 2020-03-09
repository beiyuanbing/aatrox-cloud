package com.aatrox.wechat.wxmp.base;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/6
 */
@Data
@Accessors(chain = true)
public class WxmpBaseResponse {
    private String errcode;
    private String errmsg;

    public boolean isSuccess() {
        return this.errcode == null || "0".equals(this.errcode);
    }
}