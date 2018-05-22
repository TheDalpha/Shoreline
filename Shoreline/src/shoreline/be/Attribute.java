/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

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
    public Date createdOn;
    public String createdBy;
    public String name;
    public String priority;
    public String status;
    public Date latestFinishDate;
    public Date earliestStartDate;
    public Date latestStartDate;
    public Time estimatedTime;

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

    public Date getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn)
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

    public Date getLatestFinishDate()
    {
        return latestFinishDate;
    }

    public void setLatestFinishDate(Date latestFinishDate)
    {
        this.latestFinishDate = latestFinishDate;
    }

    public Date getEarliestStartDate()
    {
        return earliestStartDate;
    }

    public void setEarliestStartDate(Date earliestStartDate)
    {
        this.earliestStartDate = earliestStartDate;
    }

    public Date getLatestStartDate()
    {
        return latestStartDate;
    }

    public void setLatestStartDate(Date latestStartDate)
    {
        this.latestStartDate = latestStartDate;
    }

    public Time getEstimatedTime()
    {
        return estimatedTime;
    }

    public void setEstimatedTime(Time estimatedTime)
    {
        this.estimatedTime = estimatedTime;
    }
    
    
}
