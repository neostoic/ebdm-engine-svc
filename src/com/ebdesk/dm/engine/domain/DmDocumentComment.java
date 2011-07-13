/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentComment")
@Table(name = "dm_document_comment")
public class DmDocumentComment implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddc_id", nullable = false, length = 36)
    private String id;
    @Column(name = "ddc_comment", length = 250)
    private String comment;
    @Column(name = "ddc_time_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id", nullable = true)
    @ManyToOne(targetEntity = DmDocument.class)
    private DmDocument document;

    @JoinColumn(name = "da_id", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount commentedBy;

    public DmDocumentComment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DmAccount getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(DmAccount commentedBy) {
        this.commentedBy = commentedBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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
}
