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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bajiesleep.entity.DeviceRecoverResponse;
import com.example.bajiesleep.entity.ReportListResponse;
import com.example.bajiesleep.entity.SearchUserInfoResponse;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter3;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter4;
import com.example.bajiesleep.fragment.recyclerview.ReportListAdapter;
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

import static android.view.View.VISIBLE;

public class ReportListActivity extends AppCompatActivity {
     public PullLoadMoreRecyclerView recyclerView;

     public ReportListAdapter listAdapter;

     private TextView mTvListMember;

     private EditText mEtSearch;

     private LinearLayout linearLeft;
    CharSequence chars ;

     private Button mBtnSearch;
     private RelativeLayout mIvCleanSearch;

    ReportListResponse.DataBeanX dataBeanX = new ReportListResponse.DataBeanX();
    public List<ReportListResponse.DataBeanX.DataBean> dataBeans = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);

        recyclerView = findViewById(R.id.report_list_recyclerview);

        mTvListMember = findViewById(R.id.tv_report_list_member);

        mEtSearch = findViewById(R.id.report_list_search);
        mBtnSearch = findViewById(R.id.btn_report_list_search);
        mIvCleanSearch= findViewById(R.id.report_list_search_clean);
        linearLeft = findViewById(R.id.report_liner_left);

        mBtnSearch.setVisibility(View.GONE);
        mIvCleanSearch.setVisibility(View.GONE);

        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getResReportList(Api.URL+"/v2/report?hospitalid="+getHosIdToSp("hosid","")+"&page=1");

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String text= String.valueOf(textView.getText()).replaceAll(" ","");

                if (i == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性
                    mIvCleanSearch.setVisibility(VISIBLE);
                    mBtnSearch.setVisibility(View.GONE);
                    dataBeans.clear();
                    getResReportList2(Api.URL+"/v2/report?hospitalid="+getHosIdToSp("hosid","")+"&keywords="+text+"&page=1");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(ReportListActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                }
                mIvCleanSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEtSearch.setText("");
                        mIvCleanSearch.setVisibility(View.GONE);
                        dataBeans.clear();
                        getResReportList(Api.URL+"/v2/report?hospitalid="+getHosIdToSp("hosid","")+"&page=1");

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
                            getResReportList2(Api.URL+"/v2/report?hospitalid="+getHosIdToSp("hosid","")+"&keywords="+text+"&page=1");
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
                            getResReportList(Api.URL+"/v2/report?hospitalid="+getHosIdToSp("hosid","")+"&page=1");
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if (imm.isActive()) {
                                imm.hideSoftInputFromWindow(ReportListActivity.this.getCurrentFocus().getWindowToken(), 0);
                            }

                        }
                    });
                }else {
                    dataBeans.clear();
                    getResReportList(Api.URL+"/v2/report?hospitalid="+getHosIdToSp("hosid","")+"&page=1");
                    mBtnSearch.setVisibility(View.GONE);
                    mIvCleanSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }


    private void getResReportList4(String url) {

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
                        ToastUtils.showTextToast2(ReportListActivity.this,"网络请求失败");
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






                        if (reportListResponse.getCode() == 0){


                            listAdapter.notifyDataSetChanged();
                            recyclerView.setPullLoadMoreCompleted();
                            listAdapter.setOnItemClickListener(new ReportListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(String url, String truename, String reportCreateTime) {
                                    Intent intent = new Intent(ReportListActivity.this,ReportPdfView1.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("reportUrl",url);
                                    bundle.putString("reportTrueName",truename);
                                    bundle.putString("reportCreateTime",reportCreateTime);

                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });



                        }else if (reportListResponse.getCode()==10010 || reportListResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(ReportListActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(ReportListActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(ReportListActivity.this,reportListResponse.getMsg());
                        }


                    }
                });


            }


        });


    }


    private void getResReportList3(String url) {

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
                        ToastUtils.showTextToast2(ReportListActivity.this,"网络请求失败");
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






                        if (reportListResponse.getCode() == 0){


                            listAdapter.notifyDataSetChanged();
                            recyclerView.setPullLoadMoreCompleted();

//                            listAdapter.setOnItemClickListener(new ReportListAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(String url, String truename, String reportCreateTime) {
//                                    Intent intent = new Intent(ReportListActivity.this,ReportPdfView.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("reportUrl",url);
//                                    bundle.putString("reportTrueName",truename);
//                                    bundle.putString("reportCreateTime",reportCreateTime);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });

                        }else if (reportListResponse.getCode()==10010 || reportListResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(ReportListActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(ReportListActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(ReportListActivity.this,reportListResponse.getMsg());
                        }


                    }
                });


            }


        });


    }


    private void getResReportList2(String url) {

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
                        ToastUtils.showTextToast2(ReportListActivity.this,"网络请求失败");
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

                             recyclerView.setLinearLayout();

                            listAdapter  = new ReportListAdapter(dataBeans,ReportListActivity.this);

                            recyclerView.setAdapter(listAdapter);
                            mTvListMember.setText("共找到"+String.valueOf(dataBeanX.getTotal())+"份报告");
//                            listAdapter.setOnItemClickListener(new ReportListAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(String url, String truename, String reportCreateTime) {
//                                    Intent intent = new Intent(ReportListActivity.this,ReportPdfView.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("reportUrl",url);
//                                    bundle.putString("reportTrueName",truename);
//                                    bundle.putString("reportCreateTime",reportCreateTime);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//                            });


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
                                        getResReportList4(Api.URL+"/v2/report?hospitalid="+ getHosIdToSp("hosid","")+"&keywords="+chars+"&page="+(dataBeanX.getCurrent_page()+1));
                                    }
                                }
                            });

                        }else if (reportListResponse.getCode()==10010 || reportListResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(ReportListActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(ReportListActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(ReportListActivity.this,reportListResponse.getMsg());
                        }


                    }
                });


            }


        });


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
                        ToastUtils.showTextToast2(ReportListActivity.this,"网络请求失败");
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


                            recyclerView.setLinearLayout();

                            listAdapter  = new ReportListAdapter(dataBeans,ReportListActivity.this);
                            recyclerView.setAdapter(listAdapter);
                            mTvListMember.setText("共找到"+String.valueOf(dataBeanX.getTotal())+"份报告");

//                            listAdapter.setOnItemClickListener(new ReportListAdapter.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(String url, String truename, String reportCreateTime) {
//                                    Intent intent = new Intent(ReportListActivity.this,ReportPdfView.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("reportUrl",url);
//                                    bundle.putString("reportTrueName",truename);
//                                    bundle.putString("reportCreateTime",reportCreateTime);
//                                    intent.putExtras(bundle);
//                                    startActivity(intent);
//                                }
//
//
//
//
//                            });

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
                                        getResReportList3(Api.URL+"/v2/report?hospitalid="+ getHosIdToSp("hosid","")+"&page="+(dataBeanX.getCurrent_page()+1));
                                    }
                                }
                            });



                        }else if (reportListResponse.getCode()==10010 || reportListResponse.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(ReportListActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(ReportListActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(ReportListActivity.this,reportListResponse.getMsg());
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