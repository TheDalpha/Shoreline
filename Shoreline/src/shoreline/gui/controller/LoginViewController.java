/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Lars
 */
public class LoginViewController implements Initializable {

    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXPasswordField txtPassword;
    
    private String checkUser, checkPw;
    @FXML
    private Label lblMessage;
    
    String user = "user";
    String password = "password";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void loginBtn(ActionEvent event)
    {
        checkUser = txtUserName.getText().toString();
        checkPw = txtPassword.getText().toString();
        if (checkUser.equals(user) && checkPw.equals(password))
        {
            lblMessage.setText("Successful");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed to login.");
            alert.setHeaderText("Login failed.");
            alert.setContentText("Wrong username or password");
            alert.showAndWait();
            alert.close();
        }
    }
    
}
