/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;

/**
 *
 * @author Jesper
 */
public abstract class Person {

    public String username;
    public String cleanPassword;
    public byte[] encryptedPassword;
    public byte[] salt;
    public int loginId;

    // Get the username
    public String getUsername() {
        return username;
    }

    // Sets the username
    public void setUsername(String username) {
        this.username = username;
    }

    // Get the clean password, non encrypted
    public String getCleanPassword() {
        return cleanPassword;
    }

    // Sets the clean password, non encrypted
    public void setCleanPassword(String password) {
        this.cleanPassword = password;
    }

    // Get user id
    public int getLoginId() {
        return loginId;
    }

    // Sets user id
    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    // Get salt for encrypting
    public byte[] getSalt() {
        return salt;
    }

    // Sets the salt for encrypting
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    // Gets the encrypted password
    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    // Sets the encrypted password
    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

}
