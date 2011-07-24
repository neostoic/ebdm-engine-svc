/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentApproval;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifhayz
 */
@Repository("dmDocumentApprovalDao")
public class DmDocumentApprovalDao extends BaseDmEngineDaoImpl<DmDocumentApproval> {

    public DmDocumentApprovalDao() {
        this.t = new DmDocumentApproval();
    }
    
    public DmDocumentApproval findByDocId(String docId){
        List<DmDocumentApproval> approvalList = findByKey("document.id", docId, 0, 1);
        if (approvalList.size() > 0) {
            return approvalList.get(0);
        }
        return null;
    }
    
}
