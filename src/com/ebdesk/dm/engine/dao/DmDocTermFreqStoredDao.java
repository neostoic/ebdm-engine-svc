/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocTermFreqStored;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentIndexed;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository("dmDocTermFreqStoredDao")
public class DmDocTermFreqStoredDao extends BaseDmEngineDaoImpl<DmDocTermFreqStored> {

    public DmDocTermFreqStoredDao() {
        this.t = new DmDocTermFreqStored();
    }

    public List<DmDocument> getNotStoredDocList(int numDocs) {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.isNull("termFreqStored.id"));
        disjunction.add(Restrictions.neProperty("timeIndexed", "termFreqStored.timeIndexed"));

        Criteria crit = getSession().createCriteria(DmDocumentIndexed.class);
        crit.setProjection(Projections.property("document"));
        crit.createAlias("document", "doc", CriteriaSpecification.LEFT_JOIN);
        crit.createAlias("doc.docTermFreqStoredList", "termFreqStored", CriteriaSpecification.LEFT_JOIN);
        crit.add(Restrictions.eq("isNeedReindex", false));
        crit.add(disjunction);
        crit.addOrder(Order.asc("timeIndexed"));
        crit.setMaxResults(numDocs);

        List<DmDocument> documentList = crit.list();
        // start - trigger lazy fetch
        for (DmDocument documentTmp : documentList) {
            if (documentTmp.getDocTermFreqStoredList() != null) {
                documentTmp.getDocTermFreqStoredList().size();
            }
            if (documentTmp.getDocumentIndexedList() != null) {
                documentTmp.getDocumentIndexedList().size();
            }
//            if (documentTmp.getDocumentAuthorList() != null) {
//                documentTmp.getDocumentAuthorList().size();
//            }
//            if (documentTmp.getDocumentKeywordList() != null) {
//                documentTmp.getDocumentKeywordList().size();
//            }
            if (documentTmp.getDocumentFolderList() != null) {
                documentTmp.getDocumentFolderList().size();
//                for (DmDocumentFolder docFolder : documentTmp.getDocumentFolderList()) {
//                    docFolder.getFolder().getPermissionList().size();
//                }
            }
        }
        // end - trigger lazy fetch

        return documentList;
    }

    public void deleteByDocumentId(String documentId) {
        String deleteQuery = "delete from"
                + " dm_doc_term_freq_stored"
                + " WHERE"
                + " dd_id = :documentId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("documentId", documentId);

        query.executeUpdate();

        flush();
    }
}
