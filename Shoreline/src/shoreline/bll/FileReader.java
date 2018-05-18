/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
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
        List<Object> overAll = new ArrayList<Object>();
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        
        for (Row row : sheet) {
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator<Cell> cellIterator = sheet.getRow(0).cellIterator();
            while (cellIterator.hasNext()) {
            for (Cell cell : row) {
                String header = cellIterator.next().getStringCellValue();
                map.put(header, printCellValue(cell));
                
                }
            }
            overAll.add(map);
        }
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
//                System.out.println(cell.getNumericCellValue());
                return cell.getNumericCellValue();
            case FORMULA:
//                System.out.println(cell.getCellFormula());
                return cell.getCellFormula();
            case BLANK:
                String blank = "";
                return blank;
            default:
//                System.out.println("");
        }
        return null;
    }
    
}
