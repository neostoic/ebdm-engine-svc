/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocViewAnnotation;
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
    
    
    
    
}
