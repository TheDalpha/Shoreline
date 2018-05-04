/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import shoreline.be.User;
import shoreline.gui.model.LoginViewModel;

/**
 * FXML Controller class
 *
 * @author ollie
 */
public class AdminViewController implements Initializable {

    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField tfPassword1;
    @FXML
    private JFXPasswordField tfPassword2;
    @FXML
    private JFXButton logOut;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> nameClm;
    
    LoginViewModel lvm;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        tableView.setItems(lvm.getAllUsers());
        
    }    

    @FXML
    private void Logout(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) logOut.getScene().getWindow();
        stage1.close();
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/gui/view/LoginView.fxml"));

        Scene scene = new Scene(root);
            

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void CreateUser(ActionEvent event) {
        
    }
    
}
