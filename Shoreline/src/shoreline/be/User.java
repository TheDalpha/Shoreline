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
public class User extends Person {

    private boolean admin;

    /**
     * Return true if the user is an admin
     * else false
     * @return 
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Sets the boolean admin
     * @param admin 
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
