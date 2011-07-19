   /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ebdesk.dm.engine.util;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;

/**
 * Convert a PDF document to an image.
 *
 * @author <a href="ben@benlitchfield.com">Ben Litchfield</a>
 * @version $Revision: 1.6 $
 */
public class PDFToImage {

    /***
     * 
     */
    private static Logger log = Logger.getLogger(PDFToImage.class);
    private static final String PASSWORD = "-password";
    private static final String START_PAGE = "-startPage";
    private static final String END_PAGE = "-endPage";
    private static final String IMAGE_FORMAT = "-imageType";
    private static final String OUTPUT_PREFIX = "-outputPrefix";
    private static final String COLOR = "-color";
    private static final String RESOLUTION = "-resolution";

    /**
     * private constructor.
     */
    private PDFToImage() {
        //static class
    }

    public static boolean extractImage(String pdfFile, String password, String outputPrefix) {
        String imageFormat = "png";
        int startPage = 1;
        int endPage = Integer.MAX_VALUE;
        int resolution;

        try {
            resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        } catch (HeadlessException e) {
            resolution = 96;
        }
        if (pdfFile == null) {
        } else {
            if (outputPrefix == null) {
                outputPrefix = pdfFile.substring(0, pdfFile.lastIndexOf('.'));
            }

            PDDocument document = null;
            try {
                document = PDDocument.load(pdfFile);
                //document.print();
                if (document.isEncrypted()) {
                    try {
                        document.decrypt(password);
                    } catch (InvalidPasswordException e) {
                        e.printStackTrace();
                    }
                }

                PDFImageWriter imageWriter = new PDFImageWriter();
                boolean success = false;

                Integer maxpage = (int) Math.ceil((double) document.getNumberOfPages() / (double) 10);

                if (document.getNumberOfPages() > 10) {
                    log.debug("More than 10 page");
                    for (int i = 1; i <= maxpage; i++) {
                        int[] page = FunctionGeneral.makePage(document.getNumberOfPages(), 10, i);
                        success = imageWriter.writeImage(document, imageFormat, password,
                                (page[0] + 1), (page[0] + 10), outputPrefix, BufferedImage.TYPE_INT_ARGB, resolution);
                        log.debug("Process : " + (page[0] + 1) + ", " + (page[0] + 10));
                    }

                } else {
                    log.debug("Less than 10 page");
                    success = imageWriter.writeImage(document, imageFormat, password,
                            startPage, endPage, outputPrefix, BufferedImage.TYPE_INT_ARGB, resolution);
                }

                if (!success) {
                    System.err.println("Error: no writer found for image format '" + imageFormat + "'");
                }
                return success;
            } catch (Exception e) {
                System.err.println(e);
                return false;
            } finally {
                if (document != null) {
                    try {
                        document.close();
                    } catch (IOException ex) {
                        Logger.getLogger(PDFToImage.class.getName()).log(Level.DEBUG, null, ex);
                    }
                }
            }
        }
        return false;
    }

    public static boolean extractImage(String pdfFile, String password, String outputPrefix,
            int startPage, int endPage) {
        String imageFormat = "png";
        int resolution;

        try {
            resolution = Toolkit.getDefaultToolkit().getScreenResolution();
        } catch (HeadlessException e) {
            resolution = 96;
        }
        if (pdfFile == null) {
        } else {
            if (outputPrefix == null) {
                outputPrefix = pdfFile.substring(0, pdfFile.lastIndexOf('.'));
            }

            PDDocument document = null;
            try {
                document = PDDocument.load(pdfFile);
                //document.print();
                if (document.isEncrypted()) {
                    try {
                        document.decrypt(password);
                    } catch (InvalidPasswordException e) {
                        e.printStackTrace();
                    }
                }

                PDFImageWriter imageWriter = new PDFImageWriter();
                boolean success = false;

                success = imageWriter.writeImage(document, imageFormat, password,
                        startPage, endPage, outputPrefix, BufferedImage.TYPE_INT_ARGB, resolution);

                if (!success) {
                    System.err.println("Error: no writer found for image format '" + imageFormat + "'");
                }
                return success;
            } catch (Exception e) {
                System.err.println(e);
                return false;
            } finally {
                if (document != null) {
                    try {
                        document.close();
                    } catch (IOException ex) {
                        Logger.getLogger(PDFToImage.class.getName()).log(Level.DEBUG, null, ex);
                    }
                }
            }
        }
        return false;
    }
}
