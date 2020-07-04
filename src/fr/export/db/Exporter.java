package fr.export.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private HashMap<String, Object[]> table= new HashMap<String, Object[]>(){{
        put("avion"     , new Object[] {"id","model","groupe","type","place","disponibilité", "stationement",
                "loueur","image","durée location","réservoir","consomation"});

        put("facture"   , new Object[] {"n° Facture","nom membre","prénom membre","n° Tel","n° Service","Prix","Adresse facturation"});

        put("hangar"    , new Object[] {"Id","Etat","Dimension","Capacité"});

        put("membre"    , new Object[] {"Id","Mail","Mot de passe","Prénom","Nom","Age","Ville","Code Postal","Client","Abonées"});

        put("historique", new Object[] {"Id","Id Service","Nom Service","Prix Service","Horraire début","Horraire fin","Type","Date"});

        put("personel"  , new Object[] {"Id","Mail","Nom","Prénom","Age","Adresse","Code Postal","Ville","Mot de Passe","Poste"});

        put("service"   , new Object[] {"Id","Nom","Prix","Horraire début","Horraire fin","Type","Date","Accompagnant"});

        put("ulm"       , new Object[] {"Id","Disponible","Loueur","Stationement","durée Location","Reservoir","Consomation"});
    }};
    private Database database;
    private XSSFWorkbook document = new XSSFWorkbook();;
    public Exporter(Database db){
        database = db;
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

        int columnCount = 0;

        for (Object field : table.get(name)) {
            Cell cell = row.createCell(++columnCount);
            if (field instanceof String) {
                cell.setCellValue((String) field);
            } else if (field instanceof Integer) {
                cell.setCellValue((Integer) field);
            }
        }
        database.query("SELECT * FROM" + name);
        while(true){
            for (Object field : database.fetch(name)) {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
            try {
                database.next();
            }catch (Exception e){
                break;
            }
        }
    }
}
