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
    
    public UserManager() {
        this.udao = new UserDAO();
    }
    
    public List<User> getAllUsers() {
        return udao.getAllUsers();
    }
    
    public void updateUser(User user) {
        udao.updateUsers(user);
    }

    public void createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordEntered = user.getCleanPassword();
        byte[] salt = generateSalt();
        user.setSalt(salt);
        user.setEncryptedPassword(getEncryptedPassword(passwordEntered, salt));
        udao.createUser(user);
    }
    
    public boolean authenticate(String passwordEntered, byte[] passwordEncrypted, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encryptedPasswordEntered = getEncryptedPassword(passwordEntered, salt);
        return Arrays.equals(passwordEncrypted, encryptedPasswordEntered);
    }
    
    public byte[] getEncryptedPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
        String algortihm = "PBKDF2WithHmacSHA1";
        int derivedKeyLenght = 160;
        int iterations = 20000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLenght);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algortihm);
        return f.generateSecret(spec).getEncoded();
    }
    
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }
    
}
