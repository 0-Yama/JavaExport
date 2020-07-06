package fr.export.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Exporter {
    private HashMap<String, Object[]> table;
    private Database database;
    private XSSFWorkbook document = new XSSFWorkbook();;
    public Exporter(Database db ,HashMap<String, Object[]> table){
        database = db;
        this.table = table;
    }
    public void createDocument(){
        try (FileOutputStream outputStream = new FileOutputStream("export.xlsx")) {
            document.write(outputStream);
        }catch(Exception e){

        }
    }



    public void addSheet(String name) {
        XSSFSheet sheet = document.createSheet(name);
        Row row = sheet.createRow(1);
        int rowCount    = 1;
        int columnCount = 0;

        for (Object field : table.get(name)) {
            Cell cell = row.createCell(++columnCount);
            if (field instanceof String) {
                cell.setCellValue((String) field);
            } else if (field instanceof Integer) {
                cell.setCellValue((Integer) field);
            }
        }
        int lineNumber = database.getRows(name);
        database.query("SELECT * FROM " + name);
        for (int i = 0; i < lineNumber; i++) {
            columnCount = 0;
            row = sheet.createRow(++rowCount);
            for (Object field : database.fetch(name)) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                    System.out.println((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                    System.out.println((Integer) field);
                } else if (field instanceof Boolean) {
                    cell.setCellValue((Boolean) field);
                    System.out.println((Boolean) field);
                } else if (field instanceof Time) {
                    cell.setCellValue((Time) field);
                    System.out.println((Time) field);
                }
            }
        }
    }
}
