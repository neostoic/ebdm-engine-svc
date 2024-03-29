/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmDocAccessHistory;
import com.ebdesk.dm.engine.domain.DmDocRenderImage;
import com.ebdesk.dm.engine.domain.DmDocTermFreqStored;
import com.ebdesk.dm.engine.domain.DmDocTermFrequency;
import com.ebdesk.dm.engine.domain.DmDocViewAnnotation;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentComment;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import com.ebdesk.dm.engine.dto.DocumentCompare;
import com.ebdesk.dm.engine.dto.DocumentDownloadWithAccessLog;
import com.ebdesk.dm.engine.dto.DocumentVersionDownloadWithAccessLog;
import com.ebdesk.dm.engine.dto.DocumentViewWithAccessLog;
import com.ebdesk.dm.engine.dto.DocumentWithAccessLog;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author designer
 */
@Component
public class DmDocumentServiceUtil {

    private static DmDocumentService documentService;

    public static DmDocumentService getDocumentService() {
        if (documentService == null) {
            throw new RuntimeException(DmDocumentService.class + " isn't initialized. ");
        }
        return documentService;
    }

    @Autowired(required = true)
    public void setDocumentService(DmDocumentService documentService) {
        DmDocumentServiceUtil.documentService = documentService;
    }

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
    public static List<DmDocument> findDocumentByFolderId(String folderId, int start, int max,
            String orderBy, String order) {
        return getDocumentService().findDocumentByFolderId(folderId, start, max, orderBy, order);
    }
    
    public static List<DmDocument> showDocumentListInFolder(String accountId, String folderId, int start, int max,
            String orderBy, String order){
        return getDocumentService().showDocumentListInFolder(accountId, folderId, start, max, orderBy, order);
    }

    /***
     * Number of documents in a folder
     * @param folderId the folder identifier.
     * @return number of documents.
     */
    public static Integer countDocumentByFolderId(String folderId) {
        return getDocumentService().countDocumentByFolderId(folderId);
    }
    
    public static Integer countDocumentByAccount(String accountId, String folderId){
        return getDocumentService().countDocumentByAccount(accountId, folderId);
    }

    /***
     * Find DmDocument by document identifier.
     * @param documentId the document id.
     * @return the document.
     */
    public static DmDocument findDocumentByDocId(String documentId) {
        return getDocumentService().findDocumentByDocId(documentId);
    }

    /***
     * Find <code>DmDocument</code> with documentId and check whether document deleted or not.
     * @param documentId document identifier
     * @param userId user account identifier
     * @return document
     * @exception UserAccountNotFoundException if the user account doesn't exist.
     * @exception DocumentNotFoundException if the document doesn't exist or deleted.
     */
    public static DocumentWithAccessLog viewDocumentInformation(String documentId, String userId) {
        return getDocumentService().viewDocumentInformation(documentId, userId);
    }
    
    public static DocumentViewWithAccessLog viewDocumentPage(String documentId, String folderId, String accountId){
        return getDocumentService().viewDocumentPage(documentId, folderId, accountId);
    }

    /****
     * Find DmDocumentVersion by document identifier
     * @param docId the document identifier.
     * @param start start row
     * @param max maximum number of row to fetch
     * @param orderBy order field by {"size", "version", "date"}. Default is "date"
     * @param order order of sorted field {"asc" , "desc"}. Default is "asc"
     * @return list of DmDocumentVersion to find.
     */
    public static List<DmDocumentVersion> findDocVersionByDocId(String documentId,
            int start, int max, String orderBy, String order) {
        return getDocumentService().findDocVersionByDocId(documentId, start, max, orderBy, order);
    }

