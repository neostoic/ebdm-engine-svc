/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.dao.DmFolderDescendantDao;
import com.ebdesk.dm.engine.domain.DmFolderDescendant;
import com.ebdesk.dm.engine.service.DmFolderDescendantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Service("dmFolderDescendantService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmFolderDescendantServiceImpl implements DmFolderDescendantService {

    @Autowired
    private DmFolderDescendantDao folderDescendantDao;

    public void clearAll() {
        folderDescendantDao.clearAll();
    }

    public void save(DmFolderDescendant folderDescendant) {
        folderDescendantDao.save(folderDescendant);
    }
}
