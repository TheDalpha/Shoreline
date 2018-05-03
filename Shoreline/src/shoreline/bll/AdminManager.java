/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import java.util.List;
import shoreline.be.Admin;
import shoreline.dal.AdminDAO;

/**
 *
 * @author ollie
 */
public class AdminManager {
    
    AdminDAO adao;
    
    public AdminManager() {
        this.adao = new AdminDAO();
    }
    
    public List<Admin> getAllAdmins() {
        return adao.getAllAdmins();
    }
    
}
