/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.solr;

import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentAuthor;
import com.ebdesk.dm.engine.domain.DmDocumentFolder;
import com.ebdesk.dm.engine.domain.DmDocumentKeyword;
import com.ebdesk.dm.engine.domain.DmFolderPermission;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.handler.extraction.ExtractingParams;


/**
 *
 * @author user
 */
public class EbDmSolr {
    private String url = null;
    private final String[] file_types = {"application/msword", "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "text/plain", "application/pdf", "application/octet-stream", "application/xml", "text/xml", "application/xhtml+xml", "text/html", "text/csv"};

    public EbDmSolr(String url) {
        this.url = url;
    }

    public void indexDocument(DmDocument document) {
        System.out.println("begin - indexDocument");

        File file = null;
        if (document.getLastVersion() != null) {
            file = new File(document.getLastVersion().getFilePath());
        }

//        String contentType = new MimetypesFileTypeMap().getContentType(file);

//        System.out.println("contentType: " + contentType);

        // CHECK MIME TYPE OF FILE TO MAKE SURE ITS ALLOWED
        boolean fileTypeAllowed = false;
        for(String type : file_types){
//            if(type.equals(contentType)) fileTypeAllowed = true;
            if(type.equals(document.getMimeType())) fileTypeAllowed = true;
        }

        if(fileTypeAllowed){
            System.out.println("fileTypeAllowed");
//            try {
//                SolrServer server = getServer();
//                SolrInputDocument inputDoc = new SolrInputDocument();
//                inputDoc.addField( "id", "id1", 1.0f );
//                server.add(inputDoc);
//                server.commit();
//            }
//            catch (MalformedURLException ex) {
//                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            catch (SolrServerException ex) {
//                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            catch (IOException ex) {
//                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
//            }
            try {
                SolrServer server = getServer();
                ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");
                req.addFile(file);
//                req.setParam("literal.id", document.getId());
//                req.setParam("literal.doc_title", document.getTitle());
//                req.setParam("literal.doc_desc", document.getDescription());

                List<DmDocumentAuthor> docAuthorList = document.getDocumentAuthorList();
                String authors = "";
                if (docAuthorList != null) {
                    int i = 0;
                    for (DmDocumentAuthor docAuthor : docAuthorList) {
                        if (i > 0) {
                            authors = authors + ",";
                        }
                        authors = authors + docAuthor.getAuthor().getName();
                        i++;
                    }
                }
//                req.setParam("literal.doc_author", authors);
                
                List<DmDocumentKeyword> keywordList = document.getDocumentKeywordList();
                String keywords = "";
                if (keywordList != null) {
                    int i = 0;
                    for (DmDocumentKeyword keyword : keywordList) {
                        if (i > 0) {
                            keywords = keywords + ",";
                        }
                        keywords = keywords + keyword.getTerm();
                        i++;
                    }
                }
//                req.setParam("literal.doc_keywords", keywords);

                ModifiableSolrParams listShareAccountId = new ModifiableSolrParams();
                List<DmDocumentFolder> docFolderList = document.getDocumentFolderList();
                String ownerAccountId = "";
                String folderId = "";
                String listShareAccountIdStr = "";        
                if (docFolderList != null) {
                    for (DmDocumentFolder docFolder : docFolderList) {
                        // must be only 1 folder

                        ownerAccountId = docFolder.getFolder().getOwner().getId();
                        folderId = docFolder.getFolder().getId();

                        List<DmFolderPermission> permissionList = docFolder.getFolder().getPermissionList();
                        if (permissionList != null) {
//                            System.out.println("permissionList.size() = " + permissionList.size());
                            for (DmFolderPermission permission : permissionList) {
//                                System.out.println("permission.getAccount().getId() = " + permission.getAccount().getId());
                                if (!ownerAccountId.equals(permission.getAccount().getId())) {
                                    if (!listShareAccountIdStr.isEmpty()) {
                                        listShareAccountIdStr = listShareAccountIdStr + ",";
                                    }
                                    listShareAccountIdStr = listShareAccountIdStr + permission.getAccount().getId();
                                    listShareAccountId.add(ExtractingParams.LITERALS_PREFIX + "doc_share_account_id", permission.getAccount().getId());
                                }
                            }
                        }

                        break;
                    }
                }
                
//                req.setParam("literal.doc_share_account_id", listShareAccountIdStr);
                req.setParams(listShareAccountId);
                req.setParam("literal.doc_owner_account_id", ownerAccountId);
                req.setParam("literal.doc_folder_id", folderId);

                req.setParam("literal.doc_last_update", document.getLastModifiedTime().toString());
                req.setParam("literal.doc_create", document.getCreatedTime().toString());
                req.setParam("literal.doc_size", document.getLastVersion().getSize().toString());

                req.setParam("literal.id", document.getId());
                req.setParam("literal.doc_title", document.getTitle());
                req.setParam("literal.doc_desc", document.getDescription());
                req.setParam("literal.doc_author", authors);
                req.setParam("literal.doc_keywords", keywords);
                
//                req.setParam("literal.file_name", file.getName());
//                req.setParam("literal.last_update", new Date().toString());

//	<field name="doc_share_account_id" type="string" indexed="true" stored="true" multiValued="true"/>

                req.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
                server.request(req);
                server.commit();
            } catch (IOException ex) {
                System.out.println("ex: " + ex.getMessage());
                System.out.println("eBSolr Exception : Parsing file "+file.getName()+" failed");
                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SolrServerException ex) {
                System.out.println("ex: " + ex.getMessage());
                System.out.println("eBSolr Exception : Parsing file "+file.getName()+" failed");
                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                System.out.println("ex: " + ex.getMessage());
                System.out.println("eBSolr Exception : Parsing file "+file.getName()+" failed");
                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            try {
                SolrServer server = getServer();
                SolrInputDocument inputDoc = new SolrInputDocument();
                inputDoc.addField("id", document.getId());
                inputDoc.addField("doc_title", document.getTitle());
                inputDoc.addField("doc_desc", document.getDescription());

                List<DmDocumentAuthor> docAuthorList = document.getDocumentAuthorList();
                String authors = "";
                if (docAuthorList != null) {
                    int i = 0;
                    for (DmDocumentAuthor docAuthor : docAuthorList) {
                        if (i > 0) {
                            authors = authors + ",";
                        }
                        authors = authors + docAuthor.getAuthor().getName();
                        i++;
                    }
                }
                inputDoc.addField("doc_author", authors);

                List<DmDocumentKeyword> keywordList = document.getDocumentKeywordList();
                String keywords = "";
                if (keywordList != null) {
                    int i = 0;
                    for (DmDocumentKeyword keyword : keywordList) {
                        if (i > 0) {
                            keywords = keywords + ",";
                        }
                        keywords = keywords + keyword.getTerm();
                        i++;
                    }
                }
                inputDoc.addField("doc_keywords", keywords);

                List<DmDocumentFolder> docFolderList = document.getDocumentFolderList();
                String ownerAccountId = "";
                String folderId = "";
                String listShareAccountIdStr = "";
                List<String> listShareAccountId = new ArrayList<String>();
                if (docFolderList != null) {
                    for (DmDocumentFolder docFolder : docFolderList) {
                        // must be only 1 folder

                        ownerAccountId = docFolder.getFolder().getOwner().getId();
                        folderId = docFolder.getFolder().getId();

                        List<DmFolderPermission> permissionList = docFolder.getFolder().getPermissionList();
//                        System.out.println("permissionList");
                        if (permissionList != null) {
//                            System.out.println("permissionList.size() = " + permissionList.size());
                            for (DmFolderPermission permission : permissionList) {
//                                System.out.println("permission.getAccount().getId() = " + permission.getAccount().getId());
                                if (!ownerAccountId.equals(permission.getAccount().getId())) {
                                    if (!listShareAccountIdStr.isEmpty()) {
                                        listShareAccountIdStr = listShareAccountIdStr + ",";
                                    }
                                    listShareAccountIdStr = listShareAccountIdStr + permission.getAccount().getId();
                                    listShareAccountId.add(permission.getAccount().getId());
                                }
                            }
                        }

                        break;
                    }
                }
                
//                inputDoc.addField("doc_share_account_id", listShareAccountIdStr);
                inputDoc.addField("doc_share_account_id", listShareAccountId);
                inputDoc.addField("doc_owner_account_id", ownerAccountId);
                inputDoc.addField("doc_folder_id", folderId);
                
                inputDoc.addField("doc_last_update", document.getLastModifiedTime());
                inputDoc.addField("doc_create", document.getCreatedTime());
                inputDoc.addField("doc_size", document.getLastVersion().getSize());
                server.add(inputDoc);
                server.commit();

//	<field name="doc_title" type="text_general" indexed="true" stored="true"/>
//	<field name="doc_desc" type="text_general" indexed="true" stored="true"/>
//	<field name="doc_author" type="text_general" indexed="true" stored="true"/>
//	<field name="doc_keywords" type="text_general" indexed="true" stored="true"/>
//	<field name="doc_owner_account_id" type="string" indexed="true" stored="true"/>
//	<field name="doc_share_account_id" type="string" indexed="true" stored="true"/>
//	<field name="doc_folder_id" type="string" indexed="true" stored="true"/>
//	<field name="doc_last_update" type="date" indexed="true" stored="true"/>
//	<field name="doc_create" type="date" indexed="true" stored="true"/>
//	<field name="doc_size" type="long" indexed="true" stored="true" />

            }
            catch (MalformedURLException ex) {
                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (SolrServerException ex) {
                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(EbDmSolr.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("end - indexDocument");
    }

    public void deleteTest(String id) throws MalformedURLException, SolrServerException, IOException{
        SolrServer server = getServer();
        server.deleteById(id);

        server.commit();
    }

    public void deleteIndexDocument(String documentId) throws MalformedURLException, SolrServerException, IOException{
        SolrServer server = getServer();
        server.deleteById(documentId);

        server.commit();
    }

    public SolrServer getServer() throws MalformedURLException{
        return new CommonsHttpSolrServer(this.getUrl());
    }

    private String getUrl(){
        return url;
    }

    private String createQuerySearchSimple(String keyword, List<String> ownerAccountIdList) {
        String keywordTrimmed = trimKeyword(keyword);

        String queryStr1 = "";
        if (!keywordTrimmed.isEmpty()) {
            keywordTrimmed = keywordTrimmed.replace(" ", " +");
            keywordTrimmed = "+" + keywordTrimmed;
            queryStr1 = "text:(" + keywordTrimmed + ")";
//            queryStr1 = "doc_title:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR doc_desc:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR doc_keywords:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR doc_author:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR text:(" + keywordTrimmed + ")";
        }

        String queryStr2 = "";
        if (ownerAccountIdList != null) {
            for (String ownerAccountId : ownerAccountIdList) {
                if (!queryStr2.isEmpty()) {
                    queryStr2 = queryStr2 + " OR ";
                }
                queryStr2 = queryStr2 + "doc_owner_account_id:" + ownerAccountId;
            }
        }

//        String queryStr = queryStr1;
//        if (!queryStr2.isEmpty()) {
//            queryStr = "(" + queryStr + ") AND (" + queryStr2 + ")";
//        }
        String queryStr = "";
        if (!queryStr1.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr1 + ")";
        }
        if (!queryStr2.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr2 + ")";
        }
//        System.out.println("query = " + queryStr);

        if (queryStr.isEmpty()) {
            queryStr = "*";
        }
        
        return queryStr;
    }

    public List<IndexItem> searchSimple(String keyword, List<String> ownerAccountIdList, int start, int limit) {
        try {
//            System.out.println("searchFiles");
            SolrServer server = getServer();
//            System.out.println("getServer");

            /* Execute Query & get the Response */
            String queryStr = createQuerySearchSimple(keyword, ownerAccountIdList);
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery(queryStr);
            solrQuery.setStart(start);
            solrQuery.setRows(limit);

    //        if(startDate != null && startDate != endDate)
    //            solrQuery.addFilterQuery("last_update:["+convertDateToQueryString(startDate)+" TO "+convertDateToQueryString(endDate)+"]");

            String keywordTrimmed = trimKeyword(keyword);
            if (keywordTrimmed.equals("")) {
                solrQuery.addSortField("doc_last_update", SolrQuery.ORDER.desc);
            }
//            System.out.println("query");
            QueryResponse response = server.query(solrQuery);
//            System.out.println("response");

            /* Get Documents result */
            return response.getBeans(IndexItem.class);
        }
        catch (MalformedURLException ex) {
            return null;
        }
        catch (SolrServerException ex) {
            return null;
        }
    }

    public int searchSimpleGetNumResult(String keyword, List<String> ownerAccountIdList) {
        try {
            SolrServer server = getServer();

            /* Execute Query & get the Response */
            SolrQuery solrQuery = new  SolrQuery();
            String queryStr = createQuerySearchSimple(keyword, ownerAccountIdList);
            solrQuery.setQuery(queryStr);

            QueryResponse response = server.query(solrQuery);

            /* Get the results */
            return Integer.parseInt(String.valueOf(response.getResults().getNumFound()));
        }
        catch (MalformedURLException ex) {
            return 0;
        }
        catch (SolrServerException ex) {
            return 0;
        }
    }

    private String createQuerySearchSimpleShared(String keyword, List<String> shareAccountIdList) {
        String keywordTrimmed = trimKeyword(keyword);

        String queryStr1 = "";
        if (!keywordTrimmed.isEmpty()) {
            keywordTrimmed = keywordTrimmed.replace(" ", " +");
            keywordTrimmed = "+" + keywordTrimmed;
            queryStr1 = "text:(" + keywordTrimmed + ")";
//            queryStr1 = "doc_title:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR doc_desc:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR doc_keywords:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR doc_author:(" + keywordTrimmed + ")";
//            queryStr1 = queryStr1 + " OR text:(" + keywordTrimmed + ")";
        }

        String queryStr2 = "";
        if (shareAccountIdList != null) {
            for (String shareAccountId : shareAccountIdList) {
                if (!queryStr2.isEmpty()) {
                    queryStr2 = queryStr2 + " OR ";
                }
                queryStr2 = queryStr2 + "doc_share_account_id:" + shareAccountId;
            }
        }

//        String queryStr = queryStr1;
//        if (!queryStr2.isEmpty()) {
//            queryStr = "(" + queryStr + ") AND (" + queryStr2 + ")";
//        }
        String queryStr = "";
        if (!queryStr1.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr1 + ")";
        }
        if (!queryStr2.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr2 + ")";
        }
//        System.out.println("query = " + queryStr);

        if (queryStr.isEmpty()) {
            queryStr = "*";
        }
        
        return queryStr;
    }

    public List<IndexItem> searchSimpleShared(String keyword, List<String> shareAccountIdList, int start, int limit) {
        try {
            System.out.println("searchFiles");
            SolrServer server = getServer();
            System.out.println("getServer");

            /* Execute Query & get the Response */
            String queryStr = createQuerySearchSimpleShared(keyword, shareAccountIdList);
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery(queryStr);
            solrQuery.setStart(start);
            solrQuery.setRows(limit);

    //        if(startDate != null && startDate != endDate)
    //            solrQuery.addFilterQuery("last_update:["+convertDateToQueryString(startDate)+" TO "+convertDateToQueryString(endDate)+"]");

            String keywordTrimmed = trimKeyword(keyword);
            if (keywordTrimmed.equals("")) {
                solrQuery.addSortField("doc_last_update", SolrQuery.ORDER.desc);
            }
            System.out.println("query");
            QueryResponse response = server.query(solrQuery);
            System.out.println("response");

            /* Get Documents result */
           return response.getBeans(IndexItem.class);
        }
        catch (MalformedURLException ex) {
            return null;
        }
        catch (SolrServerException ex) {
            return null;
        }
    }

    public int searchSimpleSharedGetNumResult(String keyword, List<String> shareAccountIdList) {
        try {
            SolrServer server = getServer();

            /* Execute Query & get the Response */
            SolrQuery solrQuery = new  SolrQuery();
            String queryStr = createQuerySearchSimpleShared(keyword, shareAccountIdList);
            solrQuery.setQuery(queryStr);

            QueryResponse response = server.query(solrQuery);

            /* Get the results */
            return Integer.parseInt(String.valueOf(response.getResults().getNumFound()));
        }
        catch (MalformedURLException ex) {
            return 0;
        }
        catch (SolrServerException ex) {
            return 0;
        }
    }

    private String trimKeyword(String keyword){
//        if(keyword.trim().equals("")) return "*";
        if (keyword != null) {
            return keyword.trim();
        }
        else {
            return "";
        }
    }

    private String createQuerySearchAdvanced(String keywordsAnd, String keywordsOr, String keywordNot
            , Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList
        ) {

        String keywordAndTrimmed = trimKeyword(keywordsAnd);
        String keywordOrTrimmed = trimKeyword(keywordsOr);
        String keywordNotTrimmed = trimKeyword(keywordNot);

        // keywords
        String queryStr1 = "";
        String queryStr1A = "";
        String queryStr1B = "";
        String queryStr1C = "";
        if (!keywordAndTrimmed.isEmpty() || !keywordOrTrimmed.isEmpty() || !keywordNotTrimmed.isEmpty()) {
            if (!keywordAndTrimmed.isEmpty()) {
                keywordAndTrimmed = keywordAndTrimmed.replace(" ", " +");
                keywordAndTrimmed = "+" + keywordAndTrimmed;
//                queryStr1A = "doc_title:(" + keywordAndTrimmed + ")";
//                queryStr1A = queryStr1A + " OR doc_desc:(" + keywordAndTrimmed + ")";
//                queryStr1A = queryStr1A + " OR doc_keywords:(" + keywordAndTrimmed + ")";
//                queryStr1A = queryStr1A + " OR doc_author:(" + keywordAndTrimmed + ")";
                if (isSearchIncludeContent) {
                    queryStr1A = queryStr1A + "text:(" + keywordAndTrimmed + ")";
                }
                else {
                    queryStr1A = queryStr1A + "doc_terms:(" + keywordAndTrimmed + ")";
                }
                if (!queryStr1.isEmpty()) {
                    queryStr1 = queryStr1 + " AND ";
                }
                queryStr1 = queryStr1 + "(" + queryStr1A + ")";
            }
            if (!keywordOrTrimmed.isEmpty()) {
//                queryStr1B = "doc_title:(" + keywordOrTrimmed + ")";
//                queryStr1B = queryStr1B + " OR doc_desc:(" + keywordOrTrimmed + ")";
//                queryStr1B = queryStr1B + " OR doc_keywords:(" + keywordOrTrimmed + ")";
//                queryStr1B = queryStr1B + " OR doc_author:(" + keywordOrTrimmed + ")";
                if (isSearchIncludeContent) {
                    queryStr1B = queryStr1B + "text:(" + keywordOrTrimmed + ")";
                }
                else {
                    queryStr1B = queryStr1B + "doc_terms:(" + keywordOrTrimmed + ")";
                }
                if (!queryStr1.isEmpty()) {
                    queryStr1 = queryStr1 + " AND ";
                }
                queryStr1 = queryStr1 + "(" + queryStr1B + ")";
            }
            if (!keywordNotTrimmed.isEmpty()) {
//                keywordNotTrimmed = keywordNotTrimmed.replace(" ", " -");
//                keywordNotTrimmed = "-" + keywordNotTrimmed;
//                queryStr1C = "-doc_title:(" + keywordNotTrimmed + ")";
//                queryStr1C = queryStr1C + " AND -doc_desc:(" + keywordNotTrimmed + ")";
//                queryStr1C = queryStr1C + " AND -doc_keywords:(" + keywordNotTrimmed + ")";
//                queryStr1C = queryStr1C + " AND -doc_author:(" + keywordNotTrimmed + ")";
                if (isSearchIncludeContent) {
                    queryStr1C = queryStr1C + "-text:(" + keywordNotTrimmed + ")";
                }
                else {
                    queryStr1C = queryStr1C + "-doc_terms:(" + keywordNotTrimmed + ")";
                }
                if (!queryStr1.isEmpty()) {
                    queryStr1 = queryStr1 + " AND ";
                }
//                queryStr1 = queryStr1 + "(" + queryStr1C + ")";
                queryStr1 = queryStr1 + queryStr1C;
            }
        }

        // owner
        String queryStr2 = "";
        if (ownerAccountIdList != null) {
            for (String ownerAccountId : ownerAccountIdList) {
                if (!queryStr2.isEmpty()) {
                    queryStr2 = queryStr2 + " OR ";
                }
                queryStr2 = queryStr2 + "doc_owner_account_id:" + ownerAccountId;
            }
        }

        // size
        String queryStr3 = "";
        String sizeMinStr = "*";
        String sizeLimitStr = "*";
        if ((sizeMin != null) || (sizeLimit != null)) {
            if (sizeMin != null) {
                sizeMinStr = Long.toString(sizeMin);
            }
            if (sizeLimit != null) {
                sizeLimitStr = Long.toString(sizeLimit);
            }
            queryStr3 = "doc_size:[" + sizeMinStr + " TO " + sizeLimitStr + "]";
        }

        // date
        String queryStr4 = "";
        String createDateFromStr = "*";
        String createDateToStr = "*";
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T00:00:00Z'");
        if ((createDateFrom != null) || (createDateTo != null)) {
            if (createDateFrom != null) {
                createDateFromStr = dateFormat.format(createDateFrom);
            }
            if (createDateTo != null) {
                createDateToStr = dateFormat.format(createDateTo);
            }
            queryStr4 = "doc_create:[" + createDateFromStr + " TO " + createDateToStr + "]";
        }

        // folder
        String queryStr5 = "";
        if (folderId != null) {
            queryStr5 = "doc_folder_id:" + folderId;
        }

        String queryStr = "";
        if (!queryStr1.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
//            queryStr = queryStr + "(" + queryStr1 + ")";
            queryStr = queryStr + queryStr1;
        }
        if (!queryStr2.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr2 + ")";
        }
        if (!queryStr3.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr3 + ")";
        }
        if (!queryStr4.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr4 + ")";
        }
        if (!queryStr5.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr5 + ")";
        }
//        System.out.println("query = " + queryStr);

        if (queryStr.isEmpty()) {
            queryStr = "*";
        }
        
        return queryStr;
    }

    public List<IndexItem> searchAdvanced(String keywordsAnd, String keywordsOr, String keywordNot
            , Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList, int start, int limit
        ) {
        try {
//            System.out.println("searchFiles");
            SolrServer server = getServer();
//            System.out.println("getServer");

            /* Execute Query & get the Response */
            String queryStr = createQuerySearchAdvanced(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, ownerAccountIdList);
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery(queryStr);
            solrQuery.setStart(start);
            solrQuery.setRows(limit);

    //        if(startDate != null && startDate != endDate)
    //            solrQuery.addFilterQuery("last_update:["+convertDateToQueryString(startDate)+" TO "+convertDateToQueryString(endDate)+"]");

//            String keywordTrimmed = trimKeyword(keyword);
//            if (keywordTrimmed.equals("")) {
//                solrQuery.addSortField("doc_last_update", SolrQuery.ORDER.desc);
//            }
//            System.out.println("query");
            QueryResponse response = server.query(solrQuery);
//            System.out.println("response");

            /* Get Documents result */
            return response.getBeans(IndexItem.class);
        }
        catch (MalformedURLException ex) {
            return null;
        }
        catch (SolrServerException ex) {
            return null;
        }
    }

    public int searchAdvancedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot
            , Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList
        ) {
        try {
            SolrServer server = getServer();

            /* Execute Query & get the Response */
            SolrQuery solrQuery = new  SolrQuery();
            String queryStr = createQuerySearchAdvanced(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, ownerAccountIdList);
            solrQuery.setQuery(queryStr);

            QueryResponse response = server.query(solrQuery);

            /* Get the results */
            return Integer.parseInt(String.valueOf(response.getResults().getNumFound()));
        }
        catch (MalformedURLException ex) {
            return 0;
        }
        catch (SolrServerException ex) {
            return 0;
        }
    }

    private String createQuerySearchAdvancedShared(String keywordsAnd, String keywordsOr, String keywordNot
            , Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList
        ) {

        String keywordAndTrimmed = trimKeyword(keywordsAnd);
        String keywordOrTrimmed = trimKeyword(keywordsOr);
        String keywordNotTrimmed = trimKeyword(keywordNot);

        // keywords
        String queryStr1 = "";
        String queryStr1A = "";
        String queryStr1B = "";
        String queryStr1C = "";
        if (!keywordAndTrimmed.isEmpty() || !keywordOrTrimmed.isEmpty() || !keywordNotTrimmed.isEmpty()) {
            if (!keywordAndTrimmed.isEmpty()) {
                keywordAndTrimmed = keywordAndTrimmed.replace(" ", " +");
                keywordAndTrimmed = "+" + keywordAndTrimmed;
//                queryStr1A = "doc_title:(" + keywordAndTrimmed + ")";
//                queryStr1A = queryStr1A + " OR doc_desc:(" + keywordAndTrimmed + ")";
//                queryStr1A = queryStr1A + " OR doc_keywords:(" + keywordAndTrimmed + ")";
//                queryStr1A = queryStr1A + " OR doc_author:(" + keywordAndTrimmed + ")";
                if (isSearchIncludeContent) {
                    queryStr1A = queryStr1A + "text:(" + keywordAndTrimmed + ")";
                }
                else {
                    queryStr1A = queryStr1A + "doc_terms:(" + keywordAndTrimmed + ")";
                }
                if (!queryStr1.isEmpty()) {
                    queryStr1 = queryStr1 + " AND ";
                }
                queryStr1 = queryStr1 + "(" + queryStr1A + ")";
            }
            if (!keywordOrTrimmed.isEmpty()) {
//                queryStr1B = "doc_title:(" + keywordOrTrimmed + ")";
//                queryStr1B = queryStr1B + " OR doc_desc:(" + keywordOrTrimmed + ")";
//                queryStr1B = queryStr1B + " OR doc_keywords:(" + keywordOrTrimmed + ")";
//                queryStr1B = queryStr1B + " OR doc_author:(" + keywordOrTrimmed + ")";
                if (isSearchIncludeContent) {
                    queryStr1B = queryStr1B + "text:(" + keywordOrTrimmed + ")";
                }
                else {
                    queryStr1B = queryStr1B + "doc_terms:(" + keywordOrTrimmed + ")";
                }
                if (!queryStr1.isEmpty()) {
                    queryStr1 = queryStr1 + " AND ";
                }
                queryStr1 = queryStr1 + "(" + queryStr1B + ")";
            }
            if (!keywordNotTrimmed.isEmpty()) {
//                keywordNotTrimmed = keywordNotTrimmed.replace(" ", " -");
//                keywordNotTrimmed = "-" + keywordNotTrimmed;
//                queryStr1C = "-doc_title:(" + keywordNotTrimmed + ")";
//                queryStr1C = queryStr1C + " AND -doc_desc:(" + keywordNotTrimmed + ")";
//                queryStr1C = queryStr1C + " AND -doc_keywords:(" + keywordNotTrimmed + ")";
//                queryStr1C = queryStr1C + " AND -doc_author:(" + keywordNotTrimmed + ")";
                if (isSearchIncludeContent) {
                    queryStr1C = queryStr1C + "-text:(" + keywordNotTrimmed + ")";
                }
                else {
                    queryStr1C = queryStr1C + "-doc_terms:(" + keywordNotTrimmed + ")";
                }
                if (!queryStr1.isEmpty()) {
                    queryStr1 = queryStr1 + " AND ";
                }
//                queryStr1 = queryStr1 + "(" + queryStr1C + ")";
                queryStr1 = queryStr1 + queryStr1C;
            }
        }

        // shared account
        String queryStr2 = "";
        if (shareAccountIdList != null) {
            for (String ownerAccountId : shareAccountIdList) {
                if (!queryStr2.isEmpty()) {
                    queryStr2 = queryStr2 + " OR ";
                }
                queryStr2 = queryStr2 + "doc_share_account_id:" + ownerAccountId;
            }
        }

        // size
        String queryStr3 = "";
        String sizeMinStr = "*";
        String sizeLimitStr = "*";
        if ((sizeMin != null) || (sizeLimit != null)) {
            if (sizeMin != null) {
                sizeMinStr = Long.toString(sizeMin);
            }
            if (sizeLimit != null) {
                sizeLimitStr = Long.toString(sizeLimit);
            }
            queryStr3 = "doc_size:[" + sizeMinStr + " TO " + sizeLimitStr + "]";
        }

        // date
        String queryStr4 = "";
        String createDateFromStr = "*";
        String createDateToStr = "*";
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T00:00:00Z'");
        if ((createDateFrom != null) || (createDateTo != null)) {
            if (createDateFrom != null) {
                createDateFromStr = dateFormat.format(createDateFrom);
            }
            if (createDateTo != null) {
                createDateToStr = dateFormat.format(createDateTo);
            }
            queryStr4 = "doc_create:[" + createDateFromStr + " TO " + createDateToStr + "]";
        }

        // folder id
        String queryStr5 = "";
        if (folderId != null) {
            queryStr5 = "doc_folder_id:" + folderId;
        }

        String queryStr = "";
        if (!queryStr1.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
//            queryStr = queryStr + "(" + queryStr1 + ")";
            queryStr = queryStr + queryStr1;
        }
        if (!queryStr2.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr2 + ")";
        }
        if (!queryStr3.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr3 + ")";
        }
        if (!queryStr4.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr4 + ")";
        }
        if (!queryStr5.isEmpty()) {
            if (!queryStr.isEmpty()) {
                queryStr = queryStr + " AND ";
            }
            queryStr = queryStr + "(" + queryStr5 + ")";
        }
//        System.out.println("query = " + queryStr);

        if (queryStr.isEmpty()) {
            queryStr = "*";
        }

        return queryStr;
    }

    public List<IndexItem> searchAdvancedShared(String keywordsAnd, String keywordsOr, String keywordNot
            , Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList, int start, int limit
        ) {
        try {
//            System.out.println("searchFiles");
            SolrServer server = getServer();
//            System.out.println("getServer");

            /* Execute Query & get the Response */
            String queryStr = createQuerySearchAdvancedShared(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, shareAccountIdList);
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.setQuery(queryStr);
            solrQuery.setStart(start);
            solrQuery.setRows(limit);

    //        if(startDate != null && startDate != endDate)
    //            solrQuery.addFilterQuery("last_update:["+convertDateToQueryString(startDate)+" TO "+convertDateToQueryString(endDate)+"]");

//            String keywordTrimmed = trimKeyword(keyword);
//            if (keywordTrimmed.equals("")) {
//                solrQuery.addSortField("doc_last_update", SolrQuery.ORDER.desc);
//            }
//            System.out.println("query");
            QueryResponse response = server.query(solrQuery);
//            System.out.println("response");

            /* Get Documents result */
            return response.getBeans(IndexItem.class);
        }
        catch (MalformedURLException ex) {
            return null;
        }
        catch (SolrServerException ex) {
            return null;
        }
    }

    public int searchAdvancedSharedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot
            , Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList
        ) {
        try {
            SolrServer server = getServer();

            /* Execute Query & get the Response */
            SolrQuery solrQuery = new  SolrQuery();
            String queryStr = createQuerySearchAdvancedShared(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, shareAccountIdList);
            solrQuery.setQuery(queryStr);

            QueryResponse response = server.query(solrQuery);

            /* Get the results */
            return Integer.parseInt(String.valueOf(response.getResults().getNumFound()));
        }
        catch (MalformedURLException ex) {
            return 0;
        }
        catch (SolrServerException ex) {
            return 0;
        }
    }

}
