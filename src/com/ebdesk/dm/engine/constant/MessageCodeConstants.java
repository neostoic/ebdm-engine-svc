/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.constant;

/**
 * Class which contains of constants for document management.
 * CODE STRUCTURE : EBDM-TXXYYZZZ
 * Description:
 * EBDM : Prefix for any message code at document management API.
 * T    : Message type 1 = SUCCESS, 2 = WARNING, 3 = ERROR
 * XX   : Object type.
 *        00 = General object. 
 *        01 = Acount
 *        02 = Folder
 *        03 = Document
 *        XX = Other Object.
 * 
 * YY   : Attribute of the Object defined in XX. The attribute code is combination 
 *        between the object code and the number of attribute itself. 
 *        For general object, attribute isn't defined yet.
 *        0100 = General attribute for Account.
 *        0101 = Account.name
 *        0301 = Document.title
 *        0302 = Document.description  
 *        0303 = Document.author
 *        0304 = Document.file
 *        0305 = Document.keyword
 *        0306 = Document.createdBy
 *        XXYY = Other object and its attribute.
 * 
 * ZZZ  : The number define for any message. Number is doesn't have any standard. 
 *        It's just a shortered number. 
 *          
 *        
 * 
 * @author Muhammad Rifai
 */
public class MessageCodeConstants {
    
    /****
     * Message code prefix for document management.
     */
    private final static String EBDM = "EBDM";
    
    /****
     * Message code prefix for success at document management API.
     */
    private final static String EBDM_SUCCESS = EBDM+"-1";
    
    /****
     * Message code prefix for WARNING at document management API.
     */
    private final static String EBDM_WARNING = EBDM+"-2";
    
    /****
     * Message code prefix for ERROR at document management API.
     */
    private final static String EBDM_ERROR = EBDM+"-3";
       
    /***
     * Mesage code prefix for SUCCESS Document object.
     */
    private final static String EBDM_SUCCESS_DOC = EBDM_SUCCESS+"03";
    
    /***
     * Mesage code prefix for WARNING Document object.
     */
    private final static String EBDM_WARNING_DOC = EBDM_WARNING+"03";
    
    /***
     * Mesage code prefix for ERROR Account object. (EBDM-301)
     */
    private final static String EBDM_ERROR_ACCOUNT = EBDM_ERROR+"01";
    /***
     * Mesage code prefix for ERROR Folder object. (EBDM-302)
     */
    private final static String EBDM_ERROR_FOLDER = EBDM_ERROR+"02";
    /***
     * Mesage code prefix for ERROR Document object.(EBDM-303)
     */
    private final static String EBDM_ERROR_DOC = EBDM_ERROR+"03";
    
    
    
    /***
     * Mesage code prefix for ERROR Account in general condition. (EBDM-30200)
     */
    private final static String EBDM_ERROR_ACCOUNT_GENERAL = EBDM_ERROR_ACCOUNT+"00";    
    /***
     * Mesage ERROR Account when account not found. (EBDM-30200001)
     */
    public final static String EBDM_ERROR_ACCOUNT_GENERAL_NO_ACCOUNT = EBDM_ERROR_ACCOUNT_GENERAL+"001";
    /***
     * Mesage ERROR Account when account space quota is insufficient for new document. (EBDM-30100002)
     */
    public final static String EBDM_ERROR_ACCOUNT_GENERAL_INSUFFICIENT_QUOTA = EBDM_ERROR_ACCOUNT_GENERAL+"002";

    
    
    /***
     * Mesage code prefix for ERROR Folder in general condition. (EBDM-30200)
     */
    private final static String EBDM_ERROR_FOLDER_GENERAL = EBDM_ERROR_FOLDER+"00";    
    /***
     * Mesage code prefix for ERROR Folder when folder not found. (EBDM-30200001)
     */
    public final static String EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER = EBDM_ERROR_FOLDER_GENERAL+"001";
    
    
    
    /***
     * Mesage code prefix for ERROR Document in general condition.
     */
    private final static String EBDM_ERROR_DOC_GENERAL = EBDM_ERROR_DOC+"00";
    /***
     * Mesage code prefix for ERROR Document.title.
     */
    private final static String EBDM_ERROR_DOC_TITLE = EBDM_ERROR_DOC+"01";
    /***
     * Mesage code prefix for ERROR Document.description.
     */
    private final static String EBDM_ERROR_DOC_DESCRIPTION = EBDM_ERROR_DOC+"02";
    /***
     * Mesage code prefix for ERROR Document.author.
     */
    private final static String EBDM_ERROR_DOC_AUTHOR = EBDM_ERROR_DOC+"03";
    /***
     * Mesage code prefix for ERROR Document.file.
     */
    private final static String EBDM_ERROR_DOC_FILE = EBDM_ERROR_DOC+"04";
    /***
     * Mesage code prefix for ERROR Document.keyword.
     */
    private final static String EBDM_ERROR_DOC_KEYWORD = EBDM_ERROR_DOC+"05";
    
    
    /***
     * Error when search document doesn't exist. (EBDM-30300001)
     */
    public static String EBDM_ERROR_DOC_GENERAL_NOT_FOUND = EBDM_ERROR_DOC_GENERAL+"001";
    /***
     * Error when user cannot create a document in a folder. (EBDM-30300002)
     */
    public static String EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE = EBDM_ERROR_DOC_GENERAL+"002";
    /***
     * Error when user cannot download any documents in a folder. (EBDM-30300003)
     */
    public static String EBDM_ERROR_DOC_GENERAL_CANNOT_DOWNLOAD = EBDM_ERROR_DOC_GENERAL+"003";
    /***
     * Error when user cannot download view any document annotation in a folder. (EBDM-30300004)
     */
    public static String EBDM_ERROR_DOC_GENERAL_CANNOT_VIEW_ANNOTATION = EBDM_ERROR_DOC_GENERAL+"004";
    /***
     * Error when user cannot write any document annotation in a folder. (EBDM-30300005)
     */
    public static String EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE_ANNOTATION = EBDM_ERROR_DOC_GENERAL+"005";
    /***
     * Error when user cannot write any comments in a folder. (EBDM-30300006)
     */
    public static String EBDM_ERROR_DOC_GENERAL_CANNOT_WRITE_COMMENT = EBDM_ERROR_DOC_GENERAL+"006";
    
    /***
     * Error when title in document doesn't specified.
     */
    public static String EBDM_ERROR_DOC_TITLE_REQUIRED = EBDM_ERROR_DOC_TITLE+"001";
    /***
     * Error when title greater than max length.
     */
    public static String EBDM_ERROR_DOC_TITLE_MAX_LENGTH = EBDM_ERROR_DOC_TITLE+"002";
    /***
     * Error when description greater than max length.
     */
    public static String EBDM_ERROR_DOC_DESCRIPTION_MAX_LENGTH = EBDM_ERROR_DOC_DESCRIPTION+"001";
    
    /***
     * Error when an author greater than max length.
     */
    public static String EBDM_ERROR_DOC_AUTHOR_MAX_LENGTH = EBDM_ERROR_DOC_AUTHOR+"001";
    
    /***
     * Error when a keyword greater than max length.
     */
    public static String EBDM_ERROR_DOC_KEYWORD_MAX_LENGTH = EBDM_ERROR_DOC_KEYWORD+"001";
    
    
    
    
    public static void main(String[] argv){
        
        System.out.println(EBDM_ERROR_FOLDER_GENERAL_NO_FOLDER);
    
    }
    
}
