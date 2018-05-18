/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author ollie
 */
public class ConfigureViewController implements Initializable
{
    
    UserViewModel uvm;

    @FXML
    private Label lblUser;
    @FXML
    private JFXComboBox<String> savedCombo;
    @FXML
    private ListView<String> selectedList;
    @FXML
    private JFXButton saveConfBtn;
    @FXML
    private JFXButton addAttributeBtn;
    @FXML
    private ListView<?> attributeView;
    @FXML
    private JFXButton cancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            uvm = UserViewModel.getInstance();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ConfigureViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    



    @FXML
    private void saveConfiguration(ActionEvent event)
    {
    }

    @FXML
    private void addAtribute(ActionEvent event)
    {
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException
    {
        Stage stage1 = (Stage) cancelBtn.getScene().getWindow();
        stage1.close();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/gui/view/UserView.fxml"));
        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setFileHeaders(File file) throws IOException, InvalidFormatException {
            
        selectedList.getItems().addAll(uvm.getFileHeaders(file).toString());
    }
    
}
