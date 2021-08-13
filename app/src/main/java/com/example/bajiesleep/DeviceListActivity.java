package com.example.bajiesleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.print.PrinterId;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bajiesleep.entity.DeviceListResponse;
import com.example.bajiesleep.entity.SearchDeviceScanResponse1;
import com.example.bajiesleep.fragment.recyclerview.DeviceListAdapter;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter2;
import com.example.bajiesleep.util.GetShp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.VISIBLE;

public class DeviceListActivity extends AppCompatActivity {

    private List<DeviceListResponse.DataBean> dataBeans = new ArrayList<>();

    private RecyclerView recyclerView;
    private DeviceListAdapter deviceListAdapter;
    private LinearLayout linearLeft;
    private EditText mEtSearch;
    private Button mBtnSearch;
    private RelativeLayout mIvCleanSearch,mRlNull;
    private TextView mTvDeviceNumber;
    CharSequence chars ;


    @Override
    protected void onResume() {
        super.onResume();
        if (dataBeans.size() > 0 ){
            dataBeans.clear();
            deviceListAdapter.notifyDataSetChanged();
            getResDeviceList(Api.URL+"/v1/splDev?hospitalid="+getHosIdToSp("hosid",""));
        }else {

            getResDeviceList(Api.URL+"/v1/splDev?hospitalid="+getHosIdToSp("hosid",""));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        recyclerView = findViewById(R.id.device_list_recyclerview);
        linearLeft = findViewById(R.id.device_liner_left);
        mEtSearch = findViewById(R.id.device_list_search);
        mBtnSearch=findViewById(R.id.btn_device_list_search);
        mIvCleanSearch = findViewById(R.id.device_list_search_clean);
        mTvDeviceNumber = findViewById(R.id.tv_device_list_number);
        mRlNull = findViewById(R.id.device_list_null);
        mRlNull.setVisibility(View.GONE);


        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mIvCleanSearch.setVisibility(View.GONE);



        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String text= String.valueOf(textView.getText()).replaceAll(" ","");
                if (i == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    mIvCleanSearch.setVisibility(VISIBLE);
                    mBtnSearch.setVisibility(View.GONE);
                    dataBeans.clear();
                    getResDeviceList2(Api.URL+"/v1/splDev?hospitalid="+getHosIdToSp("hosid","")+"&keywords="+text);


                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(DeviceListActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                }
                mIvCleanSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEtSearch.setText("");
                        mIvCleanSearch.setVisibility(View.GONE);
                        dataBeans.clear();
                        getResDeviceList(Api.URL+"/v1/splDev?hospitalid="+getHosIdToSp("hosid",""));

                    }
                });
                return false;
            }
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {

                chars = charSequence;

                final String text  = String.valueOf(charSequence).replaceAll(" ","");
                if (!"".equals(charSequence.toString())){
                    mBtnSearch.setVisibility(VISIBLE);
                    mBtnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mIvCleanSearch.setVisibility(VISIBLE);
                            mBtnSearch.setVisibility(View.GONE);
                            dataBeans.clear();
                            getResDeviceList2(Api.URL+"/v1/splDev?&hospitalid="+getHosIdToSp("hosid","")+"&keywords="+text);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                            imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(),0);


                        }
                    });

                    mIvCleanSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mEtSearch.setText("");
                            mIvCleanSearch.setVisibility(View.GONE);
                            dataBeans.clear();
                            getResDeviceList(Api.URL+"/v1/splDev?hospitalid="+getHosIdToSp("hosid",""));
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm.isActive()) {
                                imm.hideSoftInputFromWindow(DeviceListActivity.this.getCurrentFocus().getWindowToken(), 0);
                            }
                        }
                    });
                }else {
                    dataBeans.clear();
                    getResDeviceList(Api.URL+"/v1/splDev?hospitalid="+getHosIdToSp("hosid",""));
                    mBtnSearch.setVisibility(View.GONE);
                    mIvCleanSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }




    public void getResDeviceList(String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
                .build();
        //3.将request封装为call
        Call call =   okHttpClient.newCall(request);
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
                        ToastUtils.showTextToast2(DeviceListActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchres",res);

//                parseJSONWithGSON(res);


                //封装java对象


                final DeviceListResponse deviceListResponse = new DeviceListResponse();


                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");

                    //第一层封装
                    deviceListResponse.setCode(code);
                    deviceListResponse.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    deviceListResponse.setData(dataBeans);
                    //第二层解析


                    if (data != null){
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                String sn = jsonObject1.optString("sn");
                                String truename = jsonObject1.optString("truename");
                                String devStatus = jsonObject1.optString("devStatus");
                                //第二层封装

                                DeviceListResponse.DataBean dataBean = new DeviceListResponse.DataBean();

                                dataBean.setSn(sn);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setTruename(truename);
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

                        if (deviceListResponse.getCode() == 0){

                            Log.d("deviceliststart", "ok ");
                            mTvDeviceNumber.setText("共找到"+deviceListResponse.getData().size()+"台设备");
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DeviceListActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            deviceListAdapter = new DeviceListAdapter(dataBeans,DeviceListActivity.this);
                            recyclerView.setAdapter(deviceListAdapter);
                            if (deviceListResponse.getData() == null || deviceListResponse.getData().size() == 0){
                                mRlNull.setVisibility(VISIBLE);
                            }
                        }else if (deviceListResponse.getCode() == 10010 || deviceListResponse.getCode() == 10004 ){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(DeviceListActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(DeviceListActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }

                        else {
                            ToastUtils.showTextToast(DeviceListActivity.this,deviceListResponse.getMsg());
                        }




                    }
                });

            }


        });
    }

    public void getResDeviceList2(String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
                .build();
        //3.将request封装为call
        Call call =   okHttpClient.newCall(request);
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
                        ToastUtils.showTextToast2(DeviceListActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchres",res);

//                parseJSONWithGSON(res);


                //封装java对象


                final DeviceListResponse deviceListResponse = new DeviceListResponse();


                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");
                    //第一层封装
                    deviceListResponse.setCode(code);
                    deviceListResponse.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    deviceListResponse.setData(dataBeans);
                    //第二层解析

                    if (data != null){
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                String sn = jsonObject1.optString("sn");
                                String truename = jsonObject1.optString("truename");
                                String devStatus = jsonObject1.optString("devStatus");
                                //第二层封装

                                DeviceListResponse.DataBean dataBean = new DeviceListResponse.DataBean();

                                dataBean.setSn(sn);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setTruename(truename);
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

                        if (deviceListResponse.getCode() == 0){
                            mTvDeviceNumber.setText("共找到"+deviceListResponse.getData().size()+"台设备");
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DeviceListActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            deviceListAdapter = new DeviceListAdapter(dataBeans,DeviceListActivity.this);
                            recyclerView.setAdapter(deviceListAdapter);

                        }else if (deviceListResponse.getCode() == 10010 || deviceListResponse.getCode() == 10004 ){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(DeviceListActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(DeviceListActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }

                        else {
                            ToastUtils.showTextToast(DeviceListActivity.this,deviceListResponse.getMsg());
                        }


                    }
                });

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


}