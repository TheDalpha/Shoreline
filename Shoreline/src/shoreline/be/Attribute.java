/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ollie
 */
public class Attribute
{
    public int outId;
    public String ConfigurationName;
    public String siteName;
    public String assetSerialNumber;
    public String type;
    public String externalWorkOrder;
    public String systemStatus;
    public String userStatus;
    public String createdOn;
    public String createdBy;
    public String name;
    public String priority;
    public String status;
    public String latestFinishDate;
    public String earliestStartDate;
    public String latestStartDate;
    public String estimatedTime;
    public final List<Header> savedHeader;

    public Attribute() {
        this.savedHeader = new ArrayList<>();
    }

    public List<Header> getSavedHeader() {
        return savedHeader;
    }

    
    public String getConfigurationName() {
        return ConfigurationName;
    }

    public void setConfigurationName(String ConfigurationName) {
        this.ConfigurationName = ConfigurationName;
    }

    
    public int getOutId()
    {
        return outId;
    }

    public void setOutId(int outId)
    {
        this.outId = outId;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }

    public String getAssetSerialNumber()
    {
        return assetSerialNumber;
    }

    public void setAssetSerialNumber(String assetSerialNumber)
    {
        this.assetSerialNumber = assetSerialNumber;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getExternalWorkOrder()
    {
        return externalWorkOrder;
    }

    public void setExternalWorkOrder(String externalWorkOrder)
    {
        this.externalWorkOrder = externalWorkOrder;
    }

    public String getSystemStatus()
    {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus)
    {
        this.systemStatus = systemStatus;
    }

    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(String userStatus)
    {
        this.userStatus = userStatus;
    }

    public String getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(String createdOn)
    {
        this.createdOn = createdOn;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getLatestFinishDate()
    {
        return latestFinishDate;
    }

    public void setLatestFinishDate(String latestFinishDate)
    {
        this.latestFinishDate = latestFinishDate;
    }

    public String getEarliestStartDate()
    {
        return earliestStartDate;
    }

    public void setEarliestStartDate(String earliestStartDate)
    {
        this.earliestStartDate = earliestStartDate;
    }

    public String getLatestStartDate()
    {
        return latestStartDate;
    }

    public void setLatestStartDate(String latestStartDate)
    {
        this.latestStartDate = latestStartDate;
    }

    public String getEstimatedTime()
    {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime)
    {
        this.estimatedTime = estimatedTime;
    }
    
    
}
