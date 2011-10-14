/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dto;

import com.ebdesk.dm.engine.domain.DmDocAccessHistory;

/**
 *
 * @author user
 */
public class DocumentViewWithAccessLog {
    private DocumentView documentView;
    private DmDocAccessHistory accessLog;

    public DocumentViewWithAccessLog() {
    }

    public DocumentView getDocumentView() {
        return documentView;
    }

    public void setDocumentView(DocumentView documentView) {
        this.documentView = documentView;
    }

    public DmDocAccessHistory getAccessLog() {
        return accessLog;
    }

    public void setAccessLog(DmDocAccessHistory accessLog) {
        this.accessLog = accessLog;
    }
}
