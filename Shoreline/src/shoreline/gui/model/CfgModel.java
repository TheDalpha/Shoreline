/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shoreline.be.Attribute;
import shoreline.bll.CfgManager;

/**
 *
 * @author ollie
 */
public class CfgModel
{
    CfgManager cfgManager;
    ObservableList<Attribute> attributeList;
    
    public CfgModel() {
        cfgManager = new CfgManager();
        attributeList = FXCollections.observableArrayList();
    }
    
    public void loadAttributes() {
        attributeList.clear();
        attributeList.addAll(cfgManager.getAllAttributes());
    }
    
    public ObservableList<Attribute> getAllAttributes() {
        return attributeList;
    }
}
