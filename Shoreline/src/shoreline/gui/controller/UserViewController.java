/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import shoreline.be.Person;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author Jesper
 */
public class UserViewController implements Initializable {

    private JFXTextField filePath;

    UserViewModel uvm;
    @FXML
    private ListView<String> Lview;
    @FXML
    private Label lblUser;
    private Person person;
    @FXML
    private JFXButton logoutBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            uvm = UserViewModel.getInstance();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void chooseFile(ActionEvent event) throws Exception {
        {
            FileChooser fileChooser = new FileChooser();
                                FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("Text File", "*.xlsx", "*.xml");
                        fileChooser.getExtensionFilters().add(extFilter);
            Window stage = null;
//            File file = fileChooser.showOpenDialog(stage);
//            filePath.setText(file.getPath());
//            uvm.setFilePath(filePath.getText());
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+"/Desktop"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
            if (selectedFiles != null) {

                    DirectoryChooser dirch = new DirectoryChooser();
                    File file = dirch.showDialog(null);
                    CopyOption[] options = new CopyOption[] {StandardCopyOption.REPLACE_EXISTING};
                    for (File selectedFile : selectedFiles) {
                        Lview.getItems().add(selectedFile.getName());
                        System.out.println(file.getAbsolutePath()+selectedFile.getName());
                        System.out.println(selectedFile.getAbsolutePath());
                        Files.copy(Paths.get(selectedFile.getAbsolutePath()), Paths.get(file.getAbsolutePath()+ "\\" + selectedFile.getName()), options);
                    }
              
                

            }

        }

    }

    public void setUserName(Person person) {
        this.person = person;
        String Username = person.getUsername();
        lblUser.setText(Username);
    }
//    private void convertToJson(ActionEvent event) {
//        uvm.convertToJson(filePath.getText());
//    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) logoutBtn.getScene().getWindow();
        stage1.close();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/gui/view/LoginView.fxml"));
        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
