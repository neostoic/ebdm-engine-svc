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
//    @Test
    public void testFindDocumentVersionByDocId() {
        List<DmDocumentVersion> versions = documentVersionDao.findDocumentVersionByDocId("02dfb409-c386-4541-b844-6a7b3dcd25ce", 0, 10, null, null);
        for (DmDocumentVersion dmDocumentVersion : versions) {
            if (dmDocumentVersion.getApproval() == null) {
                System.out.println("APPROVAL NULL");
            }else {
                System.out.println("APPROVAL ID : "+dmDocumentVersion.getApproval().getId() + " , "+dmDocumentVersion.getApproval().getStatus());
            }
        }
    }

    @Test
    public void testGetIdListByDocId() {
        System.out.println("start - testGetIdListByDocId");
        List<String> versionIdList = documentVersionDao.getIdListByDocId("92ecb47c-ed28-451d-8b6c-29e827932621");
        if (versionIdList != null) {
            for (String versionId : versionIdList) {
                System.out.println("versionId = " + versionId);
            }
        }
        System.out.println("end - testGetIdListByDocId");
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

//    @Test
    public void testCountAllNotRenderedVersion() {
        System.out.println("Count : "+documentVersionDao.countAllNotRenderedVersion());
    }
    
//    @Test
    public void testFindNotRenderedVersion() {
        System.out.println("Count : "+documentVersionDao.findNotRenderedVersion(0, Integer.MAX_VALUE).size());
    }
}