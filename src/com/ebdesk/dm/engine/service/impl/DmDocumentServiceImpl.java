/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.constant.ApplicationConstants;
import com.ebdesk.dm.engine.constant.DatabaseConstants;
import com.ebdesk.dm.engine.constant.MessageCodeConstants;
import com.ebdesk.dm.engine.dao.DmAccountDao;
import com.ebdesk.dm.engine.dao.DmAuthorDao;
import com.ebdesk.dm.engine.dao.DmContentTypeDao;
import com.ebdesk.dm.engine.dao.DmDocAccessHistoryDao;
import com.ebdesk.dm.engine.dao.DmDocRenderImageDao;
import com.ebdesk.dm.engine.dao.DmDocTermFreqStoredDao;
import com.ebdesk.dm.engine.dao.DmDocTermFrequencyDao;
import com.ebdesk.dm.engine.dao.DmDocViewAnnotationDao;
import com.ebdesk.dm.engine.dao.DmDocumentApprovalDao;
import com.ebdesk.dm.engine.dao.DmDocumentAuthorDao;
import com.ebdesk.dm.engine.dao.DmDocumentCheckoutDao;
import com.ebdesk.dm.engine.dao.DmDocumentCommentDao;
import com.ebdesk.dm.engine.dao.DmDocumentDao;
import com.ebdesk.dm.engine.dao.DmDocumentFolderDao;
import com.ebdesk.dm.engine.dao.DmDocumentIndexedDao;
import com.ebdesk.dm.engine.dao.DmDocumentKeywordDao;
import com.ebdesk.dm.engine.dao.DmDocumentLockDao;
import com.ebdesk.dm.engine.dao.DmDocumentVersionApprovalDao;
import com.ebdesk.dm.engine.dao.DmDocumentVersionDao;
import com.ebdesk.dm.engine.dao.DmFolderDao;
import com.ebdesk.dm.engine.dao.DmFolderPermissionDao;
import com.ebdesk.dm.engine.dao.DmFolderTermFrequencyDao;
import com.ebdesk.dm.engine.domain.DmAccount;
import com.ebdesk.dm.engine.domain.DmAuthor;
import com.ebdesk.dm.engine.domain.DmContentType;
import com.ebdesk.dm.engine.domain.DmDocAccessHistory;
import com.ebdesk.dm.engine.domain.DmDocRenderImage;
import com.ebdesk.dm.engine.domain.DmDocTermFreqStored;
import com.ebdesk.dm.engine.domain.DmDocTermFrequency;
import com.ebdesk.dm.engine.domain.DmDocViewAnnotation;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentApproval;
import com.ebdesk.dm.engine.domain.DmDocumentAuthor;
import com.ebdesk.dm.engine.domain.DmDocumentCheckout;
import com.ebdesk.dm.engine.domain.DmDocumentComment;
import com.ebdesk.dm.engine.domain.DmDocumentFolder;
import com.ebdesk.dm.engine.domain.DmDocumentKeyword;
import com.ebdesk.dm.engine.domain.DmDocumentLock;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import com.ebdesk.dm.engine.domain.DmDocumentVersionApproval;
import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.util.ApprovalStatus;
import com.ebdesk.dm.engine.domain.util.DocumentAccessHistory;
import com.ebdesk.dm.engine.dto.DocumentDownload;
import com.ebdesk.dm.engine.util.FileUtils;
import com.ebdesk.dm.engine.domain.util.FolderPermission;
import com.ebdesk.dm.engine.domain.util.PDFContent;
import com.ebdesk.dm.engine.dto.DocumentCompare;
import com.ebdesk.dm.engine.dto.DocumentDownloadWithAccessLog;
import com.ebdesk.dm.engine.dto.DocumentVersionDownload;
import com.ebdesk.dm.engine.dto.DocumentVersionDownloadWithAccessLog;
import com.ebdesk.dm.engine.dto.DocumentView;
import com.ebdesk.dm.engine.dto.DocumentViewWithAccessLog;
import com.ebdesk.dm.engine.dto.DocumentWithAccessLog;
import com.ebdesk.dm.engine.exception.DocumentNotFoundException;
import com.ebdesk.dm.engine.exception.ExceedQuotaException;
import com.ebdesk.dm.engine.exception.FolderNotFoundException;
import com.ebdesk.dm.engine.exception.InsufficientPriviledgeException;
import com.ebdesk.dm.engine.exception.UserAccountNotFoundException;
import com.ebdesk.dm.engine.service.DmDocumentService;
import com.ebdesk.dm.engine.util.compare.LineDifference;
import com.ebdesk.dm.engine.util.compare.TextCompare;
import com.ebdesk.dm.engine.util.config.ConfigVar;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author designer
 */
