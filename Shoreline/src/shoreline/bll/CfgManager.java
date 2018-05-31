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
import shoreline.be.Configuration;
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
    CfgDAO cfgDAO;
    ConvertThread cThread = new ConvertThread();
    TemplateFile tfile = new TemplateFile();
    
    /**
     * Constructor
     */
    public CfgManager() {
        this.cfgDAO = new CfgDAO();
    }
    
    /**
     * Gets all configs
     * @return all config
     */
    public List<Configuration> getAllConfig() {
        return cfgDAO.getAllConfigs();
    }

    /**
     * Sends information on the config
     * @param config 
     */
    public void configSave(Configuration config) {
        cfgDAO.configSave(config);
    }

    /**
     * Sends information on the header
     * @param header 
     */
    public void headerSave(Header header) {
        cfgDAO.headerSave(header);
    }

    /**
     * Sends information on config and header
     * @param config
     * @param header 
     */
    public void saveAll(Configuration config, Header header) {
        cfgDAO.saveAll(config, header);
    }

    /**
     * Sends information on the task
     * @param task 
     */
    public void convert(Tasks task) {
        cThread.convert(task);
    }

    /**
     * Sends information on the map
     * @param jobj 
     */
    public void setTemplate(Map<String, Header> jobj) {
        tfile.setTemplate(jobj);
    }

    /**
     * @return string
     * @throws JsonProcessingException 
     */
    public String XLSXR() throws JsonProcessingException {
        return tfile.XLSXR();
    }

    /**
     * Sends information on the file
     * @param file
     * @return List
     * @throws IOException
     * @throws InvalidFormatException 
     */
    public List<Header> getFileHeaders(File file) throws IOException, InvalidFormatException {
        return tfile.getFileHeaders(file);
    }

    /**
     * Sends information on file, map and boolean
     * @param filePath
     * @param ja
     * @param oneLine
     * @throws Exception 
     */
    public void readFirstLine(String filePath, Map<String, Header> ja, boolean oneLine) throws Exception {
        tfile.readFirstLine(filePath, ja, oneLine);
    }

    public void deleteConfig(Configuration config) {
        cfgDAO.deleteConfigRelations(config);
        cfgDAO.deleteHeader(config);
        cfgDAO.deleteConfig(config);
    }
}
