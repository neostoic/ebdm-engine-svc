/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.domain;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.dao.DmDocumentIndexedDao;
import java.util.Date;
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
public class DmDocumentIndexedTest extends BaseTest {
    @Autowired
    private DmDocumentIndexedDao documentIndexedDao;

    public DmDocumentIndexedTest() {
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
     * Test of getId method, of class DmDocumentIndexed.
     */
//    @Test
    public void testGetId() {
//        System.out.println("getId");
//        DmDocumentIndexed instance = new DmDocumentIndexed();
//        String expResult = "";
//        String result = instance.getId();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class DmDocumentIndexed.
     */
//    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "";
        DmDocumentIndexed instance = new DmDocumentIndexed();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTimeIndexed method, of class DmDocumentIndexed.
     */
    @Test
    public void testGetTimeIndexed() {
        System.out.println("getTimeIndexed");
        DmDocumentIndexed instance = new DmDocumentIndexed();
        Date expResult = null;
        Date result = instance.getTimeIndexed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTimeIndexed method, of class DmDocumentIndexed.
     */
    @Test
    public void testSetTimeIndexed() {
        System.out.println("setTimeIndexed");
        Date timeIndexed = null;
        DmDocumentIndexed instance = new DmDocumentIndexed();
        instance.setTimeIndexed(timeIndexed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDocument method, of class DmDocumentIndexed.
     */
    @Test
    public void testGetDocument() {
        System.out.println("getDocument");
        DmDocumentIndexed instance = new DmDocumentIndexed();
        DmDocument expResult = null;
        DmDocument result = instance.getDocument();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDocument method, of class DmDocumentIndexed.
     */
    @Test
    public void testSetDocument() {
        System.out.println("setDocument");
        DmDocument document = null;
        DmDocumentIndexed instance = new DmDocumentIndexed();
        instance.setDocument(document);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDocModifiedTime method, of class DmDocumentIndexed.
     */
    @Test
    public void testGetDocModifiedTime() {
        System.out.println("getDocModifiedTime");
        DmDocumentIndexed instance = new DmDocumentIndexed();
        Date expResult = null;
        Date result = instance.getDocModifiedTime();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDocModifiedTime method, of class DmDocumentIndexed.
     */
    @Test
    public void testSetDocModifiedTime() {
        System.out.println("setDocModifiedTime");
        Date docModifiedTime = null;
        DmDocumentIndexed instance = new DmDocumentIndexed();
        instance.setDocModifiedTime(docModifiedTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}