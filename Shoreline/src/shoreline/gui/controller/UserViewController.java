/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author Jesper
 */
public class UserViewController implements Initializable {

    @FXML
    private JFXTextField filePath;

    UserViewModel uvm;
    @FXML
    private ListView<String> Lview;
  


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            uvm = UserViewModel.getInstance();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    

    @FXML
    private void chooseFile(ActionEvent event) throws Exception {
        {
            FileChooser fileChooser = new FileChooser();
            Window stage = null;
//            File file = fileChooser.showOpenDialog(stage);
//            filePath.setText(file.getPath());
            uvm.setFilePath(filePath.getText());
            fileChooser.setInitialDirectory(new File("C:\\Users"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
            if (selectedFiles != null) {
            for (int i = 0; i < selectedFiles.size(); i++) {
            Lview.getItems().add(selectedFiles.get(i).getName());
//            File file = fileChooser.showSaveDialog(null);
//            FileSystems.getDefault().
//            Files.copy(file.toPath(), 
                    }
            
            } 

        }
    
    }
    @FXML
    private void convertToJson(ActionEvent event) {
        uvm.convertToJson(filePath.getText());
    }
   
}
