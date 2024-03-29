/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author ifhayz
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentApproval")
@Table(name = "dm_document_approval")
public class DmDocumentApproval {

    @Id
    @Basic(optional = false)
    @Column(name = "dda_id", nullable = false, length = 36)
    private String id;
    /***
     * dda_approval status:
     *  1: REQUESTED
     *  2: APPROVED
     *  3: REJECTED
     */
    @Column(name = "dda_approval_status")
    private Integer status;
    
    @Column(name = "dda_comment", nullable = true, length = 4000)
    private String comment;

    @JoinColumn(name = "dda_approved_by", referencedColumnName = "da_id", nullable = true)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount approvedBy;
        
    @JoinColumn(name = "dda_document_id", referencedColumnName = "dd_id", nullable = true)
    @OneToOne(targetEntity = DmDocument.class)
    private DmDocument document;

    public DmDocumentApproval() {
    }

    public DmDocumentApproval(String id) {
        this.id = id;
    }
    
    public DmAccount getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(DmAccount approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
