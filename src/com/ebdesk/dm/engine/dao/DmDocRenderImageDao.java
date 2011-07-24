/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocRenderImage;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmDocRenderImageDao")
public class DmDocRenderImageDao extends BaseDmEngineDaoImpl<DmDocRenderImage>{
    
    public DmDocRenderImageDao(){
        this.t = new DmDocRenderImage();
    }
    
    public int deleteDocRenderByVersionId(String versionId){
        String hql = "DELETE FROM " + DmDocRenderImage.class.getName() + " dri WHERE dri.docVersion.id = :versionId";
        Query query  = getSession().createQuery(hql);    
        query.setString("versionId", versionId);
        return query.executeUpdate();
    }
    
    public int deleteByVersionId(String versionId){
        return deleteDocRenderByVersionId(versionId);
    }
    
    public List<DmDocRenderImage> findByVersionId(String versionId){
        Criteria criteria = getSession().createCriteria(t.getClass());
        criteria.add(Restrictions.eq("docVersion.id", versionId));
        criteria.addOrder(Order.asc("page"));
        List result = criteria.list();        
        return result;    
    }
    
    public DmDocRenderImage findByVersionIdAndPage(String versionId, int page){
        Criteria criteria = getSession().createCriteria(t.getClass());
        criteria.add(Restrictions.and(Restrictions.eq("docVersion.id", versionId), Restrictions.eq("page", page)));
        List<DmDocRenderImage> result = criteria.list();        
        if (result.size() > 0) {
            return result.get(0);
        }        
        return null;    
    }
}
