package com.ebdesk.dm.engine.util.parser;

/*
 * PDFTextParser.java
 *
 * Created on January 24, 2009, 11:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 *
 * @author prasanna
 */
import com.ebdesk.dm.engine.domain.util.PDFContent;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFTextParser {

    private static Logger logger = Logger.getLogger(PDFTextParser.class);

    // Extract text from PDF Document
    public static PDFContent extractPDF(String fileName) {

        System.out.println("Parsing text from PDF file " + fileName + "....");
        File f = new File(fileName);

        if (!f.isFile()) {
            System.out.println("File " + fileName + " does not exist.");
            return null;
        }

        PDFParser parser = null;
        PDFContent pdfContent = new PDFContent();
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;

        try {
            parser = new PDFParser(new FileInputStream(f));
        } catch (Exception e) {
            e.printStackTrace(System.out);            
            return null;
        }

        try {
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);           
            
            pdfContent.setTextContent(pdfStripper.getText(pdDoc));
            pdfContent.setNumberOfPages(pdDoc.getNumberOfPages());
        } catch (Exception e) {
            e.printStackTrace(System.err);            
            return null;
        }finally {
            try {
                if (cosDoc != null) {
                    cosDoc.close();
                }
                if (pdDoc != null) {
                    pdDoc.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }
        }

        return pdfContent;
    }

    
    
    // Write the parsed text from PDF to a file
    public void writeTexttoFile(String pdfText, String fileName) {

        System.out.println("\nWriting PDF text to output text file " + fileName + "....");
        try {
            PrintWriter pw = new PrintWriter(fileName);
            pw.print(pdfText);
            pw.close();
        } catch (Exception e) {
            System.out.println("An exception occured in writing the pdf text to file.");
            e.printStackTrace(System.out);
        }
        System.out.println("Done.");
    }
}
