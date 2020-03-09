package com.aatrox.wechat.wxmp.response;

import com.aatrox.wechat.wxmp.base.WxmpBaseResponse;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/16
 */
@Data
@Accessors(chain = true)
public class AccessTokenResponse extends WxmpBaseResponse {
    private String access_token;
    private int expires_in;
}
