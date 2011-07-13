/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentIndexed;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DmDocumentIndexedServiceUtil {

    private static DmDocumentIndexedService documentIndexedService;

    public static DmDocumentIndexedService getDocumentIndexedService() {
        if (documentIndexedService == null) {
            throw new RuntimeException(DmDocumentIndexedService.class + " isn't initialized. ");
        }
        return documentIndexedService;
    }

    @Autowired(required=true)
    public void setDocumentIndexedService(DmDocumentIndexedService documentIndexedService) {
        DmDocumentIndexedServiceUtil.documentIndexedService = documentIndexedService;
    }

    public static boolean save(DmDocumentIndexed docIndexed) {
        return getDocumentIndexedService().save(docIndexed);
    }

    public static List<DmDocument> getNotIndexedDocList(int numDocs) {
        return getDocumentIndexedService().getNotIndexedDocList(numDocs);
    }

    public static boolean deleteByDocumentId(String documentId) {
        return getDocumentIndexedService().deleteByDocumentId(documentId);
    }

    public static boolean setReindexByFolder(String folderId) {
        return getDocumentIndexedService().setReindexByFolder(folderId);
    }
}
