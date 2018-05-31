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

    /**
     * Constructor
     */
    public UserViewModel() {
        uManager = new UserManager();
        user = new User();
        userList = FXCollections.observableArrayList();
    }

    /**
     * Gets all users and put them in a list
     */
    public void loadUsers() {
        userList.clear();
        userList.addAll(uManager.getAllUsers());
    }

    /**
     * Gets all users
     * @return 
     */
    public ObservableList<User> getAllUsers() {
        return userList;
    }

    /**
     * Returns the instance of UserViewModel
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public static UserViewModel getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new UserViewModel();
        }
        return instance;
    }

    /**
     * Gets a user
     * @return 
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets a user
     * @param user 
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sends data further into the system
     * @param user
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public void createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        uManager.createUser(user);
    }

    /**
     * Sends data further into the system
     * @param selectedUser 
     */
    public void deleteUser(User selectedUser) {
        uManager.deleteUser(selectedUser);
    }

    /**
     * Sends data further into the system
     * @param filePath
     * @param ja
     * @param oneLine
     * @throws Exception 
     */
    public void readFirstLine(String filePath, Map<String, Header> ja, boolean oneLine) throws Exception {
        cfgM.readFirstLine(filePath, ja, oneLine);
    }

    /**
     * Sends data further into the system and return the list
     * @param file
     * @return
     * @throws IOException
     * @throws InvalidFormatException 
     */
    public List<Header> getFileHeaders(File file) throws IOException, InvalidFormatException {
        return cfgM.getFileHeaders(file);
    }

    /**
     * Sends data further into the system and returns the string
     * @return
     * @throws JsonProcessingException 
     */
    public String XLSXR() throws JsonProcessingException {
        return cfgM.XLSXR();
    }

    /**
     * Sends data further into the system
     * @param jobj 
     */
    public void setTemplate(Map<String, Header> jobj) {
        cfgM.setTemplate(jobj);
    }

    /**
     * Adds a task to a list
     * @param task 
     */
    public void setTask(Tasks task) {
        tasksList.add(task);
    }

    /**
     * Gets all tasks
     * @return 
     */
    public ObservableList<Tasks> getTasks() {
        return tasksList;
    }

    /**
     * Sends data further into the system
     * @param task
     * @throws IOException
     * @throws InvalidFormatException 
     */
    public void convert(Tasks task) throws IOException, InvalidFormatException {
        cfgM.convert(task);
    }

    public void stopConvert() {
        cfgM.stopConvert();
    }
}
