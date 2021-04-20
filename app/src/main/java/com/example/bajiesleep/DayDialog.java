package com.example.bajiesleep;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;

public class DayDialog extends Dialog implements View.OnClickListener {

    private String title,cancel,confirm;

    WheelView myWheelView;
    private TextView mTvTitle,mTvCancel,mTvConfirm;

    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;

    public DayDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public DayDialog setCancel(String cancel,IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public DayDialog setConfirm(String confirm,IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener=listener;
        return this;
    }

    String days;

    public DayDialog(@NonNull Context context) {
        super(context);

    }

    public DayDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DayDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_dialog);
        WindowManager m = getWindow().getWindowManager();
        Display d  = m.getDefaultDisplay();
        WindowManager.LayoutParams p  =getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.8);

        getWindow().setAttributes(p);


        mTvTitle = findViewById(R.id.day_tv_title);
        mTvCancel = findViewById(R.id.day_tv_cancel);
        mTvConfirm=findViewById(R.id.day_tv_confirm);

        if (!TextUtils.isEmpty(title)){
            mTvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(cancel)){
            mTvCancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(confirm)){
            mTvConfirm.setText(confirm);
        }
        
        myWheelView = findViewById(R.id.day_wheelview);
        myWheelView.setWheelAdapter(new MyWheelAdapter(getContext()));
        myWheelView.setWheelSize(5);

        myWheelView.setSkin(WheelView.Skin.Holo);
        myWheelView.setWheelData(createArrays());
        myWheelView.setSelection(0);

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.WHITE;
        style.textColor = Color.GRAY;
        style.selectedTextColor = Color.GREEN;
        myWheelView.setStyle(style);

        myWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                days = String.valueOf(myWheelView.getSelectionItem());
                Log.d("myWheelView", days);
            }
        });


        mTvCancel.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);
    }


    private ArrayList<String> createArrays() {
        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add("item" + i);
//        }
        list.add("1 天");
        list.add("2 天");
        list.add("3 天");
        list.add("7 天");
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.day_tv_cancel:
                if (cancelListener != null){
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;

            case R.id.day_tv_confirm:
                if (confirmListener != null){
                    confirmListener.onConfirm(this,days);
                }
                dismiss();
                break;
        }
    }

    public interface IOnCancelListener{
        void onCancel(DayDialog dialog);
    }

    public interface IOnConfirmListener{
        void onConfirm(DayDialog dialog, String days);
    }


}
