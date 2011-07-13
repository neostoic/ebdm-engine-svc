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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocAccessHistory")
@Table(name = "dm_doc_access_history")
public class DmDocAccessHistory implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddah_id", nullable = false, length = 36)
    private String id;
    @Column(name = "ddah_access_type")
    private Integer accessType;
    @Column(name = "ddah_time_access")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accessedTime;

    @JoinColumn(name = "da_id", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount accessedBy;

    @JoinColumn(name = "ddv_id", referencedColumnName = "ddv_id", nullable = true)
    @ManyToOne(targetEntity = DmDocumentVersion.class)
    private DmDocumentVersion docVersion;

    public DmDocAccessHistory() {
        
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public DmAccount getAccessedBy() {
        return accessedBy;
    }

    public void setAccessedBy(DmAccount accessedBy) {
        this.accessedBy = accessedBy;
    }

    public Date getAccessedTime() {
        return accessedTime;
    }

    public void setAccessedTime(Date accessedTime) {
        this.accessedTime = accessedTime;
    }

    public DmDocumentVersion getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(DmDocumentVersion docVersion) {
        this.docVersion = docVersion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
