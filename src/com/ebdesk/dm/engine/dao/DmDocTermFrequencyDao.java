/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocTermFrequency;
import com.ebdesk.dm.engine.dto.DmFolderNode;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 *
 * @author user
 */
@Repository("dmDocTermFrequencyDao")
public class DmDocTermFrequencyDao extends BaseDmEngineDaoImpl<DmDocTermFrequency> {

    public DmDocTermFrequencyDao() {
        this.t = new DmDocTermFrequency();
    }

    public void deleteByDocId(String documentId) {
        String deleteQuery = "delete from"
                + " dm_doc_term_frequency"
                + " WHERE"
                + " dd_id = :documentId"
                ;

        Query query = getSession().createSQLQuery(deleteQuery);

        query.setString("documentId", documentId);

        query.executeUpdate();

        flush();
    }

    public List<String> getTopTerm(String documentId, int numTerm) {
        String sql = "SELECT"
            + " dtf.ddtf_term"
            + " FROM dm_doc_term_frequency dtf"
            + " left join dm_tag_ranking_stop_word trsw"
            + " on dtf.ddtf_term = trsw.dtrsw_stop_word"
            + " WHERE dtf.dd_id = :documentId"
            + " and trsw.dtrsw_stop_word is null"
            + " and dtf.ddtf_frequency > 0"
            + " ORDER BY dtf.ddtf_frequency desc, dtf.ddtf_term asc"
            ;

        SQLQuery query = getSession().createSQLQuery(sql);

        query.setString("documentId", documentId);

        query.setMaxResults(numTerm);

//        query.addScalar("id", Hibernate.STRING);
//        query.addScalar("name", Hibernate.STRING);
//        query.addScalar("numChildren", Hibernate.INTEGER);
//        query.addScalar("ownerAccountId", Hibernate.STRING);
//        query.addScalar("permissionType", Hibernate.INTEGER);

//        query.setResultTransformer(Transformers.aliasToBean(DmFolderNode.class));

        return query.list();
    }
}
