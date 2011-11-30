/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author user
 */
@Embeddable
public class DmFolderTermFrequencyId implements Serializable {
    private String folderId;
    private String folderTerm;

    public DmFolderTermFrequencyId() {
    }

    public DmFolderTermFrequencyId(String folderId, String folderTerm) {
        this.folderId = folderId;
        this.folderTerm = folderTerm;
    }

    @Column(name="dftf_term", nullable=false, length=250)
    public String getFolderTerm() {
        return folderTerm;
    }

    public void setFolderTerm(String folderTerm) {
        this.folderTerm = folderTerm;
    }

    @Column(name="df_id", nullable=false, length=36)
    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n DmFolderTermFrequencyId");
        sb.append("{folderId='").append(folderId).append('\'');
        sb.append(", folderTerm='").append(folderTerm).append('\'');
        sb.append('}');
        return sb.toString();
    }

   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DmFolderTermFrequencyId) ) return false;
		 DmFolderTermFrequencyId castOther = ( DmFolderTermFrequencyId ) other;

		 return ( (this.getFolderId()==castOther.getFolderId()) || ( this.getFolderId()!=null && castOther.getFolderId()!=null && this.getFolderId().equals(castOther.getFolderId()) ) )
 && ( (this.getFolderTerm()==castOther.getFolderTerm()) || ( this.getFolderTerm()!=null && castOther.getFolderTerm()!=null && this.getFolderTerm().equals(castOther.getFolderTerm()) ) );
   }

   public int hashCode() {
         int result = 17;

         result = 37 * result + ( getFolderId() == null ? 0 : this.getFolderId().hashCode() );
         result = 251 * result + ( getFolderTerm() == null ? 0 : this.getFolderTerm().hashCode() );
         return result;
   }
}

