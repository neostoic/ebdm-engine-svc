/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.dao.DmConfigDao;
import com.ebdesk.dm.engine.domain.DmConfig;
import com.ebdesk.dm.engine.service.DmConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Muhammad Rifai
 */
@Service("dmConfigService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmConfigServiceImpl implements DmConfigService {

    @Autowired
    private DmConfigDao configDao;

    /****
     * Get a configuration value from key.
     * @param key the configuration key to find.
     * @return configuration value
     */
    public String getValue(String key) {
        DmConfig config = configDao.findById(key);
        return config.getValue();
    }

    public void setValue(String key, String value) {
//        System.out.println("key = " + key);
        DmConfig config = configDao.findById(key);
        if (config != null) {
//            System.out.println("config != null");
//            System.out.println("value 1 = " + config.getValue());
            config.setValue(value);
//            System.out.println("value 2 = " + value);
            configDao.update(config);
        }
//        else {
//            System.out.println("config == null");
//        }
    }
}
