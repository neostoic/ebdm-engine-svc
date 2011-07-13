/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.service.impl;

import com.ebdesk.dm.engine.dao.DmAccountDao;
import com.ebdesk.dm.engine.dao.DmConfigDao;
import com.ebdesk.dm.engine.dao.DmDocRenderImageDao;
import com.ebdesk.dm.engine.dao.DmDocViewAnnotationDao;
import com.ebdesk.dm.engine.dao.DmDocumentCommentDao;
import com.ebdesk.dm.engine.dao.DmDocumentDao;
import com.ebdesk.dm.engine.dao.DmDocumentVersionDao;
import com.ebdesk.dm.engine.domain.DmAccount;
import com.ebdesk.dm.engine.domain.DmDocRenderImage;
import com.ebdesk.dm.engine.domain.DmDocViewAnnotation;
import com.ebdesk.dm.engine.domain.DmDocument;
import com.ebdesk.dm.engine.domain.DmDocumentComment;
import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import com.ebdesk.dm.engine.service.DmExtractService;
import com.ebdesk.dm.engine.util.FileUtils;
import com.ebdesk.dm.engine.util.ImageUtils;
import com.ebdesk.dm.engine.util.PDFToImage;
import com.ebdesk.dm.engine.util.config.ConfigVar;
import java.awt.Dimension;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Muhammad Rifai
 */
@Service("dmExtractService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmExtractServiceImpl implements DmExtractService {

    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private DmDocumentVersionDao documentVersionDao;
    @Autowired
    private DmDocRenderImageDao docRenderImageDao;
    @Autowired
    private DmConfigDao configDao;
    @Autowired
    private DmDocViewAnnotationDao docViewAnnotationDao;
    @Autowired
    private DmDocumentDao documentDao;
    @Autowired
    private DmDocumentCommentDao documentCommentDao; 
    
    @Autowired
    private DmAccountDao accountDao;

    public List<DmDocumentVersion> findNotRenderedVersion(int start, int max) {
        return documentVersionDao.findNotRenderedVersion(start, max);
    }

    public void extractDocument(DmDocumentVersion documentVersion, String imageType) {
        if (documentVersion == null) {
            return;
        } else if (documentVersion.getPdfPath() == null || "".equals(documentVersion.getPdfPath())) {
            return;
        }

        docRenderImageDao.deleteDocRenderByVersionId(documentVersion.getId());
        String folderLocation = ConfigVar.baseDocImageLocation(null, null, documentVersion.getId());
        FileUtils.createFolderPath(folderLocation);
        log.debug("Version Location : " + documentVersion.getPdfPath() + "");

        String prefixOutput = folderLocation + FileUtils.FILE_SEPARATOR + "page";

        boolean success = PDFToImage.extractImage(documentVersion.getPdfPath(), null, prefixOutput);

        if (success) {
            Dimension imageDim = null;
            if (documentVersion.getNumberOfPages() == 1) {
                imageDim = ImageUtils.imageDimension(prefixOutput + "1." + imageType);
            } else {
                int part = documentVersion.getNumberOfPages() / 2;
                imageDim = ImageUtils.imageDimension(prefixOutput + part + "." + imageType);
            }
            documentVersion.setDocHeight(imageDim.height);
            documentVersion.setDocWidth(imageDim.width);
            documentVersion.setIsRendered(Boolean.TRUE);
            documentVersionDao.update(documentVersion);

            for (int i = 1; i <= documentVersion.getNumberOfPages(); i++) {
                String imgLocation = prefixOutput + i + "." + imageType;
                DmDocRenderImage renderImage = new DmDocRenderImage();
                renderImage.setDocVersion(documentVersion);
                renderImage.setFilePath(imgLocation);
                renderImage.setPage(i);
                renderImage.setId(UUID.randomUUID().toString());
                docRenderImageDao.save(renderImage);
            }
            log.debug("Document Version " + documentVersion.getId() + " has been converted.");
        }
    }

    public void extractDocument() {

        int renderedVersion = documentVersionDao.countAllNotRenderedVersion();
        int part = (int) Math.ceil(((float) renderedVersion / (float) ITEM_PER_PROCESS));

        log.debug(renderedVersion + " , " + part);

        for (int i = 1; i <= part; i++) {
            List<DmDocumentVersion> docVersionList = documentVersionDao.findNotRenderedVersion(0, ITEM_PER_PROCESS);
            for (int j = 0; j < docVersionList.size(); j++) {
                DmDocumentVersion documentVersion = docVersionList.get(j);
                docRenderImageDao.deleteDocRenderByVersionId(documentVersion.getId());
                String folderLocation = ConfigVar.baseDocImageLocation(null, null, documentVersion.getId());
                FileUtils.createFolderPath(folderLocation);
                log.debug("Version Location : " + documentVersion.getPdfPath() + "");
                boolean success = PDFToImage.extractImage(documentVersion.getPdfPath(), null, folderLocation + FileUtils.FILE_SEPARATOR + "page");
                if (success) {
                    log.debug("Document Version " + documentVersion.getId() + " has been converted.");
                }
            }
        }
    }

    public void fillAnnotation() {
        long countAll = docRenderImageDao.countAll();
        long totalProcess = (countAll / 10);
        Random random = new Random();

        List<DmAccount> accounts = accountDao.getList("", 0, Integer.MAX_VALUE);
        
        log.debug("Total User : "+accounts.size());
        
        
        for (int i = 0; i <= totalProcess; i++) {
            List<DmDocRenderImage> renderImages = docRenderImageDao.findAll(((i*10)+1), 10);
            for (int j = 0; j < renderImages.size(); j++) {
                DmDocRenderImage dmDocRenderImage = renderImages.get(j);
                int accountIdx = random.nextInt(accounts.size());
                accountIdx = accountIdx >= accounts.size() ? accounts.size()-1 : accountIdx; 
                DmAccount account = accounts.get(accountIdx);
                DmDocViewAnnotation annotation = new DmDocViewAnnotation();
                annotation.setAnnotatedBy(account);
                annotation.setAnnotatedPage(dmDocRenderImage);
                annotation.setCreatedTime(new Date());
                annotation.setId(UUID.randomUUID().toString());
                annotation.setAnnotation(dmDocRenderImage.getDocVersion().getDocument().getTitle()
                        + " - (Page "+dmDocRenderImage.getPage()+")");
                log.debug(account.getName()+" : "+annotation.getAnnotation());
                
                docViewAnnotationDao.save(annotation);
            }
            
        }
        
    }

    public void fillComment() {
        long countAll = docRenderImageDao.countAll();
        long totalProcess = (countAll / 10);
        Random random = new Random();

        List<DmAccount> accounts = accountDao.getList("", 0, Integer.MAX_VALUE);
        
        log.debug("Total User : "+accounts.size());
        
        
        for (int i = 0; i <= totalProcess; i++) {
            List<DmDocument> documents = documentDao.findAll(((i*10)+1), 10);
            for (int j = 0; j < documents.size(); j++) {
                int accountIdx = random.nextInt(accounts.size());
                accountIdx = accountIdx >= accounts.size() ? accounts.size()-1 : accountIdx; 
                DmDocumentComment comment = new DmDocumentComment();
                DmAccount account = accounts.get(accountIdx);
                comment.setCommentedBy(account);
                comment.setComment(documents.get(j).getTitle()+" comment by "+account.getName());
                comment.setCreatedTime(new Date());
                comment.setDocument(documents.get(j));
                comment.setId(UUID.randomUUID().toString());
                documentCommentDao.save(comment);
            }
        }
    }
}
