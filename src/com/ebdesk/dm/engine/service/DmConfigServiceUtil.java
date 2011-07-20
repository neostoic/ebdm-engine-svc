/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Muhammad Rifai
 */
@Component
public class DmConfigServiceUtil {
    
    
    private static DmConfigService configService;

    @Autowired
    public void setConfigService(DmConfigService configService) {
        DmConfigServiceUtil.configService = configService;
    }

    public static DmConfigService getConfigService() {
        return configService;
    }
    
    public static String getValue(String key){
        return getConfigService().getValue(key);
    }

    public static void setValue(String key, String value) {
        getConfigService().setValue(key, value);
    }
}
