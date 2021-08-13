package com.example.bajiesleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.bajiesleep.entity.ReportListResponse;
import com.example.bajiesleep.entity.SearchUserInfoResponse;
import com.example.bajiesleep.entity.UserListResponse;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter3;
import com.example.bajiesleep.fragment.recyclerview.ReportListAdapter;
import com.example.bajiesleep.fragment.recyclerview.UserListAdapter;
import com.example.bajiesleep.fragment.recyclerview.UserListInfoAdapter;
import com.example.bajiesleep.util.GetShp;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.VISIBLE;

public class UserListAcivity extends AppCompatActivity {


    UserListResponse.DataBeanX dataBeanX = new UserListResponse.DataBeanX();
    public List<UserListResponse.DataBeanX.DataBean> dataBeans = new ArrayList<>();
    public List<UserListResponse.DataBeanX.DataBean.ReportBean> reportBeans = new ArrayList<>();



    UserListAdapter userListAdapter;

    public PullLoadMoreRecyclerView recyclerView;

    private TextView mTvListMember;

    private EditText mEtSearch;

    private LinearLayout linearLeft;
    CharSequence chars ;

    private Button mBtnSearch;
    private RelativeLayout mIvCleanSearch;

    private UserListInfoAdapter listInfoAdapter;
    private RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_acivity);


        linearLeft  = findViewById(R.id.user_liner_left);
        recyclerView = findViewById(R.id.user_list_recyclerview);
        mTvListMember = findViewById(R.id.tv_user_list_member);
        recyclerView2 = findViewById(R.id.user_list_rvReport);

        mEtSearch = findViewById(R.id.user_list_search);
        mBtnSearch = findViewById(R.id.btn_user_list_search);
        mIvCleanSearch= findViewById(R.id.user_list_search_clean);
        mBtnSearch.setVisibility(View.GONE);
        mIvCleanSearch.setVisibility(View.GONE);
        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getResUserList(Api.URL+"/v2/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15&page=1");

        Log.d("hosid", getHosIdToSp("hosid",""));
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                String text= String.valueOf(textView.getText()).replaceAll(" ","");
                if (i == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    mIvCleanSearch.setVisibility(VISIBLE);
                    mBtnSearch.setVisibility(View.GONE);
                    dataBeans.clear();
                    getResUserList2(Api.URL+"/v1/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15page=1&keywords="+text);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(UserListAcivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                }
                mIvCleanSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEtSearch.setText("");
                        mIvCleanSearch.setVisibility(View.GONE);
                        dataBeans.clear();
                        getResUserList(Api.URL+"/v1/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15&page=1");

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
                final String text  = String.valueOf(charSequence).replaceAll(" ","");
                chars = text;
                if (!"".equals(charSequence.toString())){
                    mBtnSearch.setVisibility(VISIBLE);

                    mBtnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mIvCleanSearch.setVisibility(VISIBLE);
                            mBtnSearch.setVisibility(View.GONE);
                            dataBeans.clear();
                            getResUserList2(Api.URL+"/v1/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15page=1&keywords="+text);
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
                            getResUserList(Api.URL+"/v1/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15&page=1");
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm.isActive()) {
                                imm.hideSoftInputFromWindow(UserListAcivity.this.getCurrentFocus().getWindowToken(), 0);
                            }

                        }
                    });
                }else {
                    dataBeans.clear();
                    getResUserList(Api.URL+"/v1/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15&page=1");
                    mBtnSearch.setVisibility(View.GONE);
                    mIvCleanSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void getResUserList(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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
                        ToastUtils.showTextToast2(UserListAcivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo",res);

                //封装java对象

                final UserListResponse userListResponse = new UserListResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    userListResponse.setCode(code);
                    userListResponse.setMsg(msg);
                    userListResponse.setData(dataBeanX);

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
                                int id = jsonObject1.optInt("id");
                                int uid = jsonObject1.optInt("uid");
                                String truename = jsonObject1.optString("truename");
                                String mobile = jsonObject1.optString("mobile");
                                int create_time = jsonObject1.optInt("create_time");
                                String sex = jsonObject1.optString("sex");
                                boolean using = jsonObject1.optBoolean("using");
                                String hospitalName = jsonObject1.optString("hospitalName");
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                UserListResponse.DataBeanX.DataBean  dataBean = new UserListResponse.DataBeanX.DataBean();
                                List<UserListResponse.DataBeanX.DataBean.ReportUrlBean> reportUrlBeans = new ArrayList<>();

                                dataBean.setUsing(using);
                                dataBean.setId(id);
                                dataBean.setUid(uid);
                                dataBean.setTruename(truename);
                                dataBean.setMobile(mobile);
                                dataBean.setCreate_time(create_time);
                                dataBean.setSex(sex);
                                dataBean.setHospitalName(hospitalName);
                                dataBean.setReport(reportBeans);




//
//                            if (!dataBean.isUsing()){
//                                dataBean.setId(id);
//                                dataBean.setUid(uid);
//                                dataBean.setTruename(truename);
//                                dataBean.setMobile(mobile);
//                                dataBean.setCreate_time(create_time);
//                                dataBean.setSex(sex);
//
//                                dataBeans.add(dataBean);
//                                Log.d("da213dasad", String.valueOf(dataReport));
//                            }

                                UserListResponse.DataBeanX.DataBean.ReportBean  reportBean = new UserListResponse.DataBeanX.DataBean.ReportBean();

                                List<String> reporturl = new ArrayList<>();
                                List<String> reportcreateTime = new ArrayList<>();
                                List<Integer> quality1 = new ArrayList<>();



                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        int id1 = jsonObject2.optInt("id");

                                        reportBean.setId(id1);
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);


                                        reportBeans.add(reportBean);

                                        reporturl.add(reportBean.getReportUrl());
                                        reportcreateTime.add(String.valueOf(reportBean.getCreateTime()));
                                        if (reportBean.getQuality() == 1 ){
//                                            dataBean.setQuality(1);
                                            quality1.add(1);


//                                        dataBean.setReportUrl(reportBean.getReportUrl());
                                        }else {
                                            quality1.add(2);

//                                            dataBean.setQuality(2);
                                        }



                                    }



                                }

                                String[] urls = reporturl.toArray(new String[reporturl.size()]);