@Service("dmDocumentService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmDocumentServiceImpl implements DmDocumentService {

    /* Instantiate a logger named MyLogger */
    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private DmDocumentFolderDao documentFolderDao;
    @Autowired
    private DmDocumentDao documentDao;
    @Autowired
    private DmDocumentVersionDao documentVersionDao;
    @Autowired
    private DmDocumentKeywordDao documentKeywordDao;
    @Autowired
    private DmFolderDao folderDao;
    @Autowired
    private DmFolderPermissionDao folderPermissionDao;
    @Autowired
    private DmDocAccessHistoryDao docAccessHistoryDao;
    @Autowired
    private DmAccountDao accountDao;
    @Autowired
    private DmAuthorDao authorDao;
    @Autowired
    private DmDocumentLockDao documentLockDao;
    @Autowired
    private DmDocumentCheckoutDao documentCheckoutDao;
    @Autowired
    private DmDocumentAuthorDao documentAuthorDao;
    @Autowired
    private DmContentTypeDao contentTypeDao;
    @Autowired
    private DmDocRenderImageDao docRenderImageDao;
    @Autowired
    private DmDocViewAnnotationDao docViewAnnotationDao;
    @Autowired
    private DmDocumentCommentDao documentCommentDao;
    @Autowired
    private DmDocumentIndexedDao documentIndexedDao;
    @Autowired
    private DmDocumentVersionApprovalDao documentVersionApprovalDao;
    @Autowired
    private DmDocumentApprovalDao documentApprovalDao;
    @Autowired
    private DmDocTermFreqStoredDao docTermFreqStoredDao;
    @Autowired
    private DmDocTermFrequencyDao docTermFrequencyDao;
    @Autowired
    private DmFolderTermFrequencyDao folderTermFrequencyDao;

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
            String orderBy, String order) {
        if (folderId == null || "".equals(folderId)) {
            throw new IllegalArgumentException("Parameter folder couldn't be null or empty string.");
        }

        if (!folderDao.isExist(folderId)) {
            throw new FolderNotFoundException("Folder with id " + folderId + " doesn't exist.");
        }


        if (start < 0 || max < 1) {
            start = DatabaseConstants.DEFAULT_START;
            max = DatabaseConstants.DEFAULT_MAX;
        }

        if (!"title".equals(orderBy) && !"author".equals(orderBy) && !"date".equals(orderBy)) {
            orderBy = "createdTime";
        }

        if ("date".equals(orderBy)) {
            orderBy = "createdTime";
        }

        if (!DatabaseConstants.ORDER_DESC.equals(order)) {
            order = "asc";
        }

        return documentFolderDao.findDocumentByFolderId(folderId, start, max, orderBy, order);
    }
    
    
    public List<DmDocument> showDocumentListInFolder(String accountId, String folderId, int start, int max,
            String orderBy, String order) {
        if (folderId == null || "".equals(folderId)) {
            throw new IllegalArgumentException("Parameter folder couldn't be null or empty string.");
        }else if(accountId == null || "".equals(accountId)){
            throw new IllegalArgumentException("Parameter account couldn't be null or empty string.");
        }

        DmFolder folder = folderDao.findById(folderId);
        
        if (folder == null) {
            throw new FolderNotFoundException("Folder with id " + folderId + " doesn't exist.");
        }
        
        if (!isReader(accountId, folderId)) {
            throw new InsufficientPriviledgeException("No permission to see list of document.");
        }

        if (start < 0 || max < 1) {
            start = DatabaseConstants.DEFAULT_START;
            max = DatabaseConstants.DEFAULT_MAX;
        }
        
        if (order == null && orderBy == null) {
            orderBy = "createdTime";
            order = DatabaseConstants.ORDER_DESC;
        }else {
            if (!"title".equals(orderBy) && !"author".equals(orderBy) && !"date".equals(orderBy)) {
                orderBy = "createdTime";
            }

            if ("date".equals(orderBy)) {
                orderBy = "createdTime";
            }

            if (!DatabaseConstants.ORDER_DESC.equals(order)) {
                order = "asc";
            }
        }
        
        
        
        /*if (folder.getIsNeedApproval() != null && folder.getIsNeedApproval() == true) {
            log.debug("Folder "+folder.getName() +" needs an approval...");
            return documentFolderDao.findDocumentByFolderId(folderId, start, max, orderBy, order);
        }*/
        
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);

        return documentFolderDao.findDocumentByAccount(accountId, folderId, permission, start, max, orderBy, order);
    }

    /***
     * Number of documents in a folder
     * @param folderId the folder identifier.
     * @return number of documents.
     */
    public Integer countDocumentByFolderId(String folderId) {
        if (folderId == null || "".equals(folderId)) {
            return new Integer(0);
        }
        return documentFolderDao.countDocumentByFolderId(folderId);
    }
    
    public Integer countDocumentByAccount(String accountId, String folderId){
        if (folderId == null || "".equals(folderId)) {
            return new Integer(0);
        }
        
        if (accountId == null || "".equals(accountId)) {
            return new Integer(0);
        }
        
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        
        return documentFolderDao.countDocumentByAccount(accountId, folderId, permission);
    }

    /***
     * Find DmDocument by document identifier.
     * @param documentId the document id.
     * @return the document.
     */
    public DmDocument findDocumentByDocId(String documentId) {
        DmDocument document = documentDao.findDocumentByDocumentId(documentId);
        if (document != null) {
            document.setDocumentKeywordList(documentKeywordDao.findKeywordByDocumentId(documentId, 0, Integer.MAX_VALUE));
        }
        return document;
    }

    
    public DocumentWithAccessLog viewDocumentInformation(String documentId, String accountId) {
        DocumentWithAccessLog documentWithAccessLog = null;
        DmDocument document = this.findDocumentByDocId(documentId);
        if (document != null) {
            DmAccount account = accountDao.get(accountId);
            if (account == null) {
                throw new UserAccountNotFoundException("Account doesn't exist.");
            }
            
            if (document.getApproved() !=null && !document.getApproved()) {
                document.setApproval(documentApprovalDao.findByDocId(documentId));
            }

            String accessLogId = UUID.randomUUID().toString();

            DmDocAccessHistory accessHistory = new DmDocAccessHistory();
            accessHistory.setId(accessLogId);
            accessHistory.setAccessedBy(account);
            accessHistory.setAccessedTime(new Date());
            accessHistory.setDocVersion(document.getLastVersion());
            accessHistory.setAccessType(DocumentAccessHistory.META_DATA_VIEW.getAccessType());
            docAccessHistoryDao.save(accessHistory);

            documentWithAccessLog = new DocumentWithAccessLog();
            documentWithAccessLog.setDocument(document);
            documentWithAccessLog.setAccessLog(accessHistory);
        } else {
            throw new DocumentNotFoundException("Document doesn't exist.");
        }
        return documentWithAccessLog;
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
    public List<DmDocumentVersion> findDocVersionByDocId(String documentId,
            int start, int max, String orderBy, String order) {
        if (documentId == null || "".equals(documentId)) {
            return new ArrayList<DmDocumentVersion>();
        }

        if (start < 0 || max < 1) {
            start = DatabaseConstants.DEFAULT_START;
            max = DatabaseConstants.DEFAULT_MAX;
        }

        if (!"size".equals(orderBy) && !"version".equals(orderBy) && !"date".equals(orderBy)) {
            orderBy = "modifiedTime";
        }

        if ("date".equals(orderBy)) {
            orderBy = "modifiedTime";
        }

        if (!DatabaseConstants.ORDER_DESC.equals(order)) {
            order = "asc";
        }
        
        return documentVersionDao.findDocumentVersionByDocId(documentId, start, max, orderBy, order);
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
    public List<DmDocAccessHistory> findDocAccessHistoryByDocId(String documentId, int start, int max,
            String orderBy, String order) {
        if (documentId == null || "".equals(documentId)) {
            return new ArrayList<DmDocAccessHistory>();
        }

        if (start < 0 || max < 1) {
            start = DatabaseConstants.DEFAULT_START;
            max = DatabaseConstants.DEFAULT_MAX;
        }

        if ((orderBy == null || "".equals(orderBy)) && (order == null || "".equals(order))) {
            orderBy = "accessedTime";
            order = DatabaseConstants.ORDER_DESC;
        }

        if ("accountName".equals(orderBy)) {
            orderBy = "accessedBy.name";
        } else if ("activity".equals(orderBy)) {
            orderBy = "accessType";
        } else {
            orderBy = "accessedTime";
        }

        if (!DatabaseConstants.ORDER_DESC.equals(order)) {
            order = DatabaseConstants.ORDER_ASC;
        }

        return docAccessHistoryDao.findByDocumentId(documentId, start, max, orderBy, order);
    }

    /***
     * Number of DmDocAccessHistory of document.
     * @param documentId the document identifier.
     * @return number of DmDocAccessHistory.
     */
    public Integer countDocAccessHistoryByDocId(String documentId) {
        return docAccessHistoryDao.countDocumentId(documentId);
    }

    public DmDocumentComment findLastComment(String accountId, String folderId, String documentId) {
        if (!isReader(accountId, folderId)) {
            throw new InsufficientPriviledgeException("Account doesn't have permission to delete document.");
        }
        return documentCommentDao.findLastCommentOnDocument(documentId);
    }

    public List<DmDocumentComment> findComment(String accountId, String folderId, String documentId,
            int start, int max) {
        if (documentId == null || "".equals(documentId)) {
            return new ArrayList<DmDocumentComment>();
        }

        if (start < 0 || max < 1) {
            start = DatabaseConstants.DEFAULT_START;
            max = DatabaseConstants.DEFAULT_MAX;
        }

        if (!isReader(accountId, folderId)) {
            throw new InsufficientPriviledgeException("Account doesn't have permission to delete document.");
        }

        return documentCommentDao.findByDocumentId(documentId, start, max);
    }

    public String addComment(String accountId, String folderId, String documentId,
            String comment) {
        if (comment == null || "".equals(comment)) {
            throw new IllegalArgumentException("Please fill the annotation.");
        } else if (comment.length() > 250) {
            throw new IllegalArgumentException("Max character is 250 for annotation.");
        }

        if (!isReader(accountId, folderId)) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE_COMMENT + " : "
                    + "Account cannot write document comment in folder.");
        }
        DmDocumentComment docComment = new DmDocumentComment();
        docComment.setComment(comment);
        docComment.setCommentedBy(new DmAccount(accountId));
        docComment.setCreatedTime(new Date());
        docComment.setDocument(new DmDocument(documentId));
        docComment.setId(UUID.randomUUID().toString());

        String commentId = (String) documentCommentDao.save(docComment);

        return commentId;
    }

    public void deleteComment(String accountId, String folderId, String documentId, String commentId) {
        boolean isWriter = isWriter(accountId, folderId);

        if (commentId == null) {
            throw new IllegalArgumentException("Commment is required.");
        }

        DmDocumentComment comment = documentCommentDao.findById(commentId);

        if (comment == null) {
            throw new IllegalArgumentException("Commment doesn't exist.");
        }

        boolean canDelete = false;
        if (isWriter) {
            canDelete = true;
        } else if (accountId.equals(comment.getCommentedBy().getId())) {
            canDelete = true;
        }

        if (canDelete) {
            documentCommentDao.delete(comment);
        }
    }

    public void deleteDocument(String accountId, String folderId, String documentId) {
        DmDocument document = documentDao.findById(documentId);
        if (document == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND+" : "+"No document found.");
        }
        
        DmFolder folder = folderDao.findById(folderId);
        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER+" : "+"No folder found.");
        }
        
        
        document.setApproval(documentApprovalDao.findByDocId(documentId));
        
        if (isCanDelete(accountId, folderId) || 
                (!document.getApproved() 
                && (document.getApproval().getStatus() == 1 || document.getApproval().getStatus() == 3) && 
                document.getCreatedBy().getId().equals(accountId))) {
            document.setIsRemoved(Boolean.TRUE);
            Date now = new Date();
            document.setLastModifiedTime(now);
            documentDao.update(document);
            int a = documentFolderDao.deleteByDocIdAndFolderId(folderId, documentId);
        } else {
            throw new InsufficientPriviledgeException("Account doesn't have permission to delete document.");
        }

    }

    public void lockDocument(String accountId, String folderId, String documentId) {
        if (isCanLock(accountId, folderId)) {
            if (isDocumentLocked(documentId)) {
                return;
            }
            DmDocumentLock dl = new DmDocumentLock(UUID.randomUUID().toString());
            dl.setDocument(new DmDocument(documentId));
            dl.setAccount(new DmAccount(accountId));
            dl.setTimeLock(new Date());
            documentLockDao.save(dl);
        } else {
            throw new InsufficientPriviledgeException("Account doesn't have permission to lock document.");
        }
    }

    public void unlockDocument(String accountId, String folderId, String documentId) {
        if (isCanLock(accountId, folderId)) {
            documentLockDao.deleteByDocId(documentId);
        } else {
            throw new InsufficientPriviledgeException("Account doesn't have permission to lock document.");
        }
    }

    public void moveDocument(String accountId, String folderIdSource, String [] documentsId, String folderIdDest){
        //
    }
    
    public boolean approveDocument(String accountId, String folderId, String documentId, String comment, boolean approved){
        if (isOwner(accountId, folderId)) {
            DmDocument document = documentDao.findById(documentId);
            if (document == null) {
                throw new IllegalArgumentException("No Document Found");
            }
            
            DmDocumentApproval approval = documentApprovalDao.findByDocId(documentId);
            if (approval == null) {
                throw new IllegalArgumentException("No Document Approval Found");
            }
            
            approval.setApprovedBy(accountDao.get(accountId));
            approval.setComment(comment);
            if (approved){
                approval.setStatus(ApprovalStatus.APPROVED.getStatus());
                document.setApproved(Boolean.TRUE);
//                document.setLastModifiedTime(new Date());
                documentDao.update(document);

                // reindex; because approved document should be (solr) indexed,
                // while not (yet) approved document is also recorded (in dm_document_indexed) as indexed although not (solr) indexed
                documentIndexedDao.setReindexByDocument(documentId);
            }else{ 
                approval.setStatus(ApprovalStatus.REJECT.getStatus());
            }
            documentApprovalDao.update(approval);
            return true;
        } else {
            throw new InsufficientPriviledgeException("Account doesn't have permission to lock document.");
        }        
    }
    
    public boolean approveVersion(String accountId, String folderId, String documentId, 
            String versionId, String comment, boolean approved){
        if (isOwner(accountId, folderId)) {
            DmDocument document = documentDao.findById(documentId);
            if (document == null) {
                throw new IllegalArgumentException("No Document Found");
            }
            
            DmDocumentVersion version = documentVersionDao.findById(versionId);
            if (version == null) {
                throw new IllegalArgumentException("No Document Version Found");
            }
                        
            DmDocumentVersionApproval approval = documentVersionApprovalDao.findByDocVersionId(versionId);
            if (approval == null) {
                throw new IllegalArgumentException("No Document Version Approval Found");
            }
            
            approval.setApprovedBy(accountDao.get(accountId));
            approval.setComment(comment);
            if (approved){
                approval.setStatus(ApprovalStatus.APPROVED.getStatus());
                version.setApproved(Boolean.TRUE);
                documentVersionDao.update(version);
                
                document.setLastVersion(version);
                document.setLastModifiedTime(new Date());
                documentDao.update(document);
            }else{ 
                approval.setStatus(ApprovalStatus.REJECT.getStatus());
            }
            documentVersionApprovalDao.update(approval);
            
            documentCheckoutDao.deleteByDocId(documentId);
            
            return true;
        } else {
            throw new InsufficientPriviledgeException("Account doesn't have permission to lock document.");
        }        
    }
    
    public void deleteVersion(String accountId, String folderId, String versionId) {
        if (isCanCreate(accountId, folderId)) {
            DmDocumentVersion documentVersion = documentVersionDao.findById(versionId);
            if (documentVersion == null) {
                throw new IllegalArgumentException("No Document Version");
            }
            System.out.println("REQUEST DELETED VERSION : " + documentVersion.getId());

            String documentId = documentVersion.getDocument().getId();
            int countVersion = documentVersionDao.countByDocumentId(documentId);
            if (countVersion == 1) {
                //Delete the document.
                DmDocument document = documentDao.findById(documentId);
                document.setIsRemoved(Boolean.TRUE);
                Date now = new Date();
                document.setLastModifiedTime(now);
                documentDao.update(document);
                documentFolderDao.deleteByDocIdAndFolderId(folderId, documentId);
            } else {                
                // Check whether document version is last version or not 
                if (documentVersion.getId().equals(documentVersion.getDocument().getLastVersion().getId())) {
                    List<DmDocumentVersion> versions = documentVersionDao.findDocumentVersionByDocId(documentId, 0, 2, "version", "desc");
                    documentDao.updateLastVersion(versions.get(versions.size() - 1).getId(), documentId);
                }
                //Should delete doc_view_annotation and doc_render_image 
                docViewAnnotationDao.deleteByVersionId(versionId);
                docRenderImageDao.deleteByVersionId(versionId);
                docAccessHistoryDao.deleteByVersionId(versionId);
                documentVersionDao.deleteDocVersionByVersionId(versionId);
            }

        } else {
            throw new InsufficientPriviledgeException("Account doesn't have permission delete version.");
        }
    }

    /***
     * Create a new Document.
     * @param title
     * @param description
     * @param author
     * @param keyword
     * @param documentFile 
     */
    public String createNewDocument(String title, String description,
            String author, String keyword, FileItem fileItem, String folderId, String accountId) {
        if (title == null || "".equals(title)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_TITLE_REQUIRED + " : " + "Title is required.");
        } else if (title.length() >= 100) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_TITLE_MAX_LENGTH + " : " + "Title too long.");
        } else if (description != null && description.length() > 250) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_DESCRIPTION_MAX_LENGTH + " : " + "Description too long.");
        }

        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        boolean isCanWrite = isCanCreate(accountId, folderId);
        if (!isCanWrite) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE + " : "
                    + "Account " + account.getName() + " cannot create a document in folder " + folder.getName());
        }
        
        boolean isOwner = isOwner(accountId, folderId);

        String documentId = UUID.randomUUID().toString();
        String versionId = UUID.randomUUID().toString();
        String fileName = versionId + "_" + fileItem.getName();
        String folderLocation = ConfigVar.baseDocLocation(folderId, documentId, versionId);
        //String folderLocation = baseDocFolder + FileUtils.FILE_SEPARATOR + folder.getId() + FileUtils.FILE_SEPARATOR + documentId;
        log.debug("FILE LOCATION : " + folderLocation + FileUtils.FILE_SEPARATOR + fileName);
        log.debug("FILE CONTENT TYPE : " + fileItem.getContentType());
        FileUtils.createFolderPath(folderLocation);
        File file = new File(folderLocation, fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileItem.get());
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }

        // start - check owner's space quota
        DmAccount accountOwner = accountDao.get(folder.getOwner().getId());
        long spaceQuota = accountOwner.getQuota().intValue() * 1048576;
        long spaceUsed = accountDao.checkSpaceUsed(folder.getOwner().getId());
        if (spaceQuota - spaceUsed < file.length()) {
            // TODO: should delete file & folder already created on filesystem
            throw new ExceedQuotaException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_INSUFFICIENT_QUOTA + " : " + "Insufficient account quota.");
        }
        // end - check owner's space quota

        String fileExtension = FileUtils.getFileExtension(file.getAbsolutePath());
        DmContentType contentType = contentTypeDao.findByFileExtension(fileExtension);

        Date now = new Date();
        DmDocument document = new DmDocument(documentId);
        document.setTitle(title);
        document.setDescription(description);
        document.setCreatedTime(now);
        document.setCreatedBy(new DmAccount(accountId));
        document.setMimeType(contentTypeDao.getFileMimeType(file.getAbsolutePath()));
        document.setModifiedBy(account);
        document.setLastModifiedTime(now);
        document.setIsRemoved(Boolean.FALSE);
        document.setContentType(contentType);
        document.setApproved((folder.getIsNeedApproval().booleanValue() && isOwner) || (!folder.getIsNeedApproval().booleanValue()));
        documentDao.save(document);
        

        DmDocumentVersion documentVersion = new DmDocumentVersion();
        documentVersion.setId(versionId);
        documentVersion.setMajorVersion(1);
        documentVersion.setMinorVersion(0);
        documentVersion.setDocument(document);
        documentVersion.setFileName(fileItem.getName());
        documentVersion.setFilePath(file.getAbsolutePath());

        PDFContent pdfContent = null;

        if (!FileUtils.PDF_EXTENSION.equals(fileExtension)) {
            String pdfFile = FileUtils.generatePdfName(file.getAbsolutePath());
            boolean isConvert = FileUtils.convertToPdf(file.getAbsolutePath(), pdfFile);
            pdfContent = FileUtils.extractPdf(pdfFile);
            String contentText = pdfContent.getTextContent();
            if (isConvert && contentText != null) {
                documentVersion.setPdfPath(pdfFile);
                //documentVersion.setTextContent(contentText);
                documentVersion.setNumberOfPages(pdfContent.getNumberOfPages());
            }
        } else {
            pdfContent = FileUtils.extractPdf(file.getAbsolutePath());
            String contentText = pdfContent.getTextContent();
            documentVersion.setPdfPath(file.getAbsolutePath());
            if (contentText != null) {
                //documentVersion.setTextContent(contentText);
                documentVersion.setNumberOfPages(pdfContent.getNumberOfPages());
            }
        }

        if (documentVersion.getTextPath() == null && pdfContent != null) {
            String textFile = FileUtils.generateTextName(file.getAbsolutePath());
            if (FileUtils.writeFile(textFile, pdfContent.getTextContent())) {
                documentVersion.setTextPath(textFile);
            }
        }

