/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import java.io.IOException;
import java.sql.SQLException;
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

    /**
     * Constructor
     */
    private AdminViewModel() {
        aManager = new AdminManager();
        logginList = FXCollections.observableArrayList();
    }

    /**
     * Adds all logs to a list
     */
    public void loadLoggins() {
        logginList.clear();
        logginList.addAll(aManager.getAllLoggins());
    }

    /**
     * Gets all logs
     * @return List
     */
    public ObservableList<Loggin> getAllLoggins() {
        return logginList;
    }

    /**
     * Returns the instance of AdminViewModel
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    public static AdminViewModel getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new AdminViewModel();
        }
        return instance;
    }

    /**
     * Sends the data further in to the system
     * @param l
     * @throws SQLException 
     */
    public void uploadLogger(Loggin l) throws SQLException {

        aManager.uploadLogger(l);
    }

    /**
     * Makes a new logger and sets the data.
     * @param name
     * @param actionP
     * @param userName
     * @param date
     * @param desc 
     */
    public void addTraceLog(String name, String actionP, String userName,String date, String desc) {
        try { 
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
}
