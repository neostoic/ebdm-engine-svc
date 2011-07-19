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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author user
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentVersion")
@Table(name = "dm_document_version")
public class DmDocumentVersion implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddv_id", nullable = false, length = 36)
    private String id;
    @Column(name = "ddv_size")
    private Long size;
    @Column(name = "ddv_file_path", length = 255)
    private String filePath;
    @Column(name = "ddv_version_major")
    private Integer majorVersion;
    @Column(name = "ddv_version_minor")
    private Integer minorVersion;
    @Column(name = "ddv_file_name", length = 255)
    private String fileName;
    @Column(name = "ddv_time_modify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;
    @Column(name = "ddv_pdf_path")
    private String pdfPath;
    
    @Column(name = "ddv_text_path")
    private String textPath;
    
    @Lob
    @Column(name = "ddv_text_content")
    private String textContent;
    @Column(name = "ddv_num_of_pages")
    private Integer numberOfPages;

    @Column(name = "ddv_doc_height")
    private Integer docHeight;
    
    @Column(name = "ddv_doc_width")
    private Integer docWidth;
    
    @Column(name = "ddv_is_rendered")
    private Boolean isRendered;
    
    @Column(name = "dd_is_approved")
    private Boolean approved;

    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id", nullable = true)
    @ManyToOne(targetEntity = DmDocument.class)
    private DmDocument document;

    @JoinColumn(name = "da_id_modified_by", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount modifiedBy;

    public DmDocumentVersion() {
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(Integer majorVersion) {
        this.majorVersion = majorVersion;
    }

    public Integer getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(Integer minorVersion) {
        this.minorVersion = minorVersion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public DmAccount getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(DmAccount modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getTextPath() {
        return textPath;
    }

    public void setTextPath(String textPath) {
        this.textPath = textPath;
    }
    
    /***
     * @deprecated better use getTextPath() and then read the file
     */
    @Deprecated
    public String getTextContent() {
        return textContent;
    }

    /***
     * @deprecated better use setTextPath(String textPath) to save the text content of file.
     */
    @Deprecated
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getDocHeight() {
        return docHeight;
    }

    public void setDocHeight(Integer docHeight) {
        this.docHeight = docHeight;
    }

    public Integer getDocWidth() {
        return docWidth;
    }

    public void setDocWidth(Integer docWidth) {
        this.docWidth = docWidth;
    }

    public Boolean getIsRendered() {
        return isRendered;
    }

    public void setIsRendered(Boolean isRendered) {
        this.isRendered = isRendered;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
    
}
