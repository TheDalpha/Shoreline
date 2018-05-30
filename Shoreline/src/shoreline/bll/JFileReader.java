/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import shoreline.be.Header;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import shoreline.be.Tasks;

/**
 *
 * @author Jesper
 */
public class JFileReader {

    private Tasks ct;
    JSONObject jobj;
    JSONObject testobj;
    List<Object> Total;
    JSONArray jObList;

    public void readXLSXAndConvertToJSON(String filePath, Map<String, Header> ja, boolean oneLine) throws Exception {

        File file = new File(filePath);
        testobj = new JSONObject();
        jObList = new JSONArray();
        JSONObject planObjects = new JSONObject();
//        XSSFWorkbook workbook1 = new XSSFWorkbook(filePath);
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);
        int rowStart = sheet.getFirstRowNum() + 1;
        int rowEnd = sheet.getLastRowNum();

        for (int i = rowStart; i < rowEnd; i++) {
            Row row = sheet.getRow(i);

            ja.forEach((key, value) -> {
                if (key.equals("Latest Finish Date") || key.equals("Earliest Start Date") || key.equals("Latest Start Date") || key.equals("Estimated Time")) {
                    if (value.getHeaderIndex() == -1) {
                        planObjects.put(key, "");
                    } else {
                        Cell mValue = row.getCell(value.getHeaderIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        planObjects.put(key, printCellValue(mValue));
                    }
                } else if (value.getHeaderIndex() == -1) {
                    testobj.put(key, "");
                } else {
                    Cell mValue = row.getCell(value.getHeaderIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    testobj.put(key, printCellValue(mValue).toString());
                }
            });
            testobj.put("Planning", planObjects);
            jObList.put(jobj);
            if (oneLine) {
                break;
            }
        }
    }

    public String XLSXR() throws JsonProcessingException {

        return testobj.toString(4);

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
            headers.setHeaderName(sheet.getRow(0).getCell(i).toString());
            headers.setHeaderIndex(sheet.getRow(0).getCell(i).getColumnIndex());
            header.add(headers);
        }
        return header;
    }

    public void setTemplate(Map<String, Header> jobj) {
        this.jobj = new JSONObject();
        jobj.forEach((k, v) -> {
            this.jobj.put(k, v);
        });
    }

}
