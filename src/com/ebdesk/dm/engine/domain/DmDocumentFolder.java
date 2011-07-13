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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentFolder")
@Table(name = "dm_document_folder")
public class DmDocumentFolder implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddf_id", nullable = false, length = 36)
    private String id;

    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id", nullable = true)
    @ManyToOne
    private DmDocument document;

    @JoinColumn(name = "df_id", referencedColumnName = "df_id", nullable = true)
    @ManyToOne
    private DmFolder folder;

    public DmDocumentFolder() {
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public DmFolder getFolder() {
        return folder;
    }

    public void setFolder(DmFolder folder) {
        this.folder = folder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
