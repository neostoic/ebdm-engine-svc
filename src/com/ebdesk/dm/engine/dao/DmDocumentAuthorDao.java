/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentAuthor;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmDocumentAuthorDao")
public class DmDocumentAuthorDao extends BaseDmEngineDaoImpl<DmDocumentAuthor> {

    public DmDocumentAuthorDao() {
        this.t = new DmDocumentAuthor();
    }
    
    public int deleteByDocumentId(String documentId){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(DmDocumentAuthor.class.getName()).append(" da WHERE da.document.id = :documentId");
        
        Query query = getSession().createQuery(sb.toString());
        query.setString("documentId", documentId);
        return query.executeUpdate();
    }
    
    
}
