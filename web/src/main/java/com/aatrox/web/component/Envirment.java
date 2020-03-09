package com.aatrox.web.component;

import com.aatrox.web.common.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Envirment {
    @Autowired
    private Environment environment;

    /**
     * 返回环境属性
     *
     * @return
     */
    public Env getEnvironment() {
        String[] profiles = environment.getActiveProfiles();
        //如果profiles==null，默认是开发环境
        if (profiles == null || profiles.length == 0 || profiles[0] == null) {
            return Env.DEV;
        }
        return Env.getEnv(profiles[0]);
    }
}
