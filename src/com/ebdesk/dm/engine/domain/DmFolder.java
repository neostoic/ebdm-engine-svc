/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmFolder")
@Table(name = "dm_folder")
public class DmFolder implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "df_id", nullable = false, length = 36)
    private String id;
    @Column(name = "df_name", length = 50, nullable = false)
    private String name;
    @Column(name = "df_description", length = 250)
    private String description;
    @Column(name = "df_is_need_approval")
    private Boolean isNeedApproval;

    @JoinColumn(name = "df_parent_id", referencedColumnName = "df_id", nullable = true)
    @ManyToOne(targetEntity = DmFolder.class)
    private DmFolder parent;

    @JoinColumn(name = "da_id_owner", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount owner;

    @JoinColumn(name = "da_id_modified_by", referencedColumnName = "da_id", nullable = true)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount modifiedBy;

    @JoinColumn(name = "da_id_created_by", referencedColumnName = "da_id", nullable = true)
    // should be:
//    @JoinColumn(name = "da_id_created_by", referencedColumnName = "da_id", nullable = false)
    @ManyToOne(targetEntity = DmAccount.class)
    private DmAccount createdBy;

//    @OneToMany(targetEntity=DmFolder.class, mappedBy = "parent", fetch=FetchType.EAGER)
    @OneToMany(targetEntity=DmFolder.class, mappedBy = "parent", fetch=FetchType.LAZY)
    private List<DmFolder> childList;

    @OneToMany(targetEntity=DmFolderPermission.class, mappedBy = "folder", fetch=FetchType.LAZY)
    private List<DmFolderPermission> permissionList;

    public DmFolder() {
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

    public Boolean getIsNeedApproval() {
        return isNeedApproval;
    }

    public void setIsNeedApproval(Boolean isNeedApproval) {
        this.isNeedApproval = isNeedApproval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DmFolder getParent() {
        return parent;
    }

    public void setParent(DmFolder parent) {
        this.parent = parent;
    }

    public DmAccount getOwner() {
        return owner;
    }

    public void setOwner(DmAccount owner) {
        this.owner = owner;
    }

    public DmAccount getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(DmAccount modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<DmFolder> getChildList() {
        return childList;
}

    public void setChildList(List<DmFolder> childList) {
        this.childList = childList;
    }

    public List<DmFolderPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<DmFolderPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public DmAccount getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(DmAccount createdBy) {
        this.createdBy = createdBy;
    }
}
