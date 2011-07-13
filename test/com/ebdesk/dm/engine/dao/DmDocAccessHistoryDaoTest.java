/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.DmDocAccessHistory;
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
 * @author designer
 */
public class DmDocAccessHistoryDaoTest extends BaseTest {

    @Autowired
    private DmDocAccessHistoryDao docAccessHistoryDao;

    public DmDocAccessHistoryDaoTest() {
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
     * Test of findByDocumentId method, of class DmDocAccessHistoryDao.
     */
    @Test
    public void testFindByDocumentId() {
        System.out.println("findByDocumentId");
        String docId = "1";
        int start = 0;
        int max = Integer.MAX_VALUE;
        String orderBy = "accessedBy.name";
        String order = "ASC";

        List<DmDocAccessHistory> history = docAccessHistoryDao.findByDocumentId(docId, start, max, orderBy, order);
        System.out.println("HISTORY SIZE : "+history.size());
    }

    @Test
    public void testCountDocumentId(){
        String docId = "1";
        int i = docAccessHistoryDao.countDocumentId(docId);
        System.out.println("HISTORY SIZE : "+i);
    }

}