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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCleanPassword() {
        return cleanPassword;
    }

    public void setCleanPassword(String password) {
        this.cleanPassword = password;
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }
    
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
 
}
