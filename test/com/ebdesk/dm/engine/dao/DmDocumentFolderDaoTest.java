/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.constant.DatabaseConstants;
import com.ebdesk.dm.engine.domain.DmDocument;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author designer
 */
public class DmDocumentFolderDaoTest extends BaseTest {

    @Autowired
    private DmDocumentFolderDao dmDocumentFolderDao;
    
    @Autowired
    private DmFolderPermissionDao folderPermissionDao;

    public DmDocumentFolderDaoTest() {
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
     * Test of findDocumentByFolderId method, of class DmDocumentFolderDaoImpl.
     */
    //@Test
    public void testFindDocumentByFolderId() {
        List<DmDocument> documents = dmDocumentFolderDao.findDocumentByFolderId("1", 0, 10, "title", null);
        Assert.assertEquals(documents.size(), 2);
        Assert.assertEquals(documents.get(0).getTitle(), "Text Mining Kategorisasi Teks Naïve Bayes");


        documents = dmDocumentFolderDao.findDocumentByFolderId("1", 0, 10, "title", DatabaseConstants.ORDER_DESC);
        Assert.assertEquals(documents.size(), 2);
        Assert.assertEquals(documents.get(1).getTitle(), "Text Mining Kategorisasi Teks Naïve Bayes");

        documents = dmDocumentFolderDao.findDocumentByFolderId("1", 0, 10, "title", DatabaseConstants.ORDER_ASC);
        Assert.assertEquals(documents.size(), 2);
        Assert.assertEquals(documents.get(0).getTitle(), "Text Mining Kategorisasi Teks Naïve Bayes");

        documents = dmDocumentFolderDao.findDocumentByFolderId("1", 0, 1, null, null);
        Assert.assertEquals(documents.size(), 1);

        documents = dmDocumentFolderDao.findDocumentByFolderId("2", 0, 10, null, null);
        Assert.assertEquals(documents.size(), 3);

        //System.out.println("Folder name : "+documents.get(0).getDocumentFolderList().get(0).getFolder().getName());

        
    }

    //@Test
    public void testCountDocumentByFolderId() {
        Integer documentCount = dmDocumentFolderDao.countDocumentByFolderId("1");
        Assert.assertEquals(documentCount, new Integer(2));
        

        documentCount = dmDocumentFolderDao.countDocumentByFolderId("2");
        Assert.assertEquals(documentCount, new Integer(3));
    }
    
    
    //@Test
    public void testUpdateNullDocByDocIdAndFolderId() {
        String folderId = "1";
        String docId = "2";
        Integer a = dmDocumentFolderDao.deleteByDocIdAndFolderId(folderId, docId);
        Assert.assertEquals(new Integer(1), a);
    }
    
    @Test
    public void testFindDocumentByAccount(){
        String accountId = "a92874e1-b5ac-4478-a9d6-ed01d22922e0";
        String folderId = "d2847b07-7d41-4d31-8138-e5afb58c1635";
        
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        List<DmDocument> documents = dmDocumentFolderDao.findDocumentByAccount(accountId, folderId, permission, 0, 10, "","");
        //List<DmDocument> documents = dmDocumentFolderDao.findDocumentByFolderId(folderId, 0, 10, "createdTime", "desc");
        for (DmDocument dmDocument : documents) {
            if (dmDocument.getApproval() != null) {
                System.out.println("Document ID ("+dmDocument.getId()+") : "+dmDocument.getApproval().getApprovedBy().getName());
            }else
                System.out.println("Document ID ("+dmDocument.getId()+") : "+dmDocument.getApproval());
        }
    
    }
    
    
    @Test
    public void testCountDocumentByAccount(){
        //String accountId = "a92874e1-b5ac-4478-a9d6-ed01d22922e0";
        String accountId = "e07f6d15-58d3-4a12-8350-8f3f42883488";
        String folderId = "d2847b07-7d41-4d31-8138-e5afb58c1635";
        
        Integer permission = folderPermissionDao.findUserFolderPermission(accountId, folderId);
        Integer count = dmDocumentFolderDao.countDocumentByAccount(accountId, folderId, permission);
        
        System.out.println("Count : "+count);
    
    }
}
