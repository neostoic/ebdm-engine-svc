/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author ifhayz
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentVersionApproval")
@Table(name = "dm_document_version_approval")
public class DmDocumentVersionApproval {
    
    @Id
    @Basic(optional = false)
    @Column(name = "dva_id", nullable = false, length = 36)
    private String id;
    /***
     * dda_approval status:
     *  1: REQUESTED
     *  2: APPROVED
     *  3: REJECTED
     */
    @Column(name = "dva_approval_status")
    private Integer status;
    
    @Column(name = "dva_comment", nullable = true, length = 4000)
    private String comment;
    
    @JoinColumn(name = "dva_approved_by", referencedColumnName = "da_id", nullable = true)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount approvedBy;
    
    @JoinColumn(name = "dva_doc_version_id", referencedColumnName = "ddv_id", nullable = true)
    @ManyToOne(targetEntity = DmDocumentVersion.class)
    private DmDocumentVersion documentVersion;

    public DmDocumentVersionApproval() {
        
    }

    public DmDocumentVersionApproval(String id) {
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

    public DmDocumentVersion getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(DmDocumentVersion documentVersion) {
        this.documentVersion = documentVersion;
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
