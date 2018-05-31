/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.dal;

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
    
    /**
     * Constructor
     * @param task 
     */
    public ConvertFile(Tasks task) {
        this.ct = task;
    }
    
    /**
     * Reads through the file and put it in JSONObjects and then writes the file.
     */
    public void run() {
        try {
            File file = new File(ct.getInputFile().getPath());
            File fileOutput = new File(ct.getOutputFile() + "/" + ct.getTaskName() + ".json");
            FileWriter jsonWriter = new FileWriter(fileOutput);
            
            
            JSONArray jObL = new JSONArray();
            
            
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            int rowStart = sheet.getFirstRowNum() + 1;
            int rowEnd = sheet.getLastRowNum();

            for (int i = rowStart; i < rowEnd; i++) {
                Row row = sheet.getRow(i);
                JSONObject job = new JSONObject();
                JSONObject planObjects = new JSONObject();

                ct.getHeaderMap().forEach((key, value) -> {
                    if (key.equals("Latest Finish Date") || key.equals("Earliest Start Date") || key.equals("Latest Start Date") || key.equals("Estimated Time")) {
                        if (value.getHeaderIndex() == -1) {
                            planObjects.put(key, "");
                        } else {
                            Cell cell = row.getCell(value.getHeaderIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            planObjects.put(key, printCellValue(cell).toString());
                        }
                    } else if (value.getHeaderIndex() == -1) {
                        job.put(key, "");
                    } else {
                        Cell cell = row.getCell(value.getHeaderIndex(), Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        job.put(key, printCellValue(cell).toString());
                    }
                });
                job.put("Planning", planObjects);
                jObL.put(job);
            }
            jsonWriter.write(jObL.toString(4) + System.lineSeparator());
            jsonWriter.close();
        } catch (IOException ex) {

        } catch (InvalidFormatException ex) {
            Logger.getLogger(TemplateFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(TemplateFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Checks to see what the current cell datatype is
     * Then return it as its datatype value
     * @param cell
     * @return cell
     */
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
