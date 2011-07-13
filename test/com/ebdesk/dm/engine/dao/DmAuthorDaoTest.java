/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.DmAuthor;
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
public class DmAuthorDaoTest extends BaseTest {
    
    @Autowired
    private DmAuthorDao authorDao;
    
    public DmAuthorDaoTest() {
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
     * Test of findByName method, of class DmAuthorDao.
     */
    @Test
    public void testFindByName() {
        String name = "Stanislaw Osinski";
        DmAuthor author = authorDao.findByName(name);
        Assert.assertEquals(author.getId(), "1");
        Assert.assertEquals(author.getName(), "Stanislaw Osinski");
        
        name = "Masayu Leylia Khodra";
        author = authorDao.findByName(name);
        Assert.assertEquals(author.getId(), "2");
        Assert.assertEquals(author.getName(), "Masayu Leylia Khodra");
        
        name = "Ronen Feldman";
        author = authorDao.findByName(name);
        Assert.assertEquals(author.getId(), "5");
        Assert.assertEquals(author.getName(), "Ronen Feldman");
        
        name = "Muhammad Rifai";
        author = authorDao.findByName(name);
        Assert.assertNull(author);
    }
}
