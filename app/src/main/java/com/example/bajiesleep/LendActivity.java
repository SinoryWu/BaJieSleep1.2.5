package com.example.bajiesleep;

import androidx.annotation.RequiresApi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bajiesleep.entity.LendDeviceResponse;
import com.example.bajiesleep.fragment.HomeFragment;
import com.example.bajiesleep.util.GetShp;
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

public class LendActivity extends Activity {
    private Button mBtnLend;
    private TextView mTvSn,mTvTrueName,mTvMobile,mTvSurTime,mTvRangeTime,mTvLendDays,mTvLendDay,mTvUserUid;
    private LinearLayout linearLeft;
    DayDialog dayDialog = null;
    String deviceSn;
    String truename;
    String mobile;
    String uid;
    String rangeTime;
    String deviceState;
    TimeRangePickerDialog dialog=null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);

        final Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#f7f7f7"));


        mBtnLend=findViewById(R.id.btn_lend_device);
        mTvSn = findViewById(R.id.lend_device_sn);
        linearLeft=findViewById(R.id.liner_left);
        mTvTrueName = findViewById(R.id.lend_tv_name);
        mTvMobile=findViewById(R.id.lend_tv_mobile);
        mTvSurTime = findViewById(R.id.survey_time);
        mTvRangeTime=findViewById(R.id.range_time);
        mTvLendDays=findViewById(R.id.lend_days);
        mTvLendDay=findViewById(R.id.lend_day);
        mTvUserUid=findViewById(R.id.user_uid);

        mTvLendDay.setVisibility(View.GONE);
        mTvUserUid.setVisibility(View.GONE);
        mTvRangeTime.setVisibility(View.GONE);

        Intent intent=getIntent();
        deviceSn=intent.getStringExtra("sn");
        deviceState = intent.getStringExtra("deviceState");
        mTvSn.setText(deviceSn);

        mTvLendDays.setText("1天");
        mTvLendDay.setText("1");
//        Log.d("deviceSn",deviceSn);
        rangeTime = "21"+":"+"00"+" - "+"06"+":"+"00";
        mTvSurTime.setText("21"+":"+"00"+"~"+"06"+":"+"00");


        String userTrueName = intent.getStringExtra("trueName");
        String userMobile = intent.getStringExtra("mobile");

//        Log.d("name&mobile",userTrueName+userMobile);
//        ToastUtils.showTextToast(LendActivity.this,userTrueName+userMobile);

//        if (TextUtils.isEmpty(deviceSn1)  ){
//            mTvSn.setText(deviceSn);
//            mTvTrueName.setText("请输入");
//            mTvMobile.setText("");
//        }else {
//            mTvSn.setText(deviceSn1);
//            mTvTrueName.setText(userTrueName);
//            mTvMobile.setText(userMobile);
//        }
//        if (!(userTrueName == null)){
//            mTvSn.setText(deviceSn);
//            mTvTrueName.setText(userTrueName);
//            mTvMobile.setText(userMobile);
//        }else {
//            mTvSn.setText(deviceSn);
//            mTvTrueName.setText("请输入");
//            mTvMobile.setText("");
//        }

        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
//                if (deviceState.equals("lendDevice")){
//                    Intent intent = new Intent(LendActivity.this,DeviceListActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else if (deviceState.equals("scanStop")){
//
//                    finish();
//                }

            }
        });


        mTvTrueName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendActivity.this,SearchUserRecyclerView.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("devicesn",deviceSn);
