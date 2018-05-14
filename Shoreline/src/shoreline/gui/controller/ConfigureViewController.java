/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ollie
 */
public class ConfigureViewController implements Initializable
{

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
        // TODO
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
    
}
