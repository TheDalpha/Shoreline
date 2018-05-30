/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    PassSecurity pSecure;
    
    /**
     * Constructor
     */
    public UserManager() {
        this.udao = new UserDAO();
        this.pSecure = new PassSecurity();
    }
    
    /**
     * Gets all users
     * @return List
     */
    public List<User> getAllUsers() {
        return udao.getAllUsers();
    }

    /**
     * Sets the user salt generation and a encrypted version of the password
     * @param user
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public void createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordEntered = user.getCleanPassword();
        byte[] salt = pSecure.generateSalt();
        user.setSalt(salt);
        user.setEncryptedPassword(pSecure.getEncryptedPassword(passwordEntered, salt));
        udao.createUser(user);
    }
    
    /**
     * Sends information on the selected user
     * @param selectedUser 
     */
    public void deleteUser(User selectedUser) {
        udao.deleteUser(selectedUser);
    }
    
}
