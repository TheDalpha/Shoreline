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
public class Attribute {

    public int outId;
    public String ConfigurationName;
    public final List<Header> savedHeader;

    // Constructor
    public Attribute() {
        this.savedHeader = new ArrayList<>();
    }

    // Get list of headers
    public List<Header> getSavedHeader() {
        return savedHeader;
    }

    // Get configuration name
    public String getConfigurationName() {
        return ConfigurationName;
    }

    // Sets the configuration name
    public void setConfigurationName(String ConfigurationName) {
        this.ConfigurationName = ConfigurationName;
    }

    // Get the configuration id
    public int getOutId() {
        return outId;
    }

    // Sets the configuration id
    public void setOutId(int outId) {
        this.outId = outId;
    }

}
