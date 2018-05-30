/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.be;

import java.util.ArrayList;
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
    
}
