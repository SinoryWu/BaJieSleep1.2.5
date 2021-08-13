package com.example.bajiesleep.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class GetShp {
    public static String  getUserAgent(Context context){

        SharedPreferences sp = context.getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp.getString("user_agent", "");
        return token;

    }
}
