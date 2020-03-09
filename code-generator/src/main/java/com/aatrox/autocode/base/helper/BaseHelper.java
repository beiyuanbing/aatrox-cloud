package com.aatrox.autocode.base.helper;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BaseHelper {

    //是否需要打开生成的文件夹
    private boolean isNeedOpen = false;

    /**
     * 是否需要打开rootDir
     *
     * @param rootDir
     */
    public void openRoot(String rootDir) {
        //是否需要自动打开目录文件
        if (isNeedOpen) {
            try {
                Desktop.getDesktop().open(new File(rootDir));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isNeedOpen() {
        return isNeedOpen;
    }

    public BaseHelper setNeedOpen(boolean needOpen) {
        isNeedOpen = needOpen;
        return this;
    }
}
