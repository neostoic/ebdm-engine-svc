/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.constant.DatabaseConstants;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import com.ebdesk.dm.engine.domain.DmDocumentVersionApproval;
import com.ebdesk.dm.engine.domain.util.ApprovalStatus;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmDocumentVersionDao")
public class DmDocumentVersionDao extends BaseDmEngineDaoImpl<DmDocumentVersion> {
    
    public DmDocumentVersionDao() {
        this.t = new DmDocumentVersion();
    }

    /****
     * Find DmDocumentVersion by document identifier
     * @param docId the document identifier.
     * @param start start row
     * @param max maximum number of row to fetch
     * @param orderBy order field by {"size", "version", "modifiedTime"}. Default is "modifiedTime"
     * @param order order of sorted field {"asc" , "desc"}. Default is "asc"
     * @return list of DmDocumentVersion to find.
     */
    public List<DmDocumentVersion> findDocumentVersionByDocId(String docId, int start, int max,
            String orderBy, String order){
        Criteria criteria = getSession().createCriteria(DmDocumentVersion.class);
        criteria.createAlias("approval", "a", CriteriaSpecification.LEFT_JOIN);
        criteria.add(Restrictions.eq("document.id", docId));

        if (orderBy != null && !"".equals(orderBy)) {
            if (DatabaseConstants.ORDER_DESC.equals(order)) {
                if ("version".equals(orderBy)) {
                    criteria.addOrder(Order.desc("majorVersion")).addOrder(Order.desc("minorVersion")).addOrder(Order.asc("id"));
                } else {
                    criteria.addOrder(Order.desc(orderBy)).addOrder(Order.asc("id"));
                }
            } else {
                if ("version".equals(orderBy)) {
                    criteria.addOrder(Order.asc("majorVersion")).addOrder(Order.asc("minorVersion")).addOrder(Order.asc("id"));
                } else {
                    criteria.addOrder(Order.asc(orderBy)).addOrder(Order.asc("id"));
                }
            }
        } else {
            criteria.addOrder(Order.desc("modifiedTime"));
        }

        criteria.setFirstResult(start);
        criteria.setMaxResults(max);
        
        return criteria.list();
    }
    
    public int countByDocumentId(String documentId){
        Criteria criteria = getSession().createCriteria(DmDocumentVersion.class);
        criteria.add(Restrictions.eq("document.id", documentId));
        criteria.setProjection(Projections.rowCount());
               
        List<Integer> list = criteria.list();
        if (list.size() > 0) {
            return list.get(0);
        }        
        return 0;
    }
    
    public int deleteDocVersionByVersionId(String versionId){
        String hql = "DELETE FROM " + DmDocumentVersion.class.getName() + " dv WHERE dv.id = :versionId";
        Query query  = getSession().createQuery(hql);    
        query.setString("versionId", versionId);
        return query.executeUpdate();
    }   

    public int countAllNotRenderedVersion(){
        Criteria criteria = getSession().createCriteria(DmDocumentVersion.class);        
        criteria.add(Restrictions.or(Restrictions.isNull("isRendered"), Restrictions.eq("isRendered", Boolean.FALSE)));
        
        criteria.setProjection(Projections.rowCount());
               
        List<Integer> list = criteria.list();
        if (list.size() > 0) {
            return list.get(0);
        }        
        return 0;
    }
    
    public List<DmDocumentVersion> findNotRenderedVersion(int start, int max){
        Criteria criteria = getSession().createCriteria(DmDocumentVersion.class);
        criteria.add(Restrictions.or(Restrictions.isNull("isRendered"), Restrictions.eq("isRendered", Boolean.FALSE)));
        
        criteria.setFirstResult(start);
        criteria.setMaxResults(max);

        return criteria.list();
    }
    
    
    public DmDocumentVersion findRequestedVersionByDocId(String documentId){
        Criteria criteria = getSession().createCriteria(DmDocumentVersion.class);
        criteria.createAlias("approval", "a", CriteriaSpecification.LEFT_JOIN);
        criteria.add(Restrictions.eq("document.id", documentId));
        criteria.add(Restrictions.eq("a.status", ApprovalStatus.REQUESTED.getStatus()));
        criteria.add(Restrictions.eq("approved", Boolean.FALSE));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);
        List<DmDocumentVersion> versions = criteria.list();
        if (versions.size() > 0) {
            return versions.get(0);
        }
               
        
        return null;
    }

    public List<String> getIdListByDocId(String documentId) {
        Criteria criteria = getSession().createCriteria(DmDocumentVersion.class);
        criteria.add(Restrictions.eq("document.id", documentId));
        criteria.setProjection(Projections.property("id"));
        return criteria.list();
    }
}
