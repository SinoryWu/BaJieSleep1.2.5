package com.example.bajiesleep;

import android.util.Log;

public class L {
    private static final String TAG ="TAG" ;
    private static boolean debug = true;

    public static void e (String msg){

        if (debug){
            Log.e(TAG,msg);
        }

    }
}
