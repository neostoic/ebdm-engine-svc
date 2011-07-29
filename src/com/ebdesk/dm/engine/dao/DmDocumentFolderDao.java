/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.constant.DatabaseConstants;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentFolder;
import com.ebdesk.dm.engine.domain.util.ApprovalStatus;
import com.ebdesk.dm.engine.domain.util.FolderPermission;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmDocumentFolderDao")
public class DmDocumentFolderDao extends BaseDmEngineDaoImpl<DmDocumentFolder> {

    @Autowired
    private DmDocumentApprovalDao documentApprovalDao;
    
    @Autowired
    private DmDocumentVersionDao documentVersionDao;
    
    
    public DmDocumentFolderDao() {
        this.t = new DmDocumentFolder();
    }
    
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
                .append(",d.approved AS approved ")
                .append(",d.lastVersion AS lastVersion ")
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

    /****
     * Use this method when the folder is need an approval.
     * @param accountId
     * @param folderId
     * @param start
     * @param max
     * @return 
     */
    public List<DmDocument> findDocumentByAccount(String accountId, String folderId, int permission, int start, int max, String orderBy, String order) {
        StringBuilder sb = new StringBuilder("SELECT ")
                .append("d.id AS id ")
                .append(",d.title AS title ")
                .append(",d.description AS description ")
                .append(",d.createdTime AS createdTime ")
                .append(",d.lastModifiedTime AS lastModifiedTime ")
                .append(",d.mimeType AS mimeType ")
                .append(",d.isRemoved AS isRemoved ")
                .append(",d.lastVersion AS lastVersion ")
                .append(",d.approved AS approved ")
                .append(",d.createdBy AS createdBy ")
                .append("FROM ").append(DmDocumentFolder.class.getName()).append(" AS df ")
                .append("LEFT JOIN df.document AS d ")
                .append("LEFT JOIN d.approval AS a ")
                .append("LEFT JOIN df.folder AS f ")                
                .append("WHERE f.id = :folderId AND d.isRemoved = :isRemoved AND  ")
                .append("(((f.isNeedApproval = false ) AND (a.status != 3 OR d.approved = :approved)) OR "
                + "( (f.isNeedApproval = true) AND ((d.approved = :approved) "
                + "OR (d.createdBy.id = :accountId) OR (a.status = :status AND 1 = :permission) ))) ");
        
        if (orderBy != null && !"".equals(orderBy)) {
            if (DatabaseConstants.ORDER_DESC.equals(order)) {
                sb.append("ORDER BY d.").append(orderBy).append(" ").append(order).append(", d.id ASC");
            } else {
                sb.append("ORDER BY d.").append(orderBy).append(" ").append(DatabaseConstants.ORDER_ASC).append(", d.id ASC");
            }
        } else {
            sb.append("ORDER BY d.createdTime DESC");
        }

        Query query = getSession().createQuery(sb.toString());
        query.setString("folderId", folderId);
        query.setBoolean("isRemoved", false);
        query.setBoolean("approved", Boolean.TRUE);
        query.setString("accountId", accountId);
        query.setInteger("status", ApprovalStatus.REQUESTED.getStatus());
        query.setInteger("permission", permission);
        query.setFirstResult(start);
        query.setMaxResults(max);
        
        query.setResultTransformer(Transformers.aliasToBean(DmDocument.class));
        
        List<DmDocument> documents = query.list();
        for (DmDocument dmDocument : documents) {
            dmDocument.setApproval(documentApprovalDao.findByDocId(dmDocument.getId()));
            dmDocument.setRequestedVersion(documentVersionDao.findRequestedVersionByDocId(dmDocument.getId()));            
        }
        
        return documents;
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
    
    /*
    public Integer countDocumentByAccount(String accountId, String folderId, int permission){       
        
        Criteria crit = getSession().createCriteria(DmDocumentFolder.class.getName(), "df");
        crit.createAlias("df.document", "d", CriteriaSpecification.LEFT_JOIN);
        crit.createAlias("d.approval", "a", CriteriaSpecification.LEFT_JOIN);
        crit.createAlias("df.folder", "f", CriteriaSpecification.LEFT_JOIN);
        crit.createAlias("f.permissionList", "p", CriteriaSpecification.LEFT_JOIN);
        Conjunction mainCondition = Restrictions.conjunction();
        mainCondition.add(Restrictions.eq("f.id", folderId));
        mainCondition.add(Restrictions.eq("d.isRemoved", false));
        
        Disjunction dis0 = Restrictions.disjunction();
        dis0.add(Restrictions.eq("f.isNeedApproval", false));
        
        
        Conjunction con0 = Restrictions.conjunction();
        con0.add(Restrictions.eq("f.isNeedApproval", true));
        
        Disjunction dis01 = Restrictions.disjunction();
        dis01.add(Restrictions.eq("d.approved", true));
        dis01.add(Restrictions.eq("d.createdBy.id", accountId));
        dis01.add(Restrictions.and(Restrictions.eq("a.status", ApprovalStatus.REQUESTED.getStatus()), 
                Restrictions.eq("p.permissionType", FolderPermission.OWNER.getPermission())));
        con0.add(dis01);
        
        dis0.add(con0);
        
        mainCondition.add(dis0);
        
        crit.add(mainCondition);
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        crit.setProjection(Projections.distinct(Projections.property("d.id")));
        
        return crit.list().size();
    }*/
    public Integer countDocumentByAccount(String accountId, String folderId, int permission){       
        StringBuilder sb = new StringBuilder("SELECT ")
                .append("COUNT(d.id) ")
                .append("FROM ").append(DmDocumentFolder.class.getName()).append(" AS df ")
                .append("LEFT JOIN df.document AS d ")
                .append("LEFT JOIN d.approval AS a ")
                .append("LEFT JOIN df.folder AS f ")                
                .append("WHERE f.id = :folderId AND d.isRemoved = :isRemoved AND  ")
                .append("((f.isNeedApproval = false AND (a.status != 3 OR d.approved = :approved)) OR "
                + "( (f.isNeedApproval = true) AND ((d.approved = :approved) "
                + "OR (d.createdBy.id = :accountId) OR (a.status = :status AND 1 = :permission) ))) ");
        
        Query query = getSession().createQuery(sb.toString());
        query.setString("folderId", folderId);
        query.setBoolean("isRemoved", false);
        query.setBoolean("approved", Boolean.TRUE);
        query.setString("accountId", accountId);
        query.setInteger("status", ApprovalStatus.REQUESTED.getStatus());
        query.setInteger("permission", permission);
        
        
        List result = query.list();
        Long rowCount = new Long(0);
        if (result.size() > 0) {
            rowCount = (Long) result.get(0);
            return new Integer(String.valueOf(rowCount));
        }
        return new Integer(0);
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