//        Long fileSize = file.length() / 1024;
        Long fileSize = file.length();
        documentVersion.setSize(fileSize);
        documentVersion.setModifiedTime(now);
        documentVersion.setModifiedBy(account);
        documentVersion.setApproved(Boolean.TRUE);
        documentVersionDao.save(documentVersion);

        if (folder.getIsNeedApproval() && !isOwner) {
            DmDocumentApproval approval = new DmDocumentApproval(UUID.randomUUID().toString());
            approval.setStatus(ApprovalStatus.REQUESTED.getStatus());
            approval.setDocument(document);
            documentApprovalDao.save(approval);
        }
        
        document.setLastVersion(documentVersion);
        documentDao.update(document);


        DmDocumentFolder documentFolder = new DmDocumentFolder();
        documentFolder.setId(UUID.randomUUID().toString());
        documentFolder.setDocument(document);
        documentFolder.setFolder(folder);
        documentFolderDao.save(documentFolder);


        if (keyword != null && !"".equals(keyword)) {
            String[] arrayKeyword = keyword.split(",");
            log.debug("ARRAY KEYWORD : " + Arrays.asList(arrayKeyword));
            int idx = 0;
            for (String string : arrayKeyword) {
                if (string != null && !"".equals(string.trim())) {
                    if (string.length() > 50) {
                        throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_KEYWORD_MAX_LENGTH + " : " + "Keyword " + string + " is too long.");
                    }
                    idx++;
                    DmDocumentKeyword documentKeyword = new DmDocumentKeyword();
                    documentKeyword.setId(UUID.randomUUID().toString());
                    documentKeyword.setRank(new Integer(idx));
                    documentKeyword.setTerm(string.trim());
                    documentKeyword.setDocument(document);
                    documentKeywordDao.save(documentKeyword);
                }
            }
        }

        if (author != null && !"".equals(author)) {
            String[] arrayAuthor = author.split(",");
            log.debug("ARRAY AUTHOR : " + Arrays.asList(arrayAuthor));
            for (String string : arrayAuthor) {
                if (string != null && !"".equals(string.trim())) {
                    if (string.length() > 75) {
                        throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_AUTHOR_MAX_LENGTH + " : " + "Author " + string + " is too long.");
                    }
                    DmAuthor dmAuthor = authorDao.findByName(string.trim());
                    if (dmAuthor == null) {
                        dmAuthor = new DmAuthor();
                        dmAuthor.setId(UUID.randomUUID().toString());
                        dmAuthor.setName(string.trim());
                        authorDao.save(dmAuthor);
                    }
                    DmDocumentAuthor documentAuthor = new DmDocumentAuthor();
                    documentAuthor.setId(UUID.randomUUID().toString());
                    documentAuthor.setDocument(document);
                    documentAuthor.setAuthor(dmAuthor);
                    documentAuthorDao.save(documentAuthor);
                }
            }
        }

        return document.getId();
    }

    public String createNewVersion(String title, String description,
            String author, String keyword, String upMode,
            FileItem fileItem, String documentId, String folderId, String accountId) {
        if (title == null || "".equals(title)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_TITLE_REQUIRED + " : " + "Title is required.");
        } else if (title.length() >= 100) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_TITLE_MAX_LENGTH + " : " + "Title too long.");
        } else if (description != null && description.length() > 250) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_DESCRIPTION_MAX_LENGTH + " : " + "Description too long.");
        } else if (accountId == null || "".equals(accountId)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "No account id.");
        } else if (folderId == null || "".equals(folderId)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "No folder id.");
        } else if (documentId == null || "".equals(documentId)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "No document id.");
        }

        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);
        DmDocument document = documentDao.findById(documentId);


        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        if (document == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "Document not found.");
        }


        boolean isCanWrite = isCanCreate(accountId, folderId);
        if (!isCanWrite) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE + " : "
                    + "Account " + account.getName() + " cannot create a new document version in folder " + folder.getName());
        }

        boolean isOwner = isOwner(accountId, folderId);

        String versionId = UUID.randomUUID().toString();
        String fileName = versionId + "_" + fileItem.getName();
        String folderLocation = ConfigVar.baseDocLocation(folderId, documentId, versionId);
        //String folderLocation = baseDocFolder + FileUtils.FILE_SEPARATOR + folder.getId() + FileUtils.FILE_SEPARATOR + documentId;
        log.debug("FILE LOCATION : " + folderLocation + FileUtils.FILE_SEPARATOR + fileName);
        log.debug("FILE CONTENT TYPE : " + fileItem.getContentType());
        FileUtils.createFolderPath(folderLocation);
        File file = new File(folderLocation, fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileItem.get());
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
        }

        // start - check owner's space quota
        DmAccount accountOwner = accountDao.get(folder.getOwner().getId());
        long spaceQuota = accountOwner.getQuota().intValue() * 1048576;
        long spaceUsed = accountDao.checkSpaceUsedExcludeDocument(folder.getOwner().getId(), documentId);
        if (spaceQuota - spaceUsed < file.length()) {
            // TODO: should delete file & folder already created on filesystem
            throw new ExceedQuotaException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_INSUFFICIENT_QUOTA + " : " + "Insufficient account quota.");
        }
        // end - check owner's space quota

        Date now = new Date();
        document.setTitle(title);
        document.setDescription(description);
        document.setCreatedBy(new DmAccount(accountId));
        document.setMimeType(contentTypeDao.getFileMimeType(file.getAbsolutePath()));
        document.setModifiedBy(account);
        document.setLastModifiedTime(now);
        document.setIsRemoved(Boolean.FALSE);

        DmDocumentVersion documentVersion = new DmDocumentVersion();
        documentVersion.setId(versionId);

        /*if (versionMajor == document.getLastVersion().getMajorVersion()
                && versionMinor == document.getLastVersion().getMinorVersion()) {
            versionMinor++;
        }*/
        
        int versionMajor = 0;
        int versionMinor = 0;
        
        if (ApplicationConstants.MAJOR.equals(upMode)) {
            versionMajor = document.getLastVersion().getMajorVersion() + 1;            
        }else {
            versionMajor = document.getLastVersion().getMajorVersion();            
            versionMinor = document.getLastVersion().getMinorVersion() + 1;            
        }

        documentVersion.setMajorVersion(versionMajor);
        documentVersion.setMinorVersion(versionMinor);
        documentVersion.setDocument(document);
        documentVersion.setFileName(fileItem.getName());
        documentVersion.setFilePath(file.getAbsolutePath());
        documentVersion.setApproved((folder.getIsNeedApproval() && isOwner) || (!folder.getIsNeedApproval()));

        PDFContent pdfContent = null;

        if (!FileUtils.PDF_EXTENSION.equals(FileUtils.getFileExtension(file.getAbsolutePath()))) {
            String pdfFile = FileUtils.generatePdfName(file.getAbsolutePath());
            boolean isConvert = FileUtils.convertToPdf(file.getAbsolutePath(), pdfFile);
            pdfContent = FileUtils.extractPdf(pdfFile);
            String contentText = pdfContent.getTextContent();
            if (isConvert && contentText != null) {
                documentVersion.setPdfPath(pdfFile);
                //documentVersion.setTextContent(contentText);
                documentVersion.setNumberOfPages(pdfContent.getNumberOfPages());
            }
        } else {
            pdfContent = FileUtils.extractPdf(file.getAbsolutePath());
            String contentText = pdfContent.getTextContent();
            documentVersion.setPdfPath(file.getAbsolutePath());
            if (contentText != null) {
                //documentVersion.setTextContent(contentText);
                documentVersion.setNumberOfPages(pdfContent.getNumberOfPages());
            }
        }

        if (documentVersion.getTextPath() == null && pdfContent != null) {
            String textFile = FileUtils.generateTextName(file.getAbsolutePath());
            if (FileUtils.writeFile(textFile, pdfContent.getTextContent())) {
                documentVersion.setTextPath(textFile);
            }
        }


