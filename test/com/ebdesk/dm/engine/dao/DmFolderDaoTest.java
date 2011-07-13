/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
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
 * @author designer
 */
public class DmFolderDaoTest extends BaseTest {

    @Autowired
    private DmFolderDao folderDao;

    public DmFolderDaoTest() {
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

    @Test
    public void testIsExist() {
        boolean result = folderDao.isExist("1");
        Assert.assertEquals(result, true);

        result = folderDao.isExist("2");
        Assert.assertEquals(result, true);

        result = folderDao.isExist("3");
        Assert.assertEquals(result, false);

    }

}