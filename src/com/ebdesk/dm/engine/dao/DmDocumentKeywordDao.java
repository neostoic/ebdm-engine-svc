/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentKeyword;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmDocumentKeywordDao")
public class DmDocumentKeywordDao extends BaseDmEngineDaoImpl<DmDocumentKeyword> {

    public DmDocumentKeywordDao() {
        this.t = new DmDocumentKeyword();
    }

    
    
    
    public int deleteByDocumentId(String documentId){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(DmDocumentKeyword.class.getName()).append(" dk WHERE dk.document.id = :documentId");
        
        Query query = getSession().createQuery(sb.toString());
        query.setString("documentId", documentId);
        return query.executeUpdate();
    }
    
    
    public List<DmDocumentKeyword> findKeywordByDocumentId(String documentId, int start, int max) {
        StringBuilder sb = new StringBuilder("SELECT ")
                .append(" dk.id AS id, dk.term AS term, dk.rank AS rank ")
                .append("FROM ").append(DmDocumentKeyword.class.getName()).append(" AS dk ")                
                .append("WHERE dk.document.id = :documentId ")
                .append("ORDER BY dk.rank ASC ");
        Query query = getSession().createQuery(sb.toString());
        query.setString("documentId", documentId);
        query.setFirstResult(start);
        query.setMaxResults(max);

        query.setResultTransformer(Transformers.aliasToBean(DmDocumentKeyword.class));

        return query.list();
    }
    

}
