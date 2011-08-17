/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentFolder;
import com.ebdesk.dm.engine.domain.DmDocumentIndexed;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.SessionFactoryImpl;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository("dmDocumentIndexedDao")
public class DmDocumentIndexedDao extends BaseDmEngineDaoImpl<DmDocumentIndexed> {

    public DmDocumentIndexedDao() {
        this.t = new DmDocumentIndexed();
    }

    public List<DmDocument> getNotIndexedDocList(int numDocs) {
//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.neProperty("lastModifiedTime", "docIndexed.docModifiedTime"));

        Disjunction disjunction = Restrictions.disjunction();
//        disjunction.add(conjunction);
        disjunction.add(Restrictions.isNull("docIndexed.id"));
        disjunction.add(Restrictions.neProperty("lastModifiedTime", "docIndexed.docModifiedTime"));
        disjunction.add(Restrictions.eq("docIndexed.isNeedReindex", true));        

        Criteria crit = getSession().createCriteria(DmDocument.class);
        crit.createAlias("documentIndexedList", "docIndexed", CriteriaSpecification.LEFT_JOIN);
//        if (docModificationTime != null) {
//            crit.add(Restrictions.ge("lastModifiedTime", docModificationTime));
//        }
        crit.add(disjunction);
        crit.add(Restrictions.eq("approved", true));
        crit.addOrder(Order.desc("docIndexed.isNeedReindex"));
        crit.addOrder(Order.asc("lastModifiedTime"));
        crit.setMaxResults(numDocs);

        List<DmDocument> documentList = crit.list();
        // start - trigger lazy fetch
        for (DmDocument documentTmp : documentList) {
            if (documentTmp.getDocumentAuthorList() != null) {
                documentTmp.getDocumentAuthorList().size();
            }
            if (documentTmp.getDocumentKeywordList() != null) {
                documentTmp.getDocumentKeywordList().size();
            }
            if (documentTmp.getDocumentFolderList() != null) {
//                documentTmp.getDocumentFolderList().size();
                for (DmDocumentFolder docFolder : documentTmp.getDocumentFolderList()) {
                    docFolder.getFolder().getPermissionList().size();
                }
            }
        }
        // end - trigger lazy fetch

        return documentList;
    }

    public void deleteByDocumentId(String documentId) {
        String deleteQuery = "delete from"
                + " dm_document_indexed"
                + " WHERE"
                + " dd_id = :documentId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("documentId", documentId);

        query.executeUpdate();
    }

    public void setReindexByFolder(String folderId) {
        SessionFactoryImpl sessionFactImpl = (SessionFactoryImpl) getSession().getSessionFactory();
        
        String sqlSub = "SELECT"
            + " df.dd_id"
            + " from dm_document_folder df"
            + " inner join dm_folder f"
            + " on df.df_id = f.df_id"
            + " where"
            + " f.df_id = :folderId"
            ;

        String queryStr = "update"
                + " dm_document_indexed"
                + " set"
                ;
        if (sessionFactImpl.getDialect() instanceof org.hibernate.dialect.PostgreSQLDialect) {
            queryStr = queryStr + " ddi_is_need_reindex = TRUE";
        }
        else {
            queryStr = queryStr + " ddi_is_need_reindex = 1";
        }

        queryStr = queryStr + " WHERE"
                + " dd_id in (" + sqlSub + ")"
                ;

        Query query = getSession().createSQLQuery(queryStr);

        query.setString("folderId", folderId);

        query.executeUpdate();
    }

    public void setReindexByDocument(String documentId) {
        SessionFactoryImpl sessionFactImpl = (SessionFactoryImpl) getSession().getSessionFactory();
        
        String queryStr = "update"
                + " dm_document_indexed"
                + " set"
                ;
        if (sessionFactImpl.getDialect() instanceof org.hibernate.dialect.PostgreSQLDialect) {
            queryStr = queryStr + " ddi_is_need_reindex = TRUE";
        }
        else {
            queryStr = queryStr + " ddi_is_need_reindex = 1";
        }

        queryStr = queryStr + " WHERE"
                + " dd_id = :documentId"
                ;

        Query query = getSession().createSQLQuery(queryStr);

        query.setString("documentId", documentId);

        query.executeUpdate();
    }
}
