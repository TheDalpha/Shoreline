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
import shoreline.be.Loggin;
import shoreline.bll.AdminManager;

/**
 *
 * @author ollie
 */
public class AdminViewModel {
    
    private static AdminViewModel instance;
    AdminManager aManager;
    ObservableList<Loggin> logginList;
    
    private AdminViewModel() {
        aManager = new AdminManager();
        logginList = FXCollections.observableArrayList();
    }
    
    public void loadLoggins() {
        logginList.clear();
        logginList.addAll(aManager.getAllLoggins());
    }
    
    public ObservableList<Loggin> getAllLoggins() {
        return logginList;
    }
    
    public static AdminViewModel getInstance() throws SQLException, IOException
    {
        if (instance == null)
        {
            instance = new AdminViewModel();
        }
        return instance;
    }
    
    public void tLog() throws SQLException {
        aManager.tLog();
    }
}
