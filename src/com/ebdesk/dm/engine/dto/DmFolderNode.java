/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dto;

/**
 *
 * @author user
 */
public class DmFolderNode {
    private String id;
    private String name;
    private Integer numChildren;
    private String ownerAccountId;
    private Integer permissionType;

    public DmFolderNode() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(Integer numChildren) {
        this.numChildren = numChildren;
    }

    public String getOwnerAccountId() {
        return ownerAccountId;
    }

    public void setOwnerAccountId(String ownerAccountId) {
        this.ownerAccountId = ownerAccountId;
    }

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }
//    private Boolean isRequestDraft;
//    private Boolean isRequestForChange;
    
}
