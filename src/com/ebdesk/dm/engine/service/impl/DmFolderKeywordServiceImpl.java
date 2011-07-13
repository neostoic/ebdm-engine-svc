/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.dao.DmFolderKeywordDao;
import com.ebdesk.dm.engine.domain.DmFolderKeyword;
import com.ebdesk.dm.engine.service.DmFolderKeywordService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Service("dmFolderKeywordService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmFolderKeywordServiceImpl implements DmFolderKeywordService {

    @Autowired
    private DmFolderKeywordDao folderKeywordDao;

    public boolean save(DmFolderKeyword folderKeyword) {
        if(folderKeyword.getId() == null)
            folderKeyword.setId(java.util.UUID.randomUUID().toString());

        folderKeywordDao.save(folderKeyword);
        return true;
    }

    public void deleteByFolder(String folderId) {
        folderKeywordDao.deleteByFolder(folderId);
    }

    public List<DmFolderKeyword> getListByFolder(String folderId) {
        return folderKeywordDao.getListByFolder(folderId);
    }
}
