/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.gui.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.IOException;
import static java.nio.file.Files.list;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import shoreline.be.Header;
import shoreline.be.Person;
import shoreline.be.User;
import shoreline.bll.JFileReader;
import shoreline.bll.JFileWriter;
import shoreline.bll.UserManager;

/**
 *
 * @author ollie
 */
public class UserViewModel
{
    
    private static UserViewModel instance;
        UserManager uManager;
        JFileReader fileReader;
        JFileWriter fileWriter;
        User user;
        ObservableList<User> userList;
    private Person person;

    public UserViewModel() {
        uManager = new UserManager();
        user = new User();
        fileReader = new JFileReader();
        fileWriter = new JFileWriter();
        userList = FXCollections.observableArrayList();
    }
    
    public void loadUsers() {
        userList.clear();
        userList.addAll(uManager.getAllUsers());
    }
    
    public ObservableList<User> getAllUsers() {
        return userList;
    }
    
    public void updateUser(User user) {
        uManager.updateUser(user);
    }
    
    public static UserViewModel getInstance() throws SQLException, IOException
    {
        if (instance == null)
        {
            instance = new UserViewModel();
        }
        return instance;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public void createUser(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        uManager.createUser(user);
    }

    public void deleteUser(User selectedUser) {
        uManager.deleteUser(selectedUser);
    }

    public void setFilePath(String filePath, Map<String, Header> ja, boolean oneLine) throws Exception {
         fileReader.readXLSXAndConvertToJSON(filePath, ja, oneLine);
    }

    public void convertToJson(String path, String json) throws IOException {
        fileWriter.convertToJson(path, json);
    }
    public List<Header> getFileHeaders(File file) throws IOException, InvalidFormatException {
      return fileReader.getFileHeaders(file);
    }  
    public String XLSXR() throws JsonProcessingException {
      return fileReader.XLSXR();
    } 
//    public void setFilePoth (String filePoth) throws IOException {
//    fileReader.readCSVAndConvertToJSON(filePoth);
//    }
//    public List<Header> getFileH (File file) throws IOException {
//        return fileReader.getFileH(file);
//        
//    }

    public void setTemplate(Map<String, Header> jobj) {
        fileReader.setTemplate(jobj);
    }
}
