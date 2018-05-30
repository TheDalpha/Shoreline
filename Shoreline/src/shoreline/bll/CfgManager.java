/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import shoreline.be.Attribute;
import shoreline.be.Header;
import shoreline.be.Tasks;
import shoreline.dal.CfgDAO;
import shoreline.dal.ConvertThread;
import shoreline.dal.TemplateFile;

/**
 *
 * @author ollie
 */
public class CfgManager
{
    CfgDAO udao;
    ConvertThread cThread = new ConvertThread();
    TemplateFile tfile = new TemplateFile();
    
    public CfgManager() {
        this.udao = new CfgDAO();
    }
    
    public List<Attribute> getAllAttributes() {
        return udao.getAllConfigs();
    }

    public void configSave(Attribute config) {
        udao.configSave(config);
    }

    public void headerSave(Header header) {
        udao.headerSave(header);
    }

    public void saveAll(Attribute config, Header header) {
        udao.saveAll(config, header);
    }

    public void convert(Tasks task) {
        cThread.convert(task);
    }

    public void setTemplate(Map<String, Header> jobj) {
        tfile.setTemplate(jobj);
    }

    public String XLSXR() throws JsonProcessingException {
        return tfile.XLSXR();
    }

    public List<Header> getFileHeaders(File file) throws IOException, InvalidFormatException {
        return tfile.getFileHeaders(file);
    }

    public void readFirstLine(String filePath, Map<String, Header> ja, boolean oneLine) throws Exception {
        tfile.readFirstLine(filePath, ja, oneLine);
    }
}
