package com.example.bajiesleep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bajiesleep.entity.StopDeviceResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class DeviceRecoverDialog extends Dialog  {

    private Context context;
    private Button buttonFalse,buttonTrue;
    private DeviceRecoverDialog.IOnCancelListener cancelListener;
    private DeviceRecoverDialog.IOnConfirmListener confirmListener;
    private String cancel,confirm;



    String state;
    String sn;

    public DeviceRecoverDialog(@NonNull Context context) {
        super(context);
    }

    public DeviceRecoverDialog(@NonNull Context context, String sn, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.sn =sn;
    }

    public DeviceRecoverDialog setCancel(String cancel, DeviceRecoverDialog.IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public DeviceRecoverDialog setConfirm(String confirm, DeviceRecoverDialog.IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener=listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_recover_dialog_layout);
        buttonFalse = findViewById(R.id.btn_recover_device_false);
        buttonTrue = findViewById(R.id.btn_recover_device_true);




        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        buttonTrue.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                Intent intent = new Intent(getContext(),RecoverDeviceActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sn2",sn);
                bundle.putString("deviceState","stopDevice");
                intent.putExtras(bundle);
                getContext().startActivity(intent);
                ((Activity) context).finish();
                dismiss();
            }
        });

    }



    public interface IOnCancelListener{
        void onCancel(DeviceRecoverDialog dialog);
    }

    public interface IOnConfirmListener{
        void onConfirm(DeviceRecoverDialog dialog);
    }









}