//
                                String[] reportcreateTimes  = reportcreateTime.toArray(new String[reportcreateTime.size()]);

                                Log.d("quality1", String.valueOf(quality1));
                                int a = 0;
                                if (quality1.size()>0){
                                    for (int j =0;j<quality1.size();j++){
                                        if (quality1.get(j ) == 1){
                                            a = j;
//                                            continue;
                                        }

                                    }
                                    dataBean.setQuality(quality1.get(a));
                                    dataBean.setReportUrl(urls[a]);
                                    dataBean.setReportCreateTime(reportcreateTimes[a]);

                                }else {
                                    dataBean.setQuality(2);
                                }




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




                        Log.d("getPrev_page_url", String.valueOf(dataBeanX.getPrev_page_url()));


                        if (userListResponse.getCode() == 0){

                            mTvListMember.setText("共找到"+dataBeanX.getTotal()+"名用户");


//                            Log.d(" quality1", String.valueOf(quality1));


                            recyclerView.setLinearLayout();
                            userListAdapter = new UserListAdapter(dataBeans,UserListAcivity.this);
                            recyclerView.setAdapter(userListAdapter);

//                            Log.d(" userListAdaptergetName", userListAdapter.getName());
                            recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {
                                    recyclerView.setPullLoadMoreCompleted();
                                }

                                @Override
                                public void onLoadMore() {

                                    Log.d("getNext_page_url", String.valueOf(dataBeanX.getNext_page_url()));
                                    recyclerView.setPullLoadMoreCompleted();
                                    if (dataBeanX.getNext_page_url().equals(" null")){


                                        recyclerView.setPullLoadMoreCompleted();
//                                        getResSearUserInfo2(Api.URL+"/v1/User/index?hospitalid="+0+"&keywords="+"1"+"&page=1");
                                    }else {
//                                        recyclerView.setPullLoadMoreCompleted();
                                        userListAdapter.notifyDataSetChanged();
                                        getResUserList3(Api.URL+"/v1/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15&page="+(dataBeanX.getCurrent_page()+1));
                                    }
                                }
                            });
                        }else if (userListResponse.getCode()==10010 || userListResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(UserListAcivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(UserListAcivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(UserListAcivity.this,userListResponse.getMsg());
                        }


                    }
                });


            }


        });


    }


    private void getResUserList2(String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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
                        ToastUtils.showTextToast2(UserListAcivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo",res);

                //封装java对象

                final UserListResponse userListResponse = new UserListResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    userListResponse.setCode(code);
                    userListResponse.setMsg(msg);
                    userListResponse.setData(dataBeanX);

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
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeans);


                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {
                                int id = jsonObject1.optInt("id");
                                int uid = jsonObject1.optInt("uid");
                                String truename = jsonObject1.optString("truename");
                                String mobile = jsonObject1.optString("mobile");
                                int create_time = jsonObject1.optInt("create_time");
                                String sex = jsonObject1.optString("sex");
                                boolean using = jsonObject1.optBoolean("using");
                                String hospitalName = jsonObject1.optString("hospitalName");
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                UserListResponse.DataBeanX.DataBean  dataBean = new UserListResponse.DataBeanX.DataBean();
                                dataBean.setId(id);
                                dataBean.setUid(uid);
                                dataBean.setTruename(truename);
                                dataBean.setMobile(mobile);
                                dataBean.setCreate_time(create_time);
                                dataBean.setSex(sex);
                                dataBean.setUsing(using);
                                dataBean.setHospitalName(hospitalName);
                                dataBean.setReport(reportBeans);

                                UserListResponse.DataBeanX.DataBean.ReportBean  reportBean = new UserListResponse.DataBeanX.DataBean.ReportBean();
                                List<String> reporturl = new ArrayList<>();
                                List<String> reportcreateTime = new ArrayList<>();
                                List<Integer> quality1 = new ArrayList<>();



                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        int id1 = jsonObject2.optInt("id");

                                        reportBean.setId(id1);
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);


                                        reportBeans.add(reportBean);

                                        reporturl.add(reportBean.getReportUrl());
                                        reportcreateTime.add(String.valueOf(reportBean.getCreateTime()));
                                        if (reportBean.getQuality() == 1){
//                                            dataBean.setQuality(1);

                                            quality1.add(1);


//                                        dataBean.setReportUrl(reportBean.getReportUrl());
                                        }else {
                                            quality1.add(2);
//                                            dataBean.setQuality(2);
                                        }





                                    }



                                }

                                String[] urls = reporturl.toArray(new String[reporturl.size()]);
