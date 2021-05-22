package com.example.bajiesleep;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DialogWarning extends Dialog implements View.OnClickListener {


    private Button mBtnWarn;

    private IOnConfirmListener confirmListener;

    private String title;
    private String message;
    private String confirm;


    public DialogWarning setConfirm(String confirm, IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener = listener;
        return this;
    }


    public DialogWarning setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogWarning setMessage(String message) {
        this.message = message;
        return this;
    }

    public DialogWarning(@NonNull Context context) {
        super(context);
    }

    public DialogWarning(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_warning);

        mBtnWarn = findViewById(R.id.dialog_warning_btn);

        mBtnWarn.setOnClickListener(this);




    }

    public interface IOnConfirmListener{
        void OnConfirm(DialogWarning dialog);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_warning_btn:
                if (confirmListener != null){
                    confirmListener.OnConfirm(this);
                    dismiss();
                }
                break;

        }
    }
}
