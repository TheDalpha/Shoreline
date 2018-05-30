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
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import shoreline.be.Person;
import shoreline.be.Tasks;
import shoreline.be.User;
import shoreline.gui.model.AdminViewModel;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author Jesper
 */
public class UserViewController implements Initializable {

    private JFXTextField filePath;

    UserViewModel uvm;
    AdminViewModel avm;
    String outputFilename;
    String userName;
    String desc;
    List<String> outputfiles = new ArrayList<>();
    DatePicker datePicker = new DatePicker(LocalDate.now());

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
    private JFXListView<Tasks> taskView;
    @FXML
    private JFXButton startBtn;
    @FXML
    private JFXButton stopBtn;
    @FXML
    private JFXListView<File> convertedList;
    @FXML
    private JFXButton adminPanelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            avm = AdminViewModel.getInstance();
            uvm = UserViewModel.getInstance();
            avm = AdminViewModel.getInstance();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        fileNames();

    }

    @FXML
    private void chooseFile(ActionEvent event) throws Exception {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("Text File", "*.xlsx", "*.xml", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            Window stage = null;
            File selectedFiles = fileChooser.showOpenDialog(stage);
            Lview.getItems().add(selectedFiles);
        } catch (Exception e) {
            String desc = "Attempted to upload something wrong";
            String actionP = "Invalid file";
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), desc);
        }

    }

    public void setTaskList() {
        System.out.println(uvm.getTasks());
        taskView.setItems(uvm.getTasks());
    }

    public void fileNames() {
        Lview.setCellFactory(lView -> new ListCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                setText(file == null ? null : file.getName());
            }
        });
        taskView.setCellFactory(tView -> new ListCell<Tasks>() {
            @Override
            protected void updateItem(Tasks task, boolean empty) {
                super.updateItem(task, empty);
                setText(task == null ? null : task.getTaskName());
            }
        });
        convertedList.setCellFactory(cView -> new ListCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                setText(file == null ? null : file.getName());
            }
        });
    }

    public void setUserName(User person) {
        this.person = person;
        userName = person.getUsername();
        if (person.isAdmin() == true) {
            adminPanelBtn.setVisible(true);
            lblUser.setText(userName + "(Admin)");
        } else {
            adminPanelBtn.setVisible(false);
            lblUser.setText(userName);
        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        try {
            Stage stage1 = (Stage) logoutBtn.getScene().getWindow();
            stage1.close();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/shoreline/gui/view/LoginView.fxml"));
            root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            String desc = "failed to logout";
            String actionP = "Attempted to logout";
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), desc);
        }
    }

    @FXML
    private void configure(ActionEvent event) throws IOException, InvalidFormatException, Exception {
        try {
            if (Lview.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No File Selected.");
                alert.setHeaderText("File Selection Failed.");
                alert.setContentText("Please Select a File.");
                alert.showAndWait();
                alert.close();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/ConfigureView.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
                Stage stage = (Stage) confBtn.getScene().getWindow();
                Stage configView = new Stage();
                File file = Lview.getSelectionModel().getSelectedItem();
                ConfigureViewController configController = fxmlLoader.getController();
                configView.setTitle("Shoreline Configure Window");
                configView.setScene(new Scene(root));
                configController.setFileHeaders(file);
                configController.setUsername(userName);
                configController.setController(this);
                configView.show();
            }
        } catch (Exception e) {
            String desc = "No file chosen";
            String actionP = "Attempted to configure without a valid file";
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), desc);
        }
    }

    @FXML
    private void startConvert(ActionEvent event) throws IOException {
        try {
            Tasks task = taskView.getSelectionModel().getSelectedItem();
            uvm.convert(task);
            taskView.getItems().remove(task);
            File file = new File(task.getTaskName() + ".json");
            convertedList.getItems().add(file);
            outputfiles.add(task.getOutputFile() + "\\" + task.getTaskName() + ".json");
//            for (Tasks task : uvm.getTasks()) {
//                uvm.convert(task);
//                System.out.println(task.getHeaderMap());
//            }   io null         
        } catch (IOException | InvalidFormatException e) {
            String desc = "Not a valid file";
            String actionP = "Attempted to startConvert without a valid file";
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), desc);
        }
    }

    @FXML
    private void stopConvert(ActionEvent event) {
//        try
//        {
//            
//        } catch (Exception e) {
//                 desc = e.toString();
//                 String actionP = "Nothing happens";
//                 avm.addTraceLog(" ", actionP, userName, desc);
//        }
    }

    public String getFilenameWithoutExtention(String filename) {
        String[] fnameParts = filename.split("\\.");
        String lastExt = fnameParts[fnameParts.length - 1];
        return filename.substring(0, filename.length() - 1 - lastExt.length());
    }

    @FXML
    private void removeFile(ActionEvent event) {
        try {
            Lview.getItems().remove(Lview.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            String desc = "Attempted to remove nothing";
            String actionP = "Removed File" + ": " + Lview.getSelectionModel().getSelectedItem().getName();
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(Lview.getSelectionModel().getSelectedItem().getName(), actionP, userName, localDate.toString(), desc);
        }
    }

    @FXML
    private void openConvertedTask(MouseEvent event) throws IOException {
        try {
            for (int i = 0; i < outputfiles.size(); i++) {
                System.out.println(convertedList.getSelectionModel().getSelectedItem().getName());
                if (outputfiles.get(i).contains(convertedList.getSelectionModel().getSelectedItem().getName())) {
                    String fileName = outputfiles.get(i);
                    System.out.println(fileName);
                    File file = new File(fileName);
                    Desktop.getDesktop().open(file);

                }
            }
        } catch (IOException e) {
            String desc = "Attempted to open an invalid filepath";
            String actionP = "Attempted to open invalid file";
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(outputFilename, actionP, userName, localDate.toString(), desc);
        }
    }

    @FXML
    private void openAdminPanel(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoreline/gui/view/AdminView.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
        Stage adminView = new Stage();
        AdminViewController amController = fxmlLoader.getController();
        adminView.setTitle("Shoreline Admin Window");
        adminView.setScene(new Scene(root));
        adminView.show();
    }
}
