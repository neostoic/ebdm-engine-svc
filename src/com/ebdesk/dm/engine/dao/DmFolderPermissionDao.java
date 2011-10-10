/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmAccount;
import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderPermission;;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmFolderPermissionDao")
public class DmFolderPermissionDao extends BaseDmEngineDaoImpl<DmFolderPermission> {
    public DmFolderPermissionDao() {
        this.t = new DmFolderPermission();
    }

    public void deleteByFolder(String folderId) {
        String deleteQuery = "delete from"
                + " dm_folder_permission"
                + " WHERE"
                + " df_id = :folderId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("folderId", folderId);

        query.executeUpdate();
    }

    public List<DmFolderPermission> getByFolderExcludeByAccount(String folderId, String accountId, Integer permissionLevelStart) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = new DmFolder();
            folder.setId(folderId);
        }

        DmAccount account = null;
        if (accountId != null) {
            account = new DmAccount();
            account.setId(accountId);
        }
        
        Criteria crit = getSession().createCriteria(DmFolderPermission.class);
        crit.add(Restrictions.eq("folder", folder));
        if (account != null) {
            crit.add(Restrictions.ne("account", account));
        }
        if (permissionLevelStart != null) {
            crit.add(Restrictions.ge("permissionType", permissionLevelStart));
        }
        crit.addOrder(Order.asc("permissionType"));
        crit.createCriteria("account").add(Restrictions.eq("isActive", true));
//        crit.createAlias("availableSizes","size").add(Expression.gt("size.number", new Integer(40)))


        return crit.list();
    }

    public DmFolderPermission getByFolderByAccount(String folderId, String accountId) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = new DmFolder();
            folder.setId(folderId);
        }

        DmAccount account = null;
        if (accountId != null) {
            account = new DmAccount();
            account.setId(accountId);
        }

        Criteria crit = getSession().createCriteria(DmFolderPermission.class);
        crit.add(Restrictions.eq("folder", folder));
        crit.add(Restrictions.eq("account", account));

        return (DmFolderPermission) crit.uniqueResult();
    }

    public void deleteByFolderByAccount(String folderId, String accountId) {
        DmFolderPermission folderPermission = getByFolderByAccount(folderId, accountId);
        if (folderPermission != null) {
            this.delete(folderPermission);
            this.getSession().flush();
        }
    }

    public Integer getPermissionType(String folderId, String accountId) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = new DmFolder();
            folder.setId(folderId);
        }

        DmAccount account = null;
        if (accountId != null) {
            account = new DmAccount();
            account.setId(accountId);
        }

        Criteria crit = getSession().createCriteria(DmFolderPermission.class);
        crit.setProjection(Projections.property("permissionType"));
        crit.add(Restrictions.eq("folder", folder));
        crit.add(Restrictions.eq("account", account));

        return (Integer) crit.uniqueResult();
    }
	
    /****
     * Get high-level account permission for folder.
     * @param accountId account identifier to checked.
     * @param folderId folder identifier.
     * @return permission. 1 = OWNER, 2 = WRITER, 3 = CONSUMER, 4 = READER, 0 = NO PERMISSION
     */
    public Integer findUserFolderPermission(String accountId, String folderId) {
        StringBuilder hql = new StringBuilder("SELECT ");
        hql.append("fp.permissionType ");
        hql.append("FROM ").append(DmFolderPermission.class.getName()).append(" AS fp ");
        hql.append("WHERE fp.folder.id = :folderId AND fp.account.id = :accountId ");
        hql.append("ORDER BY fp.permissionType ASC");
        Query query = getSession().createQuery(hql.toString());
        query.setString("accountId", accountId);
        query.setString("folderId", folderId);
        List<Integer> integers = query.list();
        if (integers.size() > 0) {
            return (Integer) integers.get(0);
        }
        return 0;
    }

    public void deleteByFolderList(List<String> folderIdList) {
        if ((folderIdList == null) || (folderIdList.size() == 0)) {
            return;
        }

        String deleteQuery = "delete from"
                + " dm_folder_permission"
                + " where"
                + " (1 = 1)"
                ;

        deleteQuery = deleteQuery + " and (";
        int i = 0;
        for (String folderId : folderIdList) {
            if (i > 0) {
                deleteQuery = deleteQuery + " or";
            }
            deleteQuery = deleteQuery + " (df_id = :folderId" + i + ")";
            i++;
        }
        deleteQuery = deleteQuery + ")";

        Query query = getSession().createSQLQuery(deleteQuery);

        i = 0;
        for (String folderId : folderIdList) {
            query.setString("folderId" + i, folderId);
            i++;
        }

        query.executeUpdate();

        flush();
    }
}
