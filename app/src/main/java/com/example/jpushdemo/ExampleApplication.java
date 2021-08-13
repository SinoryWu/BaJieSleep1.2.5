package com.example.jpushdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.bajiesleep.LoginActivity;
import com.example.bajiesleep.PushReceiverViewModel;

import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class ExampleApplication extends Application {
    private static final String TAG = "JIGUANG-Example";

//    private PushReceiverViewModel pushReceiverViewModel;
    @Override
    public void onCreate() {
        Log.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();

         if (getJpushToSp("jpushkey","").equals("1")){
             JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
             JPushInterface.init(this);     		// 初始化 JPush
         }
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush

    }

    public String getJpushToSp(String key, String val){
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("jpushkey","没有key");
        return token;
    }
}
