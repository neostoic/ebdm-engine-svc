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
@Entity(name="com.ebdesk.dm.engine.domain.DmFolderRelated")
@Table(name = "dm_folder_related")
public class DmFolderRelated implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "dfr_id", nullable = false, length = 36)
    private String id;

    @JoinColumn(name = "df_id", referencedColumnName = "df_id", nullable = true)
    @ManyToOne(targetEntity = DmFolder.class)
    private DmFolder folder;

    @JoinColumn(name = "df_id_related", referencedColumnName = "df_id", nullable = true)
    @ManyToOne(targetEntity = DmFolder.class)
    private DmFolder folderRelated;

    public DmFolderRelated() {
    }

    public DmFolder getFolder() {
        return folder;
    }

    public void setFolder(DmFolder folder) {
        this.folder = folder;
    }

    public DmFolder getFolderRelated() {
        return folderRelated;
    }

    public void setFolderRelated(DmFolder folderRelated) {
        this.folderRelated = folderRelated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
