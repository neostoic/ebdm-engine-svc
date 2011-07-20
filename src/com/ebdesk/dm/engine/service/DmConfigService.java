/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.service;

/**
 *
 * @author Muhammad Rifai
 */
public interface DmConfigService {
    
    /****
     * Get a configuration value from key.
     * @param key the configuration key to find.
     * @return configuration value
     */
    public String getValue(String key);

    public void setValue(String key, String value);
}
