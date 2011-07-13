/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.DmDocumentKeyword;
import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

/**
 *
 * @author designer
 */
public class DmDocumentKeywordDaoTest extends BaseTest {

    @Autowired
    private DmDocumentKeywordDao documentKeywordDao;

    public DmDocumentKeywordDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of findKeywordByDocumentId method, of class DmDocumentKeywordDao.
     */
    @Test
    public void testFindKeywordByDocumentId() {
        System.out.println("findKeywordByDocumentId");
        String documentId = "1";
        int start = 0;
        int max = Integer.MAX_VALUE;
        List<DmDocumentKeyword> documentKeywords = documentKeywordDao.findKeywordByDocumentId(documentId, start, max);
        Assert.assertEquals(3, documentKeywords.size());
        Assert.assertEquals("Text Mining", documentKeywords.get(0).getTerm());
        Assert.assertEquals("Naive Bayes", documentKeywords.get(1).getTerm());
        Assert.assertEquals("Kategorisasi", documentKeywords.get(2).getTerm());


        documentId = "3";
        start = 0;
        max = Integer.MAX_VALUE;
        documentKeywords = documentKeywordDao.findKeywordByDocumentId(documentId, start, max);
        Assert.assertEquals(4, documentKeywords.size());
        Assert.assertEquals("Algoritma Lingo", documentKeywords.get(0).getTerm());
        Assert.assertEquals("Information Retrieval", documentKeywords.get(1).getTerm());
        Assert.assertEquals("Clustering", documentKeywords.get(2).getTerm());
        Assert.assertEquals("Web search results", documentKeywords.get(3).getTerm());
        
    }

}