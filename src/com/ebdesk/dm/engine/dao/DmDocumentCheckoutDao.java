/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentCheckout;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmDocumentCheckoutDao")
public class DmDocumentCheckoutDao extends BaseDmEngineDaoImpl<DmDocumentCheckout> {

    public DmDocumentCheckoutDao() {
        this.t = new DmDocumentCheckout();
    }
    
    public DmDocumentCheckout findByDocumentId(String docId){
        Criteria criteria = getSession().createCriteria(DmDocumentCheckout.class, "dc");
        criteria.createAlias("dc.document", "document");
        criteria.add(Restrictions.eq("document.id", docId));
        
        List<DmDocumentCheckout> documentCheckouts = criteria.list();
        if (documentCheckouts.size() > 0) {
            return documentCheckouts.get(0);
        }
        
        return null;    
    }
    
    public int deleteByDocId(String docId){
        String hql = "DELETE FROM "+DmDocumentCheckout.class.getName()+ " a WHERE a.document.id = :documentId";
        Query query = getSession().createQuery(hql);
        query.setString("documentId", docId);
        return query.executeUpdate();        
    }
    
}
