/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dto;

import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import com.ebdesk.dm.engine.util.compare.LineDifference;
import java.util.List;

/**
 * 
 * @author Muhammad Rifai
 */
public class DocumentCompare {

    private DmDocument document;
    
    private DmDocumentVersion firstVersion;
    
    private DmDocumentVersion secondVersion;
    
    private List<LineDifference> lineDifferences;

    public DocumentCompare() {
    
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public DmDocumentVersion getFirstVersion() {
        return firstVersion;
    }

    public void setFirstVersion(DmDocumentVersion firstVersion) {
        this.firstVersion = firstVersion;
    }

    public List<LineDifference> getLineDifferences() {
        return lineDifferences;
    }

    public void setLineDifferences(List<LineDifference> lineDifferences) {
        this.lineDifferences = lineDifferences;
    }

    public DmDocumentVersion getSecondVersion() {
        return secondVersion;
    }

    public void setSecondVersion(DmDocumentVersion secondVersion) {
        this.secondVersion = secondVersion;
    }
}