//        Long fileSize = file.length() / 1024;
        Long fileSize = file.length();
        documentVersion.setSize(fileSize);
        documentVersion.setModifiedTime(now);
        documentVersion.setModifiedBy(account);
        documentVersionDao.save(documentVersion);

        
        if ((folder.getIsNeedApproval() && isOwner) || (!folder.getIsNeedApproval())) {
            document.setLastVersion(documentVersion);
            documentDao.update(document);
        } else {
            DmDocumentVersionApproval approval = new DmDocumentVersionApproval(UUID.randomUUID().toString());
            approval.setStatus(ApprovalStatus.REQUESTED.getStatus());
            approval.setDocumentVersion(documentVersion);
            documentVersionApprovalDao.save(approval);
            
            DmDocumentCheckout checkout = new DmDocumentCheckout(UUID.randomUUID().toString());
            checkout.setAccount(account);
            checkout.setTimeCheckout(now);
            checkout.setDocument(document);
            documentCheckoutDao.save(checkout);
        }
        
        documentKeywordDao.deleteByDocumentId(documentId);
        if (keyword != null && !"".equals(keyword)) {
            String[] arrayKeyword = keyword.split(",");
            log.debug("ARRAY KEYWORD : " + Arrays.asList(arrayKeyword));
            int idx = 0;
            for (String string : arrayKeyword) {
                if (string != null && !"".equals(string.trim())) {
                    if (string.length() > 50) {
                        throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_KEYWORD_MAX_LENGTH + " : " + "Keyword " + string + " is too long.");
                    }
                    idx++;
                    DmDocumentKeyword documentKeyword = new DmDocumentKeyword();
                    documentKeyword.setId(UUID.randomUUID().toString());
                    documentKeyword.setRank(new Integer(idx));
                    documentKeyword.setTerm(string.trim());
                    documentKeyword.setDocument(document);
                    documentKeywordDao.save(documentKeyword);
                }
            }
        }

        documentAuthorDao.deleteByDocumentId(documentId);
        if (author != null && !"".equals(author)) {
            String[] arrayAuthor = author.split(",");
            System.out.println("ARRAY AUTHOR : " + Arrays.asList(arrayAuthor));
            for (String string : arrayAuthor) {
                if (string != null && !"".equals(string.trim())) {
                    if (string.length() > 75) {
                        throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_AUTHOR_MAX_LENGTH + " : " + "Author " + string + " is too long.");
                    }
                    DmAuthor dmAuthor = authorDao.findByName(string.trim());
                    if (dmAuthor == null) {
                        dmAuthor = new DmAuthor();
                        dmAuthor.setId(UUID.randomUUID().toString());
                        dmAuthor.setName(string.trim());
                        authorDao.save(dmAuthor);
                    }
                    DmDocumentAuthor documentAuthor = new DmDocumentAuthor();
                    documentAuthor.setId(UUID.randomUUID().toString());
                    documentAuthor.setDocument(document);
                    documentAuthor.setAuthor(dmAuthor);
                    documentAuthorDao.save(documentAuthor);
                }
            }
        }
        return document.getId();
    }

    public void updateDocument(String title, String description,
            String author, String keyword, String documentId, String folderId, String accountId) {
        if (title == null || "".equals(title)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_TITLE_REQUIRED + " : " + "Title is required.");
        } else if (title.length() >= 100) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_TITLE_MAX_LENGTH + " : " + "Title too long.");
        } else if (description != null && description.length() > 250) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_DESCRIPTION_MAX_LENGTH + " : " + "Description too long.");
        } else if (accountId == null || "".equals(accountId)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "No account id.");
        } else if (folderId == null || "".equals(folderId)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "No folder id.");
        } else if (documentId == null || "".equals(documentId)) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "No document id.");
        }

        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);
        DmDocument document = documentDao.findById(documentId);


        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        if (document == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "Document not found.");
        }


        boolean isCanWrite = isCanCreate(accountId, folderId);
        if (!isCanWrite) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE + " : "
                    + "Account " + account.getName() + " cannot create a new document version in folder " + folder.getName());
        }

        Date now = new Date();
        document.setTitle(title);
        document.setDescription(description);
        document.setModifiedBy(account);
        document.setLastModifiedTime(now);

        documentDao.update(document);

        documentKeywordDao.deleteByDocumentId(documentId);
        if (keyword != null && !"".equals(keyword)) {
            String[] arrayKeyword = keyword.split(",");
            log.debug("ARRAY KEYWORD : " + Arrays.asList(arrayKeyword));
            int idx = 0;
            for (String string : arrayKeyword) {
                if (string != null && !"".equals(string.trim())) {
                    if (string.length() > 50) {
                        throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_KEYWORD_MAX_LENGTH + " : " + "Keyword " + string + " is too long.");
                    }
                    idx++;
                    DmDocumentKeyword documentKeyword = new DmDocumentKeyword();
                    documentKeyword.setId(UUID.randomUUID().toString());
                    documentKeyword.setRank(new Integer(idx));
                    documentKeyword.setTerm(string.trim());
                    documentKeyword.setDocument(document);
                    documentKeywordDao.save(documentKeyword);
                }
            }
        }

        documentAuthorDao.deleteByDocumentId(documentId);
        if (author != null && !"".equals(author)) {
            String[] arrayAuthor = author.split(",");
            log.debug("ARRAY AUTHOR : " + Arrays.asList(arrayAuthor));
            for (String string : arrayAuthor) {
                if (string != null && !"".equals(string.trim())) {
                    if (string.length() > 75) {
                        throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_AUTHOR_MAX_LENGTH + " : " + "Author " + string + " is too long.");
                    }
                    DmAuthor dmAuthor = authorDao.findByName(string.trim());
                    if (dmAuthor == null) {
                        dmAuthor = new DmAuthor();
                        dmAuthor.setId(UUID.randomUUID().toString());
                        dmAuthor.setName(string.trim());
                        authorDao.save(dmAuthor);
                    }
                    DmDocumentAuthor documentAuthor = new DmDocumentAuthor();
                    documentAuthor.setId(UUID.randomUUID().toString());
                    documentAuthor.setDocument(document);
                    documentAuthor.setAuthor(dmAuthor);
                    documentAuthorDao.save(documentAuthor);
                }
            }
        }
    }

    public int countAllNotRenderedVersion() {
        return documentVersionDao.countAllNotRenderedVersion();
    }

    public List<DmDocumentVersion> findNotRenderedVersion(int start, int max) {
        return documentVersionDao.findNotRenderedVersion(start, max);
    }

    public DmDocRenderImage findDocRenderImageById(String renderImageId) {
        return docRenderImageDao.findById(renderImageId);
    }

    public DocumentDownloadWithAccessLog downloadDocument(String documentId, String folderId, String accountId) {
        DocumentDownloadWithAccessLog documentDownloadWithAccessLog = null;
        DocumentDownload download = null;
        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        boolean isCanDownload = isCanDownload(accountId, folderId);
        if (!isCanDownload) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_DOWNLOAD + " : "
                    + "Account " + account.getName() + " cannot download any documents in folder " + folder.getName());
        }

        DmDocument document = this.findDocumentByDocId(documentId);
        if (document != null) {
            download = new DocumentDownload();
            download.setDocument(document);
            byte[] fileBytes = FileUtils.getBytesFromFile(download.getDocument().getLastVersion().getFilePath());
            download.setData(fileBytes);
        } else {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "Document not found.");
        }

        String accessLogId = UUID.randomUUID().toString();

        DmDocAccessHistory accessHistory = new DmDocAccessHistory();
        accessHistory.setId(accessLogId);
        accessHistory.setAccessedBy(account);
        accessHistory.setAccessedTime(new Date());
        accessHistory.setDocVersion(document.getLastVersion());
        accessHistory.setAccessType(DocumentAccessHistory.DOWNLOAD.getAccessType());
        docAccessHistoryDao.save(accessHistory);

        documentDownloadWithAccessLog = new DocumentDownloadWithAccessLog();
        documentDownloadWithAccessLog.setDocumentDownload(download);
        documentDownloadWithAccessLog.setAccessLog(accessHistory);

        return documentDownloadWithAccessLog;
    }

    public DocumentDownloadWithAccessLog viewDocument(String documentId, String folderId, String accountId) {
        DocumentDownloadWithAccessLog documentDownloadWithAccessLog = null;
        DocumentDownload download = null;
        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        boolean isCanView = isCanView(accountId, folderId);
        if (!isCanView) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_DOWNLOAD + " : "
                    + "Account " + account.getName() + " cannot download any documents in folder " + folder.getName());
        }

        DmDocument document = this.findDocumentByDocId(documentId);
        if (document != null) {
            download = new DocumentDownload();
            download.setDocument(document);
            DmDocumentVersion version = document.getLastVersion();
            if (download.getDocument().getLastVersion().getPdfPath() == null) {
                String pdfFile = FileUtils.generatePdfName(version.getFilePath());
                if (FileUtils.PDF_EXTENSION.equals(FileUtils.getFileExtension(version.getFilePath()))) {
                    pdfFile = version.getFilePath();
                    version.setPdfPath(pdfFile);
                    documentVersionDao.update(version);
                } else {
                    boolean converted = false;
                    try {
                        converted = FileUtils.convertToPdf(version.getFilePath(), pdfFile);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("Document can't be extracted...");
                    }
                    if (converted) {
                        version.setPdfPath(pdfFile);
                        documentVersionDao.update(version);
                    }
                }
            }
            byte[] fileBytes = FileUtils.getBytesFromFile(version.getPdfPath());
            download.setData(fileBytes);
        } else {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "Document not found.");
        }

        String accessLogId = UUID.randomUUID().toString();

        DmDocAccessHistory accessHistory = new DmDocAccessHistory();
        accessHistory.setId(accessLogId);
        accessHistory.setAccessedBy(account);
        accessHistory.setAccessedTime(new Date());
        accessHistory.setDocVersion(document.getLastVersion());
        accessHistory.setAccessType(DocumentAccessHistory.DOCUMENT_VIEW.getAccessType());
        docAccessHistoryDao.save(accessHistory);

        documentDownloadWithAccessLog = new DocumentDownloadWithAccessLog();
        documentDownloadWithAccessLog.setDocumentDownload(download);
        documentDownloadWithAccessLog.setAccessLog(accessHistory);

        return documentDownloadWithAccessLog;
    }

    public DocumentViewWithAccessLog viewDocumentPage(String documentId, String folderId, String accountId) {
        DocumentViewWithAccessLog documentViewWithAccessLog = null;
        DocumentView docView = null;
        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        boolean isCanView = isCanView(accountId, folderId);
        if (!isCanView) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_DOWNLOAD + " : "
                    + "Account " + account.getName() + " cannot download any documents in folder " + folder.getName());
        }

        DmDocument document = this.findDocumentByDocId(documentId);
        if (document != null) {
            docView = new DocumentView();
            DmDocumentVersion version = document.getLastVersion();
            List<DmDocRenderImage> renderImageList = docRenderImageDao.findByVersionId(version.getId());

            docView.setDocument(document);
            docView.setRenderImageList(renderImageList);
        } else {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "Document not found.");
        }

        String accessLogId = UUID.randomUUID().toString();

        DmDocAccessHistory accessHistory = new DmDocAccessHistory();
        accessHistory.setId(accessLogId);
        accessHistory.setAccessedBy(account);
        accessHistory.setAccessedTime(new Date());
        accessHistory.setDocVersion(document.getLastVersion());
        accessHistory.setAccessType(DocumentAccessHistory.DOCUMENT_VIEW.getAccessType());
        docAccessHistoryDao.save(accessHistory);

        documentViewWithAccessLog = new DocumentViewWithAccessLog();
        documentViewWithAccessLog.setDocumentView(docView);
        documentViewWithAccessLog.setAccessLog(accessHistory);

        return documentViewWithAccessLog;
    }

    public DocumentCompare compareDocumentVersion(String firstVersionId, String secondVersionId) {

        DocumentCompare documentCompare = new DocumentCompare();

        DmDocumentVersion firstVersion = documentVersionDao.findById(firstVersionId);

        DmDocumentVersion secondVersion = documentVersionDao.findById(secondVersionId);

        if (firstVersion == null || secondVersion == null) {
            throw new IllegalArgumentException("The document version can't be found.");
        }

        if (!firstVersion.getDocument().getId().equals(secondVersion.getDocument().getId())) {
            throw new IllegalArgumentException("Please only compare 2 versions at a same document.");
        }

        documentCompare.setDocument(firstVersion.getDocument());
        documentCompare.setFirstVersion(firstVersion);
        documentCompare.setSecondVersion(secondVersion);

        String firstContent = extractVersionContent(firstVersion);
        String secondContent = extractVersionContent(secondVersion);

        if (firstContent == null) {
            throw new IllegalArgumentException("File content of version "
                    + firstVersion.getMajorVersion() + "." + firstVersion.getMinorVersion()
                    + " can't be extracted or it doesn't has any text content.");
        } else if (secondContent == null) {
            throw new IllegalArgumentException("File content of version "
                    + secondVersion.getMajorVersion() + "." + secondVersion.getMinorVersion()
                    + " can't be extracted or it doesn't has any text content.");
        }

        List<LineDifference> lineDiffs = TextCompare.mainDifference(firstContent.trim(), secondContent.trim(), true);
        documentCompare.setLineDifferences(lineDiffs);

        return documentCompare;
    }

    public String extractVersionContent(DmDocumentVersion version) {
        String textContent = null;

        if (version == null) {
            throw new IllegalArgumentException("Document version can't be null");
        }
        
        if (version.getTextPath() == null) {
            String textFile = FileUtils.generateTextName(version.getFilePath());
            if (version.getPdfPath() == null) {
                String pdfFile = FileUtils.generatePdfName(version.getFilePath());
                boolean converted = false;
                if (FileUtils.PDF_EXTENSION.equals(FileUtils.getFileExtension(version.getFilePath()))) {
                    converted = true;
                    pdfFile = version.getFilePath();
                } else {
                    try {
                        converted = FileUtils.convertToPdf(version.getFilePath(), pdfFile);
                    } catch (Exception ex) {
                        throw new IllegalArgumentException("Document can't be extracted...");
                    }
                }
                PDFContent pdfContent = FileUtils.extractPdf(pdfFile);
                String stringPdfContent = pdfContent.getTextContent();
                if (converted && stringPdfContent != null) {
                    version.setPdfPath(pdfFile);
                    //version.setTextContent(stringPdfContent);
                    if (FileUtils.writeFile(textFile, pdfContent.getTextContent())) {
                        version.setTextPath(textFile);
                    }
                    
                    version.setNumberOfPages(pdfContent.getNumberOfPages());
                    documentVersionDao.update(version);
                    textContent = stringPdfContent;
                }
            } else {
                PDFContent content = FileUtils.extractPdf(version.getPdfPath());
                String pdfContent = content.getTextContent();
                if (pdfContent != null) {
                    //version.setTextContent(pdfContent);
                    if (FileUtils.writeFile(textFile, pdfContent)) {
                        version.setTextPath(textFile);
                    }
                    
                    version.setNumberOfPages(version.getNumberOfPages());                    
                    documentVersionDao.update(version);
                }
                textContent = pdfContent;
            }
        } else {
            try {
                textContent = FileUtils.getContents(new File(version.getTextPath()));
            } catch (Exception ex) {
                textContent = "Sorry, system couldn't read file";
            }
        }
        return textContent;
    }

    public DmDocViewAnnotation findAnnotation(String accountId, String folderId, String docVersionId, int page) {
        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        boolean isCanView = isCanView(accountId, folderId);
        if (!isCanView) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_VIEW_ANNOTATION + " : "
                    + "Account " + account.getName() + " cannot view any document annotation in folder " + folder.getName());
        }

        DmDocRenderImage renderImage = docRenderImageDao.findByVersionIdAndPage(docVersionId, page);
        if (renderImage == null) {
            throw new IllegalArgumentException("Invalid Document Version and Page");
        }

        List<DmDocViewAnnotation> annotations = docViewAnnotationDao.findByKey("annotatedPage.id", renderImage.getId(), 0, 1);

        if (annotations.size() > 0) {
            return annotations.get(0);
        }

        return null;
    }

    public DmDocViewAnnotation updateAnnotation(String accountId, String folderId, String docVersionId, int page, String annotation) {
        if (annotation == null || "".equals(annotation)) {
            throw new IllegalArgumentException("Please fill the annotation.");
        } else if (annotation.length() > 250) {
            throw new IllegalArgumentException("Max character is 250 for annotation.");
        }

        if (!isWriter(accountId, folderId)) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE_ANNOTATION + " : "
                    + "Account cannot write document annotation in folder.");
        }

        DmDocViewAnnotation annotationObj = findAnnotation(accountId, folderId, docVersionId, page);
        if (annotationObj == null) {
            annotationObj = new DmDocViewAnnotation();
            annotationObj.setId(UUID.randomUUID().toString());
            annotationObj.setAnnotation(annotation);
            annotationObj.setAnnotatedBy(new DmAccount(accountId));
            annotationObj.setCreatedTime(new Date());

            DmDocRenderImage renderImage = docRenderImageDao.findByVersionIdAndPage(docVersionId, page);
            if (renderImage == null) {
                throw new IllegalArgumentException("Invalid Document Version and Page");
            }
            annotationObj.setAnnotatedPage(renderImage);
            docViewAnnotationDao.save(annotationObj);
        } else {
            DmAccount account = accountDao.get(accountId);
            annotationObj.setAnnotation(annotation);
            annotationObj.setAnnotatedBy(account);
            annotationObj.setCreatedTime(new Date());
            docViewAnnotationDao.update(annotationObj);
        }

        return annotationObj;
    }

    public boolean deleteAnnotation(String accountId, String folderId, String docVersionId, int page) {
        if (!isWriter(accountId, folderId)) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE_ANNOTATION + " : "
                    + "Account cannot delete document annotation in folder.");
        }

        DmDocViewAnnotation annotationObj = findAnnotation(accountId, folderId, docVersionId, page);
        if (annotationObj == null) {
            return true;
        } else {
            docViewAnnotationDao.delete(annotationObj);
            return true;
        }
    }

    public boolean isDocumentLocked(String docId) {
        DmDocumentLock documentLock = documentLockDao.findByDocumentId(docId);
        if (documentLock != null) {
            return true;
        }
        return false;
    }

    public boolean isDocumentCheckedOut(String docId){
        DmDocumentCheckout documentCheckout = documentCheckoutDao.findByDocumentId(docId);
        if (documentCheckout != null) {
            return true;
        }
        return false;
    }
    
    public boolean isOwner(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isWriter(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission || FolderPermission.WRITER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isConsumer(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission || FolderPermission.WRITER.getPermission() == permission
                || FolderPermission.CONSUMER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isReader(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission || FolderPermission.WRITER.getPermission() == permission
                || FolderPermission.CONSUMER.getPermission() == permission || FolderPermission.READER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isCanDelete(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isCanCreate(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission || FolderPermission.WRITER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isCanLock(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isCanDownload(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission || FolderPermission.WRITER.getPermission() == permission
                || FolderPermission.CONSUMER.getPermission() == permission) {
            return true;
        }
        return false;
    }

    public boolean isCanView(String accountId, String folderId) {
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        if (FolderPermission.OWNER.getPermission() == permission || FolderPermission.WRITER.getPermission() == permission
                || FolderPermission.CONSUMER.getPermission() == permission
                || FolderPermission.READER.getPermission() == permission) {
            return true;
        }
        return false;
    }

//    public boolean moveDocument(String documentId, String folderIdDest, String accountIdOprSource, String accountIdOprDest) {
    public boolean moveDocument(String documentId, String folderIdDest, String accountIdOprDest) {
        DmAccount accountOperatorDest = accountDao.get(accountIdOprDest);
        if (accountOperatorDest == null) {
            return false;
        }

//        DmAccount accountOperatorSource = accountDao.get(accountIdOprSource);
//        if (accountOperatorSource == null) {
//            return false;
//        }

        if (!isWriter(accountIdOprDest, folderIdDest)) {
            return false;
        }

        DmDocumentFolder docFolder = documentFolderDao.getByDocument(documentId);
        if (docFolder != null) {
            DmFolder folder = folderDao.findById(folderIdDest);
            if (folder != null) {
//                if (!isOwner(accountIdOprSource, docFolder.getFolder().getId())) {
//                    return false;
//                }
                docFolder.setFolder(folder);
                documentFolderDao.update(docFolder);

                if (folder.getIsNeedApproval().booleanValue() && !isOwner(accountIdOprDest, folderIdDest)) {
                    DmDocument document = documentDao.findById(documentId);

                    if (document != null) {
                        document.setApproved(Boolean.FALSE);
                        documentDao.update(document);

                        DmDocumentApproval docApproval = documentApprovalDao.findByDocId(documentId);
                        if (docApproval != null) {
                            docApproval.setApprovedBy(null);
                            docApproval.setComment(null);
                            docApproval.setStatus(ApplicationConstants.APPROVAL_STATUS_REQUESTED);
                            documentApprovalDao.update(docApproval);
                        }
                        else {
                            docApproval = new DmDocumentApproval();
                            docApproval.setApprovedBy(null);
                            docApproval.setComment(null);
                            docApproval.setDocument(document);
                            docApproval.setId(UUID.randomUUID().toString());
                            docApproval.setStatus(ApplicationConstants.APPROVAL_STATUS_REQUESTED);
                            documentApprovalDao.save(docApproval);
                        }
                    }                    
                }

                // start - set document to be reindexed
                documentIndexedDao.setReindexByDocument(documentId);
                // end - set document to be reindexed

                return true;
            }
        }
        return false;
    }

    public DocumentVersionDownloadWithAccessLog downloadDocumentVersion(String documentId, String versionId, String folderId, String accountId) {
        DocumentVersionDownloadWithAccessLog documentVersionDownloadWithAccessLog = null;
        DocumentVersionDownload download = null;
        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        boolean isCanDownload = isCanDownload(accountId, folderId);
        if (!isCanDownload) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_DOWNLOAD + " : "
                    + "Account " + account.getName() + " cannot download any documents in folder " + folder.getName());
        }

        DmDocument document = this.findDocumentByDocId(documentId);
        DmDocumentVersion documentVersion = documentVersionDao.findById(versionId);
        if (document != null) {
            download = new DocumentVersionDownload();
            download.setDocument(document);
            download.setDocumentVersion(documentVersion);
            byte[] fileBytes = FileUtils.getBytesFromFile(download.getDocumentVersion().getFilePath());
            download.setData(fileBytes);
        } else {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "Document not found.");
        }

        String accessLogId = UUID.randomUUID().toString();

        DmDocAccessHistory accessHistory = new DmDocAccessHistory();
        accessHistory.setId(accessLogId);
        accessHistory.setAccessedBy(account);
        accessHistory.setAccessedTime(new Date());
        accessHistory.setDocVersion(document.getLastVersion());
        accessHistory.setAccessType(DocumentAccessHistory.DOWNLOAD.getAccessType());
        docAccessHistoryDao.save(accessHistory);

        documentVersionDownloadWithAccessLog = new DocumentVersionDownloadWithAccessLog();
        documentVersionDownloadWithAccessLog.setDocumentVersionDownload(download);
        documentVersionDownloadWithAccessLog.setAccessLog(accessHistory);

        return documentVersionDownloadWithAccessLog;
    }

    public DocumentVersionDownloadWithAccessLog viewDocumentVersion(String documentId, String versionId, String folderId, String accountId) {
        DocumentVersionDownloadWithAccessLog documentVersionDownloadWithAccessLog = null;
        DocumentVersionDownload download = null;
        DmAccount account = accountDao.get(accountId);
        DmFolder folder = folderDao.findById(folderId);

        if (folder == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER + " : " + "Folder not found.");
        }

        if (account == null) {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT + " : " + "Account not found.");
        }

        boolean isCanView = isCanView(accountId, folderId);
        if (!isCanView) {
            throw new InsufficientPriviledgeException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_CANNOT_DOWNLOAD + " : "
                    + "Account " + account.getName() + " cannot download any documents in folder " + folder.getName());
        }

        DmDocument document = this.findDocumentByDocId(documentId);
        DmDocumentVersion documentVersion = documentVersionDao.findById(versionId);
        if (document != null) {
            download = new DocumentVersionDownload();
            download.setDocument(document);
            download.setDocumentVersion(documentVersion);
//            DmDocumentVersion version = document.getLastVersion();
            DmDocumentVersion version = documentVersion;
//            if (download.getDocument().getLastVersion().getPdfPath() == null) {
            if (download.getDocumentVersion().getPdfPath() == null) {
                String pdfFile = FileUtils.generatePdfName(version.getFilePath());
                if (FileUtils.PDF_EXTENSION.equals(FileUtils.getFileExtension(version.getFilePath()))) {
                    pdfFile = version.getFilePath();
                    version.setPdfPath(pdfFile);
                    documentVersionDao.update(version);
                }else {
                    boolean converted = false;
                    try{
                        converted = FileUtils.convertToPdf(version.getFilePath(), pdfFile);
                    }catch(Exception ex){
                        throw new IllegalArgumentException("Document can't be extracted...");
                    }
                    if (converted) {
                        version.setPdfPath(pdfFile);
                        documentVersionDao.update(version);
                    }
                }
            }
            byte[] fileBytes = FileUtils.getBytesFromFile(version.getPdfPath());
            download.setData(fileBytes);
        } else {
            throw new IllegalArgumentException(MessageCodeConstants.EBDM_ERROR_DOC_GENERAL_NOT_FOUND + " : " + "Document not found.");
        }

        String accessLogId = UUID.randomUUID().toString();

        DmDocAccessHistory accessHistory = new DmDocAccessHistory();
        accessHistory.setId(accessLogId);
        accessHistory.setAccessedBy(account);
        accessHistory.setAccessedTime(new Date());
        accessHistory.setDocVersion(document.getLastVersion());
        accessHistory.setAccessType(DocumentAccessHistory.DOCUMENT_VIEW.getAccessType());
        docAccessHistoryDao.save(accessHistory);

        documentVersionDownloadWithAccessLog = new DocumentVersionDownloadWithAccessLog();
        documentVersionDownloadWithAccessLog.setDocumentVersionDownload(download);
        documentVersionDownloadWithAccessLog.setAccessLog(accessHistory);

        return documentVersionDownloadWithAccessLog;
    }

    public List<String> getVersionIdList(String documentId) {
        return documentVersionDao.getIdListByDocId(documentId);
    }

    public List<DmDocument> getTermFreqNotStoredDocList(int numDocs) {
        return docTermFreqStoredDao.getNotStoredDocList(numDocs);
    }

    public void saveTermFreqStored(DmDocTermFreqStored docTermFreqStored) {
        docTermFreqStoredDao.save(docTermFreqStored);
    }

    public void deleteTermFreqStoredByDocId(String documentId) {
        docTermFreqStoredDao.deleteByDocumentId(documentId);
    }

    public void saveDocTermFrequency(DmDocTermFrequency docTermFrequency) {
        docTermFrequencyDao.save(docTermFrequency);
    }

    public void deleteDocTermFrequencyByDocId(String documentId) {
        docTermFrequencyDao.deleteByDocId(documentId);
    }

    public void addFolderTermFrequencyByDoc(String documentId, String folderId) {
        folderTermFrequencyDao.increaseByDoc(documentId, folderId);
        folderTermFrequencyDao.insertByDoc(documentId, folderId);        
    }

    public void subtractFolderTermFrequencyByDoc(String documentId, String folderId) {
        folderTermFrequencyDao.decreaseByDoc(documentId, folderId);
    }

    public List<String> getTopTerm(String documentId, int numTerm) {
        return docTermFrequencyDao.getTopTerm(documentId, numTerm);
    }
}
