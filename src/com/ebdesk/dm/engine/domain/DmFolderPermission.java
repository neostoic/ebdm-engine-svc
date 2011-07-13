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
@Entity(name="com.ebdesk.dm.engine.domain.DmFolderPermission")
@Table(name = "dm_folder_permission")
public class DmFolderPermission implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "dfp_id", nullable = false, length = 36)
    private String id;
    @Column(name = "dfp_permission_type", nullable = false)
    private Integer permissionType;

    @JoinColumn(name = "df_id", referencedColumnName = "df_id", nullable = true)
    @ManyToOne(targetEntity = DmFolder.class)
    private DmFolder folder;

    @JoinColumn(name = "da_id", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount account;

    public DmFolderPermission() {
    }

    public DmAccount getAccount() {
        return account;
    }

    public void setAccount(DmAccount account) {
        this.account = account;
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

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }    
}
