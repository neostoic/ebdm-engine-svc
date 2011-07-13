/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.solr;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author user
 */
public class EbDmSolrTest {

    public EbDmSolrTest() {
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
     * Test of indexDocument method, of class EbDmSolr.
     */
//    @Test
    public void testIndexDocument() {
        System.out.println("indexDocument");
//        DmDocument document = null;
//        EbDmSolr instance = null;
//        instance.indexDocument(document);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteTest method, of class EbDmSolr.
     */
//    @Test
    public void testDeleteTest() throws Exception {
        System.out.println("deleteTest");
//        String id = "";
//        EbDmSolr instance = null;
//        instance.deleteTest(id);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteIndexDocument method, of class EbDmSolr.
     */
//    @Test
    public void testDeleteIndexDocument() throws Exception {
        System.out.println("deleteIndexDocument");
//        String documentId = "";
//        EbDmSolr instance = null;
//        instance.deleteIndexDocument(documentId);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getServer method, of class EbDmSolr.
     */
//    @Test
    public void testGetServer() throws Exception {
        System.out.println("getServer");
//        EbDmSolr instance = null;
//        SolrServer expResult = null;
//        SolrServer result = instance.getServer();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of searchFiles method, of class EbDmSolr.
     */
    @Test
    public void testSearchSimple() throws Exception {
        System.out.println("begin - searchSimple");

        List<String> ownerAccountIdList = new ArrayList<String>();
//        ownerAccountIdList.add("94b2ea6c-9c1f-4c18-8f86-7eebda2bbaa5");
//        ownerAccountIdList.add("6dbf5393-0613-41bb-9955-6d626629e2ff");
        ownerAccountIdList.add("761aeba8-3818-4429-b89d-f7875be574ca");

        EbDmSolr solr = new EbDmSolr("http://localhost:9090/solr-ebdm");
//        List<IndexItem> itemList = solr.searchSimpleShared("doc_keywords:global", 0, 10);
//        List<IndexItem> itemList = solr.searchSimple("content", null, 0, 10);
//        List<IndexItem> itemList = solr.searchSimple("content", ownerAccountIdList, 0, 10);
//        List<IndexItem> itemList = solr.searchSimple("publisher", ownerAccountIdList, 0, 10);
        List<IndexItem> itemList = solr.searchSimpleShared("", ownerAccountIdList, 0, 10);

        if (itemList != null) {
            for (IndexItem item : itemList) {
                System.out.println("title: " + item.docTitle);
            }
        }

//        int num = solr.searchSimpleGetNumResult("content", null);
        int num = solr.searchSimpleSharedGetNumResult("", ownerAccountIdList);
        System.out.println("num = " + num);

        System.out.println("end - searchSimple");

//        String keyword = "";
//        int start = 0;
//        int limit = 0;
//        EbDmSolr instance = null;
//        List expResult = null;
//        List result = instance.searchFiles(keyword, start, limit);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}