/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity(name = "com.ebdesk.dm.engine.domain.DmDocument")
@Table(name = "dm_document")
public class DmDocument implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "dd_id", nullable = false, length = 36)
    private String id;
    @Column(name = "dd_title", length = 100, nullable = false)
    private String title;
    @Column(name = "dd_description", length = 250)
    private String description;
    @Column(name = "dd_time_create")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @Column(name = "dd_time_last_modify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;
    @Column(name = "dd_mime_type", length = 255)
    private String mimeType;
    @Column(name = "dd_is_removed")
    private Boolean isRemoved;
    @Column(name = "dd_is_approved")
    private Boolean approved;
    @JoinColumn(name = "ddv_id_last_version", referencedColumnName = "ddv_id", nullable = true)
    @ManyToOne(targetEntity = DmDocumentVersion.class)
    private DmDocumentVersion lastVersion;
    @JoinColumn(name = "da_id_created_by", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount createdBy;
    @JoinColumn(name = "da_id_modified_by", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount modifiedBy;
    @JoinColumn(name = "dct_id", referencedColumnName = "dct_id", nullable = true)
    @ManyToOne(targetEntity = DmContentType.class)
    private DmContentType contentType;
    @OneToMany(mappedBy = "document")
    private List<DmDocumentFolder> documentFolderList;
    @OneToMany(mappedBy = "document")
    private List<DmDocumentAuthor> documentAuthorList;
    @OneToMany(mappedBy = "document")
    private List<DmDocumentKeyword> documentKeywordList;
    @OneToMany(mappedBy = "document")
    private List<DmDocumentIndexed> documentIndexedList;
    @OneToOne(mappedBy = "document")
    private DmDocumentApproval approval;
    private DmDocumentVersion requestedVersion;

    public DmDocument() {
    }

    public DmDocument(String id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public DmDocumentVersion getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(DmDocumentVersion lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DmAccount getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(DmAccount createdBy) {
        this.createdBy = createdBy;
    }

    public DmAccount getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(DmAccount modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setContentType(DmContentType contentType) {
        this.contentType = contentType;
    }

    public DmContentType getContentType() {
        return contentType;
    }

    public List<DmDocumentAuthor> getDocumentAuthorList() {
        return documentAuthorList;
    }

    public void setDocumentAuthorList(List<DmDocumentAuthor> documentAuthorList) {
        this.documentAuthorList = documentAuthorList;
    }

    public List<DmDocumentFolder> getDocumentFolderList() {
        return documentFolderList;
    }

    public void setDocumentFolderList(List<DmDocumentFolder> documentFolderList) {
        this.documentFolderList = documentFolderList;
    }

    public List<DmDocumentKeyword> getDocumentKeywordList() {
        return documentKeywordList;
    }

    public void setDocumentKeywordList(List<DmDocumentKeyword> documentKeywordList) {
        this.documentKeywordList = documentKeywordList;
    }

    public List<DmDocumentIndexed> getDocumentIndexedList() {
        return documentIndexedList;
    }

    public void setDocumentIndexedList(List<DmDocumentIndexed> documentIndexedList) {
        this.documentIndexedList = documentIndexedList;
    }

    public void setApproval(DmDocumentApproval approval) {
        this.approval = approval;
    }

    public DmDocumentApproval getApproval() {
        return approval;
    }

    public DmDocumentVersion getRequestedVersion() {
        return requestedVersion;
    }

    public void setRequestedVersion(DmDocumentVersion requestedVersion) {
        this.requestedVersion = requestedVersion;
    }
}
