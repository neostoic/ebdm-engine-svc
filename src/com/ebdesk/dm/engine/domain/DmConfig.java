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
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmConfig")
@Table(name = "dm_config")
public class DmConfig implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "dc_key", nullable = false, length = 255)
    private String key;
    @Column(name = "dc_value", length = 255)
    private String value;
    @Column(name = "dc_description", length = 4000)
    private String description;

    public DmConfig() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
