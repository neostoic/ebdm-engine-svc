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
public class DmDocTermFrequencyId implements Serializable {
    private String documentId;
    private String docTerm;

    public DmDocTermFrequencyId() {
    }

    public DmDocTermFrequencyId(String documentId, String docTerm) {
        this.documentId = documentId;
        this.docTerm = docTerm;
    }

    @Column(name="ddtf_term", nullable=false, length=250)
    public String getDocTerm() {
        return docTerm;
    }

    public void setDocTerm(String docTerm) {
        this.docTerm = docTerm;
    }

    @Column(name="dd_id", nullable=false, length=36)
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\n DmDocTermFrequencyId");
        sb.append("{documentId='").append(documentId).append('\'');
        sb.append(", docTerm='").append(docTerm).append('\'');
        sb.append('}');
        return sb.toString();
    }

   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DmDocTermFrequencyId) ) return false;
		 DmDocTermFrequencyId castOther = ( DmDocTermFrequencyId ) other;

		 return ( (this.getDocumentId()==castOther.getDocumentId()) || ( this.getDocumentId()!=null && castOther.getDocumentId()!=null && this.getDocumentId().equals(castOther.getDocumentId()) ) )
 && ( (this.getDocTerm()==castOther.getDocTerm()) || ( this.getDocTerm()!=null && castOther.getDocTerm()!=null && this.getDocTerm().equals(castOther.getDocTerm()) ) );
   }

   public int hashCode() {
         int result = 17;

         result = 37 * result + ( getDocumentId() == null ? 0 : this.getDocumentId().hashCode() );
         result = 251 * result + ( getDocTerm() == null ? 0 : this.getDocTerm().hashCode() );
         return result;
   }
}
