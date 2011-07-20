/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.solr.IndexItem;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public interface DmSearchService {
    public List<IndexItem> searchSimple(String keyword, List<String> ownerAccountIdList, int start, int limit);
    public int searchSimpleGetNumResult(String keyword, List<String> ownerAccountIdList);
    public List<IndexItem> searchSimpleShared(String keyword, List<String> shareAccountIdList, int start, int limit);
    public int searchSimpleSharedGetNumResult(String keyword, List<String> shareAccountIdList);

    public List<IndexItem> searchAdvanced(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList, int start, int limit);
    public int searchAdvancedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList);
    public List<IndexItem> searchAdvancedShared(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList, int start, int limit);
    public int searchAdvancedSharedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList);
}
