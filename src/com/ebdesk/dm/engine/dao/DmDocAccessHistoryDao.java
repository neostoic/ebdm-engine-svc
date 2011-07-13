/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocAccessHistory;
import com.ebdesk.dm.engine.domain.DmDocumentFolder;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmDocAccessHistoryDao")
public class DmDocAccessHistoryDao extends BaseDmEngineDaoImpl<DmDocAccessHistory> {

    public DmDocAccessHistoryDao() {
        this.t = new DmDocAccessHistory();
    }

    /***
     * 
     * @param docId
     * @param start
     * @param max
     * @param orderBy {"accessType", "accessedTime", "accessedBy.name"}
     * @param order {"asc", "desc"}
     * @return
     */
    public List<DmDocAccessHistory> findByDocumentId(String docId, int start, int max,
            String orderBy, String order) {
        StringBuilder sb = new StringBuilder("SELECT ")
                .append(" dah ")
                .append("FROM ").append(DmDocAccessHistory.class.getName()).append(" AS dah ")
                .append("INNER JOIN FETCH dah.accessedBy AS ab ")
                .append("WHERE dah.docVersion.document.id = :documentId ")
                .append("ORDER BY dah.").append(orderBy).append(" ").append(order).append(", dah.id ASC");
        Query query = getSession().createQuery(sb.toString());
        query.setString("documentId", docId);

        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.list();
    }

    public Integer countDocumentId(String documentId) {
        Criteria crit = getSession().createCriteria(DmDocAccessHistory.class, "dah");
        crit.createAlias("dah.docVersion", "version");
        crit.createAlias("version.document", "document");
        crit.add(Restrictions.eq("document.id", documentId));
        crit.setProjection(Projections.rowCount());

        List result = crit.list();
        Integer rowCount = new Integer(0);
        if (result.size() > 0) {
            rowCount = (Integer) result.get(0);
        }
        return rowCount;
    }
    
    
    public int deleteByVersionId(String versionId){
        String hql = "DELETE FROM "+DmDocAccessHistory.class.getName()+ " a WHERE docVersion.id = :versionId";
        Query query = getSession().createQuery(hql);
        query.setString("versionId", versionId);        
        return query.executeUpdate();        
    }
}
