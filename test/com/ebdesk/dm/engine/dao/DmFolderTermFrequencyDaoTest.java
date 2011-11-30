/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class DmFolderTermFrequencyDaoTest extends BaseTest {

    @Autowired
    private DmFolderTermFrequencyDao dmFolderTermFrequencyDao;

    public DmFolderTermFrequencyDaoTest() {
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
     * Test of insertByDoc method, of class DmFolderTermFrequencyDao.
     */
//    @Test
    public void testInsertByDoc() {
        System.out.println("insertByDoc");
        String documentId = "";
        String folderId = "";
        DmFolderTermFrequencyDao instance = new DmFolderTermFrequencyDao();
        instance.insertByDoc(documentId, folderId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of increaseByDoc method, of class DmFolderTermFrequencyDao.
     */
//    @Test
    public void testIncreaseByDoc() {
        System.out.println("increaseByDoc");
        String documentId = "";
        String folderId = "";
        DmFolderTermFrequencyDao instance = new DmFolderTermFrequencyDao();
        instance.increaseByDoc(documentId, folderId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decreaseByDoc method, of class DmFolderTermFrequencyDao.
     */
//    @Test
    public void testDecreaseByDoc() {
        System.out.println("decreaseByDoc");
        String documentId = "";
        String folderId = "";
        DmFolderTermFrequencyDao instance = new DmFolderTermFrequencyDao();
        instance.decreaseByDoc(documentId, folderId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTopTerm method, of class DmFolderTermFrequencyDao.
     */
//    @Test
    public void testGetTopTerm() {
        System.out.println("getTopTerm");

        List<String> termList = dmFolderTermFrequencyDao.getTopTerm("df0bc6c0-e907-46ff-8dc5-161ef4f781e4", 30);
        if (termList != null) {
            System.out.println("termList != null");
            for (String term : termList) {
                System.out.println("term = " + term);
            }
        }
        else {
            System.out.println("termList == null");
        }

//        String folderId = "";
//        int numTerm = 0;
//        DmFolderTermFrequencyDao instance = new DmFolderTermFrequencyDao();
//        List expResult = null;
//        List result = instance.getTopTerm(folderId, numTerm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");

        System.out.println("getTopTerm - end");
    }

    /**
     * Test of getTopTermInclSubFolders method, of class DmFolderTermFrequencyDao.
     */
    @Test
    public void testGetTopTermInclSubFolders() {
        System.out.println("getTopTermInclSubFolders");

        List<String> termList = dmFolderTermFrequencyDao.getTopTermInclSubFolders("df0bc6c0-e907-46ff-8dc5-161ef4f781e4", 300, "00afda56-37ba-4734-8ba5-8e0ed64d8769");
        if (termList != null) {
            System.out.println("termList != null");
            for (String term : termList) {
                System.out.println("term = " + term);
            }
        }
        else {
            System.out.println("termList == null");
        }
        
        System.out.println("getTopTermInclSubFolders - end");

//        String folderId = "";
//        int numTerm = 0;
//        String accountId = "";
//        DmFolderTermFrequencyDao instance = new DmFolderTermFrequencyDao();
//        List expResult = null;
//        List result = instance.getTopTermInclSubFolders(folderId, numTerm, accountId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}