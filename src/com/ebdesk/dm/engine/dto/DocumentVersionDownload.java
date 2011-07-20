/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dto;

import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;

/**
 *
 * @author user
 */
public class DocumentVersionDownload {
    private DmDocument document;
    private DmDocumentVersion documentVersion;

    private byte[] data;

    public DocumentVersionDownload() {

    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public DmDocumentVersion getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(DmDocumentVersion documentVersion) {
        this.documentVersion = documentVersion;
    }
}