//
                                String[] reportcreateTimes  = reportcreateTime.toArray(new String[reportcreateTime.size()]);
                                int a = 0;
                                if (quality1.size()>0){
                                    for (int j =0;j<quality1.size();j++){
                                        if (quality1.get(j ) == 1){
                                            a = j;
                                            Log.d("quality", String.valueOf(a));
                                            break;
                                        }
                                    }
                                    dataBean.setQuality(quality1.get(a));
                                    dataBean.setReportUrl(urls[a]);
                                    dataBean.setReportCreateTime(reportcreateTimes[a]);

                                }else {
                                    dataBean.setQuality(2);
                                }



                                dataBeans.add(dataBean);
                                Log.d("quality", String.valueOf(quality1));

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

                        if (userListResponse.getCode() == 0){

                            Log.d("dataBeans", String.valueOf(dataBeans));
                            recyclerView.setLinearLayout();

                            userListAdapter  = new UserListAdapter(dataBeans,UserListAcivity.this);

                            recyclerView.setAdapter(userListAdapter);
                            mTvListMember.setText("共找到"+String.valueOf(dataBeanX.getTotal())+"份报告");

                            userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick1(String truename) {
                                    Log.d("getname",truename);
                                }
                            });

                            recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {
                                    recyclerView.setPullLoadMoreCompleted();
                                }

                                @Override
                                public void onLoadMore() {


                                    if (dataBeanX.getNext_page_url().equals(" null")){

                                        recyclerView.setPullLoadMoreCompleted();
//                                        getResSearUserInfo2(Api.URL+"/v1/User/index?hospitalid="+0+"&keywords="+"1"+"&page=1");
                                    }else {
                                        userListAdapter.notifyDataSetChanged();
                                        getResUserList4(Api.URL+"/v1/User/index?hospitalid="+ getHosIdToSp("hosid","")+"&limit=15&page="+(dataBeanX.getCurrent_page()+1)+"&keywords="+chars);
                                    }
                                }
                            });

                        }else if (userListResponse.getCode()==10010 || userListResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(UserListAcivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(UserListAcivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(UserListAcivity.this,userListResponse.getMsg());
                        }


                    }
                });


            }


        });


    }

    private void getResUserList3(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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
                        ToastUtils.showTextToast2(UserListAcivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo2",res);

                //封装java对象

                final UserListResponse userListResponse = new UserListResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    userListResponse.setCode(code);
                    userListResponse.setMsg(msg);
                    userListResponse.setData(dataBeanX);

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
//                    dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeans);


                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {
                                int id = jsonObject1.optInt("id");
                                int uid = jsonObject1.optInt("uid");
                                String truename = jsonObject1.optString("truename");
                                String mobile = jsonObject1.optString("mobile");
                                int create_time = jsonObject1.optInt("create_time");
                                String sex = jsonObject1.optString("sex");
                                boolean using = jsonObject1.optBoolean("using");
                                String hospitalName = jsonObject1.optString("hospitalName");
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                UserListResponse.DataBeanX.DataBean  dataBean = new UserListResponse.DataBeanX.DataBean();
                                dataBean.setId(id);
                                dataBean.setUid(uid);
                                dataBean.setTruename(truename);
                                dataBean.setMobile(mobile);
                                dataBean.setCreate_time(create_time);
                                dataBean.setSex(sex);
                                dataBean.setUsing(using);
                                dataBean.setHospitalName(hospitalName);
                                dataBean.setReport(reportBeans);

                                UserListResponse.DataBeanX.DataBean.ReportBean  reportBean = new UserListResponse.DataBeanX.DataBean.ReportBean();
                                List<String> reporturl = new ArrayList<>();
                                List<String> reportcreateTime = new ArrayList<>();
                                List<Integer> quality1 = new ArrayList<>();



                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        int id1 = jsonObject2.optInt("id");

                                        reportBean.setId(id1);
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);


                                        reportBeans.add(reportBean);


                                        Log.d("repisdda", String.valueOf(reportBean.getQuality()));
                                        reporturl.add(reportBean.getReportUrl());
                                        reportcreateTime.add(String.valueOf(reportBean.getCreateTime()));

                                        if (reportBean.getQuality() == 1 ){
//                                            dataBean.setQuality(1);

                                            quality1.add(1);


//                                        dataBean.setReportUrl(reportBean.getReportUrl());
                                        }else {
                                            quality1.add(2);
//                                            dataBean.setQuality(2);
                                        }





                                    }



                                }

                                String[] urls = reporturl.toArray(new String[reporturl.size()]);
