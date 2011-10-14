/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dto;

import com.ebdesk.dm.engine.domain.DmDocAccessHistory;
import com.ebdesk.dm.engine.domain.DmDocument;

/**
 *
 * @author user
 */
public class DocumentWithAccessLog {
    private DmDocument document;
    private DmDocAccessHistory accessLog;

    public DocumentWithAccessLog() {
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public DmDocAccessHistory getAccessLog() {
        return accessLog;
    }

    public void setAccessLog(DmDocAccessHistory accessLog) {
        this.accessLog = accessLog;
    }
}
