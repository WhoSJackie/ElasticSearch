package com.wang.util;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.InputStream;
import java.util.*;

public class LargeExcelUtil {

    private List<List<String>> rowContents=new ArrayList<>();
    private  SheetHandler sheetHandler;

    public List<List<String>> getRowContents() {
        return rowContents;
    }
    public void setRowContents(List<List<String>> rowContents) {
        this.rowContents = rowContents;
    }

    public SheetHandler getSheetHandler() {
        return sheetHandler;
    }
    public void setSheetHandler(SheetHandler sheetHandler) {
        this.sheetHandler = sheetHandler;
    }
    //处理一个sheet
    public void processOneSheet(String filename) throws Exception {
        InputStream sheet1=null;
        OPCPackage pkg =null;
        try {
            pkg = OPCPackage.open(filename);
            XSSFReader r = new XSSFReader(pkg);
            SharedStringsTable sst = r.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst);
            sheet1 = r.getSheet("rId1");
            InputSource sheetSource = new InputSource(sheet1);
            parser.parse(sheetSource);
            setRowContents(sheetHandler.getRowContents());
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            if(pkg!=null){
                pkg.close();
            }
            if(sheet1!=null){
                sheet1.close();
            }
        }
    }
    //处理多个sheet
    public void processAllSheets(String filename) throws Exception {
        OPCPackage pkg =null;
        InputStream sheet=null;
        try{
            pkg=OPCPackage.open(filename);
            XSSFReader r = new XSSFReader( pkg );
            SharedStringsTable sst = r.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst);
            Iterator<InputStream> sheets = r.getSheetsData();
            while(sheets.hasNext()) {
                System.out.println("Processing new sheet:\n");
                sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            if(pkg!=null){
                pkg.close();
            }
            if(sheet!=null){
                sheet.close();
            }
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser =
                XMLReaderFactory.createXMLReader(
                        "com.sun.org.apache.xerces.internal.parsers.SAXParser"
                );
        setSheetHandler(new SheetHandler(sst));
        ContentHandler handler = (ContentHandler) sheetHandler;
        parser.setContentHandler(handler);
        return parser;
    }


    public static void main(String[] args) throws Exception {
        LargeExcelUtil largeExcelUtil = new LargeExcelUtil();
        File file = new File("C:\\Users\\jiami\\Desktop\\test.xlsx");
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getPath());
        System.out.println(file.length()/(1024*1024));
        largeExcelUtil.processOneSheet(file.getPath());
        List<List<String>> rowContents = largeExcelUtil.rowContents;
        System.out.println(rowContents.size());
        for (int i = 0; i < rowContents.size(); i++) {
            System.out.println(rowContents.get(i).size());
            System.out.println(rowContents.get(i));
//            if (rowContents.get(i).size()!=30){
//                System.out.println(rowContents.get(i).size());
//                System.out.println(rowContents.get(i));
//            }

        }
        // System.out.println(rowContents.get(0));

    }



}
