package com.example.bajiesleep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.bajiesleep.entity.SearchUserInfoResponse;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter3;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchUserRecyclerView extends AppCompatActivity {
    private PullLoadMoreRecyclerView recyclerView;

    private EditText searchUser;
    ListViewAdapter3 listViewAdapter3;
    String charsSpace;
    String chars;

    SearchUserInfoResponse.DataBeanX dataBeanX = new SearchUserInfoResponse.DataBeanX();
    public List<SearchUserInfoResponse.DataBeanX.DataBean> dataBeans = new ArrayList<>();
    public List<SearchUserInfoResponse.DataBeanX.DataBean.ReportBean> reportBeans = new ArrayList<>();

    private LinearLayout linearLeft;
    String deviceSn;
    String trueName1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_recycler_view);
        String hosid = getHosIdToSp("hosid","");

        Intent intent=getIntent();
        deviceSn=intent.getStringExtra("devicesn");


        final Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#ffffff"));
        searchUser = findViewById(R.id.search_user);
        recyclerView=findViewById(R.id.search_user_recycler_pullload);
        linearLeft = findViewById(R.id.liner_left_search_user);

        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                finish();
            }
        });

        getResSearUserInfo(Api.URL+"/v1/User/index?hospitalid="+ getHosIdToSp("hosid","")+"&limit=30"+"&page=1");


        searchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                dataBeans.clear();
