package com.aatrox.base.excel;

import com.aatrox.base.excel.kernel.ExportExcelService;
import com.aatrox.base.excel.utils.ExcelExportUtil;
import com.aatrox.base.excel.utils.ExcelUtils;
import com.aatrox.common.utils.DateUtil;
import com.aatrox.common.utils.EnumStrUtils;
import com.aatrox.common.utils.ListUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author aatrox
 * @desc
 * @date 2020/12/29
 */
@Component
public class ExcelSubUnit {
    /**
     * 默认的sheetName
     **/
    private static final String DEFAULT_SHEET_NAME = "sheet0";
    @Resource
    private ExportExcelService exportExcelService;
    private HSSFWorkbook workbook = null;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void excelExport(String key, Object params, HttpServletResponse response) throws Exception {
        excelExport(key, false, params, response);
    }

    /**
     * 简单的导出
     *
     * @param key
     * @param fileNameDateEnd
     * @param params
     * @param response
     * @throws Exception
     */
    public void excelExport(String key, boolean fileNameDateEnd, Object params, HttpServletResponse response) throws Exception {
        workbook = new HSSFWorkbook();
        this.excelExport(workbook, key, fileNameDateEnd, params, response);
    }

    /**
     * 自定义workBook
     *
     * @param workbook
     * @param key
     * @param fileNameDateEnd
     * @param params
     * @param response
     * @throws Exception
     */
    public void excelExport(HSSFWorkbook workbook, String key, boolean fileNameDateEnd, Object params,
                            HttpServletResponse response) throws Exception {
        String fileName = "";
        fileName = handleExcelKernel(key, params, fileName, false);
        fileName = this.getFileName(fileNameDateEnd, fileName);
        outputExcel(workbook, response, fileName);
    }

    public void excelExport(String key, List contentList, HttpServletResponse response) throws Exception {
        Map<String, Object> excelMap = ExcelUtils.getConfigById(key);
        String fileName = "";
        workbook = new HSSFWorkbook();
        if (excelMap != null) {
            //方法名
            fileName = (String) excelMap.get("fileName");
            List<Map<String, Object>> template = (List<Map<String, Object>>) excelMap.get("columns");
            handleData(template, contentList);
        }
        outputExcel(workbook, response, fileName);
    }

    /**
     * 传递个class类
     * @param claz
     * @param contentList
     * @param response
     * @throws Exception
     */
    public void excelExport(Class claz, List contentList, HttpServletResponse response) throws Exception {
        Map<String, Object> excelMap = this.getExcelMap(claz);
        String fileName = "数据导出";
        workbook = new HSSFWorkbook();
        if (excelMap != null) {
            //方法名
            fileName = (String) excelMap.get("fileName");
            List<Map<String, Object>> template = (List<Map<String, Object>>) excelMap.get("columns");
            handleData(template, contentList);
        }
        outputExcel(workbook, response, fileName);
    }

    /**
     * 传递个class类
     * @param claz
     * @param contentList
     * @param ignoreFieldList 忽略的字段
     * @param response
     * @throws Exception
     */
    public void excelExport(Class claz, List contentList,List<String> ignoreFieldList,HttpServletResponse response) throws Exception {
        Map<String, Object> excelMap = this.getExcelMap(claz, Optional.ofNullable(ignoreFieldList).orElse(new ArrayList<>()));
        String fileName = "数据导出";
        workbook = new HSSFWorkbook();
        if (excelMap != null) {
            //方法名
            fileName = (String) excelMap.get("fileName");
            List<Map<String, Object>> template = (List<Map<String, Object>>) excelMap.get("columns");
            handleData(template, contentList);
        }
        outputExcel(workbook, response, fileName);
    }

    public synchronized Map<String,Object> getExcelMap(Class claz)throws Exception{
        return this.getExcelMap(claz,new ArrayList<>());

    }

    /**
     * 增加忽略字段的设置
     * @param claz
     * @param ignoreFieldList
     * @return
     * @throws Exception
     */
    public synchronized Map<String,Object> getExcelMap(Class claz,List<String> ignoreFieldList)throws Exception{
        Map<String, Object> excelMap = ExcelUtils.getConfigById(claz.getName());
        if(excelMap==null) {
            ExcelExportUtil excelExportUtil = new ExcelExportUtil().setFullClassPathKey(true).setIgnoreFieldList(ignoreFieldList);
            Map<String, Object> templateMap = excelExportUtil.getTemplateMap(claz);
            ExcelUtils.addAllConfigMap(templateMap);
        }
        excelMap = ExcelUtils.getConfigById(claz.getName());
        return excelMap;

    }

    /**
     * 此处提供数据，不用做二次查询的数据导出
     *
     * @param response
     * @param fileName
     * @param keyList
     * @param dataList
     * @throws Exception
     */
    public void multiSheetsExport(HttpServletResponse response, String fileName, List<String> keyList,
                                  List<List> dataList) throws Exception {
        this.multiSheetsExport(response, fileName, false, keyList, dataList);
    }

