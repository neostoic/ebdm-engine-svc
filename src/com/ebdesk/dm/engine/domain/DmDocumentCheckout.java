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
 * @author Muhammad Rifai
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentCheckout")
@Table(name = "dm_document_checkout")
public class DmDocumentCheckout implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ddc_id")
    private String id;
    @Basic(optional = false)
    @Column(name = "ddc_time_checkout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCheckout;
    @JoinColumn(name = "da_id", referencedColumnName = "da_id")
    @ManyToOne(optional = false)
    private DmAccount account;
    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id")
    @ManyToOne(optional = false)
    private DmDocument document;

    public DmDocumentCheckout() {
        
    }

    public DmDocumentCheckout(String id) {
        this.id = id;
    }

    public DmDocumentCheckout(String id, Date timeCheckout) {
        this.id = id;
        this.timeCheckout = timeCheckout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimeCheckout() {
        return timeCheckout;
    }

    public void setTimeCheckout(Date timeCheckout) {
        this.timeCheckout = timeCheckout;
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
        if (!(object instanceof DmDocumentCheckout)) {
            return false;
        }
        DmDocumentCheckout other = (DmDocumentCheckout) object;
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
