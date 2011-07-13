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
    
    public static final int ITEM_PER_PROCESS = 10;
    
    public List<DmDocumentVersion> findNotRenderedVersion(int start, int max);
    
    public void extractDocument(DmDocumentVersion documentVersion, String imageType);
    
    public void extractDocument();
    
    
    public void fillAnnotation();
    
    
    public void fillComment();
}
