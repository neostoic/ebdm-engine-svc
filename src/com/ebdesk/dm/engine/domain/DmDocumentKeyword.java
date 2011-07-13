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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocumentKeyword")
@Table(name = "dm_document_keyword")
public class DmDocumentKeyword implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "ddk_id", nullable = false, length = 36)
    private String id;
    @Column(name = "ddk_term", length = 50, nullable = false)
    private String term;
    @Column(name = "ddk_rank", nullable = false)
    private Integer rank;

    @JoinColumn(name = "dd_id", referencedColumnName = "dd_id", nullable = true)
    @ManyToOne(targetEntity = DmDocument.class)
    private DmDocument document;

    public DmDocumentKeyword() {
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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
