/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.exception;

/**
 *
 * @author designer
 */
public class UserAccountNotFoundException extends RuntimeException {

    /**
     * Creates a new instance of <code>UserAccountNotFoundException</code> without detail message.
     */
    public UserAccountNotFoundException() {
    }


    /**
     * Constructs an instance of <code>UserAccountNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UserAccountNotFoundException(String msg) {
        super(msg);
    }
}
