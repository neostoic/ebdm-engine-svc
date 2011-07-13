/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.DmDocumentLock;
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
 * @author Muhammad Rifai
 */
public class DmDocumentLockDaoTest extends BaseTest {
    
    @Autowired
    private DmDocumentLockDao documentLockDao;
    
    public DmDocumentLockDaoTest() {
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
     * Test of findByDocumentId method, of class DmDocumentLockDao.
     */
    @Test
    public void testFindByDocumentId() {
        System.out.println("findByDocumentId");
        String docId = "1";
        DmDocumentLock documentLock = documentLockDao.findByDocumentId(docId);
        Assert.assertEquals(documentLock, new DmDocumentLock("1"));
        
        docId = "2";
        documentLock = documentLockDao.findByDocumentId(docId);
        Assert.assertNull(documentLock);
        
    }
}
