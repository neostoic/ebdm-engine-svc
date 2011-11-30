/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author user
 */
@Entity(name="com.ebdesk.dm.engine.domain.DmFolderTermFrequency")
@Table(name = "dm_folder_term_frequency")
public class DmFolderTermFrequency implements Serializable {
    private DmFolderTermFrequencyId id;
    private Long frequency;

    @EmbeddedId
    public DmFolderTermFrequencyId getId() {
        return this.id;
    }

    public void setId(DmFolderTermFrequencyId id) {
        this.id = id;
    }

    @Column(name = "dftf_frequency", nullable = false)
    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n DmFolderTermFrequency");
        sb.append("{id=").append(id);
//        sb.append(", ebWfProcessDefinitions=").append(ebWfProcessDefinitions);
        sb.append('}');
        return sb.toString();
    }

}
