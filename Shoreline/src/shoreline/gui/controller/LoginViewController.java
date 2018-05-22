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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import shoreline.be.Admin;
import shoreline.be.Loggin;
import shoreline.be.Person;
import shoreline.be.User;
import shoreline.gui.model.LoginViewModel;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author Lars
 */
public class LoginViewController implements Initializable {
    private Person person; 
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
    UserViewModel um;
    boolean Users = false;
    boolean admuns = false;
    
    
    
//    String user = "user";
//    String password = "password";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Users = false;
        admuns = false;
        try
        {
            um = UserViewModel.getInstance();
            lvm = LoginViewModel.getInstance();
        } catch (SQLException ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }    

    
    @FXML
    private void loginBtn(ActionEvent event) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        
//        txtPassword.setOnKeyPressed(value);
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        
        lvm.loadAdmins();
        List<Admin> admins = lvm.getAllAdmins();
        
        for (int j = 0; j < admins.size(); j++) {
            Admin admin = admins.get(j);
            
            if (username.equals(admin.getUsername()) && lvm.authenticate(password, admin.getEncryptedPassword(), admin.getSalt()) && admuns == false) {
                admuns = true;
                lblMessage.setText("Admin Successful");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/AdminView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.close();
                Stage adminView = new Stage();
                AdminViewController amController=fxmlLoader.getController();
                adminView.setTitle("Shoreline Admin Window");
                adminView.setScene(new Scene(root));
                adminView.show();
            }
        }
        
        if (admuns == false)
        {
        lvm.loadUsers();
        List<User> users = lvm.getAllUsers();
        
        for (int i = 0; i < users.size(); i++) {
        User user = users.get(i);
           
        if (username.equals(user.getUsername()) && lvm.authenticate(password, user.getEncryptedPassword(), user.getSalt()) && Users == false)
        {
            Users = true;
            lblMessage.setText("Successful");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/UserView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.close();
                Stage userView = new Stage();
                UserViewController uvController=fxmlLoader.getController();
                userView.setTitle("Shoreline");
                userView.setScene(new Scene(root));
                uvController.setUserName(user);
                userView.show();
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

    @FXML
    private void login(KeyEvent event) throws NoSuchAlgorithmException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeySpecException, InvalidKeySpecException
    {   
        if(event.getCode().toString().equals("ENTER"))
    {
        
    
    
        event.getCode().equals(KeyCode.ENTER);
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        
        lvm.loadAdmins();
        List<Admin> admins = lvm.getAllAdmins();
        
        for (int j = 0; j < admins.size(); j++) {
            Admin admin = admins.get(j);
            
            if (username.equals(admin.getUsername()) && lvm.authenticate(password, admin.getEncryptedPassword(), admin.getSalt()) && admuns == false) {
                admuns = true;
                lblMessage.setText("Admin Successful");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/AdminView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.close();
                Stage adminView = new Stage();
                AdminViewController amController=fxmlLoader.getController();
                adminView.setTitle("Shoreline Admin Window");
                adminView.setScene(new Scene(root));
                adminView.show();
            }
        }
        
        if (admuns == false)
        {
        lvm.loadUsers();
        List<User> users = lvm.getAllUsers();
        
        for (int i = 0; i < users.size(); i++) {
        User user = users.get(i);
           
        if (username.equals(user.getUsername()) && lvm.authenticate(password, user.getEncryptedPassword(), user.getSalt()) && Users == false)
        {
            Users = true;
            lblMessage.setText("Successful");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/UserView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                stage.close();
                Stage userView = new Stage();
                UserViewController uvController=fxmlLoader.getController();
                userView.setTitle("Shoreline");
                userView.setScene(new Scene(root));
                uvController.setUserName(user);
                userView.show();
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
    }
}

