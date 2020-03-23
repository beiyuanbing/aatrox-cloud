package com.aatrox.common.excel.support;

import org.apache.commons.lang3.Validate;

import java.util.stream.Stream;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/23
 */
public class ArraySheetHeadBuilder extends CommonSheetHeadBuilder {

    private static String DEFAULT_HEAD_SEGMENTATION = ":";

    private final String[] heads;
    private String headSegmentation;

    public ArraySheetHeadBuilder(Class<?> targetClazz, String[] heads) {
        this(targetClazz, heads, DEFAULT_HEAD_SEGMENTATION);
    }

    public ArraySheetHeadBuilder(Class<?> targetClazz, String[] heads, String headSegmentation) {
        super(targetClazz);
        Validate.notEmpty(heads);
        Validate.notBlank(headSegmentation);
        this.heads = heads;
        this.headSegmentation = headSegmentation;
        this.parseHead();
    }

    private void parseHead() {
        Stream.of(heads).forEach(head -> {
            String[] hs = head.split(headSegmentation);
            Validate.isTrue(hs.length > 0 && hs.length <= 3, String.format("excel 表头格式错误请检查，支持[name / name:姓名 / name:姓名:100]，实际表头 %s", head));
            if(hs.length == 1) {
                this.append(hs[0], hs[0]);
            } else if(hs.length == 2) {
                this.append(hs[0], hs[1]);
            } else {
                this.append(hs[0], hs[1], Integer.valueOf(hs[2]));
            }
        });
    }

}
