/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import shoreline.be.Header;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static jdk.nashorn.internal.objects.NativeArray.map;
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
public class JFileReader {
    
    List<Object> overAll;

    public void readXLSXAndConvertToJSON(String filePath) throws Exception {

        File file = new File(filePath);

        overAll = new ArrayList<Object>();     
//        XSSFWorkbook workbook1 = new XSSFWorkbook(filePath);
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
//            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(overAll);
//            System.out.println(json);
}
    


    public String XLSXR() throws JsonProcessingException {

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(overAll);
    }

    private Object printCellValue(Cell cell) {
        switch (cell.getCellTypeEnum())
        {
            case BOOLEAN:
                return cell.getBooleanCellValue();
                
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC: 
                if(DateUtil.isCellDateFormatted(cell))
                {
                Date date = cell.getDateCellValue();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                    String dateFormatted = dateFormat.format(date);
                    return dateFormatted;
                }
                return cell.getNumericCellValue();
            case FORMULA: 
                return cell.getCellFormula();
            case BLANK:
                String blank = "";
                return blank;
            case _NONE:
                String blank1 = "";
                return blank1;
            default:
                
        }
        return null;
        }

    public List<Header> getFileHeaders(File file) throws IOException, InvalidFormatException {
        List<Header> header = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
        { 
            Cell cell = row.getCell(i);
            Header headers = new Header();
            headers.setHeaderName(sheet.getRow(0).getCell(i).getStringCellValue());
            headers.setHeaderIndex(sheet.getRow(0).getCell(i).getColumnIndex());
            header.add(headers);
        }
        return header;
     }
    
    
}
    
