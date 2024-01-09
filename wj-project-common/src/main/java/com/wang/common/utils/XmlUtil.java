package com.wang.common.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;

public class XmlUtil {


    public static Document loadXmlFile(String filePath){
        Document doc = null;
        try{
            SAXReader saxReader = new SAXReader();
            doc = saxReader.read(filePath);
        } catch(Exception e){
            e.printStackTrace();
        }
        return doc;
    }

    public static void parseExcelImportXml(String filePath){
        Document doc = loadXmlFile(filePath);
        Element root = doc.getRootElement();
        Iterator<Element> elementIterator = root.elementIterator();
        while(elementIterator.hasNext()){
            // 获取根节点
            Element next = elementIterator.next();
            //当节点等于对应Id,进行处理
            if ("EAST4_E01".equals(next.attribute("id").getValue())){
                // 获取sheet节点
                Element sheet = next.element("sheet");
                System.out.println("index->:"+sheet.attribute("index").getValue());
                System.out.println("name->:"+sheet.attribute("name").getValue());
            }
        }
    }

    public static void main(String[] args) {
        parseExcelImportXml("C:\\Users\\jiami\\Desktop\\import-excel.xml");
    }

}
