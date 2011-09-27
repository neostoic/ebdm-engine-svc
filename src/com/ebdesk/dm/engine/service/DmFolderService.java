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

/**
 *
 * @author user
 */
public interface DmFolderService {
    public boolean save(DmFolder folder);
    public boolean create(DmFolder folder);
    public DmFolder get(String folderId);
    public boolean update(DmFolder folder);
    public boolean delete(DmFolder folder);
    public List<DmFolder> getChildList(String parentId);
    public int getNumDocuments(String folderId);
    public DmFolder getByName(String folderName, String accountId, String folderIdParent);
    public DmFolder getByNameExcludeById(String folderName, String folderId, String accountId, String folderIdParent);
    public boolean savePermission(DmFolderPermission folderPermission);
    public boolean deletePermissionByFolder(String folderId);
    public List<DmFolderPermission> getSharedAccountList(String folderId);
    public void addPermission(String folderId, String addAccManagerList, String addAccWriterList, String addAccConsumerList, String addAccViewerList);
    public void deletePermission(String folderId, String deleteAccList);
    public void updatePermission(String folderId, String manageAccList, String writeAccList, String consumeAccList, String viewAccList);

    public List<DmFolder> getFirstLevelList(String accountId);
    public List<DmFolder> getFirstLevelList();

    public List<String> getListShareOwnerIdByAccount(String accountId);
    public List<DmFolder> getSharedFirstLevelList(String accountSharedId, String accountOwnerId);
    public List<DmFolder> getSharedChildList(String accountSharedId, String folderIdParent);
    public List<DmFolderNode> getSharedChildFolderNodeList(String accountSharedId, String folderIdParent);
    public List<DmFolderNode> getSharedFirstLevelFolderNodeList(String accountSharedId, String accountOwnerId);

    public List<DmFolderNode> getChildFolderNodeList(String folderIdParent);
    public List<DmFolderNode> getFirstLevelFolderNodeList(String accountId);

    public Integer getPermissionType(String folderId, String accountId);
    public boolean checkPermissionOnFolderModify(String folderId, String accountId);
    public boolean checkPermissionOnFolderDelete(String folderId, String accountId);

    public DmFolder getHierarchy(String folderId);
    public DmFolder getParent(String folderId, String accountId);

    public void deleteRelatedByFolder(String folderId);
    public void deleteRelatedByFolderRelated(String folderId);
    public void addRelated(String folderId, String folderIdList);

    public List<DmFolderRelated> getFolderRelatedList(String folderId, String accountId);
    public void deleteRelated(String folderId, String relatedFolderIdDelListStr);

    public boolean move(String folderId, String folderDestId, String accountDestId, String accountOperatorId);
}
