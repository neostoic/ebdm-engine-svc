/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmContentType;
import com.ebdesk.dm.engine.util.FileUtils;
import java.util.List;
import javax.swing.text.Document;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmContentTypeDao")
public class DmContentTypeDao extends BaseDmEngineDaoImpl<DmContentType> {

    public DmContentTypeDao() {
        this.t = new DmContentType();
    }

    public String validDocumentFormat() {
        StringBuilder sbFormat = new StringBuilder();
        List<DmContentType> contentTypes = findAll();
        int i = 0;
        for (DmContentType dmContentType : contentTypes) {
            if (i != 0) {
                sbFormat.append(";");
            }
            sbFormat.append("*").append(dmContentType.getFileExtension());
            i++;
        }
        return sbFormat.toString();
    }

    public DmContentType findByName(String name) {
        List<DmContentType> contentTypes = findByKey("name", name, 0, 1);
        if (contentTypes.size() > 0) {
            return contentTypes.get(0);
        }
        return null;
    }
    
    public DmContentType findByFileExtension(String extension) {
        List<DmContentType> contentTypes = findByKey("fileExtension", extension, 0, 1);
        if (contentTypes.size() > 0) {
            return contentTypes.get(0);
        }
        return null;
    }

    public DmContentType getContentType(String fileName) {
        String extension = FileUtils.getFileExtension(fileName);
        DmContentType contentType = findByFileExtension(extension);
        if (contentType == null) {
            return null;
        }
        return contentType;
    }
    
    public String getFileMimeType(String fileName) {
        String extension = FileUtils.getFileExtension(fileName);
        DmContentType contentType = findByFileExtension(extension);
        if (contentType == null) {
            return null;
        }
        return contentType.getName();
    }
    
}
