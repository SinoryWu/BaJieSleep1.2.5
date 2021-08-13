package com.example.bajiesleep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bajiesleep.entity.StopDeviceResponse;
import com.example.bajiesleep.util.GetShp;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
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

public class DeviceStopDialog extends Dialog implements View.OnClickListener {

    private TextView mTvSn,mTvTime,mTvIntentRecover;
    private TimeCount time;
    private Context context;
    private Button buttonFalse,buttonTrue;
    private DeviceStopDialog.IOnCancelListener cancelListener;
    private DeviceStopDialog.IOnConfirmListener confirmListener;
    private String cancel,confirm;
    private RelativeLayout mRl1,mRl2;

    String state;
    String sn;

    public DeviceStopDialog(@NonNull Context context) {
        super(context);
    }

    public DeviceStopDialog(@NonNull Context context, String sn,int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.sn =sn;
    }

    public DeviceStopDialog setCancel(String cancel, com.example.bajiesleep.DeviceStopDialog.IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public DeviceStopDialog setConfirm(String confirm, com.example.bajiesleep.DeviceStopDialog.IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener=listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_stop_dialog_layout);
        buttonFalse = findViewById(R.id.btn_stop_device_false);
        buttonTrue = findViewById(R.id.btn_stop_device_true);
        mTvTime  = findViewById(R.id.stop_device_dismiss_time);
        buttonFalse.setOnClickListener(this);
        buttonTrue.setOnClickListener(this);
        mTvSn = findViewById(R.id.stop_device_item_sn);
        mTvIntentRecover = findViewById(R.id.device_stop_intent_recover);
        mRl1 =  findViewById(R.id.device_stop_rl1);
        mRl2 = findViewById(R.id.device_stop_rl2);
        mTvSn.setVisibility(View.GONE);
        mTvSn.setText(sn);
        mRl2.setVisibility(View.GONE);


        time = new TimeCount(4000, 1000);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_stop_device_false:
                if (cancelListener != null){
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;

            case R.id.btn_stop_device_true:
                if (confirmListener != null){


                    StopDevicePost(sn,getHosIdToSp("hosid",""));


                    confirmListener.onConfirm(DeviceStopDialog.this,state);

                }

                break;
        }
    }




    public interface IOnCancelListener{
        void onCancel(DeviceStopDialog dialog);
    }

    public interface IOnConfirmListener{
        void onConfirm(DeviceStopDialog dialog, String state);
    }


    private void StopDevicePost( String sn, String hospitalid) {

            HashMap<String, String> map = new HashMap<>();
            map.put("sn", sn);//111

            map.put("hospitalid",hospitalid);

//        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);
            String url = Api.URL+"/v1/device/endDev?hospitalid="+hospitalid+"&sn="+sn;


            postResStopDevice(url, map);




    }

    protected void postResStopDevice(String url, HashMap<String, String> map) {
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
                .post(requestBodyJson)
                .addHeader("token",getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(context))
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

                final StopDeviceResponse stopDeviceResponse = gson.fromJson(res, StopDeviceResponse.class);

                Log.d("code", String.valueOf(stopDeviceResponse.getCode()));


                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (stopDeviceResponse.getCode() == 0) {


//                    String msg = stopDeviceResponse.getMsg();
                    ToastUtils.showTextToast(context,"操作成功");

                            mRl1.setVisibility(View.GONE);
                            mRl2.setVisibility(View.VISIBLE);
                            time.start();
                            mTvIntentRecover.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    Intent intent = new Intent(getContext(),RecoverDeviceActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("sn2",sn);
                                    bundle.putString("deviceState","stopDevice");
                                    intent.putExtras(bundle);
                                    getContext().startActivity(intent);
//                                    ((Activity) context).finish();
                                    dismiss();
                                }
                            });











                        } else if (stopDeviceResponse.getCode() == 10006){

                            ToastUtils.showTextToast(context,"操作失败:当前设备未处于使用状态");
                        }else if (stopDeviceResponse.getCode() == 10010 || stopDeviceResponse.getCode()==10004){
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
                            String msg = stopDeviceResponse.getMsg();
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


    /**
     * 实现点击获取验证码按钮倒计时效果
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {


            mTvTime.setText("" + millisUntilFinished / 1000 + "s");

        }

        @Override
        public void onFinish() {

           dismiss();
        }


    }





}
