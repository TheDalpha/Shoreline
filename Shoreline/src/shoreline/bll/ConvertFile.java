/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
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
 * @author Jesper Riis
 */
public class ConvertFile implements Runnable{
    
    private Tasks ct;
    
    public ConvertFile(Tasks task) {
        this.ct = task;
    }
    
    public void run() {
        try {
            File file = new File(ct.getInputFile().getPath());
            System.out.println(ct.getInputFile().getPath());
            File fileOutput = new File(ct.getOutputFile() + "/" + ct.getTaskName() + ".json");
            FileWriter jsonWriter = new FileWriter(fileOutput);
            JSONObject job = new JSONObject();
            JSONArray jObL = new JSONArray();
            JSONObject planObjects = new JSONObject();
//        XSSFWorkbook workbook1 = new XSSFWorkbook(filePath);
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            int rowStart = sheet.getFirstRowNum() + 1;
            int rowEnd = sheet.getLastRowNum();

            for (int i = rowStart; i < rowEnd; i++) {
                Row row = sheet.getRow(i);

                ct.getHeaderMap().forEach((key, value) -> {
                    if (key.equals("Latest Finish Date") || key.equals("Earliest Start Date") || key.equals("Latest Start Date") || key.equals("Estimated Time")) {
                        if (value.getHeaderIndex() == -1) {
                            planObjects.put(key, "");
                        } else {
                            Cell mValue = row.getCell(value.getHeaderIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            planObjects.put(key, printCellValue(mValue).toString());
                        }
                    } else if (value.getHeaderIndex() == -1) {
                        job.put(key, "");
                    } else {
                        Cell mValue = row.getCell(value.getHeaderIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        job.put(key, printCellValue(mValue).toString());
                    }
                });
                job.put("Planning", planObjects);
                jObL.put(job);
            }
            jsonWriter.write(jObL.toString(4));
            jsonWriter.close();
        } catch (IOException ex) {

        } catch (InvalidFormatException ex) {
            Logger.getLogger(JFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(JFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
}
