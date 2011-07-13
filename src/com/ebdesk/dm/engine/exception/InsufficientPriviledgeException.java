/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.exception;

/**
 *
 * @author designer
 */
public class InsufficientPriviledgeException extends RuntimeException {

    /**
     * Creates a new instance of <code>InsufficientPriviledgeException</code> without detail message.
     */
    public InsufficientPriviledgeException() {
    }


    /**
     * Constructs an instance of <code>InsufficientPriviledgeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InsufficientPriviledgeException(String msg) {
        super(msg);
    }
}
