package com.xinou.quickadmin.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by shizhida on 16/10/9.
 */
public class Pager {

    List<Map<String,Object>> rows;
    int pagecount;
    int last;

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> row) {
        this.rows = row;
    }

    public int getPagecount() {
        return pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }
}
