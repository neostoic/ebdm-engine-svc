/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.basetest.BaseTest;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import com.ebdesk.dm.engine.service.DmDocumentServiceUtil;
import java.util.List;
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
public class DmDocumentVersionDaoTest extends BaseTest {

    @Autowired
    private DmDocumentVersionDao documentVersionDao;

    public DmDocumentVersionDaoTest() {
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
     * Test of findDocumentVersionByDocId method, of class DmDocumentVersionDao.
     */
    //@Test
    public void testFindDocumentVersionByDocId() {
        //List<DmDocumentVersion> versions = documentVersionDao.findDocumentVersionByDocId("1", 0, 10, "date", "desc");
        List<DmDocumentVersion> versions = DmDocumentServiceUtil.findDocVersionByDocId("1", 0, 10, "date", "desc");
        System.out.println("SIZE : " + versions.size());
    }

    //@Test
    public void testFindById() {
        String versionId = "6c3861f7-2573-4ff7-a75c-713c2f32fa5b";
        DmDocumentVersion documentVersion = documentVersionDao.findById(versionId);
        Assert.assertNotNull(documentVersion.getDocument());
        Assert.assertNotNull(documentVersion.getDocument().getLastVersion());
        if (documentVersion.getId().equals(documentVersion.getDocument().getLastVersion().getId())) {
            System.out.println("SAMA");
        } else {
            System.out.println("BEDA");
        }

    }

    //@Test
    public void testCountByDocumentId() {
        String documentId = "d8c58e5d-8961-4a42-a89b-3117e0fae42d";
        int count = documentVersionDao.countByDocumentId(documentId);
        Assert.assertEquals(2, count);

        documentId = "1";
        count = documentVersionDao.countByDocumentId(documentId);
        Assert.assertEquals(1, count);

    }

    //@Test
    public void testDeleteDocVersionByVersionId() {
        String versionId = "9ccc9cfe-9415-48e0-8153-bae286e8f8df";
        int result = documentVersionDao.deleteDocVersionByVersionId(versionId);
        System.out.println("Result : " + result);
    }

    @Test
    public void testCountAllNotRenderedVersion() {
        System.out.println("Count : "+documentVersionDao.countAllNotRenderedVersion());
    }
    
    @Test
    public void testFindNotRenderedVersion() {
        System.out.println("Count : "+documentVersionDao.findNotRenderedVersion(0, Integer.MAX_VALUE).size());
    }
}