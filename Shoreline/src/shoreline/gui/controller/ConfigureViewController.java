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
public class ConfigureViewController implements Initializable
{
    ObservableList<String> attList = FXCollections.observableArrayList("SiteName", "Asset Serial Number", "Type", "External Work Order", "System Status", "User Status", "Created On", "Created By", "Name", "Priority", "Status", "Latest Finish Date", "Earliest Start Date", "Latest Start Date", "Estimated Time");
    ObservableList<String> alist = FXCollections.observableArrayList();
    Map<String, Header> jobj = new HashMap<>();
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
    public void initialize(URL url, ResourceBundle rb)
    {
        try {
            uvm = UserViewModel.getInstance();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ConfigureViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        attCB.setItems(attList);
        headerNames();
        try {
            templateJson();
        } catch (Exception ex) {
            Logger.getLogger(ConfigureViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    



    @FXML
    private void saveConfiguration(ActionEvent event) throws Exception
    {
        System.out.println(jobj);
//        uvm.setFilePath(filePath, jobj);
    }

    @FXML
    private void addAtribute(ActionEvent event)
    {
    }

    @FXML
    private void cancel(ActionEvent event) throws IOException
    {
        Stage stage1 = (Stage) cancelBtn.getScene().getWindow();
        stage1.close();

//        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/shoreline/gui/view/UserView.fxml"));
//        root.getStylesheets().add("/shoreline/gui/view/Css/Style.css");
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }
    
    public void setFileHeaders(File file) throws IOException, InvalidFormatException {
        filePath = file.getPath();
        List<Header> header;
        header = FXCollections.observableArrayList();
        header.addAll(uvm.getFileHeaders(file));
        for (Header header1 : header) {
            selectedList.getItems().add(header1);
        }
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
    private void addAttribute(ActionEvent event) throws JsonProcessingException, Exception
    {
        String selectedCB = attCB.getSelectionModel().getSelectedItem();
        String selected = selectedList.getSelectionModel().getSelectedItem().getHeaderName();
        attributeView.getItems().add(selectedCB + " : " + selected);
        alist.add(selected);
        jArray();
        uvm.setFilePath(filePath, jobj);
        String json = uvm.XLSXR();
        previewArea.setText(json);
              
    }

    @FXML
    private void removeAttribute(ActionEvent event)
    {
        attributeView.getItems().remove(attributeView.getSelectionModel().getSelectedItem());
    }

    public Map<String, Header> jArray() {
        Header h = selectedList.getSelectionModel().getSelectedItem();
        String sitenameheader = attCB.getValue();
        // forloop
        
        
        //key : tilføj headername somehow row 0, altid. value: samme som key, men starter fra row 1, increment to 2,3,4 etc
        jobj.put(sitenameheader, h);
        System.out.println(jobj);
        return jobj;
       
    }
    
    public Map<String, Header> templateJson() throws Exception {
        
        String emptyPrefix = "";
        for (String string : attList) {
            Header header = new Header();
            header.setHeaderName("");
            jobj.put(string, header);
        }
        uvm.setTemplate(jobj);
        String json = uvm.XLSXR();
        previewArea.setText(json);
        return jobj;
    }
   
}
