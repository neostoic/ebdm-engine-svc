/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.util.config;

import com.ebdesk.dm.engine.constant.ConfigConstants;
import com.ebdesk.dm.engine.service.DmConfigServiceUtil;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Muhammad Rifai
 */
public class ConfigVar {
    
    public static String baseDocImageLocation(String folderId, String docId, String docVersionId){
        String baseDocImageLocation = DmConfigServiceUtil.getValue(ConfigConstants.BASE_DOC_IMAGE_LOCATION);
        
        baseDocImageLocation = baseDocImageLocation.replaceAll("%year%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.YEAR), 2, "0"));
        baseDocImageLocation = baseDocImageLocation.replaceAll("%month%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.MONTH+1), 2, "0"));
        baseDocImageLocation = baseDocImageLocation.replaceAll("%date%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 2, "0"));
        baseDocImageLocation = baseDocImageLocation.replaceAll("%folder_id%", folderId);
        baseDocImageLocation = baseDocImageLocation.replaceAll("%doc_id%", docId);
        baseDocImageLocation = baseDocImageLocation.replaceAll("%version_id%", docVersionId);
        
        return baseDocImageLocation;
    }
    
    
    public static String baseDocLocation(String folderId, String docId, String docVersionId){
        String baseDocImageLocation = DmConfigServiceUtil.getValue(ConfigConstants.BASE_DOC_LOCATION);
        
        baseDocImageLocation = baseDocImageLocation.replaceAll("%year%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.YEAR), 2, "0"));
        baseDocImageLocation = baseDocImageLocation.replaceAll("%month%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.MONTH+1), 2, "0"));
        baseDocImageLocation = baseDocImageLocation.replaceAll("%date%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 2, "0"));
        baseDocImageLocation = baseDocImageLocation.replaceAll("%folder_id%", folderId);
        baseDocImageLocation = baseDocImageLocation.replaceAll("%doc_id%", docId);
        baseDocImageLocation = baseDocImageLocation.replaceAll("%version_id%", docVersionId);
        
        return baseDocImageLocation;
    }
    
}
