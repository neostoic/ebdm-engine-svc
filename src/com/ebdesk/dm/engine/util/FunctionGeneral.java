/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.util;

/**
 *
 * @author Muhammad Rifa'i
 */
public class FunctionGeneral {

    public static int[] makePage(Integer total_items, Integer items_per_page, Integer p) {
        int[] Array = new int[3];
        if (items_per_page == null || items_per_page == 0) {
            items_per_page = 1;
        }
        if (p == null || p == 0) {
            p = 1;
        }
        Integer maxpage = (int) Math.ceil(total_items / items_per_page);
        if (total_items % items_per_page > 0) {
            maxpage++;
        }
        p = ((p > maxpage) ? maxpage : ((p < 1) ? 1 : p));
        Array[0] = (p - 1) * items_per_page;
        Array[1] = p;
        Array[2] = maxpage;
        return Array;
    }

    public static int parseInteger(String data){
        try {
            return Integer.parseInt(data);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
}
