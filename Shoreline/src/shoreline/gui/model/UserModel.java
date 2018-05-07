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
import shoreline.bll.UserManager;

/**
 *
 * @author ollie
 */
public class UserModel
{
    
    private static UserModel instance;
        UserManager uManager;
        User user;
        ObservableList<User> userList;

    public UserModel() {
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
    
    public static UserModel getInstance() throws SQLException, IOException
    {
        if (instance == null)
        {
            instance = new UserModel();
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
    
    
}
