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

    // Constructor
    public Tasks(File inputFile, String outputFile, String taskName, Map<String, Header> headerMap) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.taskName = taskName;
        this.headerMap = headerMap;
    }

    // Get the input file
    public File getInputFile() {
        return inputFile;
    }

    // Sets the input file
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    // Get the output file
    public String getOutputFile() {
        return outputFile;
    }

    // Sets the output file
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    // Get the task name
    public String getTaskName() {
        return taskName;
    }

    // Sets the task name
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    // Gets the map of headers
    public Map<String, Header> getHeaderMap() {
        return headerMap;
    }

    // Sets the map of headers
    public void setHeaderMap(Map<String, Header> headerMap) {
        this.headerMap = headerMap;
    }
    
    
}
