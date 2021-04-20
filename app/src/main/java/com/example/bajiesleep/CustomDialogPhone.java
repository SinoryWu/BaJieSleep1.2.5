package com.example.bajiesleep;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomDialogPhone extends Dialog implements View.OnClickListener {

    private Button mBtnCall,mBtnCopy;
    private TextView mTvUserName,mTvPhoneNumber;

    private IOnCallListener callListener;
    private IOnCopyListener copyListener;
    private String userName;
    private String copy;
    private String call;
    private String phoneNumber;


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public CustomDialogPhone setCall(String call, IOnCallListener listener) {
        this.call = call;
        this.callListener = listener;
        return this;
    }

    public CustomDialogPhone setCopy(String copy, IOnCopyListener listener) {
        this.copy = copy;
        this.copyListener =listener;
        return this;
    }

    public CustomDialogPhone setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public CustomDialogPhone setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }




    public CustomDialogPhone(@NonNull Context context) {
        super(context);
    }

    public CustomDialogPhone(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout2);
        mBtnCall = findViewById(R.id.btn_call);
        mBtnCopy = findViewById(R.id.btn_copy);
        mTvUserName = findViewById(R.id.tv_user_name);
        mTvPhoneNumber = findViewById(R.id.tv_user_phone_number);


       if (!TextUtils.isEmpty(userName)){
           mTvUserName.setText(userName);
       }
        if (!TextUtils.isEmpty(phoneNumber)){
            mTvPhoneNumber.setText(phoneNumber);
        }
        if (!TextUtils.isEmpty(call)){
            mBtnCall.setText(call);
        }

        if (!TextUtils.isEmpty(copy)){
            mBtnCopy.setText(copy);
        }

        mBtnCall.setOnClickListener(this);
        mBtnCopy.setOnClickListener(this);



    }

    public interface IOnCallListener{
        void OnCall(CustomDialogPhone dialog);
    }

    public interface IOnCopyListener{
        void OnCopy(CustomDialogPhone dialog);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_call:
                if (callListener != null){
                    callListener.OnCall(this);
                }
                break;
            case R.id.btn_copy:
                if (copyListener != null){
                    copyListener.OnCopy(this);
                }
                break;
        }
    }
}
