/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

/**
 *
 * @author ifhayz
 */
public class DmDocViewAnnotationDaoTest extends BaseTest{
    
    @Autowired
    private DmDocViewAnnotationDao docViewAnnotationDao;
    
    public DmDocViewAnnotationDaoTest() {
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
     * Test of deleteByVersionId method, of class DmDocViewAnnotationDao.
     */
    @Test
    public void testDeleteByVersionId() {
        docViewAnnotationDao.deleteByVersionId("1");
    }
}
