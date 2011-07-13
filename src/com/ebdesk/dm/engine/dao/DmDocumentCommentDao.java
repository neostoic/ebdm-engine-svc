/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentComment;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmDocumentCommentDao")
public class DmDocumentCommentDao extends BaseDmEngineDaoImpl<DmDocumentComment>  {

    private Logger logger = Logger.getLogger(DmDocumentCommentDao.class);
    
    public DmDocumentCommentDao() {
        this.t = new DmDocumentComment();
    }
    
    public DmDocumentComment findLastCommentOnDocument(String documentId){
        Criteria criteria = getSession().createCriteria(DmDocumentComment.class);
        criteria.add(Restrictions.eq("document.id", documentId));        
        criteria.addOrder(Order.desc("createdTime"));
        criteria.setFirstResult(0);
        criteria.setMaxResults(1);
        List<DmDocumentComment> result = criteria.list();  
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }
    
    public List<DmDocumentComment> findByDocumentId(String docId, int start, int max){
        Criteria criteria = getSession().createCriteria(DmDocumentComment.class);
        criteria.add(Restrictions.eq("document.id", docId));        
        criteria.addOrder(Order.desc("createdTime"));
        criteria.setFirstResult(start);
        criteria.setMaxResults(max);
        List<DmDocumentComment> result = criteria.list();  
        return result;
    }
        
}
