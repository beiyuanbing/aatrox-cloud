package com.aatrox.web.base.controller;

import com.aatrox.common.utils.DateUtil;
import com.aatrox.common.utils.ListUtil;
import com.aatrox.common.utils.StringUtils;
import com.aatrox.web.base.service.ExportExcelService;
import com.aatrox.web.base.util.ExcelUtils;
import com.aatrox.web.base.util.MathUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author aatrox
 * @desc 导出的controller类,方便进行导出
 * @date 2019/9/2
 */
public class ExcelController extends BaseController {
    @Resource
    private ExportExcelService exportExcelService;

    private HSSFWorkbook workbook = null;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void excelExport(String key, Map<String, Object> params, HttpServletResponse response) throws Exception {
        excelExport(key,false, params, response);
    }


    public void excelExport(String key,boolean fileNameDateEnd, Map<String, Object> params, HttpServletResponse response) throws Exception {
        Map<String, Object> excelMap = ExcelUtils.getConfigById(key);
        String fileName = "";
        workbook = new HSSFWorkbook();

        if (excelMap != null) {
            List<Map<String, Object>> template = (List<Map<String, Object>>) excelMap.get("columns");
            //bean的名字
            String beanName = (String) excelMap.get("beanName");
            //方法名
            fileName = (String) excelMap.get("fileName");
            //调用的方法名
            String methodName = (String) excelMap.get("methodName");
            String paramType = (String) excelMap.get("paramType");
            Class claz=null;
            if(StringUtils.isNotEmpty(paramType)){
                claz=Class.forName(paramType);
            }
            //如果使用getListBySqlMapsId,进行查询mybatis的mapper.xml的数据库方法，因此将此方法改成通用就变成如此了
            List contentList = this.exportExcelService.getListByBeanId(beanName,methodName,params,claz);
            handleData(template, contentList);

        }
        if(fileNameDateEnd&& StringUtils.isNotEmpty(fileName)){
            fileName+="--"+DateUtil.getDateString(new Date(),DateUtil.formate_yyyyMMddhhmmss);
        }
        exportExcel(workbook, response, fileName);
    }

    public void handleData(List<Map<String, Object>> template, List contentList) {
        //没有数据的情况下
        if(ListUtil.isEmpty(contentList)){
            HSSFSheet sheet = workbook.createSheet("sheet" + 0);
            createHeader(sheet, template);
            return;
        }
        Map<Integer, Integer> splistResourseIndexByWorkCount = MathUtil.getSplistResourseIndexByWorkCount(65500, contentList.size());
        int i = 0;
        for (Map.Entry<Integer, Integer> integerIntegerEntry : splistResourseIndexByWorkCount.entrySet()) {
            List list1 = contentList.subList(integerIntegerEntry.getKey(), integerIntegerEntry.getValue());
            JSONArray array = beanToJSON(list1, template);
            HSSFSheet sheet = workbook.createSheet("sheet" + i++);
            createHeader(sheet, template);
            createBook(sheet, array, template);
        }
    }

    /**
     * 转换成JSONARRAY
     *
     * @param list
     * @param
     * @return
     */
    public JSONArray beanToJSON(List<?> list, final List<Map<String, Object>> column) {
        return JSONArray.parseArray(JSON.toJSONString(list));
    }

    /**
     * 构建表头
     *
     * @param sheet
     * @param
     */
    @SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
    public void createHeader(HSSFSheet sheet, List<Map<String, Object>> list) {
        HSSFRow row = sheet.createRow(0);

        short index = 0;
        for (Map<String, Object> column : list) {
            String key = (String) column.keySet().toArray()[0];

            for (int kk = 0; kk < column.keySet().toArray().length; kk++) {
                String str = (String) column.keySet().toArray()[kk];
                if (!str.equals("defaultKey") && !str.equals("defaultDescription") && !str.equals("width") && !str.equals("enum")
                        && !str.equals("dataType")) {
                    key = str;
                    break;
                }
            }
            HSSFCell cell = row.createCell(index);
            cell.setCellValue((String) column.get(key));

            sheet.setColumnWidth(index, column.get("width") == null ? 200 : new BigDecimal((String) column.get("width")).intValue() * 36);
            index++;
        }
    }