//                intent.putExtras(bundle);
                startActivityForResult(intent,1);


            }
        });

        mTvLendDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dayDialog == null){
                    dayDialog = new DayDialog(LendActivity.this,R.style.dialog);
                    dayDialog.setTitle("选择天数").setCancel("取消", new DayDialog.IOnCancelListener() {
                        @Override
                        public void onCancel(DayDialog dialog) {

                        }
                    }).setConfirm("确定", new DayDialog.IOnConfirmListener() {
                        @Override
                        public void onConfirm(DayDialog dialog, String days) {
                            mTvLendDays.setText(days);
                            if (days.equals("1 天")){
                                mTvLendDay.setText("1");
                            }else if (days.equals("2 天")){
                                mTvLendDay.setText("2");
                            }else if (days.equals("3 天")){
                                mTvLendDay.setText("3");
                            }else if (days.equals("7 天")){
                                mTvLendDay.setText("7");
                            }

                        }
                    });



                }
                dayDialog.setCanceledOnTouchOutside(false);
                dayDialog.show();


            }
        });


        mTvSurTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog == null){
                    dialog = new TimeRangePickerDialog(LendActivity.this, mTvSurTime.getText().toString(), new TimeRangePickerDialog.ConfirmAction() {
                        @Override
                        public void onLeftClick() {
                        }

                        @Override
                        public void onRightClick(String startHour, String starMinute, String endHour, String endMinute) {

                            rangeTime = startHour+":"+starMinute+" - "+endHour+":"+endMinute;
                            mTvSurTime.setText(startHour+":"+starMinute+"~"+endHour+":"+endMinute);
                            mTvRangeTime.setText(startHour+":"+starMinute+" - "+endHour+":"+endMinute);
                            Log.d("rangetime",rangeTime);
                        }

                    });

                }


                dialog.show();
                dialog.setCanceledOnTouchOutside(false);
            }
        });


        mBtnLend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String frequency = String.valueOf(mTvLendDay.getText());
                String type = "1";


                LendPost(type,uid,rangeTime,frequency,getHosIdToSp("hosid",""));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    truename = data.getStringExtra("truename");
                    mobile = data.getStringExtra("mobile");
                    uid = data.getStringExtra("uid");
                    mTvTrueName.setText(truename);
                    mTvMobile.setText(mobile);
                    mTvUserUid.setText(uid);

                }
                break;
        }


    }



    private void LendPost( String type, String uid1,String monitor,String frequency,String hospitalid) {


        if (TextUtils.isEmpty(uid)) {
            ToastUtils.showTextToast2(LendActivity.this, "请选择用户");

        } else if (TextUtils.isEmpty(rangeTime)) {
            ToastUtils.showTextToast2(LendActivity.this, "选择监测时段");
        } else if (TextUtils.isEmpty(mTvLendDay.getText())) {
            ToastUtils.showTextToast2(LendActivity.this, "请选择借出周期");
        }  else if (!TextUtils.isEmpty(uid)  && !TextUtils.isEmpty(mTvLendDay.getText()) &&  !TextUtils.isEmpty(rangeTime)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", type);//111
            map.put("uid",uid1);
            map.put("monitor",monitor);
            map.put("frequency", frequency);
            map.put("hospitalid",hospitalid);

//        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);
            String url = Api.URL+"/v2/device/"+deviceSn;
            L.e( String.valueOf(map));

            postResLend(url, map);
        }



    }

    protected void postResLend(String url, HashMap<String, String> map) {
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
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(LendActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);
                Log.d("lenduser", res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        L.e(res);

                        Gson gson = new Gson();

                        LendDeviceResponse lendDeviceResponse = gson.fromJson(res, LendDeviceResponse.class);

                        if (lendDeviceResponse.getCode() == 0) {

                            if (deviceState.equals("lendDevice")){
                                ToastUtils.showTextToast(LendActivity.this,"借出成功");
//                                Intent intent = new Intent(LendActivity.this,DeviceListActivity.class);
//                                startActivity(intent);
                                finish();
                            }else if (deviceState.equals("scanStop")){
                                String msg = lendDeviceResponse.getMsg();
                                ToastUtils.showTextToast(LendActivity.this,"借出成功");

                                finish();
                            }


                        }else if (lendDeviceResponse.getCode() == 10010 || lendDeviceResponse.getCode()==10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(LendActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(LendActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }
                        else {
                            String msg = lendDeviceResponse.getMsg();
                            ToastUtils.showTextToast2(LendActivity.this,msg);
                        }
                    }
                });

            }
        });
    }


    protected String getTokenToSp(String key, String val){
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token","没有token");


        return token;
    }

    protected String getUidToSp(String key, String val){
        SharedPreferences sp1 =  getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid","没有uid");


        return uid;
    }

    public void saveLendDeviceStateToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("lendDeviceState", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getLendDeviceStateToSp(String key, String val){
        SharedPreferences sp = getSharedPreferences("lendDeviceState", MODE_PRIVATE);
        String stopDeviceState = sp.getString("lendDeviceState","没有lendDeviceState");
        return stopDeviceState;
    }

    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "没有hosid");
        return hosid;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LendActivity","onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LendActivity","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LendActivity","onResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LendActivity","onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LendActivity","onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LendActivity","onStart");
    }
}