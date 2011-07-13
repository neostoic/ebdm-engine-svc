/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.domain.util;

/**
 *
 * @author Muhammad Rifai
 */
public class PDFContent {

    private String textContent;
    private int numberOfPages;

    public PDFContent() {
    
    }    
    
    public PDFContent(String textContent, int numberOfPages) {
        this.textContent = textContent;
        this.numberOfPages = numberOfPages;
    }
        
    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
