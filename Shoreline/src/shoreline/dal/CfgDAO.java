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
import shoreline.be.Configuration;
import shoreline.be.Header;

/**
 *
 * @author ollie
 */
public class CfgDAO {

    private DataBaseConnector dbConnector;
    List<Configuration> allConfig = new ArrayList();

    /**
     * Constructor
     */
    public CfgDAO() {
        dbConnector = new DataBaseConnector();
    }

    /**
     * Connects to the database and selects all from Config, configRelation, Header table
     * Sets each Config and header
     * @return List
     */
    public List<Configuration> getAllConfigs() {

        getConfigs();
        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Config,configRelation,Header WHERE "
                            + "configRelation.headerId = Header.headerId AND configRelation.configId = Config.configId");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Configuration attribute = new Configuration();
                attribute.setOutId(rs.getInt("configId"));
                Header header = new Header();
                header.setHeaderName(rs.getString("headerName"));
                header.setHeaderIndex(rs.getInt("headerIndex"));
                header.setAttName(rs.getString("attName"));
                header.setListIndex(rs.getInt("listIndex"));
                
                // Goes through list of all configs, if the config id is equal to the config id
                // from the database, it will add the header to that config
                for (Configuration attribute1 : allConfig) {
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

    /**
     * Connect to the database and selects all from Config Table
     * Gets all configs and adds them to a list
     * @return List
     */
    public List<Configuration> getConfigs() {
        allConfig.clear();

        try (Connection con = dbConnector.getConnection()) {

            PreparedStatement pstmt
                    = con.prepareStatement("Select * FROM Config");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Configuration config = new Configuration();
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

    /**
     * Connects to the database and inserts a config into the Config Table
     * @param config 
     */
    public void configSave(Configuration config) {
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

            //Get Database generated id and set config id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                config.setOutId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Connects to the database and inserts a header into the Header table
     * @param header 
     */
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

            //Get Database generated id and set header id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                header.setHeaderId(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Connects to the database and
     * inserts the config id and header id into the configRelation table
     * @param config
     * @param header 
     */
    public void saveAll(Configuration config, Header header) {
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