    /****
     * Find DmDocAccessHistory by document identifier
     * @param docId the document identifier.
     * @param start start row
     * @param max maximum number of row to fetch
     * @param orderBy order field by {"accountName", "date", "activity"}. Default is "date"
     * @param order order of sorted field {"asc" , "desc"}. Default is "asc"
     * @return list of DmDocAccessHistory to find.
     */
    public static List<DmDocAccessHistory> findDocAccessHistoryByDocId(String documentId, int start, int max,
            String orderBy, String order) {
        return getDocumentService().findDocAccessHistoryByDocId(documentId, start, max, orderBy, order);
    }
    
    public static DmDocumentComment findLastComment(String accountId, String folderId, String documentId){
        return getDocumentService().findLastComment(accountId, folderId, documentId);
    }
    
    public static List<DmDocumentComment> findComment(String accountId, String folderId, String documentId, 
            int start, int max){
        return getDocumentService().findComment(accountId, folderId, documentId, start, max);
    }
    
    public static String addComment(String accountId, String folderId, String documentId, 
            String comment){
        return getDocumentService().addComment(accountId, folderId, documentId, comment);
    }
    
    public static void deleteComment(String accountId, String folderId, String documentId, String commentId){
        getDocumentService().deleteComment(accountId, folderId, documentId, commentId);
    }
    /***
     * Number of DmDocAccessHistory of document.
     * @param documentId the document identifier.
     * @return number of DmDocAccessHistory.
     */
    public static Integer countDocAccessHistoryByDocId(String documentId) {
        return getDocumentService().countDocAccessHistoryByDocId(documentId);
    }

    /***
     * 
     */
    public static void deleteDocument(String accountId, String folderId, String documentId) {
        getDocumentService().deleteDocument(accountId, folderId, documentId);
    }

    public static void lockDocument(String accountId, String folderId, String documentId) {
        getDocumentService().lockDocument(accountId, folderId, documentId);
    }

    public static void unlockDocument(String accountId, String folderId, String documentId) {
        getDocumentService().unlockDocument(accountId, folderId, documentId);
    }
    
    public static void moveDocument(String accountId, String folderIdSource, String [] documentsId, String folderIdDest){
        
    }
    
    public static boolean approveDocument(String accountId, String folderId, String documentId, String comment, boolean approved){
        return getDocumentService().approveDocument(accountId, folderId, documentId, comment, approved);
    }
    
    public static boolean approveVersion(String accountId, String folderId, String documentId, 
            String versionId, String comment, boolean approved){
        return getDocumentService().approveVersion(accountId, folderId, documentId, versionId, comment, approved);
    }

    public static void deleteVersion(String accountId, String folderId, String versionId){
        getDocumentService().deleteVersion(accountId, folderId, versionId);
    }
    
    public static String createNewDocument(String title, String description,
            String author, String keyword, FileItem fileItem, String folderId, String accountId) {
        return getDocumentService().createNewDocument(title, description, author, keyword, fileItem, folderId, accountId);
    }
    
    public static String createNewVersion(String title, String description,
            String author, String keyword, String upMode,
            FileItem fileItem, String documentId, String folderId, String accountId){
        return getDocumentService().createNewVersion(title, description, author, keyword, 
                upMode, fileItem, documentId, folderId, accountId);
    }
    
    public static void updateDocument(String title, String description,
            String author, String keyword, String documentId, String folderId, String accountId){
        getDocumentService().updateDocument(title, description, author, keyword, documentId, folderId, accountId);
    }

    public static DmDocRenderImage findDocRenderImageById(String renderImageId){
        return getDocumentService().findDocRenderImageById(renderImageId);
    }
    
    public static DocumentDownloadWithAccessLog downloadDocument(String documentId, String folderId, String accountId){
        return getDocumentService().downloadDocument(documentId, folderId, accountId);
    }
    
    public static DocumentDownloadWithAccessLog viewDocument(String documentId, String folderId, String accountId){
        return getDocumentService().viewDocument(documentId, folderId, accountId);
    }

    public static DocumentVersionDownloadWithAccessLog downloadDocumentVersion(String documentId, String versionId, String folderId, String accountId) {
        return getDocumentService().downloadDocumentVersion(documentId, versionId, folderId, accountId);
    }

