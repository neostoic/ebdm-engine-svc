/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import java.util.List;

/**
 *
 * @author Muhammad Rifai
 */
public interface DmExtractService {
    
    public static final int ITEM_PER_PROCESS = 2;
    
    public List<DmDocumentVersion> findNotRenderedVersion(int start, int max);
    
    public String generatePrefixOutput(DmDocumentVersion documentVersion);
    
    public void updateDocumentVersion(DmDocumentVersion documentVersion, String prefixOutput, String imageType);    
    
    @Deprecated
    public void extractDocument(DmDocumentVersion documentVersion, String imageType);
    
    @Deprecated
    public void extractDocument();
    
    @Deprecated
    public void fillAnnotation();
    
    @Deprecated
    public void fillComment();
}
