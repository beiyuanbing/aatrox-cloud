package com.aatrox.common.utils;

import com.aatrox.common.excel.base.FileType;
import org.apache.commons.lang3.Validate;

import java.io.File;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public final class FileUtils {

    /**
     * 给定一个文件名，获取该文件名的后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName) {
        Validate.notBlank(fileName);
        String suffix = "";
        int indexDot = fileName.lastIndexOf(".");
        if (indexDot > 0) {
            suffix = fileName.substring(indexDot + 1, fileName.length());
        }
        return suffix;
    }


    public static String getFileSuffix(File file) {
        Validate.notNull(file);
        return getFileSuffix(file.getName());
    }


    public static FileType getFileType(String fileName) {
        String fileSuffix = getFileSuffix(fileName);
        for(FileType ft : FileType.values()) {
            if(ft.getSuffix().equalsIgnoreCase(fileSuffix))
                return ft;
        }
        return null;
    }


    public static FileType getFileType(File file) {
        Validate.notNull(file);
        return getFileType(file.getName());
    }

    private FileUtils() {}

}