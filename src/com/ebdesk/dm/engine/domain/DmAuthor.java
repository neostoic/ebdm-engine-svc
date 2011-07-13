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
@Entity(name="com.ebdesk.dm.engine.domain.DmAuthor")
@Table(name = "dm_author")
public class DmAuthor implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "da_id", nullable = false, length = 36)
    private String id;
    @Column(name = "da_name", length = 75, nullable = false)
    private String name;

    public DmAuthor() {
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
}
