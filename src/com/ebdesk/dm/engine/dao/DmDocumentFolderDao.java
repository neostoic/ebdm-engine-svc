/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.constant.DatabaseConstants;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentFolder;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmDocumentFolderDao")
public class DmDocumentFolderDao extends BaseDmEngineDaoImpl<DmDocumentFolder> {
    
    /***
     * 
     */
    public int deleteByDocIdAndFolderId(String folderId, String docId) {
        String hql = "DELETE FROM " + DmDocumentFolder.class.getName() + " a WHERE a.document.id = :docId AND a.folder.id = :folderId";
        Query query = getSession().createQuery(hql);
        query.setString("folderId", folderId);
        query.setString("docId", docId);
        return query.executeUpdate();
    }

    public List<DmDocument> findDocumentByFolderId(String folderId, int start, int max,
            String orderBy, String order) {
        StringBuilder sb = new StringBuilder("SELECT ")
                .append("d.id AS id ")
                .append(",d.title AS title ")
                .append(",d.description AS description ")
                .append(",d.createdTime AS createdTime ")
                .append(",d.lastModifiedTime AS lastModifiedTime ")
                .append(",d.mimeType AS mimeType ")
                .append(",d.isRemoved AS isRemoved ")
                .append(",d.createdBy AS createdBy ")
                .append("FROM ").append(DmDocumentFolder.class.getName()).append(" AS df ")
                .append("LEFT JOIN df.document AS d ")
                .append("LEFT JOIN df.folder AS f ")
                .append("WHERE f.id = :folderId AND d.isRemoved = :isRemoved ");

        if (orderBy != null && !"".equals(orderBy)) {
            if (DatabaseConstants.ORDER_DESC.equals(order)) {
                sb.append("ORDER BY d.").append(orderBy).append(" ").append(order).append(", d.id ASC");
            } else {
                sb.append("ORDER BY d.").append(orderBy).append(" ").append(DatabaseConstants.ORDER_ASC).append(", d.id ASC");
            }
        } else {
            sb.append("ORDER BY d.id ASC");
        }


        Query query = getSession().createQuery(sb.toString());
        query.setString("folderId", folderId);
        query.setBoolean("isRemoved", false);
        query.setFirstResult(start);
        query.setMaxResults(max);

        query.setResultTransformer(Transformers.aliasToBean(DmDocument.class));

        return query.list();
    }

    public Integer countDocumentByFolderId(String folderId) {
        Criteria crit = getSession().createCriteria(DmDocumentFolder.class, "df");
        crit.add(Restrictions.eq("df.folder.id", folderId));
        crit.setProjection(Projections.rowCount());

        List result = crit.list();
        Integer rowCount = new Integer(0);
        if (result.size() > 0) {
            rowCount = (Integer) result.get(0);            
        }
        return rowCount;
    }

    public DmDocumentFolder getByDocument(String documentId) {
        Criteria crit = getSession().createCriteria(DmDocumentFolder.class, "df");
        crit.add(Restrictions.eq("df.document.id", documentId));
        List result = crit.list();

        if (result.size() > 0) {
            return (DmDocumentFolder) result.get(0);
        }
        return null;
    }
}
