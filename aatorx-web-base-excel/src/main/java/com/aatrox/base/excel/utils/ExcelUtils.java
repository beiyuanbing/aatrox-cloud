package com.aatrox.base.excel.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author aatrox
 * @desc
 * @date 2020/12/29
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

    private final static String defaultDescription = "desc";

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

    public static void addConfigMap(String key, Map<String, Object> valueMap) throws Exception {
        excelMaps.put(key,valueMap);
    }

    public static void addAllConfigMap(Map<String, Object> resultMap) throws Exception {
        excelMaps.putAll(resultMap);
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
        // ClassPathResource resource = new ClassPathResource("excel/excel.xml");
        InputStream inputStream = this.getClass().getResourceAsStream("/excel/excel.xml");
        //InputStream inputStream = new FileInputStream(resource.getFile());
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> lst = root.elements("excel");
        for (int i = 0; lst != null && i < lst.size(); i++) {
            Element element = lst.get(i);
            //找到满足条件的节点
            excelMaps.putAll(this.parseElement(element));
        }
    }

    public Map<String,Object> parseElement( Element element){
        Map<String, Object> subMap = new HashMap<>();
        //找到满足条件的节点
        subMap.put("sqlMapsId", element.attribute("sqlMapsId") != null ? element.attribute("sqlMapsId").getText() : null);
        subMap.put("fileName", element.attribute("fileName").getText());
        subMap.put("beanName", element.attribute("beanName") != null ? element.attribute("beanName").getText() : null);
        subMap.put("methodName", element.attribute("methodName") != null ? element.attribute("methodName").getText() : null);
        subMap.put("paramType", element.attribute("paramType") != null ? element.attribute("paramType").getText() : null);
        subMap.put("columns", getColumnByElement(element));
        Map<String,Object> resultMap=new HashMap<>(1);
        resultMap.put(element.attribute("id").getText(),subMap);
        return resultMap;
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
        Map<String, Object> columns = new LinkedHashMap<>();
        for (int j = 0; columnList != null && j < columnList.size(); j++) {
            Element column = columnList.get(j);
            Map<String, Object> cell = new HashMap<>();
            cell.put("key",column.attribute("key").getText());
            cell.put(column.attribute("key").getText(), column.getText() != null ? column.getText() : "");
            cell.put("width", column.attribute("width").getText());
            if (column.attribute("dataType") != null) {
                cell.put("dataType", column.attribute("dataType").getText());
            }
            if (column.attribute("dateFormat") != null) {
                cell.put("dateFormat", column.attribute("dateFormat").getText());
            }
            if (column.attribute("enum") != null) {
                cell.put("enum", column.attribute("enum").getText());
                cell.put("enumDescription", column.attribute("enumDescription") == null ? defaultDescription : column.attribute("enumDescription").getText());

            }
            resultList.add(cell);
        }

        return resultList;
    }

    public static void main(String[] args) throws DocumentException {
        String exelStr="<excel id=\"BusinessAreaVOExport\" fileName=\"商圈信息\">\n" +
                "\t<column key=\"id\" width=\"100\">开发商id</column>\n" +
                "\t<column key=\"businessAreaName\" width=\"100\">开发商名称</column>\n" +
                "\t<column key=\"useStatus\" width=\"80\" enumDescription=\"desc\" enum=\"com.saas.newhouse.service" +
                ".outreach.apilist.enums.OutreachUseStatusEnum\">公司启用状态</column>\n" +
                "\t<column key=\"distributionDateEnd\" dataType=\"date\" dateFormat=\"yyyy-MM-dd\" " +
                "width=\"120\">分销结束时间</column>\n" +
                "</excel>";
        Document document = DocumentHelper.parseText(exelStr);
        Element rootElement = document.getRootElement();
        Map<String, Object> stringObjectMap = new ExcelUtils().parseElement(rootElement);
        System.out.println(stringObjectMap);
    }


}

