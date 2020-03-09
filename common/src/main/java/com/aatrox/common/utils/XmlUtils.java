package com.aatrox.common.utils;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @desc XML处理
 * @auth beiyuanbing
 * @date 2019-05-06 09:31
 **/
public class XmlUtils {
    public static String toXML(Object obj) throws Exception {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        //编码格式
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
        // 是否格式化生成的xml串
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        // 是否省略xm头声明信息
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        String xml = writer.toString();
        return xml.replaceFirst(" standalone=\"yes\"", "");
    }

    /**
     * XML转化为对象
     *
     * @param xml
     * @param valueType
     * @return
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXML(String xml, Class<T> valueType) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(valueType);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new StringReader(xml));
    }

    public static void main(String[] args) {
        /*XmlDemo xmlDemo=new XmlDemo().setBody(new BodyDemo().setTransDetails(Arrays.asList("1","2","3"))).setInfo(new HeadDemo());
        System.out.println(xmlDemo);
        try {
           String xml= XmlUtils.toXML(xmlDemo);
            System.out.println(xml);
            System.out.println(XmlUtils.fromXML(xml,XmlDemo.class));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
