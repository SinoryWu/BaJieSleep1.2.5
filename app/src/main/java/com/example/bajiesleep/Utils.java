package com.example.bajiesleep;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Utils extends Activity {
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    //这两个就是手机屏幕的屏幕分辨率，物理宽高值如1080*1920（ToolBar或ActionBar会占据一定空间，得到的heightPiexls会小一点）
    int width = metrics.widthPixels;// 表示屏幕的像素宽度，单位是px（像素）
    int height = metrics.heightPixels;// 表示屏幕的像素高度，单位是px（像素）
    float density = metrics.density;// 显示器的逻辑密度，Density Independent Pixel（如3.0），（metrics.scaledDensity和metrics.density数值是一样的）
    int densityDpi = metrics.densityDpi;  // 整个屏幕的像素密度DPI（dots per inch每英寸像素数），可以是密度低,密度中等,或者密度高（如240/ 360 / 480）
    float xdpi = metrics.xdpi;//表示在X轴方向上每英寸的像素值，X轴方向上的DPI（dots per inch)
    float ydpi = metrics.ydpi;//表示在Y轴方向上每英寸的像素值,  Y方向上的DPI


    /**
     * 获取屏幕宽度；
     */
    public static int getScreenWidth(Context context) {
        return context.getApplicationContext().getResources()
                .getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度；
     */
    public static int getScreenHeight(Context context) {
        return context.getApplicationContext().getResources()
                .getDisplayMetrics().heightPixels;
    }




}
