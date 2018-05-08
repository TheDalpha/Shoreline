/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shoreline.be.User;
import shoreline.bll.FileReader;
import shoreline.bll.FileWriter;
import shoreline.bll.UserManager;

/**
 *
 * @author ollie
 */
public class UserViewModel
{
    
    private static UserViewModel instance;
        UserManager uManager;
        FileReader fileReader;
        FileWriter fileWriter;
        User user;
        ObservableList<User> userList;

    public UserViewModel() {
        uManager = new UserManager();
        user = new User();
        fileReader = new FileReader();
        fileWriter = new FileWriter();
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
    
    public static UserViewModel getInstance() throws SQLException, IOException
    {
        if (instance == null)
        {
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

    public void setFilePath(String filePath) throws Exception {
        fileReader.readXLSX(filePath);
    }

    public void convertToJson(String path) {
        fileWriter.convertToJson(path);
    }
    
    
}
