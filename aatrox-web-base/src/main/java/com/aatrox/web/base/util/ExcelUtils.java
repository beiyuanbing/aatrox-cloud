package com.aatrox.web.base.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * EXCEL 配置文件 解析类
 *
 * @author:谷辉
 * @date:2012-4-13下午05:17:09
 */
public class ExcelUtils {
    @SuppressWarnings("unused")
    private static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private static Map<String, Object> excelMaps = null;

    public static Map<String, Object> getExcelMaps() {
        return excelMaps;
    }

    public static void setExcelMaps(Map<String, Object> excelMaps) {
        ExcelUtils.excelMaps = excelMaps;
    }

    private final static String defaultKey         = "key";
    private final static String defaultDescription = "description";

    private ExcelUtils() {
    }

    private static ExcelUtils instance = null;

    private static ExcelUtils getInstance() {
        if (instance == null) {
            instance = new ExcelUtils();
        }
        return instance;
    }

    /**
     * 取值
     *
     * @param key
     * @return
     * @throws Exception
     */
    @SuppressWarnings("all")
    public static Map<String, Object> getConfigById(String key) throws Exception {
        if (excelMaps == null) {
            getInstance().getDataByXML();
        }
        return (Map<String, Object>) excelMaps.get(key);
    }

    /**
     * 初始化(解析XML配置文件)
     *
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "unused"})
    private void getDataByXML() throws Exception {
        excelMaps = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputStream inputStream =new FileInputStream( ResourceUtils.getFile("classpath:excel/excel.xml"));
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> lst = root.elements("excel");
        for (int i = 0; lst != null && i < lst.size(); i++) {
            Map<String, Object> resultMap = new HashMap<>();
            Element element = lst.get(i);
            //找到满足条件的节点
            resultMap.put("sqlMapsId", element.attribute("sqlMapsId")!=null?element.attribute("sqlMapsId").getText():null);
            resultMap.put("fileName", element.attribute("fileName").getText());
            resultMap.put("beanName", element.attribute("beanName")!=null?element.attribute("beanName").getText():null);
            resultMap.put("methodName", element.attribute("methodName")!=null?element.attribute("methodName").getText():null);
            resultMap.put("columns", getColumnByElement(element));

            excelMaps.put(element.attribute("id").getText(), resultMap);
        }
    }

    /**
     * 读取节点的内容
     *
     * @param element
     * @return
     */
    @SuppressWarnings({"unused", "unchecked"})
    public static List<Map<String, Object>> getColumnByElement(Element element) {
        //读取节点的内容
        List<Element> columnList = element.elements("column");
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> columns = new HashMap<>();
        for (int j = 0; columnList != null && j < columnList.size(); j++) {
            Element column = columnList.get(j);
            Map<String, Object> cell = new HashMap<>();
            cell.put(column.attribute("key").getText(), column.getText() != null ? column.getText() : "");
            cell.put("width", column.attribute("width").getText());
            if (column.attribute("dataType") != null) {
                cell.put("dataType", column.attribute("dataType").getText());
            }
            if (column.attribute("enum") != null) {
                cell.put("enum", column.attribute("enum").getText());
                cell.put("defaultKey", column.attribute("enumKey") == null ? defaultKey : column.attribute("enumKey").getText());
                cell.put("defaultDescription", column.attribute("enumDescription") == null ? defaultDescription : column.attribute("enumDescription").getText());

            }
            resultList.add(cell);
        }

        return resultList;
    }


}