    /**
     * 此处提供数据，不用做二次查询的数据导出
     *
     * @param response
     * @param fileName
     * @param fileNameDateEnd
     * @param keyList
     * @param dataList
     * @throws Exception
     */
    public void multiSheetsExport(HttpServletResponse response, String fileName, boolean fileNameDateEnd,
                                  List<String> keyList, List<List> dataList) throws Exception {
        workbook = new HSSFWorkbook();
        Assert.notEmpty(keyList, "keyList不允许为空");
        Stream.iterate(0, i -> i + 1).limit(keyList.size()).forEach(i -> {
            try {
                Map<String, Object> excelMap = ExcelUtils.getConfigById(keyList.get(i));
                Assert.notNull(excelMap, "跳出这层不处理这个key");
                String sheetName = (String) excelMap.get("fileName");
                List<Map<String, Object>> template = (List<Map<String, Object>>) excelMap.get("columns");
                handleData(template, dataList.get(i), sheetName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fileName = this.getFileName(fileNameDateEnd, fileName);
        //导出excel
        outputExcel(workbook, response, fileName);

    }

    /**
     * 多文件导出，一定要指定文件名
     *
     * @param response
     * @param fileName
     * @param paramsObject
     * @param keysList
     * @throws Exception
     */
    public void multiSheetsExport(String fileName, Object paramsObject, List<String> keysList,
                                  HttpServletResponse response) throws Exception {
        this.multiSheetsExport(fileName, false, paramsObject, keysList, response);
    }

    /**
     * 多sheet单excel导出操作
     *
     * @param response
     * @param fileName
     * @param fileNameDateEnd
     * @param paramsObject
     * @param keysList
     * @throws Exception
     */
    public void multiSheetsExport(String fileName, boolean fileNameDateEnd, Object paramsObject,
                                  List<String> keysList, HttpServletResponse response) throws Exception {
        workbook = new HSSFWorkbook();
        Assert.notEmpty(keysList, "keysList不允许为空");
        for (String key : keysList) {
            //此处是处理核心
            handleExcelKernel(key, paramsObject, fileName, true);
        }
        fileName = this.getFileName(fileNameDateEnd, fileName);
        //导出excel
        outputExcel(workbook, response, fileName);

    }

    /**
     * 导出
     *
     * @param workbook
     * @param response
     * @param fileName
     * @throws Exception
     */
    public void outputExcel(HSSFWorkbook workbook, HttpServletResponse response, String fileName) throws Exception {
        if (fileName == null || fileName.equals("")) {
            response.setHeader("Content-disposition", "attachment; filename = " + "电子表格.xls" + "");
        } else {
            fileName = fileName + ".xls";
            //response.setHeader("Content-disposition", "attachment; filename = " + new String(fileName.getBytes(
            //      "GB2312"), "ISO8859_1") + "");
            response.setHeader("Content-disposition", "attachment; filename = " + URLEncoder.encode(fileName, "UTF-8"));
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream stream = response.getOutputStream();
        workbook.write(stream);
        stream.close();
    }

    /**
     * 从excel.xml进行做相关的文档处理,并返回文件名
     *
     * @param key
     * @param params
     * @param fileName
     * @return
     * @throws Exception
     */
    private String handleExcelKernel(String key, Object params, String fileName, boolean multiSheet) throws Exception {
        Map<String, Object> excelMap = ExcelUtils.getConfigById(key);
        if (excelMap != null) {
            List<Map<String, Object>> template = (List<Map<String, Object>>) excelMap.get("columns");
            //bean的名字
            String beanName = (String) excelMap.get("beanName");
            //方法名
            String sheetName = (String) excelMap.get("fileName");
            //多sheets导出不替换文件名
            fileName = multiSheet ? fileName : sheetName;
            //调用的方法名
            String methodName = (String) excelMap.get("methodName");
            String paramType = (String) excelMap.get("paramType");
            Class<?> aClass = null;
            if (StringUtils.isNotEmpty(paramType)) {
                aClass = Class.forName(paramType);
            }
            //如果使用getListBySqlMapsId,进行查询mybatis的mapper.xml的数据库方法，因此将此方法改成通用就变成如此了
            List contentList = exportExcelService.getListByBeanId(beanName, methodName, params, aClass);
            handleData(template, contentList, multiSheet ? sheetName : null);
        }
        return fileName;
    }

    /**
     * 获取文件名
     *
     * @param fileNameDateEnd
     * @param fileName
     * @return
     */
    private String getFileName(boolean fileNameDateEnd, String fileName) {
        if (fileNameDateEnd && StringUtils.isNotEmpty(fileName)) {
            fileName += "--" + DateUtil.format(new Date(), DateUtil.DEFAULT_DATE_MINUTE_1);
        }
        return fileName;
    }


    /**
     * 增加sheetNum的数据处理
     *
     * @param template
     * @param contentList
     */
    private void handleData(List<Map<String, Object>> template, List contentList, String sheetName) {
        HSSFSheet sheet = workbook.createSheet(StringUtils.isNotEmpty(sheetName) ? sheetName : DEFAULT_SHEET_NAME);
        createHeader(sheet, template);
        if (ListUtil.isEmpty(contentList)) {
            return;
        }
        JSONArray array = beanToJSON(contentList);
        //填充数据
        createBook(sheet, array, template);
    }

    /**
     * 增加sheetNum的数据处理
     *
     * @param template
     * @param contentList
     */
    private void handleData(List<Map<String, Object>> template, List contentList) {
        this.handleData(template, contentList, DEFAULT_SHEET_NAME);
    }

    /**
     * 转换成JSONARRAY
     *
     * @param list
     * @param
     * @return
     */
    private JSONArray beanToJSON(List<?> list) {
        return JSONArray.parseArray(JSON.toJSONString(list));
    }

    /**
     * 构建表头
     *
     * @param sheet
     * @param
     */
    @SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
    private void createHeader(HSSFSheet sheet, List<Map<String, Object>> list) {
        HSSFRow row = sheet.createRow(0);
        short index = 0;
        for (Map<String, Object> column : list) {
            String key = this.getKey(column);
            HSSFCell cell = row.createCell(index);
            cell.setCellValue((String) column.get(key));
            sheet.setColumnWidth(index, column.get("width") == null ? 200 : new BigDecimal((String) column.get("width"
            )).intValue() * 36);
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
    private void createBook(HSSFSheet sheet, JSONArray array, List<Map<String, Object>> list) {
        for (int i = 0; array != null && i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            HSSFRow row = sheet.createRow(i + 1);
            int index = 0;
            for (Map<String, Object> column : list) {
                String key = getKey(column);
                HSSFCell cell = row.createCell(index++);
                try {
                    if (column.containsKey("enum")) {
                        this.enumCellDeal(object, column, key, cell);
                    } else if (column.containsKey("dataType")) {
                        this.dataTypeCellDeal(object, column, key, cell);
                    } else {
                        this.defaultCellDeal(object, key, cell);
                    }
                } catch (Exception e) {
                    cell.setCellValue("");
                }
            }
        }
    }

    /**
     * 关于默认的类型处理理
     *
     * @param object
     * @param key
     * @param cell
     * @throws ClassNotFoundException
     */
    private void defaultCellDeal(JSONObject object, String key, HSSFCell cell) {
        String objectStr = object.getString(key);
        if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
            objectStr = "";
        }
        cell.setCellValue(objectStr);
    }

    /**
     * 关于枚举类型的述职处理
     *
     * @param object
     * @param column
     * @param key
     * @param cell
     * @throws ClassNotFoundException
     */
    private void enumCellDeal(JSONObject object, Map<String, Object> column, String key, HSSFCell cell) throws ClassNotFoundException {
        String enumStr = (String) column.get("enum");
        String enumDescription = (String) column.get("enumDescription");
        Class clazz = Class.forName(enumStr);
        cell.setCellValue(this.getEnumValues(clazz, object.getString(key), enumDescription));
    }

    /**
     * 指定了数据类型的字段处理
     *
     * @param object
     * @param column
     * @param key
     * @param cell
     */
    private void dataTypeCellDeal(JSONObject object, Map<String, Object> column, String key, HSSFCell cell) {
        String dataType = (String) column.get("dataType");
        String dateFormat = (String) column.get("dateFormat");
        String objectStr = null;
        //日期的特别处理
        if (dataType.equals("date")) {
            dateFormat = StringUtils.isEmpty(dateFormat) ? DateUtil.DEFAULT_DATEIME : dateFormat;
            objectStr = DateUtil.format(object.getDate(key), dateFormat);
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
        } else {
            //其他类型
            objectStr = object.getString(key);
            if (objectStr == null || objectStr.equals("") || objectStr.equals("{}")) {
                objectStr = "";
            }
            cell.setCellValue(objectStr);
        }
    }

    /**
     * 获取从excel过来的key值处理
     *
     * @param column
     * @return
     */
    private String getKey(Map<String, Object> column) {
        return (String) column.get("key");
    }

    /**
     * 构建行
     *
     * @param row
     * @param keyList
     */
    @SuppressWarnings("unused")
    private void createRow(HSSFRow row, List<String> keyList, JSONObject object) {
        for (int i = 0; keyList != null && i < keyList.size(); i++) {
            HSSFCell cell = row.createCell(i);
        }
    }

    /**
     * 获取枚举类型的值
     *
     * @param c
     * @param value
     * @param enumDescription
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String getEnumValues(Class c, String value, String enumDescription) {
        String description = "";
        //判断是不是枚举
        if (!c.isEnum()) {
            return description;
        }
        description = EnumStrUtils.getEnumStr(c, value, enumDescription);
        return description;
    }
}
