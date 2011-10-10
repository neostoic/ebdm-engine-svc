/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderDescendant;
import java.util.List;

/**
 *
 * @author user
 */
public interface DmFolderDescendantService {
    public void clearAll();
    public void save(DmFolderDescendant folderDescendant);
    public List<DmFolder> getDescendantList(String folderId);
    public List<DmFolder> getAscendantOrderedList(String folderId);
}
