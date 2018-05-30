/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONObject;
import shoreline.be.Attribute;
import shoreline.gui.model.CfgModel;
import shoreline.be.Header;
import shoreline.be.Tasks;
import shoreline.gui.model.AdminViewModel;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author ollie
 */
public class ConfigureViewController implements Initializable {

    ObservableList<String> attList = FXCollections.observableArrayList("SiteName", "Asset Serial Number", "Type", "External Work Order", "System Status", "User Status", "Created On", "Created By", "Name", "Priority", "Status", "Latest Finish Date", "Earliest Start Date", "Latest Start Date", "Estimated Time");
    List<Header> alist = new ArrayList<>();
    Map<String, Header> headerMap = new HashMap<>();
    JSONObject jobj = new JSONObject();
    File file;
    String outputFile;
    String userName;
    String desc;
    UserViewModel uvm;
    CfgModel cfgM;
    UserViewController uvc;
    AdminViewModel avm;
    DatePicker datePicker = new DatePicker(LocalDate.now());

    @FXML
    private Label lblUser;
    @FXML
    private JFXComboBox<Attribute> savedCombo;
    @FXML
    private ListView<Header> selectedList;
    @FXML
    private JFXButton saveConfBtn;
    @FXML
    private ListView<String> attributeView;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXComboBox<String> attCB;
    @FXML
    private JFXButton attBtn;
    @FXML
    private ContextMenu contMenu;
    @FXML
    private JFXTextArea previewArea;
    @FXML
    private JFXButton addToTaskBtn;
    @FXML
    private JFXButton chooseDestinationBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            uvm = UserViewModel.getInstance();
            cfgM = CfgModel.getInstance();
            avm = AdminViewModel.getInstance();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ConfigureViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbUpdate();
        configNames();
        attCB.setItems(attList);
        headerNames();
        attributeView.getItems().addAll(attList);
        previewArea.setEditable(false);
    }

    @FXML
    private void saveConfiguration(ActionEvent event) throws Exception {
        try {
        Attribute config = new Attribute();

        TextInputDialog nameDialog = new TextInputDialog("");
        nameDialog.setTitle("Set configuration name");
        nameDialog.setHeaderText("Set configuration name");
        nameDialog.setContentText("Please set a configuration name");
        if (nameDialog.showAndWait().isPresent()) {
            config.setConfigurationName(nameDialog.getResult());
            cfgM.configSave(config);
            for (Header header : alist) {
                for (int i = 0; i < attributeView.getItems().size(); i++) {
                    if (attributeView.getItems().get(i).contains(header.getHeaderName())) {
                        cfgM.headerSave(header);
                        cfgM.saveAll(config, header);

                    }
                }
            }
        }
        cbUpdate();
        String actionP = "Configuration " + nameDialog.getResult() + " was saved";
        LocalDate localDate = datePicker.getValue();
        avm.addTraceLog(" ", actionP, userName, localDate.toString(), " ");
        } catch (Exception e)
        {
            desc = e.toString();
            LocalDate localDate = datePicker.getValue();
            String actionP = "Nothing happens";
            avm.addTraceLog(file.getName(), actionP, userName,localDate.toString(), desc);
        }
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) cancelBtn.getScene().getWindow();
        stage1.close();
    }

    public void setFileHeaders(File file) throws IOException, InvalidFormatException, Exception {
        this.file = file;
        List<Header> header;
        header = FXCollections.observableArrayList();
        header.addAll(uvm.getFileHeaders(file));
        for (Header header1 : header) {
            selectedList.getItems().add(header1);
        }
        templateJson();
    }

    public void headerNames() {
        selectedList.setCellFactory(lView -> new ListCell<Header>() {
            @Override
            protected void updateItem(Header header, boolean empty) {
                super.updateItem(header, empty);
                setText(header == null ? null : header.getHeaderName() + header.getHeaderIndex());
            }
        });
    }

    private void configNames() {
        final ListCell<Attribute> buttonCell = new ListCell<Attribute>() {
            @Override
            protected void updateItem(Attribute attribute, boolean empty) {
                super.updateItem(attribute, empty);
                if (attribute != null) {
                    setText(attribute.getConfigurationName());
                } else {
                    setText(null);
                }
            }
        };
        savedCombo.setButtonCell(buttonCell);

        savedCombo.setCellFactory(new Callback<ListView<Attribute>, ListCell<Attribute>>() {
            @Override
            public ListCell<Attribute> call(ListView<Attribute> p) {
                final ListCell<Attribute> cell = new ListCell<Attribute>() {
                    @Override
                    protected void updateItem(Attribute attribute, boolean empty) {
                        super.updateItem(attribute, empty);
                        if (attribute != null) {
                            setText(attribute.getConfigurationName());;
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML
    private void addAttribute(ActionEvent event) throws JsonProcessingException, Exception {
        String selectedCB = attCB.getSelectionModel().getSelectedItem();
        Header selected = selectedList.getSelectionModel().getSelectedItem();
        attributeView.getItems().set(attCB.getSelectionModel().getSelectedIndex(), selectedCB + " : " + selected);
        selected.setListIndex(attCB.getSelectionModel().getSelectedIndex());
        selected.setAttName(selectedCB);
        alist.add(selected);
        System.out.println(alist);
        jArray();
        uvm.readFirstLine(file.getPath(), headerMap, true);
        String json = uvm.XLSXR();
        previewArea.setText(json);

    }

    @FXML
    private void removeAttribute(ActionEvent event) {
        attributeView.getItems().remove(attributeView.getSelectionModel().getSelectedItem());
    }

    public Map<String, Header> jArray() {
        Header h = selectedList.getSelectionModel().getSelectedItem();
        String sitenameheader = attCB.getValue();
        headerMap.put(sitenameheader, h);
        System.out.println(headerMap);
        return headerMap;

    }

    public Map<String, Header> templateJson() throws Exception {

        for (String string : attList) {
            Header header = new Header();
            header.setHeaderName("");
            header.setHeaderIndex(-1);
            headerMap.put(string, header);
        }
        uvm.setTemplate(headerMap);
        uvm.readFirstLine(file.getPath(), headerMap, true);
        String json = uvm.XLSXR();

        previewArea.setText(json);
        return headerMap;
    }

    void setUsername(String userName) {
        this.userName = userName;
        lblUser.setText(userName);
    }

    private void cbUpdate() {
        cfgM.loadAttributes();
        savedCombo.setItems(cfgM.getAllAttributes());
    }

    @FXML
    private void chooseSaveConfig(ActionEvent event) throws Exception {
        try {
        headerMap.clear();
        attributeView.getItems().clear();
        attributeView.getItems().addAll(attList);
        templateJson();
        List<Attribute> config = cfgM.getAllAttributes();
        for (Attribute attribute : config) {
            if (attribute.getOutId() == savedCombo.getValue().getOutId()) {
                List<Header> header = attribute.getSavedHeader();
                for (Header header1 : header) {
                    attributeView.getItems().set(header1.getListIndex(), header1.getAttName() + " : " + header1.getHeaderName());
                    headerMap.put(header1.getAttName(), header1);
                }
            }
        }
        uvm.readFirstLine(file.getPath(), headerMap, true);
        String json = uvm.XLSXR();
        previewArea.setText(json);
        } catch (Exception e)
        {
            desc = e.toString();
            LocalDate localDate = datePicker.getValue();
            String actionP = "Nothing happens";
            avm.addTraceLog(file.getName(), actionP, userName,localDate.toString(), desc);
        }
    }

    @FXML
    private void addToTask(ActionEvent event) {
        try {
        String taskName = null;
        TextInputDialog taskDialog = new TextInputDialog();
        taskDialog.setTitle("Set task name");
        taskDialog.setHeaderText("Set task name");
        taskDialog.setContentText("Please set a task name");
        if (taskDialog.showAndWait().isPresent()) {
            taskName = taskDialog.getResult();
            String actionP = taskDialog.getResult() + " is added to task";
            Tasks task = new Tasks(file, outputFile, taskName, headerMap);
            uvm.setTask(task);
            uvc.setTaskList();
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(" ", actionP, userName, localDate.toString(), " ");
        }
        } catch (Exception e)
        {
            desc = e.toString();
            LocalDate localDate = datePicker.getValue();
            String actionP = "Nothing happens";
            avm.addTraceLog(file.getName(), actionP, userName,localDate.toString(), desc);
        }
    }

    @FXML
    private void chooseFileDestination(ActionEvent event) {
        DirectoryChooser dirch = new DirectoryChooser();
        File outputDirectory = dirch.showDialog(null);
        outputFile = Paths.get(outputDirectory.getAbsolutePath()).toString();

    }
    
    public void setController(UserViewController userVC) {
        this.uvc = userVC;
    }
}
