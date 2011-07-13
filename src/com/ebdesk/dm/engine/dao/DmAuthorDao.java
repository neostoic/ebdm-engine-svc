/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmAuthor;
import com.ebdesk.dm.engine.domain.DmDocumentAuthor;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rifai
 */
@Repository("dmAuthorDao")
public class DmAuthorDao extends BaseDmEngineDaoImpl<DmAuthor> {

    public DmAuthorDao() {
        this.t = new DmAuthor();
    }
    
    public DmAuthor findByName(String name){
        List<DmAuthor> authorList = findByKey("name", name, 0, 1);
        if (authorList.size() > 0) {
            return authorList.get(0);
        }
        return null;
    }
    
}
