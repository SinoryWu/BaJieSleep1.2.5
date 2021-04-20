package com.example.bajiesleep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bajiesleep.entity.EditDeviceResponse;
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

public class DeviceEditDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private Button button;
    private TextView mTvLeft,mTvMid,mTvRight,mTvState;
    private DeviceEditDialog.IOnCancelListener cancelListener;
    private DeviceEditDialog.IOnConfirmListener confirmListener;
    private String cancel,confirm;

    String deviceState;
    String state;
    String sn;

    public DeviceEditDialog(@NonNull Context context) {
        super(context);
    }

    public DeviceEditDialog(@NonNull Context context, String sn,String deviceState, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.deviceState = deviceState;
        this.sn =sn;
    }

    public DeviceEditDialog setCancel(String cancel, DeviceEditDialog.IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public DeviceEditDialog setConfirm(String confirm, DeviceEditDialog.IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener=listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_edit_dialog_layout);
        mTvLeft = findViewById(R.id.device_edit_dialog_left);
        mTvMid = findViewById(R.id.device_edit_dialog_mid);
        mTvRight = findViewById(R.id.device_edit_dialog_right);
        button = findViewById(R.id.device_edit_btn);
        mTvState= findViewById(R.id.device_edit_state);


        mTvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvLeft.setBackgroundResource(R.drawable.device_edit_dialog_background_left_true);
                mTvLeft.setTextColor(Color.parseColor("#ffffff"));
                mTvMid.setBackgroundResource(R.drawable.device_edit_dialog_background_mid_false);
                mTvMid.setTextColor(Color.parseColor("#444c72"));
                mTvRight.setBackgroundResource(R.drawable.device_edit_dialog_background_right_false);
                mTvRight.setTextColor(Color.parseColor("#444c72"));
                mTvState.setText("5");
            }
        });

        mTvMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvLeft.setBackgroundResource(R.drawable.device_edit_dialog_background_left_false);
                mTvLeft.setTextColor(Color.parseColor("#444c72"));
                mTvMid.setBackgroundResource(R.drawable.device_edit_dialog_background_mid_true);
                mTvMid.setTextColor(Color.parseColor("#ffffff"));
                mTvRight.setBackgroundResource(R.drawable.device_edit_dialog_background_right_false);
                mTvRight.setTextColor(Color.parseColor("#444c72"));
                mTvState.setText("10");
            }
        });

        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvLeft.setBackgroundResource(R.drawable.device_edit_dialog_background_left_false);
                mTvLeft.setTextColor(Color.parseColor("#444c72"));
                mTvMid.setBackgroundResource(R.drawable.device_edit_dialog_background_mid_false);
                mTvMid.setTextColor(Color.parseColor("#444c72"));
                mTvRight.setBackgroundResource(R.drawable.device_edit_dialog_background_right_true);
                mTvRight.setTextColor(Color.parseColor("#ffffff"));
                mTvState.setText("1");
            }
        });

        Log.d("devicestatae",deviceState);

        if (deviceState.equals("状态：维修")){
            mTvLeft.performClick();
        }else if (deviceState.equals("状态：维保")){
            mTvMid.performClick();
        }



        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.device_edit_btn:
                if (confirmListener != null){
                    EditDevicePut(sn,getHosIdToSp("hosid",""), String.valueOf(mTvState.getText()));

                }

                dismiss();
                break;
        }
    }


    public interface IOnCancelListener{
        void onCancel(DeviceEditDialog dialog);
    }

    public interface IOnConfirmListener{
        void onConfirm(DeviceEditDialog dialog,String state);
    }


    private void EditDevicePut( String sn, String hospitalid,String status) {

        HashMap<String, String> map = new HashMap<>();
        map.put("status", status);//111

        map.put("hospitalid",hospitalid);

//        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);
        String url = Api.URL+"/v1/device/"+sn+"?hospitalid="+hospitalid+"&status="+status;


        putResEditDevice(url, map);




    }

    protected void putResEditDevice(String url, HashMap<String, String> map) {
        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();


        //2.构造request
        //2.1构造requestbody

        HashMap<String, Object> params = new HashMap<String, Object>();

        Log.e("params:",String.valueOf(params));
        Set<String> keys = map.keySet();
        for (String key:keys)
        {
            params.put(key,map.get(key));

        }


        JSONObject jsonObject = new JSONObject(params);
        String jsonStr = jsonObject.toString();

        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);

        Request request = new Request.Builder()
                .url(url)
                .put(requestBodyJson)
                .addHeader("token",getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .build();
        //3.将request封装为call
        Call call =   okHttpClient.newCall(request);
        L.e(String.valueOf(call));
        //4.执行call
//        同步执行
//        Response response = call.execute();

        //异步执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   "+e.getMessage());
                e.printStackTrace();

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(context,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);
                Gson gson = new Gson();

                final EditDeviceResponse editDeviceResponse = gson.fromJson(res, EditDeviceResponse.class);




                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (editDeviceResponse.getCode() == 0) {
                            state = String.valueOf(mTvState.getText());
                            confirmListener.onConfirm(DeviceEditDialog.this,state);

                            ToastUtils.showTextToast(context,"操作成功");

//


                        } else if (editDeviceResponse.getCode() == 10006){

                            ToastUtils.showTextToast(context,"操作失败");
                        }else if (editDeviceResponse.getCode() == 10010 || editDeviceResponse.getCode()==10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(context,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(context,LoginActivity.class);
                                    ((Activity)context).finish();
                                    context.startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }
                        else {
                            String msg = editDeviceResponse.getMsg();
                            ToastUtils.showTextToast2(context,msg);
                        }
                    }
                });


            }
        });
    }



    protected String getTokenToSp(String key, String val){
        SharedPreferences sp1 = getContext().getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token","没有token");


        return token;
    }

    protected String getUidToSp(String key, String val){
        SharedPreferences sp1 =  getContext().getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid","没有uid");


        return uid;
    }

    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = getContext().getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "没有hosid");
        return hosid;
    }







}
