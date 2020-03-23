package com.aatrox.common.excel.base;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public enum FileType {

    EXCEL97("xls"),
    EXCEL07("xlsx"),
    WORD97("doc"),
    WORD07("docx"),
    PDF("pdf"), TEXT("txt");

    private final String suffix;

    private FileType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

}
