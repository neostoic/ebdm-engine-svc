/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmFolderTermFrequency;
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
@Repository("dmFolderTermFrequencyDao")
public class DmFolderTermFrequencyDao extends BaseDmEngineDaoImpl<DmFolderTermFrequency> {

    public DmFolderTermFrequencyDao() {
        this.t = new DmFolderTermFrequency();
    }

    public void insertByDoc(String documentId, String folderId) {
//        String subQuery1 = "select df.dd_id from dm_document_folder df where df.df_id = :folderId";
//        String subQuery2 = "select dtf2.dd_id, dtf2.ddtf_term, dtf2.ddtf_frequency"
//                + " from dm_doc_term_frequency dtf2 where dtf2.dd_id in (" + subQuery1 + ")";
        String subQuery3 = "select dtf2.dd_id, dtf2.ddtf_term, dtf2.ddtf_frequency"
                + " from dm_doc_term_frequency dtf2 where dtf2.dd_id = :documentId";
        String subQuery4 = "select ftf2.df_id, ftf2.dftf_term, ftf2.dftf_frequency"
                + " from dm_folder_term_frequency ftf2 where ftf2.df_id = :folderId";
        String mainQuery = "insert into dm_folder_term_frequency"
                + " (df_id, dftf_term, dftf_frequency)"
                + " select"
                + " :folderId, dtf.ddtf_term, dtf.ddtf_frequency"
                + " from (" + subQuery3 + ") dtf"
//                + " left join dm_folder_term_frequency ftf on dtf.ddtf_term = ftf.dftf_term"
                + " left join (" + subQuery4 + ") ftf on dtf.ddtf_term = ftf.dftf_term"
                + " where"
//                + " dtf.dd_id = :documentId"
                + " ftf.df_id is null"
                ;

        Query query = getSession().createSQLQuery(mainQuery);

        query.setString("folderId", folderId);
        query.setString("documentId", documentId);

        query.executeUpdate();
//        flush();
    }

    public void increaseByDoc(String documentId, String folderId) {
        String subQuery1 = "select dtf2.ddtf_frequency"
                + " from dm_doc_term_frequency dtf2 where dtf2.dd_id = :documentId and dtf2.ddtf_term = ftf.dftf_term";
        String subQuery2 = "select ddtf_term"
                + " from dm_doc_term_frequency where dd_id = :documentId";
        String mainQuery = "update dm_folder_term_frequency ftf set"
                + " ftf.dftf_frequency = ftf.dftf_frequency + (" + subQuery1 + ")"
                + " where"
                + " ftf.df_id = :folderId"
                + " and ftf.dftf_term in (" + subQuery2 + ")"
                ;

        Query query = getSession().createSQLQuery(mainQuery);

        query.setString("folderId", folderId);
        query.setString("documentId", documentId);

        query.executeUpdate();
//        flush();
    }

    public void decreaseByDoc(String documentId, String folderId) {
        String subQuery1 = "select dtf2.ddtf_frequency"
                + " from dm_doc_term_frequency dtf2 where dtf2.dd_id = :documentId and dtf2.ddtf_term = ftf.dftf_term";
        String subQuery2 = "select ddtf_term"
                + " from dm_doc_term_frequency where dd_id = :documentId";
        String mainQuery = "update dm_folder_term_frequency ftf set"
                + " ftf.dftf_frequency = ftf.dftf_frequency - (" + subQuery1 + ")"
                + " where"
                + " ftf.df_id = :folderId"
                + " and ftf.dftf_term in (" + subQuery2 + ")"
                ;

        Query query = getSession().createSQLQuery(mainQuery);

        query.setString("folderId", folderId);
        query.setString("documentId", documentId);

        query.executeUpdate();
//        flush();
    }

    public List<String> getTopTerm(String folderId, int numTerm) {
        String sql = "SELECT"
            + " ftf.dftf_term"
            + " FROM dm_folder_term_frequency ftf"
            + " left join dm_tag_ranking_stop_word trsw"
            + " on ftf.dftf_term = trsw.dtrsw_stop_word"
            + " WHERE ftf.df_id = :folderId"
            + " and trsw.dtrsw_stop_word is null"
            + " and ftf.dftf_frequency > 0"
            + " ORDER BY ftf.dftf_frequency desc, ftf.dftf_term asc"
            ;

        SQLQuery query = getSession().createSQLQuery(sql);

        query.setString("folderId", folderId);

        query.setMaxResults(numTerm);

        return query.list();
    }

    public List<String> getTopTermInclSubFolders(String folderId, int numTerm, String accountId) {
        String sqlSub1 = "SELECT"
            + " fd.df_id_descendant"
            + " FROM dm_folder_descendant fd"
            + " inner join dm_folder_permission fp"
            + " on fd.df_id_descendant = fp.df_id"
            + " WHERE fd.df_id = :folderId"
            + " and fp.da_id = :accountId"
            ;

        String sql = "SELECT ftf.dftf_term as docTerm, sum(ftf.dftf_frequency) as frequency"
            + " FROM dm_folder_term_frequency ftf"
            + " left join dm_tag_ranking_stop_word trsw"
            + " on ftf.dftf_term = trsw.dtrsw_stop_word"
            + " WHERE ("
            + " ftf.df_id = :folderId"
            + " or ftf.df_id in (" + sqlSub1 + ")"
            + ") and trsw.dtrsw_stop_word is null"
            + " GROUP BY ftf.dftf_term"
            + " HAVING frequency > 0"
            + " ORDER BY frequency desc, ftf.dftf_term asc"
            ;

        SQLQuery query = getSession().createSQLQuery(sql);

        query.setString("folderId", folderId);
        query.setString("accountId", accountId);

        query.addScalar("docTerm", Hibernate.STRING);

//        query.setResultTransformer(Transformers.aliasToBean(String.class));

        query.setMaxResults(numTerm);

        return query.list();
    }
}
