/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentIndexed;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public interface DmDocumentIndexedService {
    public boolean save(DmDocumentIndexed docIndexed);
    public List<DmDocument> getNotIndexedDocList(int numDocs);
//    public List<DmDocument> getNotIndexedDocListFromModifiedTime(Date docModificationTime, int numDocs);
    public boolean deleteByDocumentId(String documentId);
    public boolean setReindexByFolder(String folderId);
}
