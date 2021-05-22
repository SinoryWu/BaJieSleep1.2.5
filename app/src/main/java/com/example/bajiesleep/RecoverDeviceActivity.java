package com.example.bajiesleep;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.entity.DeviceRecoverResponse;
import com.example.bajiesleep.entity.RecoverResponse;
import com.example.bajiesleep.entity.ReportListResponse;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter4;
import com.example.bajiesleep.fragment.recyclerview.ReportListAdapter;
import com.google.gson.Gson;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

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
    DeviceRecoverResponse.DataBean recoverDataBean = new DeviceRecoverResponse.DataBean();
    List<DeviceRecoverResponse.DataBean.ReportBean> recoverReportBeans = new ArrayList<>();
    String deviceSn,deviceState;
    String hosid;

    ReportListResponse.DataBeanX dataBeanX = new ReportListResponse.DataBeanX();
    public List<ReportListResponse.DataBeanX.DataBean> dataBeans = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();

    }

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
        hosid = getHosIdToSp("hosid","");
//        getDeviceRecoverResponse(Api.URL+"/v1/device/"+deviceSn+"?hospitalid="+hosid);
        getDeviceRecoverResponse(Api.URL+"/v1/device/"+deviceSn+"?hospitalid="+hosid);
//        getResReportList(Api.URL+"/v1/report?hospitalid="+hosid+"&keywords="+deviceSn);
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

                finish();
//                if (deviceState.equals("stopDevice")){
//                    Intent intent = new Intent(RecoverDeviceActivity.this,DeviceListActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else if (deviceState.equals("scanStop")){
//
//                    finish();
//                }
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
                    deviceRecoverResponse.setData(recoverDataBean);

                    if (data != null){
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
                        recoverDataBean.setType(type);
                        recoverDataBean.setUid(uid);
                        recoverDataBean.setSex(sex);
                        recoverDataBean.setMobile(mobile);
                        recoverDataBean.setTruename(truename);
                        recoverDataBean.setReportNum(reportNum);
                        recoverDataBean.setSn(sn);
                        recoverDataBean.setHospitalid(hospitalid);





                        for (int i = 0; i < report.length(); i++) {
                            JSONObject jsonObject1 = report.getJSONObject(i);
                            if (jsonObject1 != null) {
                                int update_time = jsonObject1.optInt("update_time");
                                String report_id = jsonObject1.optString("report_id");
                                int quality = jsonObject1.optInt("quality");
                                String reportUrl = jsonObject1.optString("reportUrl");
                                String hospitalId = recoverDataBean.getHospitalid();
                                int id = jsonObject1.optInt("id");
                                DeviceRecoverResponse.DataBean.ReportBean  reportBean = new DeviceRecoverResponse.DataBean.ReportBean();
                                reportBean.setHospitalid(hospitalId);
                                reportBean.setUpdate_time(update_time);
                                reportBean.setReport_id(report_id);
                                reportBean.setQuality(quality);
                                reportBean.setReportUrl(reportUrl);
                                reportBean.setId(id);
                                recoverReportBeans.add(reportBean);
                            }



                        }
                        recoverDataBean.setReport(recoverReportBeans);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (deviceRecoverResponse.getCode() == 0){
                            mTvUser.setText(recoverDataBean.getTruename()+"  "+ recoverDataBean.getSex());
                            mTvMobile.setText(recoverDataBean.getMobile());
                            mTvReport.setText(recoverDataBean.getReportNum()+"份");
                            Log.d("reportBeans", String.valueOf(recoverReportBeans));
                            Log.d("reportBeans", String.valueOf(recoverDataBean));
                            listViewAdapter4 = new ListViewAdapter4(recoverReportBeans,RecoverDeviceActivity.this);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RecoverDeviceActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
//
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
//                                Intent intent = new Intent(RecoverDeviceActivity.this,DeviceListActivity.class);
//                                startActivity(intent);
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


    private void getResReportList(String url) {

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
                Log.d("searchUserInfo",res);

                //封装java对象

                final ReportListResponse reportListResponse = new ReportListResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    reportListResponse.setCode(code);
                    reportListResponse.setMsg(msg);
                    reportListResponse.setData(dataBeanX);

                    if (datax != null){
                        //第二层解析
                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url= datax.optString("last_page_url");
                        String next_page_url= datax.optString("next_page_url");
                        String path= datax.optString("path");
                        int per_page= datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to= datax.optInt("to");
                        int total= datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        //第二层封装
                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeans);


                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {
                                int getup = jsonObject1.optInt("getup");
                                int fallasleep = jsonObject1.optInt("fallasleep");
                                int id = jsonObject1.optInt("id");
                                String sn = jsonObject1.optString("sn");
                                String ahi = jsonObject1.optString("ahi");
                                int uid = jsonObject1.optInt("uid");
                                String report_id = jsonObject1.optString("report_id");
                                String truename = jsonObject1.optString("truename");
                                int hospitalid = jsonObject1.optInt("hospitalid");
                                String sleeptime = jsonObject1.optString("sleeptime");
                                int quality = jsonObject1.optInt("quality");
                                int createTime = jsonObject1.optInt("createTime");
                                String reportUrl = jsonObject1.optString("reportUrl");
                                String hospitalName = jsonObject1.optString("hospitalName");
                                int modeType = jsonObject1.optInt("modeType");
                                int ahiLevel = jsonObject1.optInt("ahiLevel");


                                ReportListResponse.DataBeanX.DataBean  dataBean = new ReportListResponse.DataBeanX.DataBean();
                                dataBean.setId(id);
                                dataBean.setSn(sn);
                                dataBean.setAhi(ahi);
                                dataBean.setReport_id(report_id);
                                dataBean.setTruename(truename);
                                dataBean.setHospitalid(hospitalid);
                                dataBean.setSleeptime(sleeptime);
                                dataBean.setQuality(quality);
                                dataBean.setCreateTime(createTime);
                                dataBean.setReportUrl(reportUrl);
                                dataBean.setHospitalName(hospitalName);
                                dataBean.setModeType(modeType);
                                dataBean.setAhiLevel(ahiLevel);
                                dataBeans.add(dataBean);

                            }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {




                        Log.d("prev_page_url", String.valueOf(dataBeanX.getPrev_page_url()));

                        if (reportListResponse.getCode() == 0){








                        }else if (reportListResponse.getCode()==10010 || reportListResponse.getCode() == 10004){
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
                        }else {
                            ToastUtils.showTextToast(RecoverDeviceActivity.this,reportListResponse.getMsg());
                        }


                    }
                });


            }


        });


    }





}