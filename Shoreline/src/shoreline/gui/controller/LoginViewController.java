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
import shoreline.be.Admin;
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
    
//    private String checkUser, checkPw;
    @FXML
    private Label lblMessage;
    
    LoginViewModel lvm;
    UserModel um;
    boolean Users = false;
    boolean admuns = false;
    
    
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
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        
        lvm.loadAdmins();
        List<Admin> admins = lvm.getAllAdmins();
        
        for (int j = 0; j < admins.size(); j++) {
            Admin admin = admins.get(j);
            
            if (username.equals(admin.getaUsername()) && password.equals(admin.getaPassword()) && admuns == false) {
                admuns = true;
                lblMessage.setText("Admin Successful");
            }
        }
        
        if (admuns == false)
        {
        lvm.loadUsers();
        List<User> users = lvm.getAllUsers();
        
        for (int i = 0; i < users.size(); i++) {
        User user = users.get(i);
        
        
        if (username.equals(user.getUsername()) && password.equals(user.getPassword()) && Users == false)
        {
            Users = true;
            lblMessage.setText("Successful");
        }
        }
        if (Users == false)
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
    
//    public void setModel(LoginViewModel lvm) {
//        this.lvm = lvm;
//    }
    
}