//
                                String[] reportcreateTimes  = reportcreateTime.toArray(new String[reportcreateTime.size()]);
                                int a = 0;
                                if (quality1.size()>0){
                                    for (int j =0;j<quality1.size();j++){
                                        if (quality1.get(j ) == 1){
                                            a = j;
                                            break;
                                        }
                                    }
                                    dataBean.setQuality(quality1.get(a));
                                    dataBean.setReportUrl(urls[a]);
                                    dataBean.setReportCreateTime(reportcreateTimes[a]);
                                }else {
                                    dataBean.setQuality(2);
                                }


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





                        if (userListResponse.getCode() == 0){
                            userListAdapter.notifyDataSetChanged();
                            recyclerView.setPullLoadMoreCompleted();
                            userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick1(String truename) {
                                    Log.d("getname",truename);
                                }
                            });
                        }else if (userListResponse.getCode()==10010 || userListResponse.getCode() == 10004){


                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(UserListAcivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(UserListAcivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {


                            ToastUtils.showTextToast(UserListAcivity.this,userListResponse.getMsg());
                        }


                    }
                });


            }


        });


    }

    private void getResUserList4(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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
                        ToastUtils.showTextToast2(UserListAcivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo2",res);

                //封装java对象

                final UserListResponse userListResponse = new UserListResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    userListResponse.setCode(code);
                    userListResponse.setMsg(msg);
                    userListResponse.setData(dataBeanX);

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
//                    dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeans);


                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {
                                int id = jsonObject1.optInt("id");
                                int uid = jsonObject1.optInt("uid");
                                String truename = jsonObject1.optString("truename");
                                String mobile = jsonObject1.optString("mobile");
                                int create_time = jsonObject1.optInt("create_time");
                                String sex = jsonObject1.optString("sex");
                                boolean using = jsonObject1.optBoolean("using");
                                String hospitalName = jsonObject1.optString("hospitalName");
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                UserListResponse.DataBeanX.DataBean  dataBean = new UserListResponse.DataBeanX.DataBean();
                                dataBean.setId(id);
                                dataBean.setUid(uid);
                                dataBean.setTruename(truename);
                                dataBean.setMobile(mobile);
                                dataBean.setCreate_time(create_time);
                                dataBean.setSex(sex);
                                dataBean.setUsing(using);
                                dataBean.setHospitalName(hospitalName);
                                dataBean.setReport(reportBeans);

                                UserListResponse.DataBeanX.DataBean.ReportBean  reportBean = new UserListResponse.DataBeanX.DataBean.ReportBean();
                                List<String> reporturl = new ArrayList<>();
                                List<String> reportcreateTime = new ArrayList<>();
                                List<Integer> quality1 = new ArrayList<>();



                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        int id1 = jsonObject2.optInt("id");

                                        reportBean.setId(id1);
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);


                                        reportBeans.add(reportBean);

                                        reporturl.add(reportBean.getReportUrl());
                                        reportcreateTime.add(String.valueOf(reportBean.getCreateTime()));
                                        if (reportBean.getQuality() == 1){
//                                            dataBean.setQuality(1);
                                            quality1.add(1);


//                                        dataBean.setReportUrl(reportBean.getReportUrl());
                                        }else {
                                            quality1.add(2);
//                                            dataBean.setQuality(2);
                                        }





                                    }



                                }

                                String[] urls = reporturl.toArray(new String[reporturl.size()]);
//
                                String[] reportcreateTimes  = reportcreateTime.toArray(new String[reportcreateTime.size()]);
                                int a = 0;
                                if (quality1.size()>0){
                                    for (int j =0;j<quality1.size();j++){
                                        if (quality1.get(j ) == 1){
                                            a = j;
                                            break;
                                        }

                                    }
                                    dataBean.setQuality(quality1.get(a));
                                    dataBean.setReportUrl(urls[a]);
                                    dataBean.setReportCreateTime(reportcreateTimes[a]);
                                }else {
                                    dataBean.setQuality(2);
                                }


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





                        if (userListResponse.getCode() == 0){
                            Log.d("user4",dataBeanX.getNext_page_url());
                            userListAdapter.notifyDataSetChanged();
                            recyclerView.setPullLoadMoreCompleted();
                            userListAdapter.setOnItemClickListener(new UserListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick1(String truename) {
                                    Log.d("getname",truename);
                                }
                            });
                        }else if (userListResponse.getCode()==10010 || userListResponse.getCode() == 10004){


                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(UserListAcivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(UserListAcivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {


                            ToastUtils.showTextToast(UserListAcivity.this,userListResponse.getMsg());
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