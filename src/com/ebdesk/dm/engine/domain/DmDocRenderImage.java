/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmDocRenderImage")
@Table(name = "dm_doc_render_image")
public class DmDocRenderImage implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddri_id", nullable = false, length = 36)
    private String id;
    @Column(name = "ddri_file_path", length = 255)
    private String filePath;
    @Column(name = "ddri_page")
    private Integer page;

    @JoinColumn(name = "ddv_id", referencedColumnName = "ddv_id", nullable = true)
    @ManyToOne(targetEntity = DmDocumentVersion.class)
    private DmDocumentVersion docVersion;

    public DmDocRenderImage() {
    }

    public DmDocumentVersion getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(DmDocumentVersion docVersion) {
        this.docVersion = docVersion;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
