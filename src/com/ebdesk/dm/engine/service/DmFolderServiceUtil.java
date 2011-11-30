/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderPermission;
import com.ebdesk.dm.engine.domain.DmFolderRelated;
import com.ebdesk.dm.engine.dto.DmFolderNode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DmFolderServiceUtil {

    private static DmFolderService folderService;

    public static DmFolderService getFolderService() {
        if (folderService == null) {
            throw new RuntimeException(DmFolderService.class + " isn't initialized. ");
        }
        return folderService;
    }

    @Autowired(required=true)
    public void setFolderService(DmFolderService folderService) {
        DmFolderServiceUtil.folderService = folderService;
    }

    public static boolean save(DmFolder folder) {
        return getFolderService().save(folder);
    }

    public static DmFolder get(String folderId) {
        return getFolderService().get(folderId);
    }

    public static boolean update(DmFolder folder) {
        return getFolderService().update(folder);
    }

    public static boolean delete(DmFolder folder) {
        return getFolderService().delete(folder);
    }

    public static List<DmFolder> getChildList(String parentId) {
        return getFolderService().getChildList(parentId);
    }

    public static int getNumDocuments(String folderId) {
        return getFolderService().getNumDocuments(folderId);
    }

    public static DmFolder getByName(String folderName, String accountId, String folderIdParent) {
        return getFolderService().getByName(folderName, accountId, folderIdParent);
    }

    public static DmFolder getByNameExcludeById(String folderName, String folderId, String accountId, String folderIdParent) {
        return getFolderService().getByNameExcludeById(folderName, folderId, accountId, folderIdParent);
    }

    public static boolean savePermission(DmFolderPermission folderPermission) {
        return getFolderService().savePermission(folderPermission);
    }

    public static boolean deletePermissionByFolder(String folderId) {
        return getFolderService().deletePermissionByFolder(folderId);
    }

    public static List<DmFolderPermission> getSharedAccountList(String folderId) {
        return getFolderService().getSharedAccountList(folderId);
    }

    public static void addPermission(String folderId, String addAccManagerList, String addAccWriterList, String addAccConsumerList, String addAccViewerList) {
        getFolderService().addPermission(folderId, addAccManagerList, addAccWriterList, addAccConsumerList, addAccViewerList);
    }

    public static void deletePermission(String folderId, String deleteAccList) {
        getFolderService().deletePermission(folderId, deleteAccList);
    }

    public static void updatePermission(String folderId, String manageAccList, String writeAccList, String consumeAccList, String viewAccList) {
        getFolderService().updatePermission(folderId, manageAccList, writeAccList, consumeAccList, viewAccList);
    }

    public static boolean create(DmFolder folder) {
        return getFolderService().create(folder);
    }

    public static List<DmFolder> getFirstLevelList(String accountId) {
        return getFolderService().getFirstLevelList(accountId);
    }

    public static List<DmFolder> getFirstLevelList() {
        return getFolderService().getFirstLevelList();
    }    

    public static List<String> getListShareOwnerIdByAccount(String accountId) {
        return getFolderService().getListShareOwnerIdByAccount(accountId);
    }

    public static List<DmFolder> getSharedFirstLevelList(String accountSharedId, String accountOwnerId) {
        return getFolderService().getSharedFirstLevelList(accountSharedId, accountOwnerId);
    }

    public static List<DmFolder> getSharedChildList(String accountSharedId, String folderIdParent) {
        return getFolderService().getSharedChildList(accountSharedId, folderIdParent);
    }

    public static List<DmFolderNode> getSharedChildFolderNodeList(String accountSharedId, String folderIdParent) {
        return getFolderService().getSharedChildFolderNodeList(accountSharedId, folderIdParent);
    }

    public static List<DmFolderNode> getSharedFirstLevelFolderNodeList(String accountSharedId, String accountOwnerId) {
        return getFolderService().getSharedFirstLevelFolderNodeList(accountSharedId, accountOwnerId);
    }

    public static Integer getPermissionType(String folderId, String accountId) {
        return getFolderService().getPermissionType(folderId, accountId);
    }

    public static boolean checkPermissionOnFolderModify(String folderId, String accountId) {
        return getFolderService().checkPermissionOnFolderModify(folderId, accountId);
    }

    public static boolean checkPermissionOnFolderDelete(String folderId, String accountId) {
        return getFolderService().checkPermissionOnFolderDelete(folderId, accountId);
    }

    public static List<DmFolderNode> getChildFolderNodeList(String folderIdParent) {
        return getFolderService().getChildFolderNodeList(folderIdParent);
    }

    public static List<DmFolderNode> getFirstLevelFolderNodeList(String accountId) {
        return getFolderService().getFirstLevelFolderNodeList(accountId);
    }

    public static DmFolder getHierarchy(String folderId) {
        return getFolderService().getHierarchy(folderId);
    }

    public static DmFolder getParent(String folderId, String accountId) {
        return getFolderService().getParent(folderId, accountId);
    }

    public static void deleteRelatedByFolder(String folderId) {
        getFolderService().deleteRelatedByFolder(folderId);
    }

    public static void addRelated(String folderId, String folderIdList) {
        getFolderService().addRelated(folderId, folderIdList);
    }

    public static List<DmFolderRelated> getFolderRelatedList(String folderId, String accountId) {
        return getFolderService().getFolderRelatedList(folderId, accountId);
    }

    public static void deleteRelated(String folderId, String relatedFolderIdDelListStr) {
        getFolderService().deleteRelated(folderId, relatedFolderIdDelListStr);
    }

    public static void deleteRelatedByFolderRelated(String folderId) {
        getFolderService().deleteRelatedByFolderRelated(folderId);
    }

    public static boolean move(String folderId, String folderDestId, String accountDestId, String accountOperatorId) {
        return getFolderService().move(folderId, folderDestId, accountDestId, accountOperatorId);
    }

    public static List<DmFolder> getAscendantOrderedList(String folderId) {
        return getFolderService().getAscendantOrderedList(folderId);
    }

    public static List<String> getTopTerm(String folderId, int numTerm) {
        return getFolderService().getTopTerm(folderId, numTerm);
    }

    public static List<String> getTopTermInclSubFolders(String folderId, int numTerm, String accountId) {
        return getFolderService().getTopTermInclSubFolders(folderId, numTerm, accountId);
    }
}
