/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;

import java.io.File;
import java.util.Map;

/**
 *
 * @author Jesper Riis
 */
public class Tasks {
    
    File inputFile;
    String outputFile;
    String taskName;
    Map<String, Header> headerMap;

    public Tasks(File inputFile, String outputFile, String taskName, Map<String, Header> headerMap) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.taskName = taskName;
        this.headerMap = headerMap;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Map<String, Header> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, Header> headerMap) {
        this.headerMap = headerMap;
    }
    
    
}
