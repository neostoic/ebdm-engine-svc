/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.solr;

import java.util.Date;
import java.util.List;
import org.apache.solr.client.solrj.beans.Field;

/**
 *
 * @author user
 */
public class IndexItem {
    @Field
    String id;

    @Field("doc_title")
    String docTitle;

    @Field("doc_desc")
    String docDesc;

    @Field("doc_author")
    String docAuthor;

    @Field("doc_keywords")
    String docKeywords;

    @Field("doc_owner_account_id")
    String docOwnerAccountId;

    @Field("doc_share_account_id")
    List<String> docShareAccountIdList;

    @Field("doc_folder_id")
    String docFolderId;

    @Field("doc_last_update")
    Date docLastUpdate;

    @Field("doc_create")
    Date docCreate;

    @Field("doc_size")
    Long docSize;

    public String getDocAuthor() {
        return docAuthor;
    }

    public void setDocAuthor(String docAuthor) {
        this.docAuthor = docAuthor;
    }

    public Date getDocCreate() {
        return docCreate;
    }

    public void setDocCreate(Date docCreate) {
        this.docCreate = docCreate;
    }

    public String getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String docDesc) {
        this.docDesc = docDesc;
    }

    public String getDocFolderId() {
        return docFolderId;
    }

    public void setDocFolderId(String docFolderId) {
        this.docFolderId = docFolderId;
    }

    public String getDocKeywords() {
        return docKeywords;
    }

    public void setDocKeywords(String docKeywords) {
        this.docKeywords = docKeywords;
    }

    public Date getDocLastUpdate() {
        return docLastUpdate;
    }

    public void setDocLastUpdate(Date docLastUpdate) {
        this.docLastUpdate = docLastUpdate;
    }

    public String getDocOwnerAccountId() {
        return docOwnerAccountId;
    }

    public void setDocOwnerAccountId(String docOwnerAccountId) {
        this.docOwnerAccountId = docOwnerAccountId;
    }

    public List<String> getDocShareAccountIdList() {
        return docShareAccountIdList;
    }

    public void setDocShareAccountIdList(List<String> docShareAccountIdList) {
        this.docShareAccountIdList = docShareAccountIdList;
    }

    public Long getDocSize() {
        return docSize;
    }

    public void setDocSize(Long docSize) {
        this.docSize = docSize;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
