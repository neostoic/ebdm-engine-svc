/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentLock;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmDocumentLockDao")
public class DmDocumentLockDao extends BaseDmEngineDaoImpl<DmDocumentLock> {

    public DmDocumentLockDao() {
        this.t = new DmDocumentLock();
    }
    
    public DmDocumentLock findByDocumentId(String docId){
        Criteria criteria = getSession().createCriteria(DmDocumentLock.class, "dl");
        criteria.createAlias("dl.document", "document");
        criteria.add(Restrictions.eq("document.id", docId));
        
        List<DmDocumentLock> documentLocks = criteria.list();
        if (documentLocks.size() > 0) {
            return documentLocks.get(0);
        }
        
        return null;    
    }
    
    public int deleteByDocId(String docId){
        String hql = "DELETE FROM "+DmDocumentLock.class.getName()+ " a WHERE a.document.id = :documentId";
        Query query = getSession().createQuery(hql);
        query.setString("documentId", docId);
        return query.executeUpdate();        
    }
    
}
