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
import java.util.logging.Level;
import java.util.logging.Logger;
import shoreline.be.User;
import shoreline.dal.dbConnector.ConnectionPool;

/**
 *
 * @author ollie
 */
public class UserDAO
{
    ConnectionPool dbConnector = ConnectionPool.getInstance();
    
    /**
     * Connects to the database and selects all from the Login Table
     * And sets each user and then adds them to a list
     * @return List
     */
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList();
        Connection con = dbConnector.checkOut();
        
        try {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Login");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setLoginId(rs.getInt("loginId"));
                user.setUsername(rs.getString("username"));
                user.setEncryptedPassword(rs.getBytes("encryptedPassword"));
                user.setSalt(rs.getBytes("salt"));
                user.setAdmin(rs.getBoolean("admin"));
                allUsers.add(user);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
            return null;
        }
        finally {
            dbConnector.checkIn(con);
        }
        return allUsers;
    }

    /**
     * Connects to the database and inserts a user into the Login Table
     * @param user 
     */
    public void createUser(User user) {
        Connection con = dbConnector.checkOut();
        try
        {
            String sql
                    = "INSERT INTO Login"
                    + "(username, encryptedPassword, salt, admin) "
                    + "VALUES(?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setBytes(2, user.getEncryptedPassword());
            pstmt.setBytes(3, user.getSalt());
            pstmt.setBoolean(4, user.isAdmin());
            
            int affected = pstmt.executeUpdate();
            if(affected<1)
                throw new SQLException("Can't create user");
            
            //Get Database generated id and set user id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setLoginId(rs.getInt(1));
            }
              
        }catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            dbConnector.checkIn(con);
        }
    }

    /**
     * Connects to the database and deletes the selected user from the Login table
     * @param selectedUser 
     */
    public void deleteUser(User selectedUser) {
        Connection con = dbConnector.checkOut();
        try {
            String sql
                    = "DELETE FROM Login WHERE loginId=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setInt(1, selectedUser.getLoginId());
            pstmt.execute();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            dbConnector.checkIn(con);
        }
    }
    

}
        
      
     
    