    /**
     * 构建数据表格
     *
     * @param sheet
     * @param list
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void createBook(HSSFSheet sheet, JSONArray array, List<Map<String, Object>> list) {
        for (int i = 0; array != null && i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            HSSFRow row = sheet.createRow(i + 1);
            int index = 0;
            Class clazz;
            for (Map<String, Object> column : list) {
                String key = getKey(column);
                HSSFCell cell = row.createCell(index++);
                try {
                    if (column.containsKey("enum")) {
                        String enumStr = (String) column.get("enum");
                        String defaultKey = (String) column.get("defaultKey");
                        String defaultDescription = (String) column.get("defaultDescription");
                        if (key != null && (key.equals(defaultKey) || key.equals(defaultDescription))) {

                        }
                        for (int kk = 0; kk < column.keySet().toArray().length; kk++) {
                            String str = (String) column.keySet().toArray()[kk];
                            if (!str.equals("defaultKey") && !str.equals("defaultDescription") && !str.equals("width") && !str.equals("enum")) {
                                key = str;
                                break;
                            }
                        }
                        clazz = Class.forName(enumStr);

                        cell.setCellValue(this.getEnumValues(clazz, object.getString(key), defaultKey, defaultDescription));
                    } else if (column.containsKey("dataType")) {
                        String dataType = (String) column.get("dataType");
                        String dateFormat = (String) column.get("dateFormat");
                        String objectStr=null;
                        //日期的特别处理
                        if(dataType.equals("date")){
                            //日期指定格式化
                            dateFormat=StringUtils.isEmpty(dateFormat)?DateUtil.DEFAULT_FORMATE:dateFormat;
                            objectStr= DateUtil.getDateString(object.getDate(key),dateFormat);
                            if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
                                objectStr = "";
                            }
                            cell.setCellValue(objectStr);
                        } else if (dataType.equals("number")) {
                            //数字类型的处理
                            HSSFCellStyle cellStyle = this.workbook.createCellStyle();
                            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                            cell.setCellStyle(cellStyle);
                            objectStr = object.getString(key);
                            if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
                                objectStr = "0.0";
                            }
                            cell.setCellValue(Double.valueOf(objectStr));
                        }else {
                            //其他类型
                            objectStr = object.getString(key);
                            if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
                                objectStr = "";
                            }
                            cell.setCellValue(objectStr);
                        }
                    } else {
                        String objectStr = object.getString(key);
                        if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
                            objectStr = "";
                        }
                        cell.setCellValue(objectStr);
                    }
                } catch (Exception e) {
                    cell.setCellValue("");
                }
            }
        }
    }


    /**
     * 构建数据表格
     *
     * @param sheet
     * @param list
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void createBook(HSSFSheet sheet, JSONArray array, List<T> list, Class c) {
        for (int i = 0; array != null && i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            HSSFRow row = sheet.createRow(i + 1);
            int index = 0;
            Class clazz;
            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                Map<String, Object> column = (Map<String, Object>) iter.next();
                String key = getKey(column);
                HSSFCell cell = row.createCell(index++);
                try {
                    if (column.containsKey("enum")) {
                        String enumStr = (String) column.get("enum");
                        String defaultKey = (String) column.get("defaultKey");
                        String defaultDescription = (String) column.get("defaultDescription");
                        if (key != null && (key.equals(defaultKey) || key.equals(defaultDescription))) {

                        }
                        for (int kk = 0; kk < column.keySet().toArray().length; kk++) {
                            String str = (String) column.keySet().toArray()[kk];
                            if (!str.equals("defaultKey") && !str.equals("defaultDescription") && !str.equals("width") && !str.equals("enum")) {
                                key = str;
                                break;
                            }
                        }
                        clazz = Class.forName(enumStr);

                        cell.setCellValue(this.getEnumValues(clazz, object.getString(key), defaultKey, defaultDescription));
                    } else if (column.containsKey("dataType")) {
                        String dataType = (String) column.get("dataType");
                        if (dataType.equals("number")) {
                            HSSFCellStyle cellStyle = this.workbook.createCellStyle();
                            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                            cell.setCellStyle(cellStyle);
                        }
                        String objectStr = object.getString(key);
                        if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
                            objectStr = "";
                        }
                        cell.setCellValue(Double.valueOf(objectStr));
                    } else {
                        String objectStr = object.getString(key);
                        if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
                            objectStr = "";
                        }
                        cell.setCellValue(objectStr);
                    }
                } catch (Exception e) {
                    cell.setCellValue("");
                }
            }
        }
    }

    private String getKey(Map<String, Object> column) {
        String key = "";
        for (int kk = 0; kk < column.keySet().toArray().length; kk++) {
            String str = (String) column.keySet().toArray()[kk];
            if (!str.equals("defaultKey") && !str.equals("defaultDescription") && !str.equals("width") && !str.equals("enum")
                    && !str.equals("dataType")) {
                key = str;
                break;
            }
        }
        return key;
    }

    /**
     * 构建行
     *
     * @param row
     * @param keyList
     */
    @SuppressWarnings("unused")
    public void createRow(HSSFRow row, List<String> keyList, JSONObject object) {
        for (int i = 0; keyList != null && i < keyList.size(); i++) {
            HSSFCell cell = row.createCell(i);
        }
    }

