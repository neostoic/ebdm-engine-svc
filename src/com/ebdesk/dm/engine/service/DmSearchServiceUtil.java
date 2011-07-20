/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.solr.IndexItem;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DmSearchServiceUtil {

    private static DmSearchService searchService;

    public static DmSearchService getSearchService() {
        if (searchService == null) {
            throw new RuntimeException(DmSearchService.class + " isn't initialized. ");
        }
        return searchService;
    }

    @Autowired(required=true)
    public void setSearchService(DmSearchService searchService) {
        DmSearchServiceUtil.searchService = searchService;
    }

    public static List<IndexItem> searchSimple(String keyword, List<String> ownerAccountIdList, int start, int limit) {
        return getSearchService().searchSimple(keyword, ownerAccountIdList, start, limit);
    }

    public static int searchSimpleGetNumResult(String keyword, List<String> ownerAccountIdList) {
        return getSearchService().searchSimpleGetNumResult(keyword, ownerAccountIdList);
    }

    public static List<IndexItem> searchSimpleShared(String keyword, List<String> shareAccountIdList, int start, int limit) {
        return getSearchService().searchSimpleShared(keyword, shareAccountIdList, start, limit);
    }

    public static int searchSimpleSharedGetNumResult(String keyword, List<String> shareAccountIdList) {
        return getSearchService().searchSimpleSharedGetNumResult(keyword, shareAccountIdList);
    }

    public static List<IndexItem> searchAdvanced(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList, int start, int limit) {
        return getSearchService().searchAdvanced(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, ownerAccountIdList, start, limit);
    }

    public static int searchAdvancedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList) {
        return getSearchService().searchAdvancedGetNumResult(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, ownerAccountIdList);
    }

    public static List<IndexItem> searchAdvancedShared(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList, int start, int limit) {
        return getSearchService().searchAdvancedShared(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, shareAccountIdList, start, limit);
    }

    public static int searchAdvancedSharedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList) {
        return getSearchService().searchAdvancedSharedGetNumResult(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, shareAccountIdList);
    }
}
