/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import shoreline.be.Admin;
import shoreline.be.Loggin;
import shoreline.dal.AdminDAO;

/**
 *
 * @author ollie
 */
public class AdminManager {
    
    
    static final Logger logger = Logger.getLogger(Loggin.class.getName());
    
    
    AdminDAO adao;
    
    public AdminManager() {
        this.adao = new AdminDAO();
        
    }
    
    public List<Admin> getAllAdmins() {
        return adao.getAllAdmins();
    }
    
    public List<Loggin> getAllLoggins() {
       return adao.getAllLoggins();
    }
    
    public void uploadLogger(Loggin l) throws SQLException {
     //   tLog();
        adao.uploadLog(l);
    }
    
    public static void tLog() throws SQLException {
        logger.log(Level.ALL, "What is this");
        logger.warning("This is a Warning!");
        logger.fine("It's okay");
        logger.setLevel(Level.FINEST);
        logger.addHandler(new ConsoleHandler());
        logger.getHandlers();
        
    }
    
    
}
