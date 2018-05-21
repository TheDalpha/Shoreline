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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import shoreline.be.Admin;
import shoreline.be.Loggin;

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
                    = con.prepareStatement("SELECT * FROM Admin");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Admin admin = new Admin();
                admin.setLoginId(rs.getInt("adminId"));
                admin.setUsername(rs.getString("username"));
                admin.setCleanPassword(rs.getString("password"));
                admin.setEncryptedPassword(rs.getBytes("encryptedPassword"));
                admin.setSalt(rs.getBytes("salt"));
                allAdmins.add(admin);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
//            return null;
        }
        return allAdmins;
    }
    
    
    public List<Loggin> getAllLoggins() {
        List<Loggin> allLoggins = new ArrayList();
        
        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Log");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Loggin log = new Loggin();
                log.setTraceId(rs.getInt("traceId"));
                log.setUsername(rs.getString("username"));
                log.setAction(rs.getString("action"));
                log.setDate(rs.getString("date"));
                allLoggins.add(log);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
//            return null;
        }
        return allLoggins;
    }
    
    public void uploadLog (Loggin log) throws SQLException {
        try (Connection con = dbConnector.getConnection())
        {
            String sql
                    = "INSERT INTO Log"
                    + "(traceId, username, action, date)"
                    + "VALUES(?,?,?)";
            
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, log.getUsername());
            pstmt.setString(2, log.getAction());
            pstmt.setString(3, log.getDate());
            
            int affected = pstmt.executeUpdate();
            if(affected<1)
                throw new SQLException("Can't create user");
            
            //Get Database generated id and set user id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                log.setTraceId(rs.getInt(1));
        }
    }
    }
}