//                listViewAdapter3.notifyDataSetChanged();

                charsSpace = String.valueOf(charSequence);
                chars = charsSpace.replace(" ","");
                if (charSequence.length() >0){
                    Log.d("charSequence", chars);
                    Log.d("hosid1",  getHosIdToSp("hosid",""));
                    listViewAdapter3.notifyDataSetChanged();
                    dataBeans.clear();
                    getResSearUserInfoSearch(Api.URL+"/v1/User/index?hospitalid="+ getHosIdToSp("hosid","")+"&limit=30"+"&keywords="+charSequence+"&page=1");
                }else {
                    listViewAdapter3.notifyDataSetChanged();
                    dataBeans.clear();
                    getResSearUserInfo(Api.URL+"/v1/User/index?hospitalid="+ getHosIdToSp("hosid","")+"&limit=30"+"&page=1");


                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

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

    private void getResSearUserInfoSearch(String url) {

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
                        ToastUtils.showTextToast2(SearchUserRecyclerView.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo",res);

                //封装java对象

                final SearchUserInfoResponse searchUserInfoResponse = new SearchUserInfoResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    searchUserInfoResponse.setCode(code);
                    searchUserInfoResponse.setMsg(msg);
                    searchUserInfoResponse.setData(dataBeanX);

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
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                SearchUserInfoResponse.DataBeanX.DataBean  dataBean = new SearchUserInfoResponse.DataBeanX.DataBean();
                                SearchUserInfoResponse.DataBeanX.DataBean  dataBean2 = new SearchUserInfoResponse.DataBeanX.DataBean();

                                dataBean.setUsing(using);
//                            dataBean.setId(id);
//                            dataBean.setUid(uid);
//                            dataBean.setTruename(truename);
//                            dataBean.setMobile(mobile);
//                            dataBean.setCreate_time(create_time);
//                            dataBean.setSex(sex);
//                            dataBean.setReport(reportBeans);
//                            dataBeans.add(dataBean);
//                            Log.d("asdasdasdasdasde213123",String.valueOf(dataReport));
                                if (!dataBean.isUsing()){
                                    dataBean.setId(id);
                                    dataBean.setUid(uid);
                                    dataBean.setTruename(truename);
                                    dataBean.setMobile(mobile);
                                    dataBean.setCreate_time(create_time);
                                    dataBean.setSex(sex);

                                    dataBeans.add(dataBean);
//                                Log.d("da213dasad", String.valueOf(dataReport));
                                }


                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        SearchUserInfoResponse.DataBeanX.DataBean.ReportBean  reportBean = new SearchUserInfoResponse.DataBeanX.DataBean.ReportBean();
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);
                                        reportBeans.add(reportBean);

                                    }

                                }


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

                        if (searchUserInfoResponse.getCode() == 0){

                            listViewAdapter3  = new ListViewAdapter3(dataBeans,SearchUserRecyclerView.this);
                            recyclerView.setLinearLayout();
                            recyclerView.setAdapter(listViewAdapter3);

                           listViewAdapter3.setOnItemClickLitener3(new ListViewAdapter3.OnItemClickLitener3() {
                               @Override
                               public void onItemClick3(String trueName, String mobile, String uid, int position,View view) {
//                                   Intent intent = new Intent(SearchUserRecyclerView.this,LendActivity.class);
//                                   Bundle bundle = new Bundle();
//                                   bundle.putString("trueName",trueName);
//                                   bundle.putString("mobile",mobile);
//                                   bundle.putString("deviceSn1",deviceSn);
//                                   intent.putExtras(bundle);
//                                   startActivityForResult(intent,1);
                                   //隐藏软键盘
                                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                   if (imm.isActive()) {
                                       imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                   }
                                   Intent intent = new Intent();
                                   intent.putExtra("truename",trueName);
                                   intent.putExtra("mobile",mobile);
                                   intent.putExtra("uid",uid);
                                   setResult(RESULT_OK,intent);

//                                   Log.d("deviceSn",deviceSn);
                                   finish();
                               }
                           });

                            recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {
                                    recyclerView.setPullLoadMoreCompleted();
                                }

                                @Override
                                public void onLoadMore() {


                                    Log.d("getNext_page_url", String.valueOf(dataBeanX.getNext_page_url()));
                                    if (dataBeanX.getNext_page_url().equals(" null")){

                                        recyclerView.setPullLoadMoreCompleted();
//                                        getResSearUserInfo2(Api.URL+"/v1/User/index?hospitalid="+0+"&keywords="+"1"+"&page=1");
                                    }else {
                                        Log.d("chars", String.valueOf(charsSpace));
                                        getResSearUserInfoSearchLoad(Api.URL+"/v1/User/index?hospitalid="+ getHosIdToSp("hosid","")+"&limit=30"+"&keywords="+ charsSpace +"&page="+(dataBeanX.getCurrent_page()+1));
                                    }
                                }
                            });
                        }else if (searchUserInfoResponse.getCode()==10010 || searchUserInfoResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(SearchUserRecyclerView.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(SearchUserRecyclerView.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(SearchUserRecyclerView.this,searchUserInfoResponse.getMsg());
                        }


                    }
                });


            }


        });


    }



    private void getResSearUserInfoSearchLoad(String url) {

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
                        ToastUtils.showTextToast2(SearchUserRecyclerView.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo",res);

                //封装java对象

                final SearchUserInfoResponse searchUserInfoResponse = new SearchUserInfoResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    searchUserInfoResponse.setCode(code);
                    searchUserInfoResponse.setMsg(msg);
                    searchUserInfoResponse.setData(dataBeanX);

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
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                SearchUserInfoResponse.DataBeanX.DataBean  dataBean = new SearchUserInfoResponse.DataBeanX.DataBean();
                                SearchUserInfoResponse.DataBeanX.DataBean  dataBean2 = new SearchUserInfoResponse.DataBeanX.DataBean();

                                dataBean.setUsing(using);
//                            dataBean.setId(id);
//                            dataBean.setUid(uid);
//                            dataBean.setTruename(truename);
//                            dataBean.setMobile(mobile);
//                            dataBean.setCreate_time(create_time);
//                            dataBean.setSex(sex);
//                            dataBean.setReport(reportBeans);
//                            dataBeans.add(dataBean);
//                            Log.d("asdasdasdasdasde213123",String.valueOf(dataReport));
                                if (!dataBean.isUsing()){
                                    dataBean.setId(id);
                                    dataBean.setUid(uid);
                                    dataBean.setTruename(truename);
                                    dataBean.setMobile(mobile);
                                    dataBean.setCreate_time(create_time);
                                    dataBean.setSex(sex);

                                    dataBeans.add(dataBean);
//                                Log.d("da213dasad", String.valueOf(dataReport));
                                }


                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        SearchUserInfoResponse.DataBeanX.DataBean.ReportBean  reportBean = new SearchUserInfoResponse.DataBeanX.DataBean.ReportBean();
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);
                                        reportBeans.add(reportBean);

                                    }

                                }


                            }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {




                        if (searchUserInfoResponse.getCode() == 0){
//                            listViewAdapter3.notifyDataSetChanged();
                            recyclerView.setPullLoadMoreCompleted();

                        }else if (searchUserInfoResponse.getCode()==10010 || searchUserInfoResponse.getCode() == 10004){


                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(SearchUserRecyclerView.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(SearchUserRecyclerView.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {


                            ToastUtils.showTextToast(SearchUserRecyclerView.this,searchUserInfoResponse.getMsg());
                        }

                    }
                });


            }


        });


    }



    private void getResSearUserInfo(String url) {

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
                        ToastUtils.showTextToast2(SearchUserRecyclerView.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo",res);

                //封装java对象

                final SearchUserInfoResponse searchUserInfoResponse = new SearchUserInfoResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    searchUserInfoResponse.setCode(code);
                    searchUserInfoResponse.setMsg(msg);
                    searchUserInfoResponse.setData(dataBeanX);

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
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                SearchUserInfoResponse.DataBeanX.DataBean  dataBean = new SearchUserInfoResponse.DataBeanX.DataBean();
                                SearchUserInfoResponse.DataBeanX.DataBean  dataBean2 = new SearchUserInfoResponse.DataBeanX.DataBean();

                                dataBean.setUsing(using);
//                            dataBean.setId(id);
//                            dataBean.setUid(uid);
//                            dataBean.setTruename(truename);
//                            dataBean.setMobile(mobile);
//                            dataBean.setCreate_time(create_time);
//                            dataBean.setSex(sex);
//                            dataBean.setReport(reportBeans);
//                            dataBeans.add(dataBean);
//                            Log.d("asdasdasdasdasde213123",String.valueOf(dataReport));
                                if (!dataBean.isUsing()){
                                    dataBean.setId(id);
                                    dataBean.setUid(uid);
                                    dataBean.setTruename(truename);
                                    dataBean.setMobile(mobile);
                                    dataBean.setCreate_time(create_time);
                                    dataBean.setSex(sex);

                                    dataBeans.add(dataBean);
//                                Log.d("da213dasad", String.valueOf(dataReport));
                                }


                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        SearchUserInfoResponse.DataBeanX.DataBean.ReportBean  reportBean = new SearchUserInfoResponse.DataBeanX.DataBean.ReportBean();
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);
                                        reportBeans.add(reportBean);

                                    }

                                }


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

                        if (searchUserInfoResponse.getCode() == 0){

                            listViewAdapter3  = new ListViewAdapter3(dataBeans,SearchUserRecyclerView.this);
                            recyclerView.setLinearLayout();
                            recyclerView.setAdapter(listViewAdapter3);

                            listViewAdapter3.setOnItemClickLitener3(new ListViewAdapter3.OnItemClickLitener3() {
                                @Override
                                public void onItemClick3(String trueName, String mobile, String uid, int position,View view) {
//                                   Intent intent = new Intent(SearchUserRecyclerView.this,LendActivity.class);
//                                   Bundle bundle = new Bundle();
//                                   bundle.putString("trueName",trueName);
//                                   bundle.putString("mobile",mobile);
//                                   bundle.putString("deviceSn1",deviceSn);
//                                   intent.putExtras(bundle);
//                                   startActivityForResult(intent,1);
                                    //隐藏软键盘
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    if (imm.isActive()) {
                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                    }
                                    Intent intent = new Intent();
                                    intent.putExtra("truename",trueName);
                                    intent.putExtra("mobile",mobile);
                                    intent.putExtra("uid",uid);
                                    setResult(RESULT_OK,intent);

//                                   Log.d("deviceSn",deviceSn);
                                    finish();
                                }
                            });

                            recyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {
                                    recyclerView.setPullLoadMoreCompleted();
                                }

                                @Override
                                public void onLoadMore() {


                                    Log.d("getNext_page_url", String.valueOf(dataBeanX.getNext_page_url()));
                                    if (dataBeanX.getNext_page_url().equals(" null")){

                                        recyclerView.setPullLoadMoreCompleted();
//                                        getResSearUserInfo2(Api.URL+"/v1/User/index?hospitalid="+0+"&keywords="+"1"+"&page=1");
                                    }else {
                                        Log.d("chars", String.valueOf(charsSpace));
                                        getResSearUserInfoLoad(Api.URL+"/v1/User/index?hospitalid="+ getHosIdToSp("hosid","")+"&limit=30"+"&page="+(dataBeanX.getCurrent_page()+1));
                                    }
                                }
                            });
                        }else if (searchUserInfoResponse.getCode()==10010 || searchUserInfoResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(SearchUserRecyclerView.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(SearchUserRecyclerView.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(SearchUserRecyclerView.this,searchUserInfoResponse.getMsg());
                        }


                    }
                });


            }


        });


    }
    private void getResSearUserInfoLoad(String url) {

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
                        ToastUtils.showTextToast2(SearchUserRecyclerView.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchUserInfo2",res);

                //封装java对象

                final SearchUserInfoResponse searchUserInfoResponse = new SearchUserInfoResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject datax = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    searchUserInfoResponse.setCode(code);
                    searchUserInfoResponse.setMsg(msg);
                    searchUserInfoResponse.setData(dataBeanX);

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
                                JSONArray dataReport = jsonObject1.optJSONArray("report");

                                SearchUserInfoResponse.DataBeanX.DataBean  dataBean = new SearchUserInfoResponse.DataBeanX.DataBean();


                                dataBean.setUsing(using);
                                if (!dataBean.isUsing()){
                                    dataBean.setId(id);
                                    dataBean.setUid(uid);
                                    dataBean.setTruename(truename);
                                    dataBean.setMobile(mobile);
                                    dataBean.setCreate_time(create_time);
                                    dataBean.setSex(sex);
                                    dataBeans.add(dataBean);

                                }

                                for (int k = 0; k < dataReport.length(); k++) {
                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                    if (jsonObject2 != null) {
                                        String sn = jsonObject2.optString("sn");
                                        String ahi = jsonObject2.optString("ahi");
                                        String report_id = jsonObject2.optString("report_id");
                                        int quality = jsonObject2.optInt("quality");
                                        int createTime = jsonObject2.optInt("createTime");
                                        String reportUrl = jsonObject2.optString("reportUrl");
                                        SearchUserInfoResponse.DataBeanX.DataBean.ReportBean  reportBean = new SearchUserInfoResponse.DataBeanX.DataBean.ReportBean();
                                        reportBean.setSn(sn);
                                        reportBean.setAhi(ahi);
                                        reportBean.setReport_id(report_id);
                                        reportBean.setQuality(quality);
                                        reportBean.setCreateTime(createTime);
                                        reportBean.setReportUrl(reportUrl);
                                        reportBeans.add(reportBean);

                                    }

                                }


                            }

                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {





                        if (searchUserInfoResponse.getCode() == 0){
                            listViewAdapter3.notifyDataSetChanged();
                            recyclerView.setPullLoadMoreCompleted();

                        }else if (searchUserInfoResponse.getCode()==10010 || searchUserInfoResponse.getCode() == 10004){


                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(SearchUserRecyclerView.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(SearchUserRecyclerView.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {


                            ToastUtils.showTextToast(SearchUserRecyclerView.this,searchUserInfoResponse.getMsg());
                        }


                    }
                });


            }


        });


    }

}