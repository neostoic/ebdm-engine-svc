/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmConfig;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmConfigDao")
public class DmConfigDao extends BaseDmEngineDaoImpl<DmConfig> {
    
    public DmConfigDao(){
        this.t = new DmConfig();
    }
    
}
