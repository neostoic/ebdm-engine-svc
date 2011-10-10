/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import java.util.ArrayList;
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
public class DmDocumentIndexedDaoTest extends BaseTest {

    @Autowired
    private DmDocumentIndexedDao dmDocumentIndexedDao;

    public DmDocumentIndexedDaoTest() {
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
     * Test of getNotIndexedDocList method, of class DmDocumentIndexedDao.
     */
//    @Test
    public void testGetNotIndexedDocList() {
        System.out.println("getNotIndexedDocList");
        int numDocs = 0;
        DmDocumentIndexedDao instance = new DmDocumentIndexedDao();
        List expResult = null;
        List result = instance.getNotIndexedDocList(numDocs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByDocumentId method, of class DmDocumentIndexedDao.
     */
//    @Test
    public void testDeleteByDocumentId() {
        System.out.println("deleteByDocumentId");
        String documentId = "";
        DmDocumentIndexedDao instance = new DmDocumentIndexedDao();
        instance.deleteByDocumentId(documentId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReindexByFolder method, of class DmDocumentIndexedDao.
     */
//    @Test
    public void testSetReindexByFolder() {
        System.out.println("setReindexByFolder");
        String folderId = "";
        DmDocumentIndexedDao instance = new DmDocumentIndexedDao();
        instance.setReindexByFolder(folderId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setReindexByFolderList method, of class DmDocumentIndexedDao.
     */
    @Test
    public void testSetReindexByFolderList() {
        System.out.println("setReindexByFolderList");
//        List<String> folderIdList = null;
//        DmDocumentIndexedDao instance = new DmDocumentIndexedDao();
//        instance.setReindexByFolderList(folderIdList);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");

        List<String> folderIdList = new ArrayList<String>();
        folderIdList.add("84623453-20b0-4977-80d1-f162a80d70af");
        dmDocumentIndexedDao.setReindexByFolderList(folderIdList);

        System.out.println("setReindexByFolderList - end");
    }

    /**
     * Test of setReindexByDocument method, of class DmDocumentIndexedDao.
     */
//    @Test
    public void testSetReindexByDocument() {
        System.out.println("setReindexByDocument");
        String documentId = "";
        DmDocumentIndexedDao instance = new DmDocumentIndexedDao();
        instance.setReindexByDocument(documentId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}