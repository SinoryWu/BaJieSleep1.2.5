package com.example.bajiesleep;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class DialogProgress extends Dialog  {


    public DialogProgress(@NonNull Context context) {
        super(context);
    }

    public DialogProgress(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar_dialog_layout);
    }

}
