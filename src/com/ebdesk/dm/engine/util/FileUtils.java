/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.ebdesk.dm.engine.domain.util.PDFContent;
import com.ebdesk.dm.engine.util.parser.PDFTextParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Muhammad Rifai
 */
public class FileUtils {

    public static String FILE_SEPARATOR = "/";
    
    public static String PDF_EXTENSION = ".pdf";

    /***
     * Create folder path if the folder doesn't exist.
     * @param path path to the folder.
     * @return true if folder exist or folder created. false if any exception is happened.
     */
    public static boolean createFolderPath(String path) {
        File f = new File(path);
        try {
            if (f.mkdirs()) {
            } else {
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getFileExtension(String fileName) {
        String extension = null;
        int dotPos = fileName.lastIndexOf(".");
        if (dotPos < 0) {
            return extension;
        }
        extension = fileName.substring(dotPos);
        return extension;
    }
    
    public static String generatePdfName(String fileName){
        String extension = getFileExtension(fileName);
        String pdfName = StringUtils.replace(fileName, extension, ".pdf");
        return pdfName;
    }
    
//    @Deprecated
//    public static String generateTextFromPdf(String fileName){
//        return PDFTextParser.extractPDF(fileName).getTextContent();
//    }
    
    public static PDFContent extractPdf(String fileName){
        return PDFTextParser.extractPDF(fileName);
    }

    /****
     * 
     * @param fileUrl
     * @return
     * @deprecated 
     */
    @Deprecated
    public static String getMimeType(String fileUrl) {
        try {
            FileNameMap fileNameMap = URLConnection.getFileNameMap();
            String type = fileNameMap.getContentTypeFor(fileUrl);
            return type;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }

    public static byte[] getBytesFromFile(String path) {
        return getBytesFromFile(new File(path));
    }

    public static byte[] getBytesFromFile(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            // Get the size of the file
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                // File is too large
            }
            // Create the byte array to hold the data
            byte[] bytes = new byte[(int) length];
            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
            return bytes;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new byte[0];
    }

    /***
     * Convert document(*.doc, *.docx, *.ppt, *.pptx, *.xls, *.xlsx and others to *.pdf format)
     * @param fileInput file location of document to be converted.
     * @param fileOutput location of pdf file.
     * @return true if successfully converted or false if failed.
     */
    public static boolean convertToPdf(String fileInput, String fileOutput) {
        try {
            File inputFile = new File(fileInput);
            File outputFile = new File(fileOutput);

            // connect to an OpenOffice.org instance running on port 8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection();
            connection.connect();

            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);

            // close the connection
            connection.disconnect();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return false;
        }
    }
}
