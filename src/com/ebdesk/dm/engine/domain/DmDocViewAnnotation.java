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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocViewAnnotation")
@Table(name = "dm_doc_view_annotation")
public class DmDocViewAnnotation implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddva_id", nullable = false, length = 36)
    private String id;
    @Column(name = "ddva_annotation", length = 250)
    private String annotation;
    @Column(name = "ddva_time_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @JoinColumn(name = "da_id", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount annotatedBy;

    @JoinColumn(name = "ddri_id", referencedColumnName = "ddri_id", nullable = false)
    @ManyToOne(targetEntity = DmDocRenderImage.class)
    private DmDocRenderImage annotatedPage;

    public DmDocViewAnnotation() {
    }

    public DmAccount getAnnotatedBy() {
        return annotatedBy;
    }

    public void setAnnotatedBy(DmAccount annotatedBy) {
        this.annotatedBy = annotatedBy;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DmDocRenderImage getAnnotatedPage() {
        return annotatedPage;
    }

    public void setAnnotatedPage(DmDocRenderImage annotatedPage) {
        this.annotatedPage = annotatedPage;
    }
}
