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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shoreline.be.User;
import shoreline.gui.model.LoginViewModel;
import shoreline.gui.model.UserModel;

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
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> nameClm;
    
    LoginViewModel lvm;
    UserModel usm;
    @FXML
    private TableView<User> userView;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem menuItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            usm = UserModel.getInstance();
            lvm = LoginViewModel.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        usm.loadUsers();
        userView.setItems(usm.getAllUsers());
        
        nameClm.setCellValueFactory(
                new PropertyValueFactory("username"));
        
        
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
    private void CreateUser(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        if(tfPassword1.getText().equals(tfPassword2.getText()) && !tfUsername.getText().isEmpty()) {
            Alert userCreatedAlert = new Alert(Alert.AlertType.INFORMATION);
            User user = new User();
            user.setUsername(tfUsername.getText());
            user.setCleanPassword(tfPassword1.getText());
            usm.createUser(user);
            userCreatedAlert.setTitle("Succes!");
            userCreatedAlert.setHeaderText("Succes!");
            userCreatedAlert.setContentText("User " + user.getUsername() + " has been created!");
            userCreatedAlert.showAndWait();
            userCreatedAlert.close();
            tfUsername.clear();
            tfPassword1.clear();
            tfPassword2.clear();
        } else {
            Alert createUserAlert = new Alert(Alert.AlertType.ERROR);
            createUserAlert.setContentText("Passwords don't match");
            createUserAlert.showAndWait();
            createUserAlert.close();
            
        }
        
    }

    @FXML
    private void deleteUser(ActionEvent event) {
       Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", ButtonType.YES, ButtonType.NO);
       deleteAlert.setTitle("Warning");
       deleteAlert.setContentText("Do you want to delete " + userView.getSelectionModel().getSelectedItem().getUsername() + " ?");
       deleteAlert.showAndWait();
       if(deleteAlert.getResult() == ButtonType.YES) {
       usm.deleteUser(userView.getSelectionModel().getSelectedItem());
       userView.getItems().remove(userView.getSelectionModel().getSelectedItem());
       usm.loadUsers();
       } else if (deleteAlert.getResult() == ButtonType.NO){
           deleteAlert.close();
       }
        
    }
    
}
