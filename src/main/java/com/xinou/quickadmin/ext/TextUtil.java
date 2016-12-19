package com.xinou.quickadmin.ext;

/**
 * Created by shizhida on 16/11/29.
 */
public class TextUtil {

    public static String replaceEnter2Html(String str){
        return str.replaceAll("\n","<br/>");
    }
}
