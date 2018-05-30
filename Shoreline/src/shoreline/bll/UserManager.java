/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
    
    public UserManager() {
        this.udao = new UserDAO();
        this.pSecure = new PassSecurity();
    }
    
    public List<User> getAllUsers() {
        return udao.getAllUsers();
    }

    public void createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordEntered = user.getCleanPassword();
        byte[] salt = pSecure.generateSalt();
        user.setSalt(salt);
        user.setEncryptedPassword(pSecure.getEncryptedPassword(passwordEntered, salt));
        udao.createUser(user);
    }
    
    public void deleteUser(User selectedUser) {
        udao.deleteUser(selectedUser);
    }
    
}
