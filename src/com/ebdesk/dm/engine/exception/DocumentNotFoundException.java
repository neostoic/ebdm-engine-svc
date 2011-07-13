/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.exception;

/**
 *
 * @author designer
 */
public class DocumentNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of <code>DocumentNotFoundException</code> without detail message.
     */
    public DocumentNotFoundException() {
    }


    /**
     * Constructs an instance of <code>DocumentNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DocumentNotFoundException(String msg) {
        super(msg);
    }
}
