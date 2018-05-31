/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shoreline.be.Configuration;
import shoreline.be.Header;
import shoreline.bll.CfgManager;

/**
 *
 * @author ollie
 */
public class CfgModel {

    CfgManager cfgManager;
    private static CfgModel instance;
    ObservableList<Configuration> attributeList;

    /**
     * Constructor
     */
    public CfgModel() {
        cfgManager = new CfgManager();
        attributeList = FXCollections.observableArrayList();
    }

    /**
     * Returns the instance of CfgModel
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public static CfgModel getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new CfgModel();
        }
        return instance;
    }

    /**
     * Adds all configs to a list
     */
    public void loadConfig() {
        attributeList.clear();
        attributeList.addAll(cfgManager.getAllConfig());
    }

    /**
     * Gets all configs
     * @return 
     */
    public ObservableList<Configuration> getAllConfigs() {
        return attributeList;
    }

    /**
     * Sends data further into the system
     * @param config 
     */
    public void configSave(Configuration config) {
        cfgManager.configSave(config);
    }

    /**
     * Sends data further into the system
     * @param header 
     */
    public void headerSave(Header header) {
        cfgManager.headerSave(header);
    }

    /**
     * Sends data further into the system
     * @param config
     * @param header 
     */
    public void saveAll(Configuration config, Header header) {
        cfgManager.saveAll(config,header);
    }

    public void deleteConfig(Configuration config) {
        cfgManager.deleteConfig(config);
    }
    
}
