/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderKeyword;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository("dmFolderKeywordDao")
public class DmFolderKeywordDao extends BaseDmEngineDaoImpl<DmFolderKeyword> {

    public DmFolderKeywordDao() {
        this.t = new DmFolderKeyword();
    }

    public void deleteByFolder(String folderId) {
        String deleteQuery = "delete from"
                + " dm_folder_keyword"
                + " WHERE"
                + " df_id = :folderId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("folderId", folderId);

        query.executeUpdate();

    }

    public List<DmFolderKeyword> getListByFolder(String folderId) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = new DmFolder();
            folder.setId(folderId);
        }
        Criteria crit = getSession().createCriteria(DmFolderKeyword.class);
        crit.add(Restrictions.eq("folder", folder));
        crit.addOrder(Order.asc("rank"));

        return crit.list();
    }
}
