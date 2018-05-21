/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import com.fasterxml.jackson.core.JsonFactory;
import shoreline.be.Header;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Jesper
 */
public class JFileReader {

    List<Object> overAll;
    List<Object> Total;

    public void readXLSXAndConvertToJSON(String filePath, Map ja) throws Exception {

        File file = new File(filePath);

        overAll = new ArrayList<>();
//        XSSFWorkbook workbook1 = new XSSFWorkbook(filePath);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        int rowStart = sheet.getFirstRowNum() +1;
        int rowEnd = sheet.getLastRowNum();
        Iterator<Row> rowIterator = sheet.rowIterator();
        for (int i = rowStart; i < rowEnd; i++) {
        }
        for (Row row : sheet) {
            Map<String, Object> map = new HashMap<>();
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
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                return cell.getBooleanCellValue();

            case STRING:
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
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            Header headers = new Header();
            headers.setHeaderName(sheet.getRow(0).getCell(i).getStringCellValue());
            headers.setHeaderIndex(sheet.getRow(0).getCell(i).getColumnIndex());
            header.add(headers);
        }
        return header;
    }

    public void readCSVAndConvertToJSON(String filePoth) throws IOException {
        Total = new ArrayList<>();
//        CsvReader r = new CsvParser().reader(filePath);
        JsonFactory jfac = new JsonFactory();
        jfac.createParser(filePoth);
        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();
        
        // Read data from CSV file
        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(filePoth).readAll();
                
        readAll.add(Total);
    }
    
    
    public String CSV() throws JsonProcessingException {

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(Total);
        
    }

}
