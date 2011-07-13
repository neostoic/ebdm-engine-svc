/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.exception;

/**
 *
 * @author designer
 */
public class FolderNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of <code>FolderNotFoundException</code> without detail message.
     */
    public FolderNotFoundException() {
    }


    /**
     * Constructs an instance of <code>FolderNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FolderNotFoundException(String msg) {
        super(msg);
    }
}
