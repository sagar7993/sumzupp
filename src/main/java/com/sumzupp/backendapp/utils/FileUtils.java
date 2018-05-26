package com.sumzupp.backendapp.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by akash.mercer on 20-Jul-17.
 */
public class FileUtils {

    public static XSSFWorkbook getWorkbookFromFile(File file) throws Exception {
        XSSFWorkbook xssfWorkbook;

        try {
            xssfWorkbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            throw e;
        } catch (InvalidFormatException e) {
            throw e;
        }

        return xssfWorkbook;
    }

    public static XSSFSheet getSheetFromWorkBook(XSSFWorkbook xssfWorkbook) {
        XSSFSheet xssfSheet = null;

        if (xssfWorkbook.getNumberOfSheets() > 0) {
            xssfSheet = xssfWorkbook.getSheetAt(0);
        }

        return xssfSheet;
    }

    public static void initializeStudentDataSheetHeader(XSSFSheet xssfSheet) {
        XSSFRow xssfRow = xssfSheet.getRow(0);

        XSSFCell usernameXssfCell = xssfRow.createCell(5);
        usernameXssfCell.setCellType(CellType.STRING);
        usernameXssfCell.setCellValue("Username");

        XSSFCell passwordXssfCell = xssfRow.createCell(6);
        passwordXssfCell.setCellType(CellType.STRING);
        passwordXssfCell.setCellValue("Password");
    }

    public static void initializeTeacherDataSheetHeader(XSSFSheet xssfSheet) {
        XSSFRow xssfRow = xssfSheet.getRow(0);

        XSSFCell usernameXssfCell = xssfRow.createCell(3);
        usernameXssfCell.setCellType(CellType.STRING);
        usernameXssfCell.setCellValue("Username");

        XSSFCell passwordXssfCell = xssfRow.createCell(4);
        passwordXssfCell.setCellType(CellType.STRING);
        passwordXssfCell.setCellValue("Password");
    }

    public static void writeToWorkbook(File file, File newFile, XSSFWorkbook xssfWorkbook) throws Exception {
        FileOutputStream fileOutputStream;

        if (file.exists()){
            fileOutputStream = new FileOutputStream(newFile);
        } else {
            fileOutputStream = new FileOutputStream(file);
        }

        xssfWorkbook.write(fileOutputStream);
        xssfWorkbook.close();
        fileOutputStream.close();

        newFile.delete();
    }
}
