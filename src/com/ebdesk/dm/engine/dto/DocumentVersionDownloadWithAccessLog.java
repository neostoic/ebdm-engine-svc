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
public class DocumentVersionDownloadWithAccessLog {
    private DocumentVersionDownload documentVersionDownload;
    private DmDocAccessHistory accessLog;

    public DocumentVersionDownloadWithAccessLog() {
    }

    public DocumentVersionDownload getDocumentVersionDownload() {
        return documentVersionDownload;
    }

    public void setDocumentVersionDownload(DocumentVersionDownload documentVersionDownload) {
        this.documentVersionDownload = documentVersionDownload;
    }

    public DmDocAccessHistory getAccessLog() {
        return accessLog;
    }

    public void setAccessLog(DmDocAccessHistory accessLog) {
        this.accessLog = accessLog;
    }
}
