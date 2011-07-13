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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentIndexed")
@Table(name = "dm_document_indexed")
public class DmDocumentIndexed implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddi_id", nullable = false, length = 36)
    private String id;
//    @Column(name = "ddi_document_id", nullable = false, length = 36)
//    private String documentId;
    @Column(name = "ddi_time_indexed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIndexed;

    @Column(name = "ddi_doc_modified_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date docModifiedTime;

    @Column(name = "ddi_is_need_reindex")
    private Boolean isNeedReindex;

    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id", nullable = true)
    @ManyToOne(targetEntity = DmDocument.class)
    private DmDocument document;

    public DmDocumentIndexed() {
    }

//    public String getDocumentId() {
//        return documentId;
//    }

//    public void setDocumentId(String documentId) {
//        this.documentId = documentId;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimeIndexed() {
        return timeIndexed;
    }

    public void setTimeIndexed(Date timeIndexed) {
        this.timeIndexed = timeIndexed;
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public Date getDocModifiedTime() {
        return docModifiedTime;
    }

    public void setDocModifiedTime(Date docModifiedTime) {
        this.docModifiedTime = docModifiedTime;
    }

    public Boolean getIsNeedReindex() {
        return isNeedReindex;
    }

    public void setIsNeedReindex(Boolean isNeedReindex) {
        this.isNeedReindex = isNeedReindex;
    }

}
