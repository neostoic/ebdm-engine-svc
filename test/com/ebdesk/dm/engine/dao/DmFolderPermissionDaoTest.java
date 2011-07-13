/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.util.FolderPermission;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author designer
 */
public class DmFolderPermissionDaoTest extends BaseTest {

    @Autowired
    private DmFolderPermissionDao folderPermissionDao;

    public DmFolderPermissionDaoTest() {
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
     * Test of findUserFolderPermission method, of class DmFolderPermissionDao.
     */
    @Test
    public void testFindUserFolderPermission() {
        System.out.println("findUserFolderPermission");

        String accountId = "e07f6d15-58d3-4a12-8350-8f3f42883488"; //ifhayz
        String folderId = "1";
        Integer result = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        Assert.assertEquals(result, new Integer(FolderPermission.OWNER.getPermission()));
        
        accountId = "e07f6d15-58d3-4a12-8350-8f3f42883488"; //ifhayz
        folderId = "2";
        result = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        Assert.assertEquals(result, new Integer(FolderPermission.OWNER.getPermission()));
        
        accountId = "a92874e1-b5ac-4478-a9d6-ed01d22922e0"; //administrator
        folderId = "1";
        result = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        Assert.assertEquals(result, new Integer(FolderPermission.READER.getPermission()));

        accountId = "c96dfde0-113a-44b0-a171-b90c842d9213";        //josefbudi
        folderId = "2";
        result = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        Assert.assertEquals(result, new Integer(FolderPermission.CONSUMER.getPermission()));
        
        accountId = "a92874e1-b5ac-4478-a9d6-ed01d22922e0"; //administrator
        folderId = "2";
        result = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        Assert.assertEquals(result, new Integer(FolderPermission.NO_PERMISSION.getPermission()));
    }

}