    /**
     * 导出
     *
     * @param workbook
     * @param response
     * @param fileName
     * @throws Exception
     */
    public void exportExcel(HSSFWorkbook workbook, HttpServletResponse response, String fileName) throws Exception {
        if (fileName == null || fileName.equals("")) {
            response.setHeader("Content-disposition", "attachment; filename = " + "电子表格.xls" + "");
        } else {
            fileName = fileName + ".xls";
            response.setHeader("Content-disposition", "attachment; filename = " + new String(fileName.getBytes("GB2312"), "ISO8859_1") + "");
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream stream = response.getOutputStream();
        workbook.write(stream);
        stream.close();
    }

    @SuppressWarnings("rawtypes")
    private String getEnumValues(Class c, String key, String defaultKey, String defaultDescription) {
        String description = "";
        //判断是不是枚举
        if (c.isEnum()) {
            Object[] objs = c.getEnumConstants();
            for (Object obj : objs) {
                try {
                    Method m = obj.getClass().getDeclaredMethod("values", null);
                    Object[] result = (Object[]) m.invoke(obj, null);
                    List list = Arrays.asList(result);
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        Object objOne = it.next();
                        try {
                            Field code = objOne.getClass().getDeclaredField(defaultKey);
                            code.setAccessible(true);
                            Field codeDesc = objOne.getClass().getDeclaredField(defaultDescription);
                            codeDesc.setAccessible(true);
                            if (key != null && (key.equals(code.get(objOne))
                                    || key.equals(objOne.toString()))
                            ) {
                                description = (String) codeDesc.get(objOne);
                                break;
                            }
                        } catch (Exception e) {
                            Field codeDesc = objOne.getClass().getDeclaredField(defaultDescription);
                            codeDesc.setAccessible(true);
                            if (key != null && (key.equals(codeDesc.get(objOne))
                                    || key.equals(objOne.toString()))
                            ) {
                                description = (String) codeDesc.get(objOne);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }

        }
        return description;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    private static Map<String, Object> excelMaps = null;

    public static Map<String, Object> getExcelMaps() {
        return excelMaps;
    }

}
