/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import shoreline.be.Admin;

/**
 *
 * @author ollie
 */
public class AdminDAO {
    
    private DataBaseConnector dbConnector;
    
    public AdminDAO(){
        dbConnector = new DataBaseConnector();
    }
    
    public List<Admin> getAllAdmins() {
        List<Admin> allAdmins = new ArrayList();
        
        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Login");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Admin admin = new Admin();
                admin.setLoginId(rs.getInt("loginId"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                allAdmins.add(admin);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
//            return null;
        }
        return allAdmins;
    }
    
    
}
