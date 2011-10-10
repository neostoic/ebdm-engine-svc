/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderDescendant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DmFolderDescendantServiceUtil {

    private static DmFolderDescendantService folderDescendantService;

    public static DmFolderDescendantService getFolderDescendantService() {
        if (folderDescendantService == null) {
            throw new RuntimeException(DmFolderDescendantService.class + " isn't initialized. ");
        }
        return folderDescendantService;
    }

    @Autowired(required=true)
    public void setFolderDescendantService(DmFolderDescendantService folderDescendantService) {
        DmFolderDescendantServiceUtil.folderDescendantService = folderDescendantService;
    }

    public static void clearAll() {
        getFolderDescendantService().clearAll();
    }

    public static void save(DmFolderDescendant folderDescendant) {
        getFolderDescendantService().save(folderDescendant);
    }

    public static List<DmFolder> getDescendantList(String folderId) {
        return getFolderDescendantService().getDescendantList(folderId);
    }

    public static List<DmFolder> getAscendantOrderedList(String folderId) {
        return getFolderDescendantService().getAscendantOrderedList(folderId);
    }
}
