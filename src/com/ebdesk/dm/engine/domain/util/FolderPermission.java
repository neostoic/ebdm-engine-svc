/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.domain.util;

/**
 *
 * @author designer
 */
public enum FolderPermission {
    
    NO_PERMISSION(0),
    
    OWNER(1),

    WRITER(2),

    CONSUMER(3),
    
    READER(4);
    
    private int permission;

    FolderPermission(int permission) {
        this.permission = permission;
    }

    public int getPermission() {
        return permission;
    }   
}
