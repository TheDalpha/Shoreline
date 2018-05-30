/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;
import java.sql.SQLException;
import java.util.List;
import shoreline.be.Admin;
import shoreline.be.Loggin;
import shoreline.dal.AdminDAO;

/**
 *
 * @author ollie
 */
public class AdminManager {

    AdminDAO adao;
    
    /**
     * Constructor
     */
    public AdminManager() {
        this.adao = new AdminDAO();
    }
    
    /**
     * Gets all admins
     * @return All admins
     */
    public List<Admin> getAllAdmins() {
        return adao.getAllAdmins();
    }
    
    /**
     * Gets all logs
     * @return All logs
     */
    public List<Loggin> getAllLoggins() {
       return adao.getAllLoggins();
    }
    
    /**
     * Sends information on a log
     * @param l
     * @throws SQLException 
     */
    public void uploadLogger(Loggin l) throws SQLException {
        adao.uploadLog(l);
    }
}
