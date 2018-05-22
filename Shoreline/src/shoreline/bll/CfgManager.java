/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shoreline.bll;

import java.util.List;
import shoreline.be.Attribute;
import shoreline.be.Header;
import shoreline.dal.CfgDAO;

/**
 *
 * @author ollie
 */
public class CfgManager
{
    CfgDAO udao;
    
    public CfgManager() {
        this.udao = new CfgDAO();
    }
    
    public List<Attribute> getAllAttributes() {
        return udao.getAllConfigs();
    }

    public void configSave(Attribute config) {
        udao.configSave(config);
    }

    public void headerSave(Header header) {
        udao.headerSave(header);
    }

    public void saveAll(Attribute config, Header header) {
        udao.saveAll(config, header);
    }
}
