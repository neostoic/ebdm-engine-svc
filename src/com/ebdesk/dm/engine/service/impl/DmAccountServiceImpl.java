/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.dao.DmAccountDao;
import com.ebdesk.dm.engine.domain.DmAccount;
import com.ebdesk.dm.engine.service.DmAccountService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Service("dmAccountService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmAccountServiceImpl implements DmAccountService {
    @Autowired
    private DmAccountDao dmAccountDao;

    public DmAccount save(DmAccount account) {
        return dmAccountDao.save(account);
    }

    public DmAccount getByName(String name) {
        return dmAccountDao.getByName(name);
    }

    public List<DmAccount> getList(String searchTerm, int recordStartNo, int pageNumItems) {
        return dmAccountDao.getList(searchTerm, recordStartNo, pageNumItems);
    }

    public int getListNum(String searchTerm) {
        return dmAccountDao.getListNum(searchTerm);
    }

    public DmAccount get(String id) {
        return dmAccountDao.get(id);
    }

    public boolean delete(DmAccount account) {
        return dmAccountDao.delete(account);
    }

    public DmAccount getByNameExcludeById(String name, String id) {
        return dmAccountDao.getByNameExcludeById(name, id);
    }

    public boolean update(DmAccount account) {
        return dmAccountDao.update(account);
    }
}
