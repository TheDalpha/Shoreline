/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shoreline.be.Loggin;
import shoreline.bll.AdminManager;
import shoreline.gui.controller.UserViewController;

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

    public static AdminViewModel getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new AdminViewModel();
        }
        return instance;
    }

    public void uploadLogger(Loggin l) throws SQLException {

        aManager.uploadLogger(l);
    }
    
    public void uploadL (Loggin el) throws SQLException  {
        aManager.uploadL(el);
    }

    public void addTraceLog(String name, String actionP, String userName,String date, String desc) {
        try { //String actionP
            Loggin l = new Loggin();
            l.setUsername(userName);
            l.setFilename(name);
            l.setAction(actionP);
            l.setDate(date);
            l.setError(desc);

            uploadLogger(l);
        } catch (SQLException ex) {
            Logger.getLogger(UserViewController.class.getName()).log(Level.SEVERE, "w.e", ex);
        }

    }

//    public void addErrorLog(String desc) {
//        try {
//            Loggin el = new Loggin();
//            el.setError(desc);
//            
//            uploadL(el);
//        } catch (Exception e) {
//        }
//    }
}
