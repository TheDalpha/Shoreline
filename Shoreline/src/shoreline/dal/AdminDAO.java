/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.dal;

import shoreline.dal.dbConnector.DataBaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import shoreline.be.Admin;
import shoreline.be.Loggin;
import shoreline.dal.dbConnector.ConnectionPool;

/**
 *
 * @author ollie
 */
public class AdminDAO {
    
    ConnectionPool dbConnector = ConnectionPool.getInstance();
    
    /**
     * Connects to the database and selects all admins from the admin table
     * Sets each admin and adds them to a list
     * @return List
     */
    public List<Admin> getAllAdmins() {
        List<Admin> allAdmins = new ArrayList();
        Connection con = dbConnector.checkOut();
        try {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Admin");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Admin admin = new Admin();
                admin.setLoginId(rs.getInt("adminId"));
                admin.setUsername(rs.getString("username"));
                admin.setEncryptedPassword(rs.getBytes("encryptedPassword"));
                admin.setSalt(rs.getBytes("salt"));
                allAdmins.add(admin);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
        }
        finally {
                dbConnector.checkIn(con);
                }
        return allAdmins;
        
    }
    
    /**
     * Connects to the database and selects all logs from the log table
     * Sets each log and adds them to a list
     * @return List
     */
    public List<Loggin> getAllLoggins() {
        List<Loggin> allLoggins = new ArrayList();
        Connection con = dbConnector.checkOut();
        try {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Log");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Loggin log = new Loggin();
                log.setTraceId(rs.getInt("traceId"));
                log.setUsername(rs.getString("username"));
                log.setAction(rs.getString("action"));
                log.setDate(rs.getString("date"));
                log.setFilename(rs.getString("filename"));
                log.setError(rs.getString("error"));
                allLoggins.add(log);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
        }
        finally {
                dbConnector.checkIn(con);
                }
        return allLoggins;
    }
    
    /**
     * Connects to the database and insert a log into the Log table
     * @param log
     * @throws SQLException 
     */
    public void uploadLog (Loggin log) throws SQLException {
        Connection con = dbConnector.checkOut();
        try
        {
            String sql
                    = "INSERT INTO Log"
                    + "(username, action, date, filename, error)"
                    + "VALUES(?,?,?,?,?)";
            
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, log.getUsername());
            pstmt.setString(2, log.getAction());
            pstmt.setString(3, log.getDate());
            pstmt.setString(4, log.getFilename());
            pstmt.setString(5, log.getError());
            
            int affected = pstmt.executeUpdate();
            if(affected<1)
                throw new SQLException("Can't Update Log");
    }
        finally {
                dbConnector.checkIn(con);
                }
    }
}
