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
    @Test
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

    @Test
    public void testCountDocumentByFolderId() {
        Integer documentCount = dmDocumentFolderDao.countDocumentByFolderId("1");
        Assert.assertEquals(documentCount, new Integer(2));
        

        documentCount = dmDocumentFolderDao.countDocumentByFolderId("2");
        Assert.assertEquals(documentCount, new Integer(3));
    }
    
    
    @Test
    public void testUpdateNullDocByDocIdAndFolderId() {
        String folderId = "1";
        String docId = "2";
        Integer a = dmDocumentFolderDao.deleteByDocIdAndFolderId(folderId, docId);
        Assert.assertEquals(new Integer(1), a);
    }
}
