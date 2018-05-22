/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.controller;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import shoreline.be.Attribute;
import shoreline.gui.model.CfgModel;
import shoreline.be.Header;
import shoreline.gui.model.UserViewModel;

/**
 * FXML Controller class
 *
 * @author ollie
 */
public class ConfigureViewController implements Initializable {
    
    ObservableList<String> attList = FXCollections.observableArrayList("SiteName", "Asset Serial Number", "Type", "External Work Order", "System Status", "User Status", "Created On", "Created By", "Name", "Priority", "Status", "Latest Finish Date", "Earliest Start Date", "Latest Start Date", "Estimated Time");
    Map<String, String> alist = new HashMap<>();
    Map<String, Header> headerMap = new HashMap<>();
    JSONObject jobj = new JSONObject();
    String filePath;
    UserViewModel uvm;
    CfgModel cfgM;
    
    @FXML
    private Label lblUser;
    @FXML
    private JFXComboBox<String> savedCombo;
    @FXML
    private ListView<Header> selectedList;
    @FXML
    private JFXButton saveConfBtn;
    @FXML
    private JFXButton addAttributeBtn;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            uvm = UserViewModel.getInstance();
            cfgM = CfgModel.getInstance();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ConfigureViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        attCB.setItems(attList);
        headerNames();
        attributeView.getItems().addAll(attList);
        previewArea.setEditable(false);
    }
    
    @FXML
    private void saveConfiguration(ActionEvent event) throws Exception {
        Attribute config = new Attribute();
        alist.forEach((key, value) -> {
            if (key.equals("SiteName")) {
                config.setSiteName(value);
            }
            if (key.equals("Asset Serial Number")) {
                config.setAssetSerialNumber(value);
            }
            if (key.equals("Type")) {
                config.setType(value);
            }
            if (key.equals("External Work Order")) {
                config.setExternalWorkOrder(value);
            }
            if (key.equals("System Status")) {
                config.setSystemStatus(value);
            }
            if (key.equals("User Status")) {
                config.setUserStatus(value);
            }
            if (key.equals("Created On")) {
                config.setCreatedOn(value);
            }
            if (key.equals("Created By")) {
                config.setCreatedBy(value);
            }
            if (key.equals("Name")) {
                config.setName(value);
            }
            if (key.equals("Priority")) {
                config.setPriority(value);
            }
            if (key.equals("Status")) {
                config.setStatus(value);
            }
            if (key.equals("Latest Finish Date")) {
                config.setLatestFinishDate(value);
            }
            if (key.equals("Earliest Start Date")) {
                config.setEarliestStartDate(value);
            }
            if (key.equals("Latest Start Date")) {
                config.setLatestStartDate(value);
            }
            if (key.equals("Estimated Time")) {
                config.setEstimatedTime(value);
            }
        });
        
        TextInputDialog nameDialog = new TextInputDialog("");
        nameDialog.setTitle("Set configuration name");
        nameDialog.setHeaderText("Set configuration name");
        nameDialog.setContentText("Please set a configuration name");
        if(nameDialog.showAndWait().isPresent()) {
            config.setConfigurationName(nameDialog.getResult());
            cfgM.configSave(config);
        }
        
    }
    
    @FXML
    private void addAtribute(ActionEvent event) {
    }
    
    @FXML
    private void cancel(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) cancelBtn.getScene().getWindow();
        stage1.close();

//        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/gui/view/UserView.fxml"));
//        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }
    
    public void setFileHeaders(File file) throws IOException, InvalidFormatException, Exception {
        filePath = file.getPath();
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
    
    @FXML
    private void addAttribute(ActionEvent event) throws JsonProcessingException, Exception {
        String selectedCB = attCB.getSelectionModel().getSelectedItem();
        String selected = selectedList.getSelectionModel().getSelectedItem().getHeaderName()
                + selectedList.getSelectionModel().getSelectedItem().getHeaderIndex();
//        attributeView.getItems().add(selectedCB + " : " + selected);
//        for (int i = 0; i < attributeView.getItems().size(); i++) {
//            if(attributeView.getItems().get(i).equals(attCB.getSelectionModel().getSelectedItem())) {
//                attributeView.getItems().set(i, selectedCB + " : " + selected);
//            }
//        }
        attributeView.getItems().set(attCB.getSelectionModel().getSelectedIndex(), selectedCB + " : " + selected);
        alist.put(selectedCB, selected);
        jArray();
        uvm.setFilePath(filePath, headerMap, true);
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
        // forloop

        //key : tilføj headername somehow row 0, altid. value: samme som key, men starter fra row 1, increment to 2,3,4 etc
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
        uvm.setFilePath(filePath, headerMap, true);
        String json = uvm.XLSXR();
        
        previewArea.setText(json);
        return headerMap;
    }
    
    void setUsername(String userName) {
        lblUser.setText(userName);
    }
    
}
