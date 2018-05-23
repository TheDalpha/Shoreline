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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import shoreline.be.Admin;
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
    @FXML
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
    @FXML
    private JFXButton fileConvertingBtn;
    @FXML
    private JFXCheckBox adminCheckB;
    @FXML
    private Label lblUser;

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
    private void Logout(ActionEvent event) throws IOException
    {
        Stage stage1 = (Stage) logOut.getScene().getWindow();
        stage1.close();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/gui/view/LoginView.fxml"));
        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void CreateUser(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        if (adminCheckB.isSelected() && tfPassword1.getText().equals(tfPassword2.getText()) && !tfUsername.getText().isEmpty())
        {
            Alert adminCreatedAlert = new Alert(Alert.AlertType.INFORMATION);
            Admin admin = new Admin();
            admin.setUsername(tfUsername.getText());
            admin.setCleanPassword(tfPassword1.getText());
            avm.createAdmin(admin);
            adminCreatedAlert.setTitle("Succes!");
            adminCreatedAlert.setHeaderText("Succes!");
            adminCreatedAlert.setContentText("Admin " + admin.getUsername() + " has been created!");
            adminCreatedAlert.showAndWait();
            adminCreatedAlert.close();
            avm.loadAdmins();
            tfUsername.clear();
            tfPassword1.clear();
            tfPassword2.clear();

        } else if (tfPassword1.getText().equals(tfPassword2.getText()) && !tfUsername.getText().isEmpty())
        {
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
            usm.loadUsers();
            tfUsername.clear();
            tfPassword1.clear();
            tfPassword2.clear();
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
            usm.deleteUser(tableView.getSelectionModel().getSelectedItem());
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedItem());
            usm.loadUsers();
        } else if (deleteAlert.getResult() == ButtonType.NO)
        {
            deleteAlert.close();
        }
    }

    public void setUserName(Person person)
    {
        userName = person.getUsername();
        lblUser.setText(userName);
    }

    @FXML
    private void goToUserWin(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/UserView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
        Stage stage = (Stage) fileConvertingBtn.getScene().getWindow();
        stage.close();
        Stage userView = new Stage();
        UserViewController uvController = fxmlLoader.getController();
        userView.setTitle("Shoreline");
        userView.setScene(new Scene(root));
        uvController.setUserName(person);
        userView.show();
    }

}
