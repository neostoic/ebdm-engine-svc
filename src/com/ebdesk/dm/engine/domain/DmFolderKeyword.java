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
@Entity(name="com.ebdesk.dm.engine.domain.DmFolderKeyword")
@Table(name = "dm_folder_keyword")
public class DmFolderKeyword implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "dfk_id", nullable = false, length = 36)
    private String id;
    @Column(name = "dfk_term", length = 50, nullable = false)
    private String term;
    @Column(name = "dfk_rank", nullable = false)
    private Integer rank;

    @JoinColumn(name = "df_id", referencedColumnName = "df_id", nullable = true)
    @ManyToOne(targetEntity = DmFolder.class)
    private DmFolder folder;

    public DmFolderKeyword() {
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
