/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmDocAccessHistory;
import com.ebdesk.dm.engine.domain.DmDocRenderImage;
import com.ebdesk.dm.engine.domain.DmDocViewAnnotation;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentComment;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import com.ebdesk.dm.engine.dto.DocumentCompare;
import com.ebdesk.dm.engine.dto.DocumentDownload;
import com.ebdesk.dm.engine.dto.DocumentVersionDownload;
import com.ebdesk.dm.engine.dto.DocumentView;
import java.util.List;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author designer
 */
public interface DmDocumentService {

    /****
     * Find DmDocument by folder id.
     * @param folderId folder identifier
     * @param start start row
     * @param max maximum row to fetch
     * @param orderBy order field by {"title", "author", "date"}. Default is "date"
     * @param order order of sorted field {"asc" , "desc"}. Default is "asc"
     * @return list of <code>com.ebdesk.dm.engine.domain.DmDocument</code>
     * @exception IllegalArgumentException if folderId is null or empty string.
     * @exception FolderNotFoundException if folder doesn't exist.
     */
    public List<DmDocument> findDocumentByFolderId(String folderId, int start, int max,
            String orderBy, String order);

    /***
     * Number of documents in a folder
     * @param folderId the folder identifier.
     * @return number of documents.
     */
    public Integer countDocumentByFolderId(String folderId);

    /***
     * Find DmDocument by document identifier.
     * @param documentId the document id.
     * @return the document.
     */
    public DmDocument findDocumentByDocId(String documentId);


    /***
     * Find <code>DmDocument</code> with documentId and check whether document deleted or not.
     * @param documentId document identifier
     * @param userId user account identifier
     * @return document
     * @exception UserAccountNotFoundException if the user account doesn't exist.
     * @exception DocumentNotFoundException if the document doesn't exist or deleted.
     */
    public DmDocument viewDocumentInformation(String documentId, String userId);


    /****
     * Find DmDocumentVersion by document identifier
     * @param docId the document identifier.
     * @param start start row
     * @param max maximum number of row to fetch
     * @param orderBy order field by {"size", "version", "date"}. Default is "date"
     * @param order order of sorted field {"asc" , "desc"}. Default is "asc"
     * @return list of DmDocumentVersion to find.
     */
    public List<DmDocumentVersion> findDocVersionByDocId(String documentId, int start, int max,
            String orderBy, String order);


    /****
     * Find DmDocAccessHistory by document identifier
     * @param docId the document identifier.
     * @param start start row
     * @param max maximum number of row to fetch
     * @param orderBy order field by {"accountName", "date", "activity"}. Default is "date"
     * @param order order of sorted field {"asc" , "desc"}. Default is "asc"
     * @return list of DmDocAccessHistory to find.
     */
    public List<DmDocAccessHistory> findDocAccessHistoryByDocId(String documentId, int start, int max,
            String orderBy, String order);

    /***
     * Number of DmDocAccessHistory of document.
     * @param documentId the document identifier.
     * @return number of DmDocAccessHistory.
     */
    public Integer countDocAccessHistoryByDocId(String documentId);

    public DmDocumentComment findLastComment(String accountId, String folderId, String documentId);
    
    /****
     * Find document comment 
     * @param accountId account identifier
     * @param folderId folder identifier
     * @param documentId document identifier
     * @param start start index of comments
     * @param max max row to fetch
     * @return List of comments
     */
    public List<DmDocumentComment> findComment(String accountId, String folderId, String documentId, 
            int start, int max);
    
    /****
     * Find document comment 
     * @param accountId account identifier
     * @param folderId folder identifier
     * @param documentId document identifier
     * @param start start index of comments
     * @param max max row to fetch
     * @return List of comments
     */
    public String addComment(String accountId, String folderId, String documentId, 
            String comment);
    
    public void deleteComment(String accountId, String folderId, String documentId, 
            String commentId);
    
    /***
     * Delete the document.
     * @param accountId account identifier that has responsibility about deletion.
     * @param folderId the folder location of document.
     * @param documentId document identifier to delete.
     * @exception InsufficientPriviledgeException when userId doesn't exist or doesn't has priviledge to delete.
     */
    public void deleteDocument(String accountId, String folderId, String documentId);
    
    public void lockDocument(String accountId, String folderId, String documentId);
    
    public void unlockDocument(String accountId, String folderId, String documentId);
    
    public void moveDocument(String accountId, String folderIdSource, String [] documentsId, String folderIdDest);
    
    public void deleteVersion(String accountId, String folderId, String versionId);
       
    /***
     * Create a new Document.
     * @param title
     * @param description
     * @param author
     * @param keyword
     * @param documentFile 
     */
    public String createNewDocument(String title, String description,
            String author, String keyword, FileItem fileItem, String folderId, String accountId);
    
    
    public String createNewVersion(String title, String description,
            String author, String keyword, int versionMajor, int versionMinor,
            FileItem fileItem, String documentId, String folderId, String accountId);
    
    public void updateDocument(String title, String description,
            String author, String keyword, String documentId, String folderId, String accountId);
    
    
    public int countAllNotRenderedVersion();
    
    public List<DmDocumentVersion> findNotRenderedVersion(int start, int max);
    
    public DmDocRenderImage findDocRenderImageById(String renderImageId);
    
    public DocumentDownload downloadDocument(String documentId, String folderId, String accountId);
    
    public DocumentDownload viewDocument(String documentId, String folderId, String accountId);

    public DocumentVersionDownload downloadDocumentVersion(String documentId, String versionId, String folderId, String accountId);

    public DocumentVersionDownload viewDocumentVersion(String documentId, String versionId, String folderId, String accountId);
 
    public DocumentView viewDocumentPage(String documentId, String folderId, String accountId);
    
    public DocumentCompare compareDocumentVersion(String firstVersionId, String secondVersionId);
    
    public String extractVersionContent(DmDocumentVersion version);
    
    public DmDocViewAnnotation findAnnotation(String accountId, String folderId, String docVersionId, int page);
    
    public DmDocViewAnnotation updateAnnotation(String accountId, String folderId, String docVersionId, int page, String annotation);
    
    public boolean deleteAnnotation(String accountId, String folderId, String docVersionId, int page);
    
    public boolean isDocumentLocked(String docId);    
    
    public boolean isOwner(String accountId, String folderId);

    public boolean isWriter(String accountId, String folderId);

    public boolean isConsumer(String accountId, String folderId);

    public boolean isReader(String accountId, String folderId);
    
    public boolean isCanCreate(String accountId, String folderId);
    
    public boolean isCanDelete(String accountId, String folderId);
    
    public boolean isCanLock(String accountId, String folderId);
    
    public boolean isCanDownload(String accountId, String folderId);

    public boolean moveDocument(String documentId, String folderIdDest);
}
