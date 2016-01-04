package com.larno.pickview.demo.util;

import android.util.Log;

/**
 * Created by sks on 2016/1/4.
 */
public class LogUtil {
    private static final boolean IS_DEBUG = true;
    public static void e(String tag, String msg){
        if(IS_DEBUG){
            Log.e(tag, msg);
        }
    }

    public static void e(Object tag, String msg){
        if(IS_DEBUG){
            Log.e(tag.getClass().getSimpleName(), msg);
        }
    }
}
