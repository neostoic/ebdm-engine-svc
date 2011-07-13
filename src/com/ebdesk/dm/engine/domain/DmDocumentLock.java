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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Muhammad Rifai
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentLock")
@Table(name = "dm_document_lock")
public class DmDocumentLock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ddl_id")
    private String id;
    @Basic(optional = false)
    @Column(name = "ddl_time_lock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLock;
    @JoinColumn(name = "da_id", referencedColumnName = "da_id")
    @ManyToOne(optional = false)
    private DmAccount account;
    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id")
    @ManyToOne(optional = false)
    private DmDocument document;

    public DmDocumentLock() {
        
    }

    public DmDocumentLock(String id) {
        this.id = id;
    }

    public DmDocumentLock(String id, Date timeLock) {
        this.id = id;
        this.timeLock = timeLock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimeLock() {
        return timeLock;
    }

    public void setTimeLock(Date timeLock) {
        this.timeLock = timeLock;
    }

    public DmAccount getAccount() {
        return account;
    }

    public void setAccount(DmAccount account) {
        this.account = account;
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DmDocumentLock)) {
            return false;
        }
        DmDocumentLock other = (DmDocumentLock) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ebdesk.dm.domain.DmDocumentLock[ ddlId=" + id + " ]";
    }
    
}
