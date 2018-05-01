/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;

/**
 *
 * @author ollie
 */
public class User
{
 public String username;
 
 public String password;
 
 public int loginId;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getLoginId()
    {
        return loginId;
    }

    public void setLoginId(int loginId)
    {
        this.loginId = loginId;
    }

    @Override
    public String toString()
    {
        return "User{" + "username=" + username + ", password=" + password + ", loginId=" + loginId + '}';
    }

    
 
    
    
}
