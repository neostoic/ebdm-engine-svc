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
public class DocumentDownloadWithAccessLog {
    private DocumentDownload documentDownload;
    private DmDocAccessHistory accessLog;

    public DocumentDownloadWithAccessLog() {
    }

    public DocumentDownload getDocumentDownload() {
        return documentDownload;
    }

    public void setDocumentDownload(DocumentDownload documentDownload) {
        this.documentDownload = documentDownload;
    }

    public DmDocAccessHistory getAccessLog() {
        return accessLog;
    }

    public void setAccessLog(DmDocAccessHistory accessLog) {
        this.accessLog = accessLog;
    }
}
