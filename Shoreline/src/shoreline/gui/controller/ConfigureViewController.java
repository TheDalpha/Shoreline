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
import shoreline.be.Configuration;
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
public class ConfigureViewController implements Initializable
{

    ObservableList<String> attList = FXCollections.observableArrayList("SiteName", "Asset Serial Number", "Type", "External Work Order", "System Status", "User Status", "Created On", "Created By", "Name", "Priority", "Status", "Latest Finish Date", "Earliest Start Date", "Latest Start Date", "Estimated Time");
    List<Header> alist = new ArrayList<>();
    HashMap<String, Header> headerMap = new HashMap<>();
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
    private JFXComboBox<Configuration> savedCombo;
    @FXML
    private ListView<Header> selectedList;
    @FXML
    private JFXButton saveConfBtn;
    @FXML
    private ListView<String> attributeView;
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
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            uvm = UserViewModel.getInstance();
            cfgM = CfgModel.getInstance();
            avm = AdminViewModel.getInstance();
        } catch (SQLException | IOException ex)
        {
            Logger.getLogger(ConfigureViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Updates the combobox
        cbUpdate();
        // Sets the config names in the combobox
        configNames();
        // Sets the items in the combobox
        attCB.setItems(attList);
        // Sets the header names + index in the list
        headerNames();
        attributeView.getItems().addAll(attList);
        previewArea.setEditable(false);
    }

    /**
     * Goes through each header in the list and then through each item in the
     * attributeView. If attributeView contains header name it will be saved.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    private void saveConfiguration(ActionEvent event) throws Exception
    {
        try
        {
            Configuration config = new Configuration();

            TextInputDialog nameDialog = new TextInputDialog("");
            nameDialog.setTitle("Set configuration name");
            nameDialog.setHeaderText("Set configuration name");
            nameDialog.setContentText("Please set a configuration name");
            if (nameDialog.showAndWait().isPresent())
            {
                config.setConfigurationName(nameDialog.getResult());
                cfgM.configSave(config);
                for (Header header : alist)
                {
                    for (int i = 0; i < attributeView.getItems().size(); i++)
                    {
                        if (attributeView.getItems().get(i).contains(header.getHeaderName()))
                        {
                            cfgM.headerSave(header);
                            cfgM.saveAll(config, header);

                        }
                    }
                }
            }
            cbUpdate();
            String actionP = "Configuration " + nameDialog.getResult() + " was saved";
            LocalDate localDate = datePicker.getValue();
            avm.addTraceLog(file.getName(), actionP, userName, localDate.toString(), " ");
        } catch (Exception e)
        {
            desc = e.toString();
            LocalDate localDate = datePicker.getValue();
            String actionP = "Nothing happens";
            avm.addTraceLog(file.getName(), actionP, userName, localDate.toString(), desc);
        }
    }

    /**
     * Gets all headers and adds the to a list Then adds them to a listview
     *
     * @param file
     * @throws IOException
     * @throws InvalidFormatException
     * @throws Exception
     */
    public void setFileHeaders(File file) throws IOException, InvalidFormatException, Exception
    {
        this.file = file;
        List<Header> header;
        header = FXCollections.observableArrayList();
        header.addAll(uvm.getFileHeaders(file));
        for (Header header1 : header)
        {
            selectedList.getItems().add(header1);
        }
        templateJson();
    }

    /**
     * Sets the cells for the listview
     */
    public void headerNames()
    {
        selectedList.setCellFactory(lView -> new ListCell<Header>()
        {
            @Override
            protected void updateItem(Header header, boolean empty)
            {
                super.updateItem(header, empty);
                setText(header == null ? null : header.getHeaderName() + header.getHeaderIndex());
            }
        });
    }

    /**
     * Sets the cell for the buttom cell in the combobox and the cells in the
     * list
     */
    private void configNames()
    {
        final ListCell<Configuration> buttonCell = new ListCell<Configuration>()
        {
            @Override
            protected void updateItem(Configuration attribute, boolean empty)
            {
                super.updateItem(attribute, empty);
                if (attribute != null)
                {
                    setText(attribute.getConfigurationName());
                } else
                {
                    setText(null);
                }
            }
        };
        savedCombo.setButtonCell(buttonCell);

        savedCombo.setCellFactory(new Callback<ListView<Configuration>, ListCell<Configuration>>()
        {
            @Override
            public ListCell<Configuration> call(ListView<Configuration> p)
            {
                final ListCell<Configuration> cell = new ListCell<Configuration>()
                {
                    @Override
                    protected void updateItem(Configuration attribute, boolean empty)
                    {
                        super.updateItem(attribute, empty);
                        if (attribute != null)
                        {
                            setText(attribute.getConfigurationName());;
                        } else
                        {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    /**
     * Gets the selected item in the attribute combobox and the selected header
     * Then puts it in the attributeView listview Then it reads the first line
     * of the file and puts the corresponding cell into the json preview
     *
     * @param event
     * @throws JsonProcessingException
     * @throws Exception
     */
    @FXML
    private void addAttribute(ActionEvent event) throws JsonProcessingException, Exception
    {
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
    private void removeAttribute(ActionEvent event)
    {
        attributeView.getItems().remove(attributeView.getSelectionModel().getSelectedItem());
    }

    /**
     * Gets the selected header and attribute value and puts it in a hashmap
     *
     * @return HashMap
     */
    public Map<String, Header> jArray()
    {
        Header h = selectedList.getSelectionModel().getSelectedItem();
        String sitenameheader = attCB.getValue();
        headerMap.put(sitenameheader, h);
        return headerMap;

    }

    /**
     * Goes through each of the attributes and sets the header name to an empty
     * string and index to -1, so it doesn't read index 0 in the file. Then it
     * puts it in the hashmap and sets the JSON preview
     *
     * @return HashMap
     * @throws Exception
     */
    public Map<String, Header> templateJson() throws Exception
    {

        for (String string : attList)
        {
            Header header = new Header();
            header.setHeaderName("");
            header.setHeaderIndex(-1);
            headerMap.put(string, header);
        }
        uvm.setTemplate(headerMap);
        String json = uvm.XLSXR();

        previewArea.setText(json);
        return headerMap;
    }

    /**
     * Sets the active user
     *
     * @param userName
     */
    void setUsername(String userName)
    {
        this.userName = userName;
        lblUser.setText(userName);
    }

    /**
     * Updates the saved configuration combobox
     */
    private void cbUpdate()
    {
        cfgM.loadConfig();
        savedCombo.setItems(cfgM.getAllConfigs());
    }

    /**
     * Gets the chosen configuration and sets the attributeView listview and
     * hashmap headermap accordingly
     *
     * @param event
     * @throws Exception
     */
    @FXML
    private void chooseSaveConfig(ActionEvent event) throws Exception
    {
        try
        {
            headerMap.clear();
            attributeView.getItems().clear();
            attributeView.getItems().addAll(attList);
            templateJson();
            List<Configuration> config = cfgM.getAllConfigs();
            for (Configuration attribute : config)
            {
                if (attribute.getOutId() == savedCombo.getValue().getOutId())
                {
                    List<Header> header = attribute.getSavedHeader();
                    for (Header header1 : header)
                    {
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
            avm.addTraceLog(file.getName(), actionP, userName, localDate.toString(), desc);
        }
    }

    /**
     * Gets the data that has been chosen and puts it in a task object
     *
     * @param event
     */
    @FXML
    private void addToTask(ActionEvent event)
    {
        try
        {
            String taskName = null;
            TextInputDialog taskDialog = new TextInputDialog();
            taskDialog.setTitle("Set task name");
            taskDialog.setHeaderText("Set task name");
            taskDialog.setContentText("Please set a task name");
            if (taskDialog.showAndWait().isPresent())
            {
                taskName = taskDialog.getResult();
                String actionP = taskDialog.getResult() + " is added to task";
                Tasks task = new Tasks();
                headerMap.forEach((key, value) ->
                {
                    task.getHeaderMap().put(key, value);
                });
                task.setInputFile(file);
                task.setOutputFile(outputFile);
                task.setTaskName(taskName);
                uvm.setTask(task);
                uvc.setTaskList();
                headerMap.clear();
                LocalDate localDate = datePicker.getValue();
                avm.addTraceLog(" ", actionP, userName, localDate.toString(), " ");
            }
        } catch (Exception e)
        {
            desc = e.toString();
            LocalDate localDate = datePicker.getValue();
            String actionP = "Nothing happens";
            avm.addTraceLog(file.getName(), actionP, userName, localDate.toString(), desc);
        }
    }

    /**
     * Choose where the output file should go
     *
     * @param event
     */
    @FXML
    private void chooseFileDestination(ActionEvent event)
    {
        DirectoryChooser dirch = new DirectoryChooser();
        File outputDirectory = dirch.showDialog(null);
        outputFile = Paths.get(outputDirectory.getAbsolutePath()).toString();

    }

    /**
     * Sets the controller
     *
     * @param userVC
     */
    public void setController(UserViewController userVC)
    {
        this.uvc = userVC;
    }
}
