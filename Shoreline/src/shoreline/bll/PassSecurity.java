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
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Jesper Riis
 */
public class PassSecurity {

    /**
     * Encrypts the entered password and gets the salt.
     * Then compare it with the encrypted password.
     * @param passwordEntered
     * @param passwordEncrypted
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public boolean authenticate(String passwordEntered, byte[] passwordEncrypted, 
            byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encryptedPasswordEntered = getEncryptedPassword(passwordEntered, salt);
        return Arrays.equals(passwordEncrypted, encryptedPasswordEntered);
    }

    /**
     * Encrypts the password with salt.
     * @param password
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    public byte[] getEncryptedPassword(String password, byte[] salt) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        
        String algortihm = "PBKDF2WithHmacSHA1";
        int derivedKeyLenght = 160;
        int iterations = 20000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLenght);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algortihm);
        return f.generateSecret(spec).getEncoded();
    }

    /**
     * Generates salt for the encrypted password.
     * @return byte
     * @throws NoSuchAlgorithmException 
     */
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

}
