/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dto;

import com.ebdesk.dm.engine.domain.DmDocRenderImage;
import com.ebdesk.dm.engine.domain.DmDocument;
import java.util.List;

/**
 *
 * @author Muhammad Rifai
 */
public class DocumentView {

    private DmDocument document;
    
    private List<DmDocRenderImage> renderImageList;

    public DocumentView() {
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public List<DmDocRenderImage> getRenderImageList() {
        return renderImageList;
    }

    public void setRenderImageList(List<DmDocRenderImage> renderImageList) {
        this.renderImageList = renderImageList;
    }
}
