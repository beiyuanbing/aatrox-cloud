package com.aatrox.wechat.wxmp;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/6
 */
@Data
@Accessors(chain = true)
public class WxmpConfigInfo {
    private String appid;
    private String secret;
}
