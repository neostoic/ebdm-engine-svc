/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderDescendant;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository("dmFolderDescendantDao")
public class DmFolderDescendantDao extends BaseDmEngineDaoImpl<DmFolderDescendant> {

    public DmFolderDescendantDao() {
        this.t = new DmFolderDescendant();
    }

    public boolean clearAll() {
        String deleteQuery = "delete from"
                + " dm_folder_descendant"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.executeUpdate();

        flush();
        
        return true;
    }

    public boolean deleteByDescendant(String folderId) {
        String deleteQuery = "delete from"
                + " dm_folder_descendant"
                + " WHERE"
                + " df_id_descendant = :folderId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("folderId", folderId);
        
        query.executeUpdate();

        flush();
        
        return true;
    }

    public List<DmFolder> getAscendantList(String folderId) {
        Criteria crit = getSession().createCriteria(DmFolderDescendant.class);
        crit.add(Restrictions.eq("folderDescendant.id", (folderId != null) ? folderId : ""));
//        crit.createAlias("folder", "folderInfo");
        crit.setProjection(Projections.property("folder"));
//        crit.createAlias("folder", "folderDescendant.folder");
//        crit.createAlias("folderParent.permissionList", "permissions");
//        crit.add(Restrictions.eq("folderDescendant.id", folderId));
        
        return crit.list();
//        return null;
    }

    public List<DmFolder> getDescendantList(String folderId) {
        Criteria crit = getSession().createCriteria(DmFolderDescendant.class);
        crit.add(Restrictions.eq("folder.id", (folderId != null) ? folderId : ""));
        crit.setProjection(Projections.property("folderDescendant"));

        return crit.list();
    }

    public List<String> getAscendantIdList(String folderId) {
        Criteria crit = getSession().createCriteria(DmFolderDescendant.class);
        crit.add(Restrictions.eq("folderDescendant.id", (folderId != null) ? folderId : ""));
//        crit.createAlias("folder", "folderInfo");
        crit.setProjection(Projections.property("folder.id"));
//        crit.createAlias("folder", "folderDescendant.folder");
//        crit.createAlias("folderParent.permissionList", "permissions");
//        crit.add(Restrictions.eq("folderDescendant.id", folderId));

        return crit.list();
//        return null;
    }

    public List<String> getDescendantIdList(String folderId) {
        Criteria crit = getSession().createCriteria(DmFolderDescendant.class);
        crit.add(Restrictions.eq("folder.id", (folderId != null) ? folderId : ""));
//        crit.createAlias("folder", "folderInfo");
        crit.setProjection(Projections.property("folderDescendant.id"));
//        crit.createAlias("folder", "folderDescendant.folder");
//        crit.createAlias("folderParent.permissionList", "permissions");
//        crit.add(Restrictions.eq("folderDescendant.id", folderId));

        return crit.list();
    }

    public List<DmFolder> getAscendantOrderedList(String folderId) {
        Criteria crit = getSession().createCriteria(DmFolderDescendant.class);
        crit.add(Restrictions.eq("folderDescendant.id", (folderId != null) ? folderId : ""));
        crit.createAlias("folder", "folderInfo");
        crit.setProjection(Projections.property("folder"));
        crit.addOrder(Order.asc("folderInfo.level"));
//        crit.createAlias("folder", "folderDescendant.folder");
//        crit.createAlias("folderParent.permissionList", "permissions");
//        crit.add(Restrictions.eq("folderDescendant.id", folderId));

        return crit.list();
//        return null;
    }

    public void deleteByFoldersByDescendants(List<String> folderAndDescendantFolderIdToUpdateList, List<String> ascendantIdToRemoveList) {
        if ((ascendantIdToRemoveList == null) || (ascendantIdToRemoveList.size() == 0)) {
            return;
        }

        if ((folderAndDescendantFolderIdToUpdateList == null) || (folderAndDescendantFolderIdToUpdateList.size() == 0)) {
            return;
        }
        
        String deleteQuery = "delete from"
                + " dm_folder_descendant"
                + " where"
                + " (1 = 1)"
                ;

//        if ((folderAndDescendantFolderIdToUpdateList != null) && (folderAndDescendantFolderIdToUpdateList.size() > 0)) {
            deleteQuery = deleteQuery + " and (";
            int i = 0;
            for (String folderAndDescendantFolderIdToUpdate : folderAndDescendantFolderIdToUpdateList) {
                if (i > 0) {
                    deleteQuery = deleteQuery + " or";
                }
                deleteQuery = deleteQuery + " (df_id_descendant = :folderIdDesc" + i + ")";
                i++;
            }
            deleteQuery = deleteQuery + ")";
//        }

//        if ((ascendantIdToRemoveList != null) && (ascendantIdToRemoveList.size() > 0)) {
            deleteQuery = deleteQuery + " and (";
            i = 0;
            for (String ascendantIdToRemove : ascendantIdToRemoveList) {
                if (i > 0) {
                    deleteQuery = deleteQuery + " or";
                }
                deleteQuery = deleteQuery + " (df_id = :folderId" + i + ")";
                i++;
            }
            deleteQuery = deleteQuery + ")";
//        }

        Query query = getSession().createSQLQuery(deleteQuery);

        if ((folderAndDescendantFolderIdToUpdateList != null) && (folderAndDescendantFolderIdToUpdateList.size() > 0)) {
            i = 0;
            for (String folderAndDescendantFolderIdToUpdate : folderAndDescendantFolderIdToUpdateList) {
                query.setString("folderIdDesc" + i, folderAndDescendantFolderIdToUpdate);
                i++;
            }
        }
        if ((ascendantIdToRemoveList != null) && (ascendantIdToRemoveList.size() > 0)) {
            i = 0;
            for (String ascendantIdToRemove : ascendantIdToRemoveList) {
                query.setString("folderId" + i, ascendantIdToRemove);
                i++;
            }
        }
        
        query.executeUpdate();

        flush();
    }
}
