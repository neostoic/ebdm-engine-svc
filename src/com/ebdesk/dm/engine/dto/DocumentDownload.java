/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dto;

import com.ebdesk.dm.engine.domain.DmDocument;

/**
 *
 * @author Muhammad Rifai
 */
public class DocumentDownload {
    
    private DmDocument document;
    
    private byte[] data;

    public DocumentDownload() {
        
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
}
