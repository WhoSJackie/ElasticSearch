package com.wang.common.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;

public class ExcelUtil {

    public static void handleExcel(String filename){
        File file = new File(filename);
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i= sheet.getFirstRowNum()+2;i<sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(1);
                System.out.println(cell1.getStringCellValue()+"->"+cell2.getStringCellValue());
                System.out.println("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        handleExcel("C:\\Users\\jiami\\Desktop\\demo.xlsx");
    }

}
