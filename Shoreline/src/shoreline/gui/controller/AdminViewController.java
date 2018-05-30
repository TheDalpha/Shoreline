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
    LoginViewModel lvm;
    UserViewModel usm;
    AdminViewModel avm;
    private Person person;
    String userName;

    @FXML
    private JFXTextField tfUsername;
    @FXML
    private JFXPasswordField tfPassword1;
    @FXML
    private JFXPasswordField tfPassword2;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> nameClm;
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
    @FXML
    private JFXCheckBox adminCheckB;
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
        // Loads all users
        usm.loadUsers();
        // Sets the items in the tableview of all users
        tableView.setItems(usm.getAllUsers());
        // Loads all logs
        avm.loadLoggins();
        // Sets the items in tableview of logs
        logView.setItems(avm.getAllLoggins());

        // Sets all columns cell value
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
    }

    /**
     * Makes a new user object and sets the user.
     *
     * @param event
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
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
            // Loads all users and clear all textfields
            usm.loadUsers();
            tfUsername.clear();
            tfPassword1.clear();
            tfPassword2.clear();
            // Sets the data for the log
            String actionP = "User " + tfUsername.getText() + " was created";
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

    /**
     * Removes the selected user
     *
     * @param event
     */
    @FXML
    private void removeUser(ActionEvent event)
    {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Delete", ButtonType.YES, ButtonType.NO);
        deleteAlert.setTitle("Warning");
        deleteAlert.setContentText("Do you want to delete " + tableView.getSelectionModel().getSelectedItem().getUsername() + " ?");
        deleteAlert.showAndWait();
        if (deleteAlert.getResult() == ButtonType.YES)
        {
            usm.deleteUser(tableView.getSelectionModel().getSelectedItem());
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            usm.loadUsers();
            // Sets the data for the log
            String actionP = "User " + tableView.getSelectionModel().getSelectedItem().getUsername() + " was Removed";
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), " ");
        } else if (deleteAlert.getResult() == ButtonType.NO)
        {
            deleteAlert.close();
        }
    }

    /**
     * Sets the active username
     *
     * @param person
     */
    public void setUserName(Person person)
    {
        userName = person.getUsername();
    }

}
