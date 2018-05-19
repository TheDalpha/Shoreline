/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
    String outputFilename;
    @FXML
    private ListView<File> Lview;
    @FXML
    private Label lblUser;
    private Person person;
    @FXML
    private JFXButton logoutBtn;
    @FXML
    private JFXButton confBtn;
    @FXML
    private JFXListView<String> taskView;
    @FXML
    private JFXButton startBtn;
    @FXML
    private JFXButton stopBtn;

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
        fileNames();

    }

    @FXML
    private void chooseFile(ActionEvent event) throws Exception {
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("Text File", "*.xlsx", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            Window stage = null;
//            File file = fileChooser.showOpenDialog(stage);
//            filePath.setText(file.getPath());
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
            if (selectedFiles != null) {

                DirectoryChooser dirch = new DirectoryChooser();
                File outputDirectory = dirch.showDialog(null);
                CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING};
                for (File selectedFile : selectedFiles) {
                    Lview.getItems().add(selectedFile);
//                    uvm.setFilePath(selectedFile.getPath());
                    outputFilename = Paths.get(outputDirectory.getAbsolutePath(), getFilenameWithoutExtention(selectedFile.getName()) + ".json").toString();
                    System.out.println(selectedFile.getAbsolutePath());
                    Files.copy(Paths.get(selectedFile.getAbsolutePath()), Paths.get(outputDirectory.getAbsolutePath() + "\\" + selectedFile.getName()), options);
                    // lblUser.setText(selectedFile.getPath());
                }

            }

        }

    }

    public void fileNames() {
        Lview.setCellFactory(lView -> new ListCell<File>() {
        @Override
        protected void updateItem(File file, boolean empty) {
        super.updateItem(file, empty);
        setText(file == null ? null : file.getName());
    }
    });
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

    @FXML
        private void configure(ActionEvent event) throws IOException, InvalidFormatException {
            if (Lview.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No File Selected.");
                alert.setHeaderText("File Selection Failed.");
                alert.setContentText("Please Select a File.");
                alert.showAndWait();
                alert.close();
            }
            else {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/ConfigureView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
        Stage stage = (Stage) confBtn.getScene().getWindow();
        stage.close();
        Stage configView = new Stage();
        File file = Lview.getSelectionModel().getSelectedItem();
        ConfigureViewController configController = fxmlLoader.getController();
        configView.setTitle("Shoreline Configure Window");
        configView.setScene(new Scene(root));
        configController.setFileHeaders(file);
        configView.show();
            }
    }

    @FXML
        private void startConvert(ActionEvent event) throws IOException {
                    String json = uvm.XLSXR();
        System.out.println(json);

            uvm.convertToJson(outputFilename, json);
        }

    @FXML
        private void stopConvert(ActionEvent event) {
    }

    public String getFilenameWithoutExtention(String filename) {
        String[] fnameParts = filename.split("\\.");
        String lastExt = fnameParts[fnameParts.length - 1];
        return filename.substring(0, filename.length() - 1 - lastExt.length());
    }

    @FXML
    private void removeFile(ActionEvent event)
    {
        Lview.getItems().remove(Lview.getSelectionModel().getSelectedItem());
    }

}
