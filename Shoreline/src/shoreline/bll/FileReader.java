/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 *
 * @author Jesper
 */
public class FileReader {
    
    
    public void readXLSX(String filePath) throws Exception {
        File file = new File(filePath);
        List<Object> overAll = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();
        
        for (int i = rowStart; i < rowEnd; i++) {
            Row row = sheet.getRow(i);
            
            Map<String, Object> map = new HashMap<>();
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                map.put(sheet.getRow(0).getCell(j).toString(), printCellValue(cell));
                System.out.println(sheet.getRow(0).getCell(j).getStringCellValue());
               
            }
            overAll.add(map);
        }
// {
//            Map<String, Object> map = new HashMap<String, Object>();
//            Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
//            while (cellIterator.hasNext()) {
//            for (Cell cell : row) {
//                String header = cellIterator.next().getStringCellValue();
//                map.put(header, printCellValue(cell));
//                
//                }
//            }
//            overAll.add(map);
//        }
      String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(overAll);
        System.out.println(json);
    }
    
    private Object printCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
//                System.out.println(cell.getBooleanCellValue());
                return cell.getBooleanCellValue();
            case STRING:
//                System.out.println(cell.getStringCellValue());
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                    String dateFormatted = dateFormat.format(date);
                    return dateFormatted;
                }
                return cell.getNumericCellValue();
            case FORMULA:
//                System..println(cell.getCellFormula());
                return cell.getCellFormula();
            case BLANK:
                String blank = "";
                return blank;
            case _NONE:
                String blank1 = "";
                return blank1;
            default:
//                System.out.println("");
        }
        return null;
    }

    public List<Object> getFileHeaders(File file) throws IOException, InvalidFormatException {
        List<Object> header = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            
            header.add(sheet.getRow(0).getCell(i).getStringCellValue());
            System.out.println(header);
        }
        return header;
    }
            
    
}