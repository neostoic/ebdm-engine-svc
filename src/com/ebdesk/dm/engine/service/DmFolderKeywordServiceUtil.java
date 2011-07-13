/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.service;

import com.ebdesk.dm.engine.domain.DmFolderKeyword;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author user
 */
@Component
public class DmFolderKeywordServiceUtil {

    private static DmFolderKeywordService folderKeywordService;

    public static DmFolderKeywordService getFolderKeywordService() {
        if (folderKeywordService == null) {
            throw new RuntimeException(DmFolderKeywordService.class + " isn't initialized. ");
        }
        return folderKeywordService;
    }

    @Autowired(required=true)
    public void setFolderKeywordService(DmFolderKeywordService folderKeywordService) {
        DmFolderKeywordServiceUtil.folderKeywordService = folderKeywordService;
    }

    public static boolean save(DmFolderKeyword folderKeyword) {
        return getFolderKeywordService().save(folderKeyword);
    }

    public static void deleteByFolder(String folderId) {
        getFolderKeywordService().deleteByFolder(folderId);
    }

    public static List<DmFolderKeyword> getListByFolder(String folderId) {
        return getFolderKeywordService().getListByFolder(folderId);
    }
}
