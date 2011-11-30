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
@Entity(name="com.ebdesk.dm.engine.domain.DmTagRankingStopWord")
@Table(name = "dm_tag_ranking_stop_word")
public class DmTagRankingStopWord implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "dtrsw_stop_word", length = 250, nullable = false)
    private String stopWord;

    public DmTagRankingStopWord() {
    }

    public String getStopWord() {
        return stopWord;
    }

    public void setStopWord(String stopWord) {
        this.stopWord = stopWord;
    }
}
