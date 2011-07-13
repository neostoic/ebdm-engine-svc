/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmAccount;
import com.ebdesk.dm.engine.domain.DmFolder;
import org.springframework.stereotype.Repository;
import com.ebdesk.dm.engine.domain.DmFolderRelated;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author user
 */
@Repository("dmFolderRelatedDao")
public class DmFolderRelatedDao extends BaseDmEngineDaoImpl<DmFolderRelated> {
    public DmFolderRelatedDao() {
        this.t = new DmFolderRelated();
    }

    public void deleteByFolder(String folderId) {
        String deleteQuery = "delete from"
                + " dm_folder_related"
                + " WHERE"
                + " df_id = :folderId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("folderId", folderId);

        query.executeUpdate();        
    }

    public DmFolderRelated getByFolderAndFolderRelated(String folderId, String folderIdRelated) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = new DmFolder();
            folder.setId(folderId);
        }

        DmFolder folderRelated = null;
        if (folderIdRelated != null) {
            folderRelated = new DmFolder();
            folderRelated.setId(folderIdRelated);
        }

        Criteria crit = getSession().createCriteria(DmFolderRelated.class);
        crit.add(Restrictions.eq("folder", folder));
        crit.add(Restrictions.eq("folderRelated", folderRelated));

        return (DmFolderRelated) crit.uniqueResult();

    }

    public List<DmFolderRelated> getFolderRelatedList(String folderId, String accountId) {
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

        Criteria crit = getSession().createCriteria(DmFolderRelated.class);
        crit.add(Restrictions.eq("folder", folder));
        crit.createAlias("folderRelated", "folderRelated");
        crit.createAlias("folderRelated.permissionList", "permissions");
        crit.add(Restrictions.eq("permissions.account", account));
        crit.addOrder(Order.asc("folderRelated.name"));
        return crit.list();
    }

    public void deleteRelated(String folderId, String relatedFolderIdDelListStr) {
        String deleteQuery = "delete from"
                + " dm_folder_related"
                + " WHERE"
                + " df_id = :folderId"
                ;

        String relatedFolderIdDelList[] = null;
        if ((relatedFolderIdDelListStr != null) && (!relatedFolderIdDelListStr.isEmpty())) {
            relatedFolderIdDelList = relatedFolderIdDelListStr.split(",");

            deleteQuery = deleteQuery + " AND (";
            int i = 0;
            for (String relatedFolderIdDel : relatedFolderIdDelList) {
                if (i > 0) {
                    deleteQuery = deleteQuery + " OR ";
                }
                deleteQuery = deleteQuery + " (df_id_related = :folderIdRelated" + i + ")";
                i++;
            }
            deleteQuery = deleteQuery + ")";

            Query query = getSession().createSQLQuery(deleteQuery);

            query.setString("folderId", folderId);
            i = 0;
            for (String relatedFolderIdDel : relatedFolderIdDelList) {
                query.setString("folderIdRelated" + i, relatedFolderIdDel);
                i++;
            }

            query.executeUpdate();
        }
    }

    public void deleteByFolderRelated(String folderId) {
        String deleteQuery = "delete from"
                + " dm_folder_related"
                + " WHERE"
                + " df_id_related = :folderId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("folderId", folderId);

        query.executeUpdate();        
    }
    
//    public void addRelated(String folderId, String folderIdList) {
//
//    }
}
