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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocTermFreqStored")
@Table(name = "dm_doc_term_freq_stored")
public class DmDocTermFreqStored implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddtfs_id", nullable = false, length = 36)
    private String id;
    @Column(name = "ddtfs_time_stored")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStored;

    @Column(name = "ddi_time_indexed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIndexed;

    @Column(name = "df_id")
    private String folderId;

    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id", nullable = true)
    @ManyToOne(targetEntity = DmDocument.class)
    private DmDocument document;

    public DmDocTermFreqStored() {
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

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimeStored() {
        return timeStored;
    }

    public void setTimeStored(Date timeStored) {
        this.timeStored = timeStored;
    }

}
