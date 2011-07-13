/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmAccount;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author user
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-spring-config.xml"})
public class DmAccountDaoTest {

    @Autowired
    private DmAccountDao dmAccountDao;

    public DmAccountDaoTest() {
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
     * Test of save method, of class DmAccountDao.
     */
//    @Test
    public void testSave() {
//        System.out.println("save");
//        DmAccount account = null;
//        DmAccountDao instance = new DmAccountDaoImpl();
//        DmAccount expResult = null;
//        DmAccount result = instance.save(account);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");

        System.out.println("start - save");

//        DmAppClient appClient = new DmAppClient();
//        appClient.setId("0a997474-7315-4781-93f2-35947734d167");

        DmAccount account = new DmAccount();
        account.setIsActive(Boolean.TRUE);
        account.setName("dmuser2");
        account.setQuota(0);
//        account.setAppClient(appClient);
        
        dmAccountDao.save(account);

        System.out.println("end - save");
    }

//    @Test
    public void testGetByName() {
//public DmAccount getByName(String name)
        System.out.println("start - save");

//        DmAccount account = dmAccountDao.getByName("0a997474-7315-4781-93f2-35947734d167", "akuN01");
        DmAccount account = dmAccountDao.getByName("akuN01");
        if (account != null) {
            System.out.println("account id = " + account.getId());
            System.out.println("active = " + account.getIsActive().toString());
        }
        else {
            System.out.println("not found");
        }

        System.out.println("end - save");
    }

//    @Test
    public void testGetList() {
//    public List<DmAccount> getList(String searchTerm, int recordStartNo, int pageNumItems)
        System.out.println("start - getList");

        List<DmAccount> accountList = dmAccountDao.getList("AaKun00", 0, 10);
        if (accountList != null) {
            System.out.println("size = " + accountList.size());
        }
        else {
            System.out.println("accountList == null");
        }

        System.out.println("end - getList");
    }

//    @Test
    public void testGet() {
//    public DmAccount get(String id);
        System.out.println("start - get");

        DmAccount account = dmAccountDao.get("6cec9ec8-7d9d-47c8-8ae7-6c3e32bfc510");
        if (account != null) {
            System.out.println("name = " + account.getName());
        }
        else {
            System.out.println("account == null");
        }

        System.out.println("end - get");
    }

//    @Test
    public void testDelete() {
//    public boolean delete(DmAccount account)
        System.out.println("start - delete");

        DmAccount account = dmAccountDao.get("6cec9ec8-7d9d-47c8-8ae7-6c3e32bfc510");
        if (account != null) {
            System.out.println("name = " + account.getName());
            boolean isDeleteOk = dmAccountDao.delete(account);
            System.out.println("deleted: " + isDeleteOk);
        }
        else {
            System.out.println("account == null");
        }

        System.out.println("end - delete");
    }

    @Test
    public void testGetByNameExcludeById() {
//    public DmAccount getByNameExcludeById(String name, String id) {
        System.out.println("start - getByNameExcludeById");

        DmAccount account = dmAccountDao.getByNameExcludeById("akun01", "72cac61a-d566-4ec3-ac20-b215b2ce6e24");
        if (account != null) {
            System.out.println("name = " + account.getName());
        }
        else {
            System.out.println("account == null");
        }


        System.out.println("end - getByNameExcludeById");
    }

//    public class DmAccountDaoImpl extends DmAccountDao {
//
//        public DmAccount save(DmAccount account) {
//            return null;
//        }
//    }

}