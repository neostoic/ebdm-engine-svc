/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.constant.ConfigConstants;
import com.ebdesk.dm.engine.dao.DmConfigDao;
import com.ebdesk.dm.engine.domain.DmConfig;
import com.ebdesk.dm.engine.service.DmSearchService;
import com.ebdesk.dm.engine.solr.EbDmSolr;
import com.ebdesk.dm.engine.solr.IndexItem;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Service("dmSearchService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmSearchServiceImpl implements DmSearchService {
    @Autowired
    private DmConfigDao dmConfigDao;
    
    private EbDmSolr getEbDmSolr() {
        DmConfig config = dmConfigDao.findById(ConfigConstants.SOLR_URL);
        if (config != null) {
            return new EbDmSolr(config.getValue());
        }
        return null;
    }

    public List<IndexItem> searchSimple(String keyword, List<String> ownerAccountIdList, int start, int limit) {
        EbDmSolr solr = getEbDmSolr();        
        return solr.searchSimple(keyword, ownerAccountIdList, start, limit);
    }

    public int searchSimpleGetNumResult(String keyword, List<String> ownerAccountIdList) {
        EbDmSolr solr = getEbDmSolr();
        return solr.searchSimpleGetNumResult(keyword, ownerAccountIdList);
    }

    public List<IndexItem> searchSimpleShared(String keyword, List<String> shareAccountIdList, int start, int limit) {
        EbDmSolr solr = getEbDmSolr();
        return solr.searchSimpleShared(keyword, shareAccountIdList, start, limit);
    }

    public int searchSimpleSharedGetNumResult(String keyword, List<String> shareAccountIdList) {
        EbDmSolr solr = getEbDmSolr();
        return solr.searchSimpleSharedGetNumResult(keyword, shareAccountIdList);
    }

    public List<IndexItem> searchAdvanced(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList, int start, int limit) {
        EbDmSolr solr = getEbDmSolr();
        return solr.searchAdvanced(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, ownerAccountIdList, start, limit);
    }

    public int searchAdvancedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> ownerAccountIdList) {
        EbDmSolr solr = getEbDmSolr();
        return solr.searchAdvancedGetNumResult(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, ownerAccountIdList);
    }

    public List<IndexItem> searchAdvancedShared(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList, int start, int limit) {
        EbDmSolr solr = getEbDmSolr();
        return solr.searchAdvancedShared(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, shareAccountIdList, start, limit);
    }

    public int searchAdvancedSharedGetNumResult(String keywordsAnd, String keywordsOr, String keywordNot, Long sizeMin, Long sizeLimit
            , Date createDateFrom, Date createDateTo, String folderId
            , boolean isSearchIncludeContent, List<String> shareAccountIdList) {
        EbDmSolr solr = getEbDmSolr();
        return solr.searchAdvancedSharedGetNumResult(keywordsAnd, keywordsOr, keywordNot, sizeMin, sizeLimit, createDateFrom, createDateTo, folderId, isSearchIncludeContent, shareAccountIdList);
    }
}
