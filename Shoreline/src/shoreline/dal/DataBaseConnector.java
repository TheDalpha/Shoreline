/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

/**
 *
 * @author Lars
 */
public class DataBaseConnector {
    
    private SQLServerDataSource dataSource = new SQLServerDataSource();

    /*
    * sets the database sources. Husk at encrypt.
    */
    public DataBaseConnector()
    {;
        dataSource.setServerName("EASV-DB2");
        dataSource.setPortNumber(1433);
        dataSource.setDatabaseName("Ministre_Shoreline");
        dataSource.setUser("CS2017A_27");
        dataSource.setPassword("Donttype12398");
    }
    
    /*
    * gets the connection to the database
    */
    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }
    
}
