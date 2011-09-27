/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.DmFolder;
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
public class DmFolderDescendantDaoTest extends BaseTest {

    @Autowired
    private DmFolderDescendantDao dmFolderDescendantDao;

    public DmFolderDescendantDaoTest() {
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
     * Test of clearAll method, of class DmFolderDescendantDao.
     */
//    @Test
    public void testClearAll() {
        System.out.println("clearAll");
//        DmFolderDescendantDao instance = new DmFolderDescendantDao();
//        boolean expResult = false;
//        boolean result = instance.clearAll();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByDescendant method, of class DmFolderDescendantDao.
     */
//    @Test
    public void testDeleteByDescendant() {
        System.out.println("deleteByDescendant");
//        String folderId = "";
//        DmFolderDescendantDao instance = new DmFolderDescendantDao();
//        boolean expResult = false;
//        boolean result = instance.deleteByDescendant(folderId);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAscendantList method, of class DmFolderDescendantDao.
     */
//    @Test
    public void testGetAscendantList() {
        System.out.println("start - getAscendantList");
//        String folderId = "";
//        DmFolderDescendantDao instance = new DmFolderDescendantDao();
//        List expResult = null;
//        List result = instance.getAscendantList(folderId);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
        
//        List<DmFolder> folderList = dmFolderDescendantDao.getAscendantList("84623453-20b0-4977-80d1-f162a80d70af");
        List<DmFolder> folderList = dmFolderDescendantDao.getAscendantList("e107d079-f05f-44fe-93ba-ccb819bc301e");
        if (folderList != null) {
            for (DmFolder folder : folderList) {
                System.out.println("ascendant = " + folder.getName());
            }
        }

        System.out.println("end - getAscendantList");
    }

    /**
     * Test of getAscendantIdList method, of class DmFolderDescendantDao.
     */
//    @Test
    public void testGetAscendantIdList() {
        System.out.println("start - getAscendantIdList");

        List<String> ascendantIdList = dmFolderDescendantDao.getAscendantIdList("94e437b7-3be3-4baf-b838-a629ecd01055");
        if (ascendantIdList != null) {
            for (String ascendantId : ascendantIdList) {
                System.out.println("ascendant id = " + ascendantId);
            }
        }

        System.out.println("end - getAscendantIdList");
    }

    /**
     * Test of getDescendantIdList method, of class DmFolderDescendantDao.
     */
//    @Test
    public void testGetDescendantIdList() {
        System.out.println("start - getDescendantIdList");

        List<String> desscendantIdList = dmFolderDescendantDao.getDescendantIdList("e107d079-f05f-44fe-93ba-ccb819bc301e");
        if (desscendantIdList != null) {
            for (String descendantId : desscendantIdList) {
                System.out.println("descendant id = " + descendantId);
            }
        }

        System.out.println("end - getDescendantIdList");
    }

/**
     * Test of getAscendantOrderedList method, of class DmFolderDescendantDao.
     */
    @Test
    public void testGetAscendantOrderedList() {
        System.out.println("start - getAscendantOrderedList");
//        String folderId = "";
//        DmFolderDescendantDao instance = new DmFolderDescendantDao();
//        List expResult = null;
//        List result = instance.getAscendantList(folderId);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");

        String folderId = "94e437b7-3be3-4baf-b838-a629ecd01055";

        List<DmFolder> folderList = dmFolderDescendantDao.getAscendantList(folderId);
        if (folderList != null) {
            for (DmFolder folder : folderList) {
                System.out.println("ascendant = " + folder.getName());
            }
        }
        System.out.println("");
        List<DmFolder> folderList2 = dmFolderDescendantDao.getAscendantOrderedList(folderId);
        if (folderList2 != null) {
            for (DmFolder folder : folderList2) {
                System.out.println("ascendant = " + folder.getName());
            }
        }

        System.out.println("end - getAscendantOrderedList");
    }
}