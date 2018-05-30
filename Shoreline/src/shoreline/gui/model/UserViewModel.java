/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shoreline.be.Header;
import shoreline.be.Person;
import shoreline.be.Tasks;
import shoreline.be.User;
import shoreline.bll.CfgManager;
import shoreline.dal.ConvertThread;
import shoreline.dal.TemplateFile;
import shoreline.bll.UserManager;

/**
 *
 * @author ollie
 */
public class UserViewModel {

    private static UserViewModel instance;
    UserManager uManager;
    CfgManager cfgM = new CfgManager();
    User user;
    ObservableList<User> userList;
    ObservableList<Tasks> tasksList = FXCollections.observableArrayList();
    private Person person;

    public UserViewModel() {
        uManager = new UserManager();
        user = new User();
        userList = FXCollections.observableArrayList();
    }

    public void loadUsers() {
        userList.clear();
        userList.addAll(uManager.getAllUsers());
    }

    public ObservableList<User> getAllUsers() {
        return userList;
    }

    public void updateUser(User user) {
        uManager.updateUser(user);
    }

    public static UserViewModel getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new UserViewModel();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        uManager.createUser(user);
    }

    public void deleteUser(User selectedUser) {
        uManager.deleteUser(selectedUser);
    }

    public void readFirstLine(String filePath, Map<String, Header> ja, boolean oneLine) throws Exception {
        cfgM.readFirstLine(filePath, ja, oneLine);
    }

    public List<Header> getFileHeaders(File file) throws IOException, InvalidFormatException {
        return cfgM.getFileHeaders(file);
    }

    public String XLSXR() throws JsonProcessingException {
        return cfgM.XLSXR();
    }

    public void setTemplate(Map<String, Header> jobj) {
        cfgM.setTemplate(jobj);
    }

    public void setTask(Tasks task) {
        tasksList.add(task);
    }

    public ObservableList<Tasks> getTasks() {
        return tasksList;
    }

    public void convert(Tasks task) throws IOException, InvalidFormatException {
        cfgM.convert(task);
    }
}
