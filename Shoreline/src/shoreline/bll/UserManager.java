/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import java.util.List;
import shoreline.be.User;
import shoreline.dal.UserDAO;

/**
 *
 * @author ollie
 */
public class UserManager
{
    
    UserDAO udao;
    
    public UserManager() {
        this.udao = new UserDAO();
    }
    
    public List<User> getAllUsers() {
        return udao.getAllUsers();
    }
    
    public void updateUser(User user) {
        udao.updateUsers(user);
    }
}
