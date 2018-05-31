/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.dal.dbConnector;

import shoreline.dal.dbConnector.ObjectPool;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import shoreline.gui.model.AdminViewModel;

/**
 *
 * @author Jesper Riis
 */
public class ConnectionPool extends ObjectPool<Connection> {

    private Connection con;
    private DataBaseConnector dbConnector;
    private static ConnectionPool instance;

    public ConnectionPool()
    {
        super();
        dbConnector = new DataBaseConnector();
    }
    
    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }
    
    @Override
    public void expire(Connection o)
    {
        try
        {
            o.close();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean validate(Connection o)
    {
        try
        {
            return !o.isClosed();
        } catch (SQLException ex)
       {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    protected Connection create()
    {
        try
        {
            return dbConnector.getConnection();
        } catch (SQLServerException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

}