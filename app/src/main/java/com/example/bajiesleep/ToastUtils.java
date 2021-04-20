package com.example.bajiesleep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils {
    public static void showTextToast(Context context, String message){
        View toastview= LayoutInflater.from(context).inflate(R.layout.layout_toast,null);
        TextView text = (TextView) toastview.findViewById(R.id.tv_toast);
        text.setText(message);
        Toast toast=new Toast(context);
//        toast.setGravity(Gravity.CENTER,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastview);
        toast.show();
    }

    public static void showTextToast2(Context context, String message){
        View toastview= LayoutInflater.from(context).inflate(R.layout.layout_toast2,null);
        TextView text = (TextView) toastview.findViewById(R.id.tv_toast2);
        text.setText(message);
        Toast toast=new Toast(context);
//        toast.setGravity(Gravity.CENTER,0,0);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastview);
        toast.show();
    }



}
