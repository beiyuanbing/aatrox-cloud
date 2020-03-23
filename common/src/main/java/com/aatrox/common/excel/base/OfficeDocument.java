package com.aatrox.common.excel.base;


import com.aatrox.common.utils.FileUtils;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class OfficeDocument {

    // 文件名
    protected final String fileName;

    // 文件类型
    protected final FileType fileType;

    public OfficeDocument(String fileName) {
        this.fileName = fileName;
        this.fileType = FileUtils.getFileType(fileName);
    }

    public String getFileName() {
        return fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

}