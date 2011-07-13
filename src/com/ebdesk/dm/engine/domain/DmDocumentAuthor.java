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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentAuthor")
@Table(name = "dm_document_author")
public class DmDocumentAuthor implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "dda_id", nullable = false, length = 36)
    private String id;

    @JoinColumn(name = "da_id", referencedColumnName = "da_id", nullable = true)
    @ManyToOne(targetEntity = DmAuthor.class)
    private DmAuthor author;

    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id", nullable = true)
    @ManyToOne(targetEntity = DmDocument.class)
    private DmDocument document;

    public DmDocumentAuthor() {
    }

    public DmAuthor getAuthor() {
        return author;
    }

    public void setAuthor(DmAuthor author) {
        this.author = author;
    }

    public DmDocument getDocument() {
        return document;
    }

    public void setDocument(DmDocument document) {
        this.document = document;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
