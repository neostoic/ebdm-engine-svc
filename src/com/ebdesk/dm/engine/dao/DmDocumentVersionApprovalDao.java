/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentVersionApproval;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ifhayz
 */
@Repository("dmDocumentVersionApprovalDao")
public class DmDocumentVersionApprovalDao extends BaseDmEngineDaoImpl<DmDocumentVersionApproval> {

    public DmDocumentVersionApprovalDao() {
        this.t = new DmDocumentVersionApproval();
    }
    
    public DmDocumentVersionApproval findByDocVersionId(String versionId){
        List<DmDocumentVersionApproval> approvalList = findByKey("documentVersion.id", versionId, 0, 1);
        if (approvalList.size() > 0) {
            return approvalList.get(0);
        }
        return null;
    }
    
}
