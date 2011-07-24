/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.domain.util;

/**
 *
 * @author ifhayz
 */
public enum ApprovalStatus {
    REQUESTED(1),
    
    APPROVED(2),

    REJECT(3);
    
    private int status;

    ApprovalStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }  
}
