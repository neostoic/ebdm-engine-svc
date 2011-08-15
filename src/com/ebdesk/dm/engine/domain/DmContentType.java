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
 * @author Muhammad Rifai
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmContentType")
//@Table(name = "dm_content_type", catalog = "eb_dm_engine", schema = "")
@Table(name = "dm_content_type")
public class DmContentType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dct_id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dct_file_extension", nullable = false, length = 255)
    private String fileExtension;
    @Basic(optional = false)
    @Column(name = "dct_name", nullable = false, length = 255)
    private String name;
    @Column(name = "dct_description", length = 255)
    private String description;

    public DmContentType() {
    }

    public DmContentType(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public DmContentType(String fileExtension, String name) {
        this.fileExtension = fileExtension;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileExtension != null ? fileExtension.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DmContentType)) {
            return false;
        }
        DmContentType other = (DmContentType) object;
        if ((this.fileExtension == null && other.fileExtension != null) || (this.fileExtension != null && !this.fileExtension.equals(other.fileExtension))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ebdesk.dm.engine.domain.DmContentType[ fileExtension=" + fileExtension + " ]";
    }
    
}
