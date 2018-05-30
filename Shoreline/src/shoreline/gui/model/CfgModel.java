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
import shoreline.be.Attribute;
import shoreline.be.Header;
import shoreline.bll.CfgManager;

/**
 *
 * @author ollie
 */
public class CfgModel {

    CfgManager cfgManager;
    private static CfgModel instance;
    ObservableList<Attribute> attributeList;

    public CfgModel() {
        cfgManager = new CfgManager();
        attributeList = FXCollections.observableArrayList();
    }

    public static CfgModel getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new CfgModel();
        }
        return instance;
    }

    public void loadAttributes() {
        attributeList.clear();
        attributeList.addAll(cfgManager.getAllConfig());
    }

    public ObservableList<Attribute> getAllAttributes() {
        return attributeList;
    }

    public void configSave(Attribute config) {
        cfgManager.configSave(config);
    }

    public void headerSave(Header header) {
        cfgManager.headerSave(header);
    }

    public void saveAll(Attribute config, Header header) {
        cfgManager.saveAll(config,header);
    }
}
