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
import java.util.ArrayList;
import java.util.List;
import shoreline.be.Attribute;
import shoreline.be.User;

/**
 *
 * @author ollie
 */
public class CfgDAO
{
    private DataBaseConnector dbConnector;
    
    public CfgDAO(){
        dbConnector = new DataBaseConnector();
    }
    
    public List<Attribute> getAllAttributes() {
        List<Attribute> allAttributes = new ArrayList();
        
        try (Connection con = dbConnector.getConnection()) {
            PreparedStatement pstmt
                    = con.prepareStatement("SELECT * FROM Configuration");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Attribute attribute = new Attribute();
                attribute.setOutId(rs.getInt("outId"));
                attribute.setAssetSerialNumber(rs.getString("assetSerialNumber"));
                attribute.setType(rs.getString("type"));
                attribute.setExternalWorkOrder(rs.getString("externalWorkOrder"));
                attribute.setSystemStatus(rs.getString("systemStatus"));
                attribute.setUserStatus(rs.getString("userStatus"));
                attribute.setCreatedOn(rs.getDate("createdOn"));
                attribute.setCreatedBy(rs.getString("createdBy"));
                attribute.setName(rs.getString("name"));
                attribute.setPriority(rs.getString("priority"));
                attribute.setStatus(rs.getString("status"));
                attribute.setLatestFinishDate(rs.getDate("latestFinishDate"));
                attribute.setEarliestStartDate(rs.getDate("earliestStartDate"));
                attribute.setLatestStartDate(rs.getDate("latestStartDate"));
                attribute.setEstimatedTime(rs.getTime("estimatedTime"));
                allAttributes.add(attribute);
            }
        } catch (SQLException ex) {
            System.err.print(ex);
            return null;
        }
        return allAttributes;
    }

    public void configSave(Attribute config) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
