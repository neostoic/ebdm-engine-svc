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
public class DmDocTermFrequencyDaoTest extends BaseTest {

    @Autowired
    private DmDocTermFrequencyDao dmDocTermFrequencyDao;

    public DmDocTermFrequencyDaoTest() {
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
     * Test of deleteByDocId method, of class DmDocTermFrequencyDao.
     */
//    @Test
    public void testDeleteByDocId() {
        System.out.println("deleteByDocId");
        String documentId = "";
        DmDocTermFrequencyDao instance = new DmDocTermFrequencyDao();
        instance.deleteByDocId(documentId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTopTerm method, of class DmDocTermFrequencyDao.
     */
    @Test
    public void testGetTopTerm() {
        System.out.println("getTopTerm");

        List<String> termList = dmDocTermFrequencyDao.getTopTerm("279047b6-3fa6-418e-a942-c4a3dbf98f62", 30);
        if (termList != null) {
            System.out.println("termList != null");
            for (String term : termList) {
                System.out.println("term = " + term);
            }
        }
        else {
            System.out.println("termList == null");
        }

        System.out.println("getTopTerm - end");
//        String documentId = "";
//        int numTerm = 0;
//        DmDocTermFrequencyDao instance = new DmDocTermFrequencyDao();
//        List expResult = null;
//        List result = instance.getTopTerm(documentId, numTerm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}