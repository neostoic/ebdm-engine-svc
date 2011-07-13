/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.DmDocumentComment;
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
public class DmDocumentCommentDaoTest extends BaseTest {
    @Autowired
    private DmDocumentCommentDao documentCommentDao;
    
    public DmDocumentCommentDaoTest() {
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
     * Test of findLastCommentOnDocument method, of class DmDocumentCommentDao.
     */
    @Test
    public void testFindLastCommentOnDocument() {
        DmDocumentComment comment = documentCommentDao.findLastCommentOnDocument("fbefd075-1b24-4b9d-8831-65c420eb4969");
        
        System.out.println("Comment : "+comment);
    }
}
