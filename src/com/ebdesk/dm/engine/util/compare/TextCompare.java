/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.util.compare;

import com.ebdesk.dm.engine.util.StringFunction;
import com.ebdesk.dm.engine.util.compare.diff_match_patch.Diff;
import com.ebdesk.dm.engine.util.compare.diff_match_patch.Operation;
import com.ebdesk.dm.engine.util.parser.PDFTextParser;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Muhammad Rifa'i
 */
public class TextCompare {

    /**
     * Find the differences between two texts.
     * @param text1 Old string to be diffed.
     * @param text2 New string to be diffed.
     * @param checklines Speedup flag.  If false, then don't run a
     *     line-level diff first to identify the changed areas.
     *     If true, then run a faster slightly less optimal diff.
     * @return Linked List of LineDifference objects.
     */
    public static List<LineDifference> mainDifference(String text1, String text2,
            boolean checklines) {
        diff_match_patch dmp = new diff_match_patch();
        List<Diff> diffs = dmp.diff_main(text1, text2, checklines);

        List<LineDifference> lineDiffs = new ArrayList<LineDifference>();
        for (Diff diff : diffs) {
            if (diff.operation.equals(Operation.EQUAL)) {
                String[] lines = StringFunction.splitTextByLine(diff.text);
                for (int i = 0; i < lines.length; i++) {
                    String string = StringEscapeUtils.escapeHtml(lines[i]);
                    LineDifference lineDiff = new LineDifference(string, string, true);
                    if (i == 0 && (diff.text.startsWith("\\n") || diff.text.startsWith("\\r"))) {
                        lineDiff.setNewLine(true);
                    } else if (i > 0) {
                        lineDiff.setNewLine(true);
                    } else {
                        lineDiff.setNewLine(false);
                    }
                    lineDiffs.add(lineDiff);
                }
            } else if (diff.operation.equals(Operation.INSERT)) {
                String[] lines = StringFunction.splitTextByLine(diff.text);
                for (int i = 0; i < lines.length; i++) {
                    String string = "<new>" + StringEscapeUtils.escapeHtml(lines[i]) + "</new>";
                    LineDifference lineDiff = new LineDifference("", string, false);
                    if (i == 0 && (diff.text.startsWith("\\n") || diff.text.startsWith("\\r"))) {
                        lineDiff.setNewLine(true);
                    } else if (i > 0) {
                        lineDiff.setNewLine(true);
                    } else {
                        lineDiff.setNewLine(false);
                    }
                    lineDiffs.add(lineDiff);
                }
            } else if (diff.operation.equals(Operation.DELETE)) {
                String[] lines = StringFunction.splitTextByLine(diff.text);
                for (int i = 0; i < lines.length; i++) {
                    String string = "<old>" + StringEscapeUtils.escapeHtml(lines[i]) + "</old>";
                    LineDifference lineDiff = new LineDifference(string, "", false);
                    if (i == 0 && (diff.text.startsWith("\\n") || diff.text.startsWith("\\r"))) {
                        lineDiff.setNewLine(true);
                    } else if (i > 0) {
                        lineDiff.setNewLine(true);
                    } else {
                        lineDiff.setNewLine(false);
                    }
                    lineDiffs.add(lineDiff);
                }
            }
        }

        List<LineDifference> newLineDiffs = new ArrayList<LineDifference>();
        LineDifference tempLineDiff = null;
        int i = 0;
        for (LineDifference lineDiff : lineDiffs) {
            if (lineDiff.isNewLine() || (i == 0)) {
                tempLineDiff = new LineDifference(lineDiff.getOldText(), lineDiff.getNewText(), lineDiff.isEqual());
                newLineDiffs.add(tempLineDiff);
            } else {
                String tempOldText = tempLineDiff.getOldText() + lineDiff.getOldText();
                String tempNewText = tempLineDiff.getNewText() + lineDiff.getNewText();
                if (tempLineDiff.isEqual() && lineDiff.isEqual()) {
                    tempLineDiff.setEqual(true);
                } else {
                    tempLineDiff.setEqual(false);
                }
                if (newLineDiffs.size() > 0) {
                    newLineDiffs.remove(newLineDiffs.size() - 1);
                }

                tempLineDiff = new LineDifference(tempOldText, tempNewText, tempLineDiff.isEqual());
                newLineDiffs.add(tempLineDiff);
            }
            i++;
        }

        return newLineDiffs;

    }

}
