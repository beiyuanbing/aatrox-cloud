package com.aatrox.web.base.util;

import com.aatrox.common.anno.ExcelField;
import com.aatrox.common.utils.ReflectionUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author aatrox
 * @desc 数据model转成excel模板导出的工具类
 * @date 2020/11/27
 */
public class ExcelExportUtil {
    private static final String EXCEL_DEFAULT="<excel id=\"%sExport\" fileName=\"%s\">\n%s</excel>";
    private static final String CLOUMN_DEFAULT="\t<column key=\"%s\" width=\"100\">%s</column>\n";
    private static final String CLOUMN_ENUM="\t<column key=\"%s\" width=\"80\" enumDescription=\"desc\" enum=\"%s\">%s</column>\n";
    private static final String CLOUMN_DATE="\t<column key=\"%s\" dataType=\"date\" dateFormat=\"%s\" width=\"120\">%s</column>\n";
    private static final String DATE_DEFAULT_FORMAT="yyyy-MM-dd HH:mm:ss";
    private static final String EXCEL_DEFAULT_FILE_NAME="数据导出";
    /***转map使用***/
    private static final String CELL_KEY="key";
    private static final String CELL_WIDTH="width";
    private static final String CELL_ENUM="enum";
    private static final String CELL_ENUM_DESCRIPTION="enumDescription";
    private final static String DEFAULT_DESCRIPTION = "desc";
    private final static String CELL_DATE_FORMAT = "dateFormat";
    private final static String CELL_DATA_TYPE = "dataType";
    private final static String CELL_DATA_TYPE_DATE = "date";
    /***类路径***/
    private String classPath;
    /**class全路径作为key**/
    private boolean fullClassPathKey;

    public ExcelExportUtil() {
    }

    public ExcelExportUtil(String classPath) {
        this.classPath = classPath;
    }

