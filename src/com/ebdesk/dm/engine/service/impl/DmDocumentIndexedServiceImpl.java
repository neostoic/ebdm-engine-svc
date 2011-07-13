/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.dao.DmDocumentIndexedDao;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentIndexed;
import com.ebdesk.dm.engine.service.DmDocumentIndexedService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Service("dmDocumentIndexedService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmDocumentIndexedServiceImpl implements DmDocumentIndexedService {

    @Autowired
    private DmDocumentIndexedDao documentIndexedDao;

    public boolean save(DmDocumentIndexed docIndexed) {
        documentIndexedDao.save(docIndexed);
        return true;
    }

    public List<DmDocument> getNotIndexedDocList(int numDocs) {
        return documentIndexedDao.getNotIndexedDocList(numDocs);
    }

    public boolean deleteByDocumentId(String documentId) {
        documentIndexedDao.deleteByDocumentId(documentId);
        return true;
    }

    public boolean setReindexByFolder(String folderId) {
        documentIndexedDao.setReindexByFolder(folderId);
        return true;        
    }
}
