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
@Entity(name="com.ebdesk.dm.engine.domain.DmDocTermFrequency")
@Table(name = "dm_doc_term_frequency")
public class DmDocTermFrequency implements Serializable {
    private DmDocTermFrequencyId id;
    private Long frequency;
    
    @EmbeddedId
    public DmDocTermFrequencyId getId() {
        return this.id;
    }

    public void setId(DmDocTermFrequencyId id) {
        this.id = id;
    }

    @Column(name = "ddtf_frequency", nullable = false)
    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n DmDocTermFrequency");
        sb.append("{id=").append(id);
//        sb.append(", ebWfProcessDefinitions=").append(ebWfProcessDefinitions);
        sb.append('}');
        return sb.toString();
    }

}
