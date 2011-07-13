/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.util.compare;

/**
 *
 * @author Muhammad Rifa'i
 */
public class LineDifference {

    private String oldText;
    private String newText;
    private boolean equal;
    private boolean newLine;

    public LineDifference(String oldText, String newText, boolean equal) {
        this.oldText = oldText;
        this.newText = newText;
        this.equal = equal;
        this.newLine = false;
    }

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public String getOldText() {
        return oldText;
    }

    public void setOldText(String oldText) {
        this.oldText = oldText;
    }

    public boolean isNewLine() {
        return newLine;
    }

    public void setNewLine(boolean newLine) {
        this.newLine = newLine;
    }
}
