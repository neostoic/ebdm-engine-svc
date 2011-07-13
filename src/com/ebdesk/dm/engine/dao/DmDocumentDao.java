/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocument;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmDocumentDao")
public class DmDocumentDao extends BaseDmEngineDaoImpl<DmDocument> {

    public DmDocumentDao() {
        this.t = new DmDocument();
    }

    public DmDocument findDocumentByDocumentId(String documentId) {
        StringBuilder sb = new StringBuilder("SELECT ")
                .append(" d ")
                .append("FROM ").append(DmDocument.class.getName()).append(" AS d ")
                .append("LEFT JOIN FETCH d.lastVersion AS lv ")
                .append("LEFT JOIN FETCH d.documentAuthorList AS da ")
                .append("WHERE d.id = :documentId AND d.isRemoved = :isRemoved");
        Query query = getSession().createQuery(sb.toString());
        query.setString("documentId", documentId);
        query.setBoolean("isRemoved", Boolean.FALSE);
        query.setFirstResult(0);
        query.setMaxResults(1);

        //query.setResultTransformer(Transformers.aliasToBean(DmDocument.class));

        List<DmDocument> documents = query.list();
         if (documents.size() > 0) {
             return documents.get(0);
         }else {
            return null;
         }

    }
    
    
    public int updateLastVersion(String lastVersionId, String documentId){
        Query query = getSession().createSQLQuery("UPDATE dm_document SET ddv_id_last_version = :versionId WHERE dd_id = :documentId");
        query.setString("versionId", lastVersionId);
        query.setString("documentId", documentId);
        return query.executeUpdate();
    }

}