    public static DocumentVersionDownloadWithAccessLog viewDocumentVersion(String documentId, String versionId, String folderId, String accountId) {
        return getDocumentService().viewDocumentVersion(documentId, versionId, folderId, accountId);
    }

    public static DocumentCompare compareDocumentVersion(String firstVersionId, String secondVersionId){
        return getDocumentService().compareDocumentVersion(firstVersionId, secondVersionId);
    }
    
    public static DmDocViewAnnotation findAnnotation(String accountId, String folderId, String docVersionId, int page){
        return getDocumentService().findAnnotation(accountId, folderId, docVersionId, page);
    }
    
    public static DmDocViewAnnotation updateAnnotation(String accountId, String folderId, String docVersionId, int page, String annotation){
        return getDocumentService().updateAnnotation(accountId, folderId, docVersionId, page, annotation);
    }
    
    public static boolean deleteAnnotation(String accountId, String folderId, String docVersionId, int page){
        return getDocumentService().deleteAnnotation(accountId, folderId, docVersionId, page);
    }
    
    public static boolean isDocumentLocked(String docId) {
        return getDocumentService().isDocumentLocked(docId);
    }
    
    public static boolean isDocumentCheckedOut(String docId){
        return getDocumentService().isDocumentCheckedOut(docId);
    }

    public static boolean isOwner(String accountId, String folderId) {
        return getDocumentService().isOwner(accountId, folderId);
    }

    public static boolean isWriter(String accountId, String folderId) {
        return getDocumentService().isWriter(accountId, folderId);
    }

    public static boolean isConsumer(String accountId, String folderId) {
        return getDocumentService().isConsumer(accountId, folderId);
    }

    public static boolean isReader(String accountId, String folderId) {
        return getDocumentService().isReader(accountId, folderId);
    }
    
    public static boolean isCanCreate(String accountId, String folderId){
        return getDocumentService().isCanCreate(accountId, folderId);
    }

    public static boolean isCanDelete(String accountId, String folderId) {
        return getDocumentService().isCanDelete(accountId, folderId);
    }

    public static boolean isCanLock(String accountId, String folderId) {
        return getDocumentService().isCanLock(accountId, folderId);
    }
    
    public static boolean isCanDownload(String accountId, String folderId){
        return getDocumentService().isCanDownload(accountId, folderId);
    }

    public static boolean moveDocument(String documentId, String folderIdDest, String accountIdOperator) {
        return getDocumentService().moveDocument(documentId, folderIdDest, accountIdOperator);
    }

    public static List<String> getVersionIdList(String documentId) {
        return getDocumentService().getVersionIdList(documentId);
    }

    public static List<DmDocument> getTermFreqNotStoredDocList(int numDocs) {
        return getDocumentService().getTermFreqNotStoredDocList(numDocs);
    }

    public static void saveTermFreqStored(DmDocTermFreqStored docTermFreqStored) {
        getDocumentService().saveTermFreqStored(docTermFreqStored);
    }

    public static void deleteTermFreqStoredByDocId(String documentId) {
        getDocumentService().deleteTermFreqStoredByDocId(documentId);
    }

    public static void saveDocTermFrequency(DmDocTermFrequency docTermFrequency) {
        getDocumentService().saveDocTermFrequency(docTermFrequency);
    }

    public static void deleteDocTermFrequencyByDocId(String documentId) {
        getDocumentService().deleteDocTermFrequencyByDocId(documentId);
    }

    public static void addFolderTermFrequencyByDoc(String documentId, String folderId) {
        getDocumentService().addFolderTermFrequencyByDoc(documentId, folderId);
    }

    public static void subtractFolderTermFrequencyByDoc(String documentId, String folderId) {
        getDocumentService().subtractFolderTermFrequencyByDoc(documentId, folderId);
    }

    public static List<String> getTopTerm(String documentId, int numTerm) {
        return getDocumentService().getTopTerm(documentId, numTerm);
    }
}
