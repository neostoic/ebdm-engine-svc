/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.domain.util;

/**
 *
 * @author designer
 */
public enum DocumentAccessHistory {
    
    META_DATA_VIEW(1),
    
    DOCUMENT_VIEW(2),

    DOWNLOAD(3);

    
    private int accessType;

    DocumentAccessHistory(int accessType) {        
        this.accessType = accessType;
    }

    public int getAccessType() {
        return accessType;
    }
}
