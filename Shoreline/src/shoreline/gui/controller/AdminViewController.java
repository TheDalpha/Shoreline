/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shoreline.be.Loggin;
import shoreline.be.Person;
import shoreline.be.User;
import shoreline.gui.model.AdminViewModel;
import shoreline.gui.model.LoginViewModel;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author ollie
 */
public class AdminViewController implements Initializable
{

    DatePicker datePicker = new DatePicker(LocalDate.now());
    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField tfPassword1;
    @FXML
    private JFXPasswordField tfPassword2;
    private JFXButton logOut;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> nameClm;

    LoginViewModel lvm;
    UserViewModel usm;
    AdminViewModel avm;
    private Person person;
    String userName;
    @FXML
    private TableView<Loggin> logView;
    @FXML
    private TableColumn<String, User> userClm;
    @FXML
    private TableColumn<String, Loggin> descriptionClm;
    @FXML
    private TableColumn<String, Loggin> whenClm;
    @FXML
    private TableColumn<String, Loggin> fileNameClm;
    @FXML
    private TableColumn<String, Loggin> errorClm;
    private JFXButton fileConvertingBtn;
    @FXML
    private JFXCheckBox adminCheckB;
    private Label lblUser;
    @FXML
    private JFXButton closeBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            avm = AdminViewModel.getInstance();
            usm = UserViewModel.getInstance();
            lvm = LoginViewModel.getInstance();
        } catch (SQLException ex)
        {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        usm.loadUsers();
        tableView.setItems(usm.getAllUsers());
        avm.loadLoggins();
        logView.setItems(avm.getAllLoggins());

        nameClm.setCellValueFactory(
                new PropertyValueFactory("username"));
        userClm.setCellValueFactory(
                new PropertyValueFactory("username"));
        descriptionClm.setCellValueFactory(
                new PropertyValueFactory("action"));
        fileNameClm.setCellValueFactory(
                new PropertyValueFactory("filename"));
        errorClm.setCellValueFactory(
                new PropertyValueFactory("error"));
        whenClm.setCellValueFactory(
                new PropertyValueFactory("date"));
//        setUserName(person);
    }

    @FXML
    private void CreateUser(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        if (tfPassword1.getText().equals(tfPassword2.getText()) && !tfUsername.getText().isEmpty())
        {
            Alert userCreatedAlert = new Alert(Alert.AlertType.INFORMATION);
            User user = new User();
            user.setAdmin(adminCheckB.isSelected());
            user.setUsername(tfUsername.getText());
            user.setCleanPassword(tfPassword1.getText());
            usm.createUser(user);
            userCreatedAlert.setTitle("Succes!");
            userCreatedAlert.setHeaderText("Succes!");
            userCreatedAlert.setContentText("User " + user.getUsername() + " has been created!");
            userCreatedAlert.showAndWait();
            userCreatedAlert.close();
            String actionP = "User " + tfUsername.getText() + " was created";
            usm.loadUsers();
            tfUsername.clear();
            tfPassword1.clear();
            tfPassword2.clear();
            LocalDate localDate = datePicker.getValue();
            System.out.println(userName);
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), " ");
        } else
        {
            Alert createUserAlert = new Alert(Alert.AlertType.ERROR);
            createUserAlert.setContentText("Passwords don't match");
            createUserAlert.showAndWait();
            createUserAlert.close();

        }

    }

    @FXML
    private void removeUser(ActionEvent event)
    {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", ButtonType.YES, ButtonType.NO);
        deleteAlert.setTitle("Warning");
        deleteAlert.setContentText("Do you want to delete " + tableView.getSelectionModel().getSelectedItem().getUsername() + " ?");
        deleteAlert.showAndWait();
        if (deleteAlert.getResult() == ButtonType.YES)
        {
            String actionP = "User " + tableView.getSelectionModel().getSelectedItem().getUsername() + " was Removed";
            usm.deleteUser(tableView.getSelectionModel().getSelectedItem());
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            usm.loadUsers();
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), " ");
        } else if (deleteAlert.getResult() == ButtonType.NO)
        {
            deleteAlert.close();
        }
    }

    public void setUserName(Person person)
    {
        userName = person.getUsername();
    }

    @FXML
    private void close(ActionEvent event)
    {
        Stage stage1 = (Stage) closeBtn.getScene().getWindow();
        stage1.close();
    }

}
