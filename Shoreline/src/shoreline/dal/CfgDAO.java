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

/**
 *
 * @author ollie
 */
public class CfgDAO {

    private DataBaseConnector dbConnector;
    List<Attribute> allConfig = new ArrayList();

    public CfgDAO() {
        dbConnector = new DataBaseConnector();
    }

    public List<Attribute> getAllConfigs() {

        getAttributes();
        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Config,configRelation,Header WHERE configRelation.headerId = Header.headerId AND configRelation.configId = Config.configId");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Attribute attribute = new Attribute();
                attribute.setOutId(rs.getInt("configId"));
                Header header = new Header();
                header.setHeaderName(rs.getString("headerName"));
                header.setHeaderIndex(rs.getInt("headerIndex"));
                header.setAttName(rs.getString("attName"));
                header.setListIndex(rs.getInt("listIndex"));
                for (Attribute attribute1 : allConfig) {
                    if (attribute1.getOutId() == attribute.getOutId()) {
                        attribute1.getSavedHeader().add(header);
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.print(ex);
            return null;
        }
        return allConfig;
    }

    public List<Attribute> getAttributes() {
        allConfig.clear();

        try (Connection con = dbConnector.getConnection()) {

            PreparedStatement pstmt
                    = con.prepareStatement("Select * FROM Config");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Attribute config = new Attribute();
                config.setOutId(rs.getInt("configId"));
                config.setConfigurationName(rs.getString("configName"));

                allConfig.add(config);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CfgDAO.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
        return allConfig;

    }

    public void configSave(Attribute config) {
        try (Connection con = dbConnector.getConnection()) {
            String sql
                    = "INSERT INTO Config"
                    + " VALUES(?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, config.getConfigurationName());
            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Can't save configuration");
            }

            //Get Database generated id and set user id
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
                    + " VALUES(?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, header.getHeaderName());
            pstmt.setInt(2, header.getHeaderIndex());
            pstmt.setInt(3, header.getListIndex());
            pstmt.setString(4, header.getAttName());
            int affected = pstmt.executeUpdate();
            if (affected < 1) {
                throw new SQLException("Can't save configuration");
            }

            //Get Database generated id and set user id
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
