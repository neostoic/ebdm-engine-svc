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
@Entity(name="com.ebdesk.dm.engine.domain.DmAccount")
@Table(name = "dm_account")
public class DmAccount implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "da_id", nullable = false, length = 36)
    private String id;
    @Column(name = "da_name", length = 15, nullable = false)
    private String name;
    @Column(name = "da_quota")
    private Integer quota;
    @Column(name = "da_is_active")
    private Boolean isActive;
    @Column(name = "da_is_removed")
    private Boolean isRemoved;

    public DmAccount() {
    }
    
    public DmAccount(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Boolean isIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }
}


