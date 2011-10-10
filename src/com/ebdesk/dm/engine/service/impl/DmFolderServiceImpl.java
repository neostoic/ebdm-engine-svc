/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.constant.ApplicationConstants;
import com.ebdesk.dm.engine.dao.DmAccountDao;
import com.ebdesk.dm.engine.dao.DmDocumentDao;
import com.ebdesk.dm.engine.dao.DmDocumentFolderDao;
import com.ebdesk.dm.engine.dao.DmDocumentIndexedDao;
import com.ebdesk.dm.engine.dao.DmFolderDao;
import com.ebdesk.dm.engine.dao.DmFolderDescendantDao;
import com.ebdesk.dm.engine.dao.DmFolderPermissionDao;
import com.ebdesk.dm.engine.dao.DmFolderRelatedDao;
import com.ebdesk.dm.engine.domain.DmAccount;
import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderDescendant;
import com.ebdesk.dm.engine.domain.DmFolderPermission;
import com.ebdesk.dm.engine.domain.DmFolderRelated;
import com.ebdesk.dm.engine.dto.DmFolderNode;
import com.ebdesk.dm.engine.service.DmFolderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Service("dmFolderService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmFolderServiceImpl implements DmFolderService {

    @Autowired
    private DmFolderDao folderDao;
    @Autowired
    private DmFolderPermissionDao folderPermissionDao;
    @Autowired
    private DmAccountDao accountDao;
    @Autowired
    private DmFolderRelatedDao folderRelatedDao;
    @Autowired
    private DmDocumentFolderDao docFolderDao;
    @Autowired
    private DmDocumentDao docDao;
    @Autowired
    private DmFolderDescendantDao folderDescendantDao;
    @Autowired
    private DmDocumentIndexedDao documentIndexedDao;

    public boolean save(DmFolder folder) {
        if(folder.getId() == null)
            folder.setId(java.util.UUID.randomUUID().toString());

        folderDao.save(folder);
        return true;
    }

    public DmFolder get(String folderId) {
        DmFolder folder = folderDao.findById(folderId);

        // start - get children (trigger lazy fetch)
        if (folder != null) {
            if (folder.getChildList() != null) {
                folder.getChildList().size();
            }
        }
        // end - get children (trigger lazy fetch)

        return folder;
    }

    public boolean update(DmFolder folder) {
        folderDao.update(folder);
        return true;
    }


    public boolean delete(DmFolder folder) {
        if (folder == null) {
            return false;
        }
        folderDescendantDao.deleteByDescendant(folder.getId());
        docDao.setIsRemovedByFolder(folder.getId());
        docFolderDao.deleteByFolderId(folder.getId());
        folderDao.delete(folder);
        return true;
    }

    public List<DmFolder> getChildList(String parentId) {
        return folderDao.getChildList(parentId);
    }

    public int getNumDocuments(String folderId) {
        return folderDao.getNumDocuments(folderId);
    }

    public DmFolder getByName(String folderName, String accountId, String folderIdParent) {
        return folderDao.getByName(folderName, accountId, folderIdParent);
    }

    public DmFolder getByNameExcludeById(String folderName, String folderId, String accountId, String folderIdParent) {
        return folderDao.getByNameExcludeById(folderName, folderId, accountId, folderIdParent);
    }

    public boolean savePermission(DmFolderPermission folderPermission) {
        if(folderPermission.getId() == null)
            folderPermission.setId(java.util.UUID.randomUUID().toString());

        folderPermissionDao.save(folderPermission);
        return true;
    }

    public boolean deletePermissionByFolder(String folderId) {
        folderPermissionDao.deleteByFolder(folderId);
        return true;
    }

    public List<DmFolderPermission> getSharedAccountList(String folderId) {
        DmFolder folder = folderDao.findById(folderId);
        if ((folder != null) && (folder.getOwner() != null)) {
            // exclude folder owner
            return folderPermissionDao.getByFolderExcludeByAccount(folderId, folder.getOwner().getId(), ApplicationConstants.FOLDER_PERMISSION_MANAGER);
        }
        return null;
    }

    public void addPermission(String folderId, String addAccManagerList, String addAccWriterList, String addAccConsumerList, String addAccViewerList) {
        DmFolder folder = null;
        if (!folderId.isEmpty()) {
            folder = folderDao.findById(folderId);
        }
        if (folder != null) {
            // manager
            String accountManagerList[] = null;
            if ((addAccManagerList != null) && (!addAccManagerList.isEmpty())) {
                accountManagerList = addAccManagerList.split(",");
            }
            if (accountManagerList != null) {
                for (String accountManager : accountManagerList) {
                    DmAccount account = accountDao.get(accountManager);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission == null) {
                            folderPermission = new DmFolderPermission();
                            folderPermission.setId(java.util.UUID.randomUUID().toString());
                            folderPermission.setFolder(folder);
                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_MANAGER);
                            folderPermissionDao.save(folderPermission);
                        }
                    }
                }
            }

            // writer
            String accountWriterList[] = null;
            if ((addAccWriterList != null) && (!addAccWriterList.isEmpty())) {
                accountWriterList = addAccWriterList.split(",");
            }
            if (accountWriterList != null) {
                for (String accountWriter : accountWriterList) {
                    DmAccount account = accountDao.get(accountWriter);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission == null) {
                            folderPermission = new DmFolderPermission();
                            folderPermission.setId(java.util.UUID.randomUUID().toString());
                            folderPermission.setFolder(folder);
                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_WRITER);
                            folderPermissionDao.save(folderPermission);
                        }
                    }
                }
            }

            // consumer
            String accountConsumerList[] = null;
            if ((addAccConsumerList != null) && (!addAccConsumerList.isEmpty())) {
                accountConsumerList = addAccConsumerList.split(",");
            }
            if (accountConsumerList != null) {
                for (String accountConsumer : accountConsumerList) {
                    DmAccount account = accountDao.get(accountConsumer);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission == null) {
                            folderPermission = new DmFolderPermission();
                            folderPermission.setId(java.util.UUID.randomUUID().toString());
                            folderPermission.setFolder(folder);
                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_CONSUMER);
                            folderPermissionDao.save(folderPermission);
                        }
                    }
                }
            }

            // viewer
            String accountViewerList[] = null;
            if ((addAccViewerList != null) && (!addAccViewerList.isEmpty())) {
                accountViewerList = addAccViewerList.split(",");
            }
            if (accountViewerList != null) {
                for (String accountViewer : accountViewerList) {
                    DmAccount account = accountDao.get(accountViewer);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission == null) {
                            folderPermission = new DmFolderPermission();
                            folderPermission.setId(java.util.UUID.randomUUID().toString());
                            folderPermission.setFolder(folder);
                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_VIEWER);
                            folderPermissionDao.save(folderPermission);
                        }
                    }
                }
            }
        }
    }

    public void deletePermission(String folderId, String deleteAccList) {
        DmFolder folder = null;
        if (!folderId.isEmpty()) {
            folder = folderDao.findById(folderId);
        }

        if (folder != null) {
            String deleteAccounts[] = null;
            if (deleteAccList != null) {
                deleteAccounts = deleteAccList.split(",");
            }
            if (deleteAccounts != null) {
                for (String deleteAccount : deleteAccounts) {
                    folderPermissionDao.deleteByFolderByAccount(folderId, deleteAccount);
                }
            }
        }
    }

    public void updatePermission(String folderId, String manageAccList, String writeAccList, String consumeAccList, String viewAccList) {
        DmFolder folder = null;
        if (!folderId.isEmpty()) {
            folder = folderDao.findById(folderId);
        }
        if (folder != null) {
            // manager
            String accountManagerList[] = null;
            if ((manageAccList != null) && (!manageAccList.isEmpty())) {
                accountManagerList = manageAccList.split(",");
            }
            if (accountManagerList != null) {
                for (String accountManager : accountManagerList) {
                    DmAccount account = accountDao.get(accountManager);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission != null) {
//                            folderPermission = new DmFolderPermission();
//                            folderPermission.setId(java.util.UUID.randomUUID().toString());
//                            folderPermission.setFolder(folder);
//                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_MANAGER);
                            folderPermissionDao.update(folderPermission);
                        }
                    }
                }
            }

            // writer
            String accountWriterList[] = null;
            if ((writeAccList != null) && (!writeAccList.isEmpty())) {
                accountWriterList = writeAccList.split(",");
            }
            if (accountWriterList != null) {
                for (String accountWriter : accountWriterList) {
                    DmAccount account = accountDao.get(accountWriter);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission != null) {
//                            folderPermission = new DmFolderPermission();
//                            folderPermission.setId(java.util.UUID.randomUUID().toString());
//                            folderPermission.setFolder(folder);
//                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_WRITER);
                            folderPermissionDao.update(folderPermission);
                        }
                    }
                }
            }

            // consumer
            String accountConsumerList[] = null;
            if ((consumeAccList != null) && (!consumeAccList.isEmpty())) {
                accountConsumerList = consumeAccList.split(",");
            }
            if (accountConsumerList != null) {
                for (String accountConsumer : accountConsumerList) {
                    DmAccount account = accountDao.get(accountConsumer);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission != null) {
//                            folderPermission = new DmFolderPermission();
//                            folderPermission.setId(java.util.UUID.randomUUID().toString());
//                            folderPermission.setFolder(folder);
//                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_CONSUMER);
                            folderPermissionDao.update(folderPermission);
                        }
                    }
                }
            }

            // viewer
            String accountViewerList[] = null;
            if ((viewAccList != null) && (!viewAccList.isEmpty())) {
                accountViewerList = viewAccList.split(",");
            }
            if (accountViewerList != null) {
                for (String accountViewer : accountViewerList) {
                    DmAccount account = accountDao.get(accountViewer);
                    if (account != null) {
                        DmFolderPermission folderPermission = null;
                        folderPermission = folderPermissionDao.getByFolderByAccount(folder.getId(), account.getId());
                        if (folderPermission != null) {
//                            folderPermission = new DmFolderPermission();
//                            folderPermission.setId(java.util.UUID.randomUUID().toString());
//                            folderPermission.setFolder(folder);
//                            folderPermission.setAccount(account);
                            folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_VIEWER);
                            folderPermissionDao.update(folderPermission);
                        }
                    }
                }
            }
        }
    }

    public boolean create(DmFolder folder) {        
        if (folder != null) {
            // start - get parent permission
            List<DmFolderPermission> parentfolderPermissionList = null;
            if (folder.getParent() != null) {
//                parentfolderPermissionList = folderPermissionDao.getByFolderExcludeByAccount(folder.getParent().getId(), folder.getParent().getOwner().getId(), ApplicationConstants.FOLDER_PERMISSION_MANAGER);
                parentfolderPermissionList = folderPermissionDao.getByFolderExcludeByAccount(folder.getParent().getId(), null, ApplicationConstants.FOLDER_PERMISSION_MANAGER);
            }
            // end - get parent permission

            // start - create folder
            // // start - get parent
            DmFolder folderParent = null;
            if (folder.getParent() != null) {
                folderParent = folderDao.findById(folder.getParent().getId());
            }
            // // end - get parent
            if (folderParent != null) {
                folder.setLevel(folderParent.getLevel().intValue() + 1);
            }
            else {
                folder.setLevel(1);
            }
            boolean isFolderSaved = save(folder);
            // end - create folder

            if (isFolderSaved) {
//                System.out.println("Folder is saved");
                // start - copy parent permissions
                if (parentfolderPermissionList != null) {
//                    System.out.println("parentfolderPermissionList != null");
                    for (DmFolderPermission parentfolderPermission : parentfolderPermissionList) {
//                        System.out.println("parentfolderPermission id = " + parentfolderPermission.getId());
                        DmFolderPermission folderPermission = new DmFolderPermission();
                        folderPermission.setId(java.util.UUID.randomUUID().toString());
                        folderPermission.setFolder(folder);
                        folderPermission.setAccount(parentfolderPermission.getAccount());
                        folderPermission.setPermissionType(parentfolderPermission.getPermissionType());
                        folderPermissionDao.save(folderPermission);
                    }
                }
                else {
                    DmFolderPermission folderPermission = new DmFolderPermission();
                    folderPermission.setId(java.util.UUID.randomUUID().toString());
                    folderPermission.setAccount(folder.getOwner());
                    folderPermission.setFolder(folder);
                    folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_MANAGER);
                    folderPermissionDao.save(folderPermission);
                }
                // end - copy parent permissions

                // start - insert folder descendants                
                if (folderParent != null) {
                    // // start - for parent
                    DmFolderDescendant folderDescendant = new DmFolderDescendant();
                    folderDescendant.setId(java.util.UUID.randomUUID().toString());
                    folderDescendant.setFolder(folderParent);
                    folderDescendant.setFolderDescendant(folder);
                    folderDescendantDao.save(folderDescendant);
                    // // end - for parent
                    // // start - for grandparent and other ascendants
                    List<DmFolder> ascendantList = folderDescendantDao.getAscendantList(folderParent.getId());
                    if (ascendantList != null) {
                        for (DmFolder ascendant : ascendantList) {
                            DmFolderDescendant folderDescendantTmp = new DmFolderDescendant();
                            folderDescendantTmp.setId(java.util.UUID.randomUUID().toString());
                            folderDescendantTmp.setFolder(ascendant);
                            folderDescendantTmp.setFolderDescendant(folder);
                            folderDescendantDao.save(folderDescendantTmp);
                        }
                    }
                    // // end - for grandparent and other ascendants
                }                
                // end - insert folder descendants
            }
        }        

        return true;
    }

    public List<DmFolder> getFirstLevelList(String accountId) {
        return folderDao.getFirstLevelList(accountId);
    }

    public List<DmFolder> getFirstLevelList() {
        return folderDao.getFirstLevelList();
    }

    public List<String> getListShareOwnerIdByAccount(String accountId) {
//        List<DmFolder> folderList = folderPermissionDao.getListFolderByAccount(accountId, permissionLevelStart);
//        List<DmAccount> ownerAccountList = folderDao.getListShareOwnerBySharedAccount(accountId, permissionLevelStart);
        int permissionLevelStart = ApplicationConstants.FOLDER_PERMISSION_MANAGER;
        List<DmAccount> ownerAccountList = folderDao.getListOwnerByPermittedAccount(accountId, permissionLevelStart);

        List<String> ownerAccountIdList = null;
        if (ownerAccountList != null) {
            ownerAccountIdList = new ArrayList<String>();
            for (DmAccount ownerAccount : ownerAccountList) {
                ownerAccountIdList.add(ownerAccount.getId());
            }
        }

        return ownerAccountIdList;

//        List<String> shareOwnerIdList = null;
//        if (ownerAccountList != null) {
//            shareOwnerIdList = new ArrayList<String>();
//            for (DmAccount ownerAccount : ownerAccountList) {
//                shareOwnerIdList.add(ownerAccount.getId());
//            }
//        }

//        if (folderList != null) {
//            shareOwnerIdList = new ArrayList<String>();
//            for (DmFolder folder : folderList) {
//                if (!shareOwnerIdList.contains(folder.getOwner().getId())) {
//                    shareOwnerIdList.add(folder.getOwner().getId());
//                }
//            }
//        }

//        return shareOwnerIdList;
    }

    public List<DmFolder> getSharedFirstLevelList(String accountSharedId, String accountOwnerId) {
        return folderDao.getSharedFirstLevelList(accountSharedId, accountOwnerId);
    }

    public List<DmFolder> getSharedChildList(String accountSharedId, String folderIdParent) {
        return folderDao.getSharedChildList(accountSharedId, folderIdParent);
    }

    public List<DmFolderNode> getSharedChildFolderNodeList(String accountSharedId, String folderIdParent) {
        DmAccount accountShared = null;
        DmAccount accountOwner = null;

        if (accountSharedId != null) {
            accountShared = accountDao.getActive(accountSharedId);
        }

        if (accountShared != null) {
            return folderDao.getSharedChildFolderNodeList(accountSharedId, folderIdParent);
        }

        return null;
    }

    public List<DmFolderNode> getSharedFirstLevelFolderNodeList(String accountSharedId, String accountOwnerId) {
        DmAccount accountShared = null;
        DmAccount accountOwner = null;

        if (accountSharedId != null) {
            accountShared = accountDao.getActive(accountSharedId);
        }

        if (accountShared != null) {
            if (accountOwnerId != null) {
                accountOwner = accountDao.getActive(accountOwnerId);
            }
            if (accountOwner != null) {
                return folderDao.getSharedFirstLevelFolderNodeList(accountSharedId, accountOwnerId);
            }
        }

        return null;
    }

    public Integer getPermissionType(String folderId, String accountId) {
        DmAccount account = null;

        if (accountId != null) {
            account = accountDao.getActive(accountId);
        }

        if (account != null) {
            return folderPermissionDao.getPermissionType(folderId, accountId);
        }

        return null;
    }

    public boolean checkPermissionOnFolderModify(String folderId, String accountId) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = folderDao.findById(folderId);
        }

        DmAccount account = null;
        if (accountId != null) {
            account = accountDao.getActive(accountId);
        }

        if ((folder != null) && (account != null)) {
            DmFolder folderParent = null;
            if (folder != null) {
                folderParent = folder.getParent();
            }

            boolean isAllowModifyFolder = false;
            Integer folderPermissionType = null;            
            folderPermissionType = folderPermissionDao.getPermissionType(folderId, accountId);
            if ((folderPermissionType != null) && (folderPermissionType <= ApplicationConstants.FOLDER_PERMISSION_MANAGER)) {
                isAllowModifyFolder = true;
            }

            boolean isAllowModifyFolderParent = false;
            Integer folderParentPermissionType = null;
            if (folderParent != null) {
                folderParentPermissionType = folderPermissionDao.getPermissionType(folderParent.getId(), accountId);
                if ((folderParentPermissionType != null) && (folderParentPermissionType <= ApplicationConstants.FOLDER_PERMISSION_MANAGER)) {
                    isAllowModifyFolderParent = true;
                }
            }
            else {
                if (account.getId().equals(folder.getOwner().getId()) == true) {
                    isAllowModifyFolderParent = true;
                }
            }

            if (isAllowModifyFolder && isAllowModifyFolderParent) {
                return true;
            }
        }

        return false;
    }

    public boolean checkPermissionOnFolderDelete(String folderId, String accountId) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = folderDao.findById(folderId);
        }

        DmAccount account = null;
        if (accountId != null) {
            account = accountDao.getActive(accountId);
        }

        if ((folder != null) && (account != null)) {
            DmFolder folderParent = null;
            if (folder != null) {
                folderParent = folder.getParent();
            }

            boolean isAllowDeleteFolder = false;
            Integer folderPermissionType = null;
            folderPermissionType = folderPermissionDao.getPermissionType(folderId, accountId);
            if ((folderPermissionType != null) && (folderPermissionType <= ApplicationConstants.FOLDER_PERMISSION_MANAGER)) {
                isAllowDeleteFolder = true;
            }

            boolean isAllowDeleteFolderParent = false;
            Integer folderParentPermissionType = null;
            if (folderParent != null) {
                folderParentPermissionType = folderPermissionDao.getPermissionType(folderParent.getId(), accountId);
                if ((folderParentPermissionType != null) && (folderParentPermissionType <= ApplicationConstants.FOLDER_PERMISSION_MANAGER)) {
                    isAllowDeleteFolderParent = true;
                }
            }
            else {
                if (account.getId().equals(folder.getOwner().getId()) == true) {
                    isAllowDeleteFolderParent = true;
                }
            }

            if (isAllowDeleteFolder && isAllowDeleteFolderParent) {
                return true;
            }
        }

        return false;
    }

    public List<DmFolderNode> getChildFolderNodeList(String folderIdParent) {
        if (folderIdParent != null) {
            return folderDao.getChildFolderNodeList(folderIdParent);
        }
        return null;
    }

    public List<DmFolderNode> getFirstLevelFolderNodeList(String accountId) {
        DmAccount account = null;

        if (accountId != null) {
            account = accountDao.getActive(accountId);
        }

        if (account != null) {
            return folderDao.getFirstLevelFolderNodeList(accountId);
        }

        return null;
    }

    public DmFolder getHierarchy(String folderId) {
        DmFolder folder = folderDao.findById(folderId);
        
        if (folder != null) {
            // start - get ascendants
            DmFolder folderTmp = folder;
            while (folderTmp != null) {
//                System.out.print(" << " + folderTmp.getName());
                folderTmp = folderTmp.getParent();
            }
            // end - get ascendants

            // start - get children (trigger lazy fetch)
            if (folder.getChildList() != null) {
                folder.getChildList().size();
            }
            // end - get children (trigger lazy fetch)
        }        

        return folder;        
    }

    public DmFolder getParent(String folderId, String accountId) {
        return folderDao.getParent(folderId, accountId);
    }

    public void deleteRelatedByFolder(String folderId) {
        folderRelatedDao.deleteByFolder(folderId);
    }
    
    public void addRelated(String folderId, String folderIdListStr) {
        String folderIdRelatedList[] = null;
        if ((folderIdListStr != null) && (!folderIdListStr.isEmpty())) {
            folderIdRelatedList = folderIdListStr.split(",");
        }
        if (folderIdRelatedList != null) {
            DmFolder folder = new DmFolder();
            folder.setId(folderId);
            for (String folderIdRelated : folderIdRelatedList) {
                DmFolder folderRelated = new DmFolder();
                folderRelated.setId(folderIdRelated);

                DmFolderRelated dmFolderRelated = folderRelatedDao.getByFolderAndFolderRelated(folderId, folderIdRelated);
                if (dmFolderRelated == null) {
                    dmFolderRelated = new DmFolderRelated();
                    dmFolderRelated.setId(java.util.UUID.randomUUID().toString());
                    dmFolderRelated.setFolder(folder);
                    dmFolderRelated.setFolderRelated(folderRelated);
                    folderRelatedDao.save(dmFolderRelated);
                }
            }
        }        
    }

    public List<DmFolderRelated> getFolderRelatedList(String folderId, String accountId) {
        return folderRelatedDao.getFolderRelatedList(folderId, accountId);
    }

    public void deleteRelated(String folderId, String relatedFolderIdDelListStr) {
        folderRelatedDao.deleteRelated(folderId, relatedFolderIdDelListStr);
    }

    public void deleteRelatedByFolderRelated(String folderId) {
        folderRelatedDao.deleteByFolderRelated(folderId);
    }

    public boolean move(String folderId, String folderDestId, String accountDestId, String accountOperatorId) {
        // start - move folder
        DmFolder folderDest = folderDao.findById(folderDestId);
        DmAccount accountOperator = accountDao.get(accountOperatorId);
        if (accountOperator == null) {
            return false;
        }
        DmFolder folder = folderDao.findById(folderId);
        if (folder == null) {
            return false;
        }
        DmAccount accountDestination = accountDao.get(accountDestId);

        String folderOwnerIdOrig = folder.getOwner().getId();

        if (folderDest != null) {
            folder.setLevel(folderDest.getLevel().intValue() + 1);
            folder.setOwner(folderDest.getOwner());
        }
        else {
            folder.setLevel(1);
            if (accountDestination != null) {
                folder.setOwner(accountDestination);
            }
        }
        folder.setModifiedBy(accountOperator);
        folder.setParent(folderDest);
        // end - move folder

        // start - check if owner changes
        boolean isChangeOwner = false;
        String folderOwnerIdNew = null;
        if (folderDest != null) {
            if (!folderOwnerIdOrig.equals(folderDest.getOwner().getId())) {
                isChangeOwner = true;
                folderOwnerIdNew = folderDest.getOwner().getId();
            }
        }
        else {
            if (accountDestination != null) {
                if (!folderOwnerIdOrig.equals(accountDestination.getId())) {
                    isChangeOwner = true;
                    folderOwnerIdNew = accountDestination.getId();
                }
            }
        }
        // end - check if owner changes

        // start - delete related folders if owner changes
        if (isChangeOwner) {
            folderRelatedDao.deleteByFolder(folderId);
        }
        // end - delete related folders if owner changes

        // start - list containing descendants' folder id
        List<String> descendantFolderIdList = folderDescendantDao.getDescendantIdList(folderId);
        // end - list containing descendants' folder id

        // start - update owner property of descendant folders
        if ((descendantFolderIdList != null) && (descendantFolderIdList.size() > 0)) {
            if (folderDest != null) {
                if (isChangeOwner) {
                    folderDao.updateOwnerByFolderIdList(descendantFolderIdList, folderDest.getOwner().getId());
                }
            }
            else {
                if ((isChangeOwner) && (accountDestination != null)) {
                    folderDao.updateOwnerByFolderIdList(descendantFolderIdList, accountDestination.getId());
                }
            }
        }
        // end - update owner property of descendant folders

        // start - list containing folder id and descendants' folder id
        List<String> folderAndDescendantFolderIdToUpdateList = descendantFolderIdList;
        if (folderAndDescendantFolderIdToUpdateList == null) {
            folderAndDescendantFolderIdToUpdateList = new ArrayList<String>();
        }
        folderAndDescendantFolderIdToUpdateList.add(folder.getId());
        // end - list containing folder id and descendants' folder id

        // start - modify folder descendants: for folder and its descendants
        // // start - folder's current ascendants
        List<String> folderCurrentAscendantIdList = folderDescendantDao.getAscendantIdList(folderId);
        // // end - folder's current ascendants
        // // start - folder's new ascendants
        List<String> folderNewAscendantIdList = null;
        if (folderDest != null) {
            folderNewAscendantIdList = folderDescendantDao.getAscendantIdList(folderDest.getId());
            if (folderNewAscendantIdList == null) {
                folderNewAscendantIdList = new ArrayList<String>();
            }
            folderNewAscendantIdList.add(folderDest.getId());
        }
        // // end - folder's new ascendants

        // // test: start -
//        if (folderCurrentAscendantIdList != null) {
//            for (String folderCurrentAscendantId : folderCurrentAscendantIdList) {
//                System.out.println("folderCurrentAscendantId: " + folderCurrentAscendantId);
//            }
//        }
//        if (folderNewAscendantIdList != null) {
//            for (String folderNewAscendantId : folderNewAscendantIdList) {
//                System.out.println("folderNewAscendantId: " + folderNewAscendantId);
//            }
//        }
        // // test: end -

        // // start - ascendants that should be removed
        List<String> ascendantIdToRemoveList = new ArrayList<String>();
        if (folderCurrentAscendantIdList != null) {
            for (String folderCurrentAscendantId : folderCurrentAscendantIdList) {
                if (folderNewAscendantIdList == null) {
                    ascendantIdToRemoveList.add(folderCurrentAscendantId);
                }
                else {
                    if (folderNewAscendantIdList.indexOf(folderCurrentAscendantId) == -1) {
                        ascendantIdToRemoveList.add(folderCurrentAscendantId);
                    }
                }
            }
        }
        // // end - ascendants records that should be removed
        // // start - ascendants records that should be added
        List<String> ascendantIdToAddList = new ArrayList<String>();
        if (folderNewAscendantIdList != null) {
            for (String folderNewAscendantId : folderNewAscendantIdList) {
                if (folderCurrentAscendantIdList == null) {
                    ascendantIdToAddList.add(folderNewAscendantId);
                }
                else {
                    if (folderCurrentAscendantIdList.indexOf(folderNewAscendantId) == -1) {
                        ascendantIdToAddList.add(folderNewAscendantId);
                    }
                }
            }
        }
        // // end - ascendants records that should be added

        // // test: start -
//        if (ascendantIdToRemoveList != null) {
//            for (String ascendantIdToRemove : ascendantIdToRemoveList) {
//                System.out.println("ascendantIdToRemove: " + ascendantIdToRemove);
//            }
//        }
//        if (ascendantIdToAddList != null) {
//            for (String ascendantIdToAdd : ascendantIdToAddList) {
//                System.out.println("ascendantIdToAdd: " + ascendantIdToAdd);
//            }
//        }
        // // test: end -
        
        // // start - delete folder descendant records
        folderDescendantDao.deleteByFoldersByDescendants(folderAndDescendantFolderIdToUpdateList, ascendantIdToRemoveList);
        // // end - delete folder descendant records

        // // start - insert folder descendant records
        if (folderAndDescendantFolderIdToUpdateList != null) {
            for (String folderAndDescendantFolderIdToUpdate : folderAndDescendantFolderIdToUpdateList) {
                if (ascendantIdToAddList != null) {
                    for (String ascendantIdToAdd : ascendantIdToAddList) {
                        DmFolder folderTmp = new DmFolder();
                        folderTmp.setId(ascendantIdToAdd);

                        DmFolder folderDescTmp = new DmFolder();
                        folderDescTmp.setId(folderAndDescendantFolderIdToUpdate);

                        DmFolderDescendant folderDescendantTmp = new DmFolderDescendant();
                        folderDescendantTmp.setId(java.util.UUID.randomUUID().toString());
                        folderDescendantTmp.setFolder(folderTmp);
                        folderDescendantTmp.setFolderDescendant(folderDescTmp);
                        folderDescendantDao.save(folderDescendantTmp);
                    }
                }
            }
        }
        // // end - insert folder descendant records
        // end - modify folder descendants: for folder and its descendants

        // start - modify folder permission if owner changes
        if (isChangeOwner) {
            folderPermissionDao.deleteByFolderList(folderAndDescendantFolderIdToUpdateList);
            if (folderAndDescendantFolderIdToUpdateList != null) {
                DmAccount accountTmp = new DmAccount();
                accountTmp.setId(folderOwnerIdNew);
                for (String folderAndDescendantFolderIdToUpdate : folderAndDescendantFolderIdToUpdateList) {
                    DmFolder folderTmp = new DmFolder();
                    folderTmp.setId(folderAndDescendantFolderIdToUpdate);

                    DmFolderPermission folderPermission = new DmFolderPermission();
                    folderPermission.setId(java.util.UUID.randomUUID().toString());
                    folderPermission.setPermissionType(ApplicationConstants.FOLDER_PERMISSION_MANAGER);
                    folderPermission.setAccount(accountTmp);
                    folderPermission.setFolder(folderTmp);
                    folderPermissionDao.save(folderPermission);
                }
            }
        }
        // end - modify folder permission if owner changes

        // start - handle reindexing of documents
        // TODO later: when indexing of document includes ascendant folders information,
        // reindex even when owner is the same account
        if (isChangeOwner) {
            System.out.println("set reindex by folder list");
            if (folderAndDescendantFolderIdToUpdateList != null) {
                for (String folderAndDescendantFolderIdToUpdate : folderAndDescendantFolderIdToUpdateList) {
                    System.out.println("folder id: " + folderAndDescendantFolderIdToUpdate);
                }
            }
            documentIndexedDao.setReindexByFolderList(folderAndDescendantFolderIdToUpdateList);
        }
        // end - handle reindexing of documents

        return true;
    }

    public List<DmFolder> getAscendantOrderedList(String folderId) {
        return folderDescendantDao.getAscendantOrderedList(folderId);
    }
}
