/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ollie
 */
public class UserDAO
{
    
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList();
        
        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Student");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("studentId"));
                student.setFirstName(rs.getString("firstName"));
                student.setLastName(rs.getString("lastName"));
                student.setEmail(rs.getString("mail"));
                student.setPhoneNr(rs.getInt("phone"));
                student.setAddress(rs.getString("address"));
                student.setUsername(rs.getString("username"));
                student.setPassword(rs.getString("password"));
    
}
