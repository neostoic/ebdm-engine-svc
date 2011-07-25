/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;

/**
 *
 * @author user
 */
public class PDFToImage {
    public static boolean extractImage(String pdfFile, String password, String outputPrefix) {
        System.out.println("pdfFile: " + pdfFile);
        System.out.println("outputPrefix: " + outputPrefix);

        boolean isSuccessful = false;

        /**instance of PdfDecoder to convert PDF into image*/
        PdfDecoder decode_pdf = new PdfDecoder(true);

        /**set mappings for non-embedded fonts to use*/
        PdfDecoder.setFontReplacements(decode_pdf);

        /**open the PDF file - can also be a URL or a byte array*/
        try {
            decode_pdf.openPdfFile(pdfFile); //file
                    //decode_pdf.openPdfFile("C:/myPDF.pdf", "password"); //encrypted file
                    //decode_pdf.openPdfArray(bytes); //bytes is byte[] array with PDF
                    //decode_pdf.openPdfFileFromURL("http://www.mysite.com/myPDF.pdf",false);

            /**get page 1 as an image*/
            //page range if you want to extract all pages with a loop
            //int start = 1,  end = decode_pdf.getPageCount();
            for (int i = 1; i <= decode_pdf.getPageCount(); i++) {
                BufferedImage img=decode_pdf.getPageAsImage(i);
                File file = new File(outputPrefix + i +".png");
                ImageIO.write(img, "png", file);
            }

            /**close the pdf file*/
            decode_pdf.closePdfFile();
        } catch (PdfException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        isSuccessful = true;
        return isSuccessful;
    }

    public static boolean extractImage(String pdfFile, String password, String outputPrefix,
            int startPage, int endPage) {
        return extractImage(pdfFile, password, outputPrefix);
    }
}
