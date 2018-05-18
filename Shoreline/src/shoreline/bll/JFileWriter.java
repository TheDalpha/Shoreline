/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author Jesper
 */
public class JFileWriter {

    
    public void convertToJson(String path, String json) throws IOException {
        File file = new File(path);
        
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        fw.write(json);
        
        fw.close();
        
    }
    
}