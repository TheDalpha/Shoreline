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
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import shoreline.be.User;
import shoreline.bll.UserManager;
import shoreline.gui.model.LoginViewModel;
import shoreline.gui.model.UserModel;

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
    
    LoginViewModel lvm;
    UserModel um;
    
    
//    String user = "user";
//    String password = "password";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lvm = new LoginViewModel();
        try
        {
            um = UserModel.getInstance();
        } catch (SQLException ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void loginBtn(ActionEvent event)
    {
        checkUser = txtUserName.getText().toString();
        checkPw = txtPassword.getText().toString();
        
        lvm.loadUsers();
        List<User> users = lvm.getAllUsers();
        User user = new User();
        
        if (checkUser.equals(user.getUsername()) && checkPw.equals(user.getPassword()))
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
    
//    public void setModel(LoginViewModel lvm) {
//        this.lvm = lvm;
//    }
    
}
