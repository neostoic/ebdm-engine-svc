/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmAccount;
import java.util.List;

/**
 *
 * @author user
 */
public interface DmAccountDao {
    public DmAccount save(DmAccount account);
    public DmAccount getByName(String name);
    public List<DmAccount> getList(String searchTerm, int recordStartNo, int pageNumItems);
    public int getListNum(String searchTerm);
    public DmAccount get(String id);
    public boolean delete(DmAccount account);
    public DmAccount getByNameExcludeById(String name, String id);
    public boolean update(DmAccount account);
    public DmAccount getActive(String id);
}
