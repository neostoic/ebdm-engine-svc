/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocRenderImage;
import com.ebdesk.dm.engine.domain.DmDocViewAnnotation;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmDocViewAnnotationDao")
public class DmDocViewAnnotationDao extends BaseDmEngineDaoImpl<DmDocViewAnnotation> {

    public DmDocViewAnnotationDao() {
        this.t = new DmDocViewAnnotation();
    }
    
    public int deleteByVersionId(String versionId){
        String hql = "DELETE FROM " + DmDocViewAnnotation.class.getName() + " AS dva WHERE dva.annotatedPage.id IN ("
                + " SELECT dri.id FROM "+DmDocRenderImage.class.getName()+" AS dri WHERE dri.docVersion.id = :versionId "
                + ")";
        Query query  = getSession().createQuery(hql);    
        query.setString("versionId", versionId);
        return query.executeUpdate();
    }
    
    
}
