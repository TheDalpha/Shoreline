/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shoreline.be.User;
import shoreline.bll.UserManager;

/**
 *
 * @author ollie
 */
public class LoginViewModel
{
    private UserManager uManager = new UserManager();
    private ObservableList<User> users = FXCollections.observableArrayList();
    
    public ObservableList<User> getAllUsers() {
        return users;
    }
    
    public void loadUsers(){
    List<User> allUsers = uManager.getAllUsers();
    users.addAll(allUsers);
    }
    
//    public void loadStudents() {
//        studentList.clear();
//        studentList.addAll(stdMan.getAllStudents());
//    }
}
