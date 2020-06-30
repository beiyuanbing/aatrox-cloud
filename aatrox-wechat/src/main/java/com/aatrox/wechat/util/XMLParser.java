package com.aatrox.wechat.util;

import com.aatrox.wechat.wxpay.util.Util;
import com.alibaba.fastjson.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author aatrox
 * @desc
 * @date 2020/1/6
 */
public class XMLParser {
    public static <T> T getResponseFromXML(String xmlString, Class<T> clazz) throws ParserConfigurationException, IOException, SAXException {
        JSONObject map = getJsonObject(xmlString);
        if(clazz.isAssignableFrom(Map.class)){
            return (T)map.getInnerMap();
        }else{
            return map.toJavaObject(clazz);
        }
    }

    private static JSONObject getJsonObject(String xmlString) throws ParserConfigurationException, SAXException, IOException {
        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setExpandEntityReferences(false);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = Util.getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        JSONObject map = new JSONObject();
        for(int i=0;i<allNodes.getLength();i++){
            Node node = allNodes.item(i);
            if (node instanceof Element) {
                map.put(node.getNodeName(), node.getTextContent());
            }
        }
        return map;
    }
}
