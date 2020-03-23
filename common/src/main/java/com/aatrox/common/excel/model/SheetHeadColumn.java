package com.aatrox.common.excel.model;

/**
 * @author aatrox
 * @desc
 * @date 2020/3/20
 */
public class SheetHeadColumn {

    public static final int DEFAULT_MULTIPLE = 256;

    public static final Integer DEFAULT_COLUMN_WIDTH = Integer.valueOf(10);

    public static final String DEFAULT_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";



    /**当前表列的标题**/
    private final String title;

    /**对象实体的属性名**/
    private final String fieldName;

    /**当前表列的宽度**/
    private final Integer width;

    /**日期格式专用formate类型**/
    private final String dateFormat;

    public SheetHeadColumn(String title, String fieldName){
        this(title,fieldName,DEFAULT_COLUMN_WIDTH,DEFAULT_DATEFORMAT);
    }

    public SheetHeadColumn(String title, String fieldName, Integer width) {
        this.title = title;
        this.fieldName = fieldName;
        this.width = width;
        this.dateFormat = DEFAULT_DATEFORMAT;
    }

    public SheetHeadColumn(String title, String fieldName, Integer width, String dateFormat) {
        this.title = title;
        this.fieldName = fieldName;
        this.width = width;
        this.dateFormat = dateFormat;
    }

    public String getTitle() {
        return title;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Integer getWidth() {
        return width;
    }

    public String getDateFormat() {
        return dateFormat;
    }
}
