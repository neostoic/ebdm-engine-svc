/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.exception;

/**
 *
 * @author user
 */
public class ExceedQuotaException extends RuntimeException {

    public ExceedQuotaException(String message) {
        super(message);
    }

    public ExceedQuotaException() {
    }

}