    /**
     * 输入的字串例子
     * <excel id="BusinessAreaVOExport" fileName="商圈信息">
     * 	<column key="id" width="100">开发商id</column>
     * 	<column key="businessAreaName" width="100">开发商名称</column>
     * 	<column key="startTime" dataType="date" dateFormat="yyyy-MM-dd HH:mm:ss" width="120">开始时间</column>
     * </excel>
     * @throws ClassNotFoundException
     */
    public String getExcelStr(){
        Class<?> aClass = null;
        try {
            aClass=Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return analyseClassExcel(aClass);
    }

    public String getExcelStr(Class claz){
        return analyseClassExcel(claz);
    }

    /**
     * 解析类
     * @param aClass
     * @return
     */
    public String analyseClassExcel( Class<?> aClass) {
        try {
            ApiModel annotation = aClass.getAnnotation(ApiModel.class);
            String fileName=annotation==null?EXCEL_DEFAULT_FILE_NAME:annotation.description();
            StringBuilder stringBuilder=new StringBuilder();
            for (Field field : aClass.getDeclaredFields()) {
                if(this.isTransient(field)){
                    continue;
                }
                stringBuilder.append(getCloumnStr(field));
            }
            return String.format(EXCEL_DEFAULT,isFullClassPathKey()?aClass.getName():aClass.getSimpleName(),
                    StringUtils.isEmpty(fileName)?EXCEL_DEFAULT_FILE_NAME:fileName,
                    stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否忽略该字段
     * @param field
     * @return
     */
    public boolean isTransient(Field field) {
        ExcelField annotation = field.getAnnotation(ExcelField.class);
        return ReflectionUtils.isTransient(field)||(annotation!=null&&annotation.ignore());
    }

    /**
     * 获取excel模板的字段模板
     * @param field
     * @return
     */
    private String getCloumnStr(Field field){
        if(field==null){
            return null;
        }
        ApiModelProperty fieldAnnotation = field.getAnnotation(ApiModelProperty.class);
        String desc=getFieldDesc(fieldAnnotation==null?"":fieldAnnotation.value());
        //枚举类型
        if(field.getType().isEnum()){
            return String.format(CLOUMN_ENUM,field.getName(),field.getType().getName(),desc);
        }else if(field.getType().isAssignableFrom(Date.class)){
            return String.format(CLOUMN_DATE,field.getName(),this.getDatePattern(field),desc);
        }else{
            return String.format(CLOUMN_DEFAULT,field.getName(),desc);
        }
    }

    public Map<String,Object> getTemplateMap(Class<?> aClass){

        try {
            Map<String,Object> resultMap=new HashMap();
            Map<String,Object> subMap=new HashMap();
            ApiModel annotation = aClass.getAnnotation(ApiModel.class);
            String fileName=annotation==null?EXCEL_DEFAULT_FILE_NAME:annotation.description();
            List<Map<String, Object>> columnList=new ArrayList<>();
            for (Field field : aClass.getDeclaredFields()) {
                if(this.isTransient(field)){
                    continue;
                }
                columnList.add(this.getCellMap(field));
            }
            subMap.put("columns",columnList);
            subMap.put("fileName",fileName);
            resultMap.put(isFullClassPathKey()?aClass.getName():aClass.getSimpleName(),subMap);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取propert的map
     * @param field
     * @return
     */
    private Map<String,Object> getCellMap(Field field){
        if(field==null){
            return null;
        }
        Map<String,Object> cellMap= new LinkedHashMap<>();
        ApiModelProperty fieldAnnotation = field.getAnnotation(ApiModelProperty.class);
        String desc=getFieldDesc(fieldAnnotation==null?"":fieldAnnotation.value());
        cellMap.put(field.getName(),desc);
        cellMap.put(CELL_KEY,field.getName());
        cellMap.put(CELL_WIDTH,"100");
        //枚举类型
        if(field.getType().isEnum()){
            cellMap.put(CELL_WIDTH,"80");
            cellMap.put(CELL_ENUM,field.getType().getName());
            cellMap.put(CELL_ENUM_DESCRIPTION,this.getEnumDescription(field));
        }else if(field.getType().isAssignableFrom(Date.class)){
            cellMap.put(CELL_WIDTH,"120");
            cellMap.put(CELL_DATE_FORMAT,this.getDatePattern(field));
            cellMap.put(CELL_DATA_TYPE,CELL_DATA_TYPE_DATE);
        }
        return cellMap;
    }

    /**
     * 获取枚举的描述方法
     * @param field
     * @return
     */
    private String getEnumDescription(Field field){
        ExcelField excelEnumAnno = field.getAnnotation(ExcelField.class);
        if(excelEnumAnno ==null){
            return DEFAULT_DESCRIPTION;
        }
        return excelEnumAnno.enumDesc();
    }

    /**
     * 获取字段描述
     * @param desc
     * @return
     */
    private  String getFieldDesc(String desc){
        if(StringUtils.isEmpty(desc)){
            return "";
        }
        int index = desc.indexOf(" ");
        return index>-1?desc.substring(0,index):desc;
    }

    /**
     * 日期格式化
     * @return
     */
    private String getDatePattern(Field field){
        if(field==null){
            return null;
        }
        Annotation[] annotations = field.getAnnotations();
        if(annotations==null||annotations.length==0){
            return null;
        }
        Map<? extends Class<? extends Annotation>, Annotation> map =
                Arrays.stream(annotations).filter(elem -> elem.annotationType() == DateTimeFormat.class || elem.annotationType() == JsonFormat.class)
                        .collect(Collectors.toMap(Annotation::annotationType, e -> e));
        DateTimeFormat dateFormatAnnotation = (DateTimeFormat)map.get(DateTimeFormat.class);
        JsonFormat jsonFormatAnnotation = (JsonFormat)map.get(JsonFormat.class);
        if(dateFormatAnnotation==null&&jsonFormatAnnotation==null){
            return DATE_DEFAULT_FORMAT;
        }
        if(jsonFormatAnnotation!=null){
            return StringUtils.isEmpty(jsonFormatAnnotation.pattern())?DATE_DEFAULT_FORMAT:jsonFormatAnnotation.pattern();
        }
        if(dateFormatAnnotation!=null){
            return StringUtils.isEmpty(dateFormatAnnotation.pattern())?DATE_DEFAULT_FORMAT:dateFormatAnnotation.pattern();
        }
        return DATE_DEFAULT_FORMAT;
    }

    public String getClassPath() {
        return classPath;
    }

    public ExcelExportUtil setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public boolean isFullClassPathKey() {
        return fullClassPathKey;
    }

    public ExcelExportUtil setFullClassPathKey(boolean fullClassPathKey) {
        this.fullClassPathKey = fullClassPathKey;
        return this;
    }
}

