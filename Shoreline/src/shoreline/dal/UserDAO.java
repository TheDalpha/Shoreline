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
import java.util.logging.Level;
import java.util.logging.Logger;
import shoreline.be.User;

/**
 *
 * @author ollie
 */
public class UserDAO
{
    private DataBaseConnector dbConnector;
    
    public UserDAO(){
        dbConnector = new DataBaseConnector();
    }
    
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList();
        
        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Login");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setLoginId(rs.getInt("loginId"));
                user.setUsername(rs.getString("username"));
                user.setCleanPassword(rs.getString("password"));
                user.setEncryptedPassword(rs.getBytes("encryptedPassword"));
                user.setSalt(rs.getBytes("salt"));
                allUsers.add(user);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
            return null;
        }
        return allUsers;
    }
    
    public void updateUsers(User user) {
        String sql = "UPDATE Login "
                + "username = ?, "
                + "password = ?, "
                + "encryptedPassword = ?, "
                + " WHERE loginId = ? ;";
        try (Connection con = dbConnector.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getCleanPassword());
            ps.setBytes(3, user.getEncryptedPassword());
            ps.setInt(4, user.getLoginId());

            ps.executeUpdate();
        }catch (SQLException ex) {
            System.err.print(ex);
        }
    }

    public void createUser(User user) {
        try (Connection con = dbConnector.getConnection())
        {
            String sql
                    = "INSERT INTO Login"
                    + "(username, password, encryptedPassword, salt) "
                    + "VALUES(?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getCleanPassword());
            pstmt.setBytes(3, user.getEncryptedPassword());
            pstmt.setBytes(4, user.getSalt());
            
            int affected = pstmt.executeUpdate();
            if(affected<1)
                throw new SQLException("Can't create user");
            
            //Get Database generated id and set user id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                user.setLoginId(rs.getInt(1));
            }
              
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
        
      
     
    

