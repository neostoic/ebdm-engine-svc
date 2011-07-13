/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ebdesk.dm.engine.domain.DmDocument;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.junit.Assert.*;

/**
 *
 * @author designer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-config.xml"})
public class DmDocumentDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired
    private DmDocumentDao documentDao;

    public DmDocumentDaoTest() {
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
     * Test of findDocumentByDocumentId method, of class DmDocumentDao.
     */
    //@Test
    public void testFindDocumentByDocumentId() {
        DmDocument document = documentDao.findDocumentByDocumentId("3");
        
        System.out.println("AUTHOR SIZE : "+document.getDocumentAuthorList().size());
        Assert.assertEquals(document.getContentType().getName(), "application/pdf");
        Assert.assertEquals(document.getContentType().getDescription(), "Adobe PDF Format");
        Assert.assertEquals(document.getContentType().getFileExtension(), ".pdf");

    }

    @Test
    public void testFindById() {
        DmDocument document = documentDao.findById("3");

        System.out.println("AUTHOR SIZE : "+document.getDocumentAuthorList().size());
    }

}