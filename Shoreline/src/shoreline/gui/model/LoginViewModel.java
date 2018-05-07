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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shoreline.be.Admin;
import shoreline.be.User;
import shoreline.bll.AdminManager;
import shoreline.bll.UserManager;

/**
 *
 * @author ollie
 */
public class LoginViewModel
{
    private static LoginViewModel instance;
    private UserManager uManager = new UserManager();
    private AdminManager aManager = new AdminManager();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private ObservableList<Admin> admins = FXCollections.observableArrayList();
    
    public static LoginViewModel getInstance() throws SQLException, IOException
    {
        if (instance == null)
        {
            instance = new LoginViewModel();
        }
        return instance;
    }
    
    public ObservableList<User> getAllUsers() {
        return users;
    }
    
    public void loadUsers(){
    List<User> allUsers = uManager.getAllUsers();
    users.addAll(allUsers);
    }
    
    public ObservableList<Admin> getAllAdmins() {
        return admins;
    }
    
    public void loadAdmins() {
        List<Admin> allAdmins = aManager.getAllAdmins();
        admins.addAll(allAdmins);
    }
    
    public boolean authenticate(String passwordEntered, byte[] passwordEncrypted, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return uManager.authenticate(passwordEntered, passwordEncrypted, salt);
    }

}
