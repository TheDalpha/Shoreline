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
import shoreline.be.Attribute;
import shoreline.be.Header;
import shoreline.be.User;

/**
 *
 * @author ollie
 */
public class CfgDAO {

    private DataBaseConnector dbConnector;

    public CfgDAO() {
        dbConnector = new DataBaseConnector();
    }

    public List<Attribute> getAllAttributes() {
        List<Attribute> allAttributes = new ArrayList();

        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Configuration");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Attribute attribute = new Attribute();
                attribute.setOutId(rs.getInt("outId"));
                attribute.setAssetSerialNumber(rs.getString("assetSerialNumber"));
                attribute.setType(rs.getString("type"));
                attribute.setExternalWorkOrder(rs.getString("externalWorkOrder"));
                attribute.setSystemStatus(rs.getString("systemStatus"));
                attribute.setUserStatus(rs.getString("userStatus"));
                attribute.setCreatedOn(rs.getString("createdOn"));
                attribute.setCreatedBy(rs.getString("createdBy"));
                attribute.setName(rs.getString("name"));
                attribute.setPriority(rs.getString("priority"));
                attribute.setStatus(rs.getString("status"));
                attribute.setLatestFinishDate(rs.getString("latestFinishDate"));
                attribute.setEarliestStartDate(rs.getString("earliestStartDate"));
                attribute.setLatestStartDate(rs.getString("latestStartDate"));
                attribute.setEstimatedTime(rs.getString("estimatedTime"));
                attribute.setConfigurationName(rs.getString("configurationName"));
                allAttributes.add(attribute);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
            return null;
        }
        return allAttributes;
    }

    public void configSave(Attribute config) {
        try (Connection con = dbConnector.getConnection()) {
            String sql
                    = "INSERT INTO Config"
                    + " VALUES(?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, config.getConfigurationName());
//            pstmt.setString(2, config.getAssetSerialNumber());
//            pstmt.setString(3, config.getType());
//            pstmt.setString(4, config.getExternalWorkOrder());
//            pstmt.setString(5, config.getSystemStatus());
//            pstmt.setString(6, config.getUserStatus());
//            pstmt.setString(7, config.getCreatedOn());
//            pstmt.setString(8, config.getCreatedBy());
//            pstmt.setString(9, config.getName());
//            pstmt.setString(10, config.getPriority());
//            pstmt.setString(11, config.getStatus());
//            pstmt.setString(12, config.getLatestFinishDate());
//            pstmt.setString(13, config.getEarliestStartDate());
//            pstmt.setString(14, config.getLatestStartDate());
//            pstmt.setString(15, config.getEstimatedTime());
//            pstmt.setString(16, config.getConfigurationName());
            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Can't save configuration");
            }

//            //Get Database generated id and set user id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                config.setOutId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void headerSave(Header header) {
        try (Connection con = dbConnector.getConnection()) {
            String sql
                    = "INSERT INTO Header"
                    + " VALUES(?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, header.getHeaderName());
            pstmt.setInt(2, header.getHeaderIndex());
            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Can't save configuration");
            }

//            //Get Database generated id and set user id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                header.setHeaderId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveAll(Attribute config, Header header) {
        try (Connection con = dbConnector.getConnection()) {
            String sql = "INSERT INTO configRelation"
                    + " VALUES (?,?)";
            PreparedStatement pstmt
                    = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, config.getOutId());
            pstmt.setInt(2, header.getHeaderId());

            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Can't save config");
            }

        } catch (SQLException ex) {
            Logger.getLogger(CfgDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
