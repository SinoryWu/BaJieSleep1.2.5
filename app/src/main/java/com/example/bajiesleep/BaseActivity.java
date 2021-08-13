package com.example.bajiesleep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BaseActivity extends AppCompatActivity {

    public Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext =this;

    }



    public void navigateTo(Class cls){

        Intent intent = new Intent(mContext,cls);
        this.finish();
        startActivity(intent);
    }

    protected void saveStringToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.putString(key, val);
        editor.commit();
    }




    public String getTokenToSp(String key, String val){
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token","没有token");
        return token;
    }

    public String getUidToSp(String key, String val){
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid","没有uid");
        return uid;
    }


}
