package com.example.bajiesleep;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.entity.DeviceRecoverResponse;
import com.example.bajiesleep.entity.LendDeviceResponse;
import com.example.bajiesleep.entity.RecoverResponse;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter4;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecoverDeviceActivity extends Activity {
    private ImageView mIvDvFalse, mIvDvTrue;
    private TextView mTvDeviceState,mTvDvSn,mTvUser,mTvMobile,mTvReport;
    private Button mBtnRecover;
    private RecyclerView recyclerView;
    private LinearLayout linearLeft;
    String Dvstate;
    ListViewAdapter4 listViewAdapter4;
    DeviceRecoverResponse.DataBean dataBean = new DeviceRecoverResponse.DataBean();
    List<DeviceRecoverResponse.DataBean.ReportBean> reportBeans = new ArrayList<>();
    String deviceSn,deviceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_device);
        mIvDvFalse =findViewById(R.id.recover_device_false);
        mIvDvTrue =findViewById(R.id.recover_device_true);
        mTvDeviceState = findViewById(R.id.recover_device_tv_state);
        mTvDvSn = findViewById(R.id.recover_device_sn);
        mTvUser = findViewById(R.id.recover_tv_name);
        mTvMobile = findViewById(R.id.recover_tv_mobile);
        mTvReport = findViewById(R.id.recover_tv_report);
        linearLeft = findViewById(R.id.recover_liner_left);
        mBtnRecover = findViewById(R.id.btn_recover_device);
        recyclerView = findViewById(R.id.recover_rv_report_date);
        Intent intent=getIntent();
        deviceSn=intent.getStringExtra("sn2");
        deviceState = intent.getStringExtra("deviceState");
        final String hosid = getHosIdToSp("hosid","");
        getDeviceRecoverResponse(Api.URL+"/v1/device/"+deviceSn+"?hospitalid="+hosid);
        mTvDeviceState.setVisibility(View.GONE);
        mTvDvSn.setText(deviceSn);


        mIvDvFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvDvFalse.setImageResource(R.drawable.device_false_state_button_focus);
                mIvDvTrue.setImageResource(R.drawable.device_true_state_button_normal);
                Dvstate = "2";
                mTvDeviceState.setText(Dvstate);
            }
        });

        mIvDvTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvDvTrue.setImageResource(R.drawable.device_true_state_button_focus);
                mIvDvFalse.setImageResource(R.drawable.device_false_state_button_normal);
                Dvstate ="1";
                mTvDeviceState.setText(Dvstate);
            }
        });

        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deviceState.equals("stopDevice")){
                    Intent intent = new Intent(RecoverDeviceActivity.this,DeviceListActivity.class);
                    startActivity(intent);
                    finish();
                }else if (deviceState.equals("scanStop")){

                    finish();
                }
            }
        });

        mBtnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = "2";
                RecoverPost(type,Dvstate,getHosIdToSp("hosid",""));
            }
        });
    }



    protected String getTokenToSp(String key, String val) {
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "没有token");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "没有uid");


        return uid;
    }


    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "没有hosid");
        return hosid;
    }

    private void getDeviceRecoverResponse(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .build();
        //3.将request封装为call
        Call call = okHttpClient.newCall(request);
        //4.执行call
//        同步执行
//        Response response = call.execute();

        //异步执行
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   " + e.getMessage());
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(RecoverDeviceActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("recoverRes",res);

                //封装java对象

                final DeviceRecoverResponse deviceRecoverResponse = new DeviceRecoverResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject data = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    deviceRecoverResponse.setCode(code);
                    deviceRecoverResponse.setMsg(msg);
                    deviceRecoverResponse.setData(dataBean);

                    //第二层解析
                    int type = data.optInt("type");
                    int uid = data.optInt("uid");
                    String sex = data.optString("sex");
                    String mobile = data.optString("mobile");
                    String truename = data.optString("truename");
                    int reportNum = data.optInt("reportNum");
                    String sn = data.optString("sn");
                    String hospitalid = data.optString("hospitalid");

                    JSONArray report = data.optJSONArray("report");

                    //第二层封装
                     dataBean.setType(type);
                     dataBean.setUid(uid);
                     dataBean.setSex(sex);
                     dataBean.setMobile(mobile);
                     dataBean.setTruename(truename);
                     dataBean.setReportNum(reportNum);
                     dataBean.setSn(sn);
                     dataBean.setHospitalid(hospitalid);


                    DeviceRecoverResponse.DataBean.ReportBean  reportBean = new DeviceRecoverResponse.DataBean.ReportBean();


                    for (int i = 0; i < report.length(); i++) {
                        JSONObject jsonObject1 = report.getJSONObject(i);
                        if (jsonObject1 != null) {
                            int update_time = jsonObject1.optInt("update_time");
                            String report_id = jsonObject1.optString("report_id");
                            int quality = jsonObject1.optInt("quality");
                            String reportUrl = jsonObject1.optString("reportUrl");


                            reportBean.setUpdate_time(update_time);
                            reportBean.setReport_id(report_id);
                            reportBean.setQuality(quality);
                            reportBean.setReportUrl(reportUrl);
                            reportBean.setTrueName(dataBean.getTruename());
                            reportBeans.add(reportBean);
                        }



                    }
                    dataBean.setReport(reportBeans);
                    Log.d("recoverRes123123", String.valueOf(reportBeans));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (deviceRecoverResponse.getCode() == 0){
                            mTvUser.setText(dataBean.getTruename()+"  "+dataBean.getSex());
                            mTvMobile.setText(dataBean.getMobile());
                            mTvReport.setText(dataBean.getReportNum()+"份");
//                            Log.d("reportBeans", String.valueOf(reportBeans));
                            listViewAdapter4 = new ListViewAdapter4(reportBeans,RecoverDeviceActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecoverDeviceActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            recyclerView.setAdapter(listViewAdapter4);
                        }else if(deviceRecoverResponse.getCode() == 10010 || deviceRecoverResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(RecoverDeviceActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(RecoverDeviceActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }



                    }
                });


            }


        });


    }



    private void RecoverPost( String type, String devStatus,String hospitalid) {


        if (TextUtils.isEmpty(Dvstate)) {
            ToastUtils.showTextToast2(RecoverDeviceActivity.this, "请选择设备状态");

        }  else {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", type);//111
            map.put("devStatus",devStatus);
            map.put("hospitalid",hospitalid);

//        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);
            String url = Api.URL+"/v1/device/"+deviceSn+"?devStatus="+devStatus+"&type=2";
            L.e( String.valueOf(map));

            postResRecover(url, map);
        }



    }

    protected void postResRecover(String url, HashMap<String, String> map) {
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
                        ToastUtils.showTextToast2(RecoverDeviceActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        L.e(res);

                        Gson gson = new Gson();

                        RecoverResponse recoverResponse = gson.fromJson(res, RecoverResponse.class);

                        if (recoverResponse.getCode() == 0) {
                            Log.d("asdajsdlka",deviceState);
                            if (deviceState.equals("stopDevice")){
                                ToastUtils.showTextToast(RecoverDeviceActivity.this,"回收成功");
                                Intent intent = new Intent(RecoverDeviceActivity.this,DeviceListActivity.class);
                                startActivity(intent);
                                finish();
                            }else if (deviceState.equals("scanStop")){
                                String msg = recoverResponse.getMsg();
                                ToastUtils.showTextToast(RecoverDeviceActivity.this,"回收成功");

                                finish();
                            }



//
                        } else {
                            String msg = recoverResponse.getMsg();
                            ToastUtils.showTextToast2(RecoverDeviceActivity.this,msg);
                        }
                    }
                });

            }
        });
    }
    public void saveStopDeviceStateToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("stopDeviceState", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getStopDeviceStateToSp(String key, String val){
        SharedPreferences sp = getSharedPreferences("stopDeviceState", MODE_PRIVATE);
        String stopDeviceState = sp.getString("stopDeviceState","没有stopDeviceState");
        return stopDeviceState;
    }


}