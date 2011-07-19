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
        
        baseDocImageLocation = StringUtils.replace(baseDocImageLocation, "%year%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.YEAR), 2, "0"));
        baseDocImageLocation = StringUtils.replace(baseDocImageLocation, "%month%", StringUtils.leftPad(""+(Calendar.getInstance().get(Calendar.MONTH)+1), 2, "0"));
        baseDocImageLocation = StringUtils.replace(baseDocImageLocation, "%date%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 2, "0"));
        baseDocImageLocation = StringUtils.replace(baseDocImageLocation, "%folder_id%", folderId);
        baseDocImageLocation = StringUtils.replace(baseDocImageLocation, "%document_id%", docId);
        baseDocImageLocation = StringUtils.replace(baseDocImageLocation, "%version_id%", docVersionId);
        
        return baseDocImageLocation;
    }
    
    
    public static String baseDocTextLocation(String folderId, String docId, String docVersionId){
        String baseDocTextLocation = DmConfigServiceUtil.getValue(ConfigConstants.BASE_DOC_TEXT_LOCATION);
        
        baseDocTextLocation = StringUtils.replace(baseDocTextLocation, "%year%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.YEAR), 2, "0"));
        baseDocTextLocation = StringUtils.replace(baseDocTextLocation, "%month%", StringUtils.leftPad(""+(Calendar.getInstance().get(Calendar.MONTH)+1), 2, "0"));
        baseDocTextLocation = StringUtils.replace(baseDocTextLocation, "%date%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 2, "0"));
        baseDocTextLocation = StringUtils.replace(baseDocTextLocation, "%folder_id%", folderId);
        baseDocTextLocation = StringUtils.replace(baseDocTextLocation, "%document_id%", docId);
        baseDocTextLocation = StringUtils.replace(baseDocTextLocation, "%version_id%", docVersionId);
        
        return baseDocTextLocation;
    }
    
    
    public static String baseDocLocation(String folderId, String docId, String docVersionId){
        String baseDocLocation = DmConfigServiceUtil.getValue(ConfigConstants.BASE_DOC_LOCATION);
        
        baseDocLocation = StringUtils.replace(baseDocLocation, "%year%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.YEAR), 2, "0"));
        baseDocLocation = StringUtils.replace(baseDocLocation, "%month%", StringUtils.leftPad(""+(Calendar.getInstance().get(Calendar.MONTH)+1), 2, "0"));
        baseDocLocation = StringUtils.replace(baseDocLocation, "%date%", StringUtils.leftPad(""+Calendar.getInstance().get(Calendar.DAY_OF_MONTH), 2, "0"));
        baseDocLocation = StringUtils.replace(baseDocLocation, "%folder_id%", folderId);
        baseDocLocation = StringUtils.replace(baseDocLocation, "%document_id%", docId);
        baseDocLocation = StringUtils.replace(baseDocLocation, "%version_id%", docVersionId);
        
        return baseDocLocation;
    }
    
}
