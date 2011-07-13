/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmAccount;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DmAccountServiceUtil {
    private static DmAccountService dmAccountService;

    public static DmAccountService getService() {
        if (dmAccountService == null) {
            throw new RuntimeException("DM Account service is not initialized yet.");
        }
        return dmAccountService;
    }

    @Autowired(required=true)
    public void setDmAccountService(DmAccountService service) {
        DmAccountServiceUtil.dmAccountService = service;
    }

    public static DmAccount save(DmAccount account) throws Exception {
        return getService().save(account);
    }

    public static DmAccount getByName(String name) throws Exception {
//        if (name == null) {
//            throw new DmException();
//        }
        return getService().getByName(name);
    }

    public static List<DmAccount> getList(String searchTerm, int recordStartNo, int pageNumItems) {
        return getService().getList(searchTerm, recordStartNo, pageNumItems);
    }

    public static int getListNum(String searchTerm) {
        return getService().getListNum(searchTerm);
    }

    public static DmAccount get(String id) {
        return getService().get(id);
    }

    public static boolean delete(DmAccount account) {
        return getService().delete(account);
    }

    public static DmAccount getByNameExcludeById(String name, String id) {
        return getService().getByNameExcludeById(name, id);
    }

    public static boolean update(DmAccount account) {
        return getService().update(account);
    }
}
