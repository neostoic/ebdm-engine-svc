/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmFolderKeyword;
import java.util.List;

/**
 *
 * @author user
 */
public interface DmFolderKeywordService {
    public boolean save(DmFolderKeyword folderKeyword);
    public void deleteByFolder(String folderId);
    public List<DmFolderKeyword> getListByFolder(String folderId);
}
