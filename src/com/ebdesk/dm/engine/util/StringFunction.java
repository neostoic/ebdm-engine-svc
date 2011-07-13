/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.util;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Muhammad Rifa'i
 */
public class StringFunction {

    public static String[] splitTextByLine(String data){
        if (data == null || "".equals(data)) {
            return new String[]{""};
        }               
                
        String lines[] = data.split("\\r?\\n");
        return lines;                
    }

    
    
}
