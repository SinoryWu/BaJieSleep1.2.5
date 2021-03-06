package com.example.bajiesleep.fragment.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.Api;
import com.example.bajiesleep.DialogTokenIntent;
import com.example.bajiesleep.L;
import com.example.bajiesleep.LoginActivity;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ReportPdfView;
import com.example.bajiesleep.ReportPdfView1;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.entity.UserListInfoResponse;
import com.example.bajiesleep.entity.UserListResponse;
import com.example.bajiesleep.util.GetShp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.InnerHolder> {
    private Context context;
    private List<UserListResponse.DataBeanX.DataBean> dataBeans;


    private UserListAdapter.OnItemClickListener mClickListener;
    private List<String> truename = new ArrayList<>();
    private List<String> report = new ArrayList<>();


    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    UserListInfoResponse.DataBeanX.DataBean dataBean = new UserListInfoResponse.DataBeanX.DataBean();
    UserListInfoResponse.DataBeanX dataBeanX1 = new UserListInfoResponse.DataBeanX();
    public List<UserListInfoResponse.DataBeanX.DataBean> dataBeans1 = new ArrayList<>();
    public List<UserListInfoResponse.DataBeanX.DataBean.ReportBean> reportBeans1 = new ArrayList<>();


    public UserListAdapter(List<UserListResponse.DataBeanX.DataBean> dataBeans, Context context) {

        this.dataBeans = dataBeans;


        this.context = context;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {


        void onItemClick1(String truename);
    }

    public void setOnItemClickListener(UserListAdapter.OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    /**
     * ???????????????item
     */
    private int opened = -1;

    /**
     * ??????????????????????????????View
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public UserListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //??????????????????view???????????????????????????
        //????????????
        //1.??????veiw
        //2.??????InnerHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
//        View view = View.inflate(parent.getContext(), R.layout.report_list_item, null);
        return new InnerHolder(view);
    }

    /**
     * ????????????????????????holder???????????????????????????
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.InnerHolder holder, final int position) {
        //?????????????????????
//        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);

        holder.setData(position, dataBeans.get(position), context);


    }

    /**
     * ??????????????????
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (dataBeans != null) {
            return dataBeans.size();
        }
        return 0;

    }


    public class InnerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvUserName, mTvDate, mTvUserHospital, mTvUserReportNumber;
        private RelativeLayout mRlDown, mRlUp, mUserReport;
        private RecyclerView recyclerView;
        private UserListInfoAdapter listInfoAdapter;
        private ImageView mIvOpenNewPdfTrue,mIvOpenNewPdfFalse;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);


            recyclerView = itemView.findViewById(R.id.user_list_rvReport);
            mRlDown = itemView.findViewById(R.id.user_item_down);
            mRlUp = itemView.findViewById(R.id.user_item_up);
            mUserReport = itemView.findViewById(R.id.user_list_rlReport);
            mTvUserName = itemView.findViewById(R.id.user_item_userName);
            mTvDate = itemView.findViewById(R.id.user_item_date);
            mTvUserHospital = itemView.findViewById(R.id.user_list_hospital);
            mTvUserReportNumber = itemView.findViewById(R.id.user_list_report_number);

            mIvOpenNewPdfTrue = itemView.findViewById(R.id.user_open_new_pdf_true);
            mIvOpenNewPdfFalse = itemView.findViewById(R.id.user_open_new_pdf_false);
            mRlDown.setOnClickListener(this);
            mRlUp.setOnClickListener(this);
        }


        public void setData(int position, final UserListResponse.DataBeanX.DataBean dataBean, final Context context) {

//            String url = "";
//            String reportcreateTime1 = "";
//            if (dataBean.getReportUrl() !=null){
//                reportcreateTime1 = dataBean.getReportCreateTime();
//            }else {
//                reportcreateTime1 = "";
//            }



//             final String reporturl;
//             final String reportCreateTime;
//
//            if (dataBean.getReportUrl() == null || dataBean.getReportUrl().isEmpty()) {
//
//            } else {
//                reporturl.add(dataBean.getReportUrl());
//                reportcreateTime.add(dataBean.getReportCreateTime());
//                String[] urls = reporturl.toArray(new String[reporturl.size()]);
////
//                String[] reportcreateTimes = reportcreateTime.toArray(new String[reportcreateTime.size()]);
//
//
//                for (int j = 0; j < urls.length; j++) {
//                    url = urls[0];
//                    reportcreateTime1 = reportcreateTimes[0];
//
//                }
//
//
//            }


            Log.d("uselist", String.valueOf(dataBean));





            if (dataBean.getQuality() == 1) {
                mIvOpenNewPdfTrue.setVisibility(View.VISIBLE);
                mIvOpenNewPdfFalse.setVisibility(View.GONE);
                mIvOpenNewPdfTrue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ReportPdfView1.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("reportUrl", dataBean.getReportUrl());
                        bundle.putString("reportTrueName", dataBean.getTruename());
                        bundle.putString("reportCreateTime", timestamp2Date(dataBean.getReportCreateTime(), "MM??? dd??? HH:mm"));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });


            } else {
                mIvOpenNewPdfTrue.setVisibility(View.GONE);
                mIvOpenNewPdfFalse.setVisibility(View.VISIBLE);
            }


            report.add(String.valueOf(dataBean.getReport()));


            truename.add(dataBean.getTruename());

//            mTvUserReportNumber.setText("???????????????"+String.valueOf(dataBeans.getReport().size())+"???");

//            mTvUserReportNumber.setText("???????????????"+dataBean.getReport().get(position).getQuality()+"???");
//            getResUserInfoList(Api.URL +"/v1/User/index?hospitalid="+getHosIdToSp("hosid","")+"&limit=15&page=0&keywords="+dataBean.getTruename());

//
//            if (dataBean.getReport().size()== 0){
//                mIvOpenNewPdf.setImageResource(R.drawable.open_new_report_false);
//            }else {
//                mIvOpenNewPdf.setImageResource(R.drawable.open_new_report_true);
//            }

//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//            recyclerView.setLayoutManager(linearLayoutManager);
////            listInfoAdapter = new UserListInfoAdapter(,context);
//            recyclerView.setAdapter(listInfoAdapter);
//            mUserReport.setVisibility(View.GONE);
            if (position == opened) {

                dataBeans1.clear();

                reportBeans1.clear();
//                dataBeansNew.clear();
//                listInfoAdapter.notifyDataSetChanged();
                getResUserInfoList(Api.URL + "/v1/User/index?hospitalid=" + getHosIdToSp("hosid", "") + "&limit=15&page=0&keywords=" + dataBean.getTruename());
                mRlUp.setVisibility(View.VISIBLE);
                mRlDown.setVisibility(View.GONE);
                mUserReport.setVisibility(View.VISIBLE);
                saveReportNameToSp("reportName", dataBean.getTruename());

            } else {
                dataBeans1.clear();
                reportBeans1.clear();

//                reportBeansNew.clear();
                mRlUp.setVisibility(View.GONE);

                mRlDown.setVisibility(View.VISIBLE);
                mUserReport.setVisibility(View.GONE);
            }

            mTvUserName.setText(dataBean.getTruename());
            mTvUserHospital.setText("?????????"+dataBean.getHospitalName());

            String data = timestamp2Date(String.valueOf(dataBean.getCreate_time()), "yyyy-MM-dd");
            mTvDate.setText(data);


//


        }


        private void getResUserInfoList(String url) {

            //1.??????okhttp??????
            OkHttpClient okHttpClient = new OkHttpClient();

            //2.??????request
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .addHeader("token", getTokenToSp("token", ""))
                    .addHeader("uid", getUidToSp("uid", ""))
                    .addHeader("user-agent", GetShp.getUserAgent(context))
                    .build();
            //3.???request?????????call
            Call call = okHttpClient.newCall(request);
            //4.??????call
//        ????????????
//        Response response = call.execute();

            //????????????
            call.enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    L.e("OnFailure   " + e.getMessage());
                    e.printStackTrace();

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showTextToast2(context, "??????????????????");
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    L.e("OnResponse");
                    final String res = response.body().string();
                    Log.d("searchUserInfo2", res);

                    //??????java??????

                    final UserListInfoResponse userListInfoResponse = new UserListInfoResponse();

//                final ProgressBar1 progressBar1 =  new ProgressBar1();

                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        //???????????????
                        int code = jsonObject.optInt("code");
                        JSONObject datax = jsonObject.optJSONObject("data");
                        String msg = jsonObject.optString("msg");

                        //???????????????
                        userListInfoResponse.setCode(code);
                        userListInfoResponse.setMsg(msg);
                        userListInfoResponse.setData(dataBeanX1);

                        if (datax != null){
                            //???????????????
                            int current_page = datax.optInt("current_page");
                            String first_page_url = datax.optString("first_page_url");
                            int from = datax.optInt("from");
                            int last_page = datax.optInt("last_page");
                            String last_page_url = datax.optString("last_page_url");
                            String next_page_url = datax.optString("next_page_url");
                            String path = datax.optString("path");
                            int per_page = datax.optInt("per_page");
                            String prev_page_url = datax.optString("prev_page_url");
                            int to = datax.optInt("to");
                            int total = datax.optInt("total");
                            JSONArray data = datax.optJSONArray("data");

                            //???????????????
                            dataBeanX1.setCurrent_page(current_page);
                            dataBeanX1.setFirst_page_url(first_page_url);
                            dataBeanX1.setFrom(from);
                            dataBeanX1.setLast_page(last_page);
                            dataBeanX1.setLast_page_url(last_page_url);
                            dataBeanX1.setNext_page_url(next_page_url);
                            dataBeanX1.setPath(path);
                            dataBeanX1.setPer_page(per_page);
                            dataBeanX1.setPrev_page_url(prev_page_url);
                            dataBeanX1.setData(dataBeans1);


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


                                    dataBean.setId(id);
                                    dataBean.setUid(uid);
                                    dataBean.setTruename(truename);
                                    dataBean.setMobile(mobile);
                                    dataBean.setCreate_time(create_time);
                                    dataBean.setSex(sex);
                                    dataBean.setUsing(using);
                                    dataBean.setHospitalName(hospitalName);
                                    dataBean.setReport(reportBeans1);
                                    dataBeans1.add(dataBean);

                                    for (int k = 0; k < dataReport.length(); k++) {
                                        JSONObject jsonObject2 = dataReport.getJSONObject(k);
                                        if (jsonObject1 != null) {
                                            String sn = jsonObject2.optString("sn");
                                            String ahi = jsonObject2.optString("ahi");
                                            String report_id = jsonObject2.optString("report_id");
                                            int quality = jsonObject2.optInt("quality");
                                            int createTime = jsonObject2.optInt("createTime");
                                            String reportUrl = jsonObject2.optString("reportUrl");
                                            int id1 = jsonObject2.optInt("id");
                                            UserListInfoResponse.DataBeanX.DataBean.ReportBean reportBean = new UserListInfoResponse.DataBeanX.DataBean.ReportBean();
                                            reportBean.setId(id1);
                                            reportBean.setSn(sn);
                                            reportBean.setAhi(ahi);
                                            reportBean.setReport_id(report_id);
                                            reportBean.setQuality(quality);
                                            reportBean.setCreateTime(createTime);
                                            reportBean.setReportUrl(reportUrl);

                                            reportBeans1.add(reportBean);
                                        }

                                    }


                                }

                            }
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (userListInfoResponse.getCode() == 0) {
//                                mTvUserHospital.setText("?????????"+dataBean.getHospitalName());
//                                listInfoAdapter.notifyDataSetChanged();
                                mTvUserReportNumber.setText("???????????????" + reportBeans1.size() + "???");
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                listInfoAdapter = new UserListInfoAdapter(reportBeans1, context);
                                recyclerView.setAdapter(listInfoAdapter);


//                                if (reportBeanNew.getQuality() == 1 || reportBeanNew.getQuality() == 2){
//                                    mIvOpenNewPdf.setImageResource(R.drawable.open_new_report_true);
//                                }else {
//                                    mIvOpenNewPdf.setImageResource(R.drawable.open_new_report_false);
//                                }

                                name = dataBean.getTruename();
                                setName(name);


                            } else if (userListInfoResponse.getCode() == 10010 || userListInfoResponse.getCode() == 10004) {


                                DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(context, R.style.CustomDialog);
                                dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(context, LoginActivity.class);
                                        ((Activity) context).finish();
                                        context.startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            } else {


                                ToastUtils.showTextToast(context, userListInfoResponse.getMsg());
                            }


                        }
                    });


                }


            });


        }


//
//        private void getResUserNewPdf(String url) {
//
//            //1.??????okhttp??????
//            OkHttpClient okHttpClient = new OkHttpClient();
//
//            //2.??????request
//            Request request = new Request.Builder()
//                    .get()
//                    .url(url)
//                    .addHeader("token", getTokenToSp("token", ""))
//                    .addHeader("uid", getUidToSp("uid", ""))
//                    .build();
//            //3.???request?????????call
//            Call call = okHttpClient.newCall(request);
//            //4.??????call
////        ????????????
////        Response response = call.execute();
//
//            //????????????
//            call.enqueue(new Callback() {
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    L.e("OnFailure   " + e.getMessage());
//                    e.printStackTrace();
//
//                    ((Activity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ToastUtils.showTextToast2(context,"??????????????????");
//                        }
//                    });
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    L.e("OnResponse");
//                    final String res = response.body().string();
//                    Log.d("asdasq22ee13",res);
//
//                    //??????java??????
//
//                    final UserNewPdfResponse userNewPdfResponse = new UserNewPdfResponse();
//
////                final ProgressBar1 progressBar1 =  new ProgressBar1();
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(res);
//                        //???????????????
//                        int code = jsonObject.optInt("code");
//                        JSONObject datax = jsonObject.optJSONObject("data");
//                        String msg = jsonObject.optString("msg");
//
//                        //???????????????
//                        userNewPdfResponse.setCode(code);
//                        userNewPdfResponse.setMsg(msg);
//                        userNewPdfResponse.setData(dataBeanXNew);
//
//                        //???????????????
//                        int current_page = datax.optInt("current_page");
//                        String first_page_url = datax.optString("first_page_url");
//                        int from = datax.optInt("from");
//                        int last_page = datax.optInt("last_page");
//                        String last_page_url= datax.optString("last_page_url");
//                        String next_page_url= datax.optString("next_page_url");
//                        String path= datax.optString("path");
//                        int per_page= datax.optInt("per_page");
//                        String prev_page_url = datax.optString("prev_page_url");
//                        int to= datax.optInt("to");
//                        int total= datax.optInt("total");
//                        JSONArray data = datax.optJSONArray("data");
//
//                        //???????????????
//                        dataBeanXNew.setCurrent_page(current_page);
//                        dataBeanXNew.setFirst_page_url(first_page_url);
//                        dataBeanXNew.setFrom(from);
//                        dataBeanXNew.setLast_page(last_page);
//                        dataBeanXNew.setLast_page_url(last_page_url);
//                        dataBeanXNew.setNext_page_url(next_page_url);
//                        dataBeanXNew.setPath(path);
//                        dataBeanXNew.setPer_page(per_page);
//                        dataBeanXNew.setPrev_page_url(prev_page_url);
//                        dataBeanXNew.setData(dataBeansNew);
//
//
//                        for (int i = 0; i < data.length(); i++) {
//                            JSONObject jsonObject1 = data.getJSONObject(i);
//                            if (jsonObject1 != null) {
//                                int id = jsonObject1.optInt("id");
//                                int uid = jsonObject1.optInt("uid");
//                                String truename = jsonObject1.optString("truename");
//                                String mobile = jsonObject1.optString("mobile");
//                                int create_time = jsonObject1.optInt("create_time");
//                                String sex = jsonObject1.optString("sex");
//                                boolean using = jsonObject1.optBoolean("using");
//                                String hospitalName = jsonObject1.optString("hospitalName");
//                                JSONArray dataReport = jsonObject1.optJSONArray("report");
//
//
//                                dataBeanNew.setId(id);
//                                dataBeanNew.setUid(uid);
//                                dataBeanNew.setTruename(truename);
//                                dataBeanNew.setMobile(mobile);
//                                dataBeanNew.setCreate_time(create_time);
//                                dataBeanNew.setSex(sex);
//                                dataBeanNew.setUsing(using);
//                                dataBeanNew.setHospitalName(hospitalName);
//                                dataBeansNew.add(dataBeanNew);
//
//                                for (int k = 0; k < dataReport.length(); k++) {
//                                    JSONObject jsonObject2 = dataReport.getJSONObject(k);
//                                    if (jsonObject1 != null) {
//                                        String sn = jsonObject2.optString("sn");
//                                        String ahi = jsonObject2.optString("ahi");
//                                        String report_id = jsonObject2.optString("report_id");
//                                        int quality = jsonObject2.optInt("quality");
//                                        int createTime = jsonObject2.optInt("createTime");
//                                        String reportUrl = jsonObject2.optString("reportUrl");
//
//                                        reportBeanNew.setSn(sn);
//                                        reportBeanNew.setAhi(ahi);
//                                        reportBeanNew.setReport_id(report_id);
//                                        reportBeanNew.setQuality(quality);
//                                        reportBeanNew.setCreateTime(createTime);
//                                        reportBeanNew.setReportUrl(reportUrl);
//                                        reportBeansNew.add(reportBeanNew);
//                                    }
//
//                                }
//
//
//                            }
//
//                        }
//
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    ((Activity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//
//                            if (userNewPdfResponse.getCode() == 0){
//
//
//                                Log.d("212dasdasd", String.valueOf(reportBeanNew.getQuality()));
//
//                                    if (reportBeanNew.getQuality() == 1 || reportBeanNew.getQuality() == 2){
//                                        mIvOpenNewPdf.setImageResource(R.drawable.open_new_report_true);
//                                    }else {
//                                        mIvOpenNewPdf.setImageResource(R.drawable.open_new_report_false);
//                                    }
//
//
//
//                            }else if (userNewPdfResponse.getCode()==10010 || userNewPdfResponse.getCode() == 10004){
//
//
//                                DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(context,R.style.CustomDialog);
//                                dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
//                                    @Override
//                                    public void OnConfirm(DialogTokenIntent dialog) {
//                                        Intent intent = new Intent(context,LoginActivity.class);
//                                        ((Activity)context).finish();
//                                        context.startActivity(intent);
//
//                                    }
//                                }).show();
//
//                                dialogTokenIntent.setCanceledOnTouchOutside(false);
//                                dialogTokenIntent.setCancelable(false);
//                            }else {
//
//
//                                ToastUtils.showTextToast(context,userNewPdfResponse.getMsg());
//                            }
//
//
//                        }
//                    });
//
//
//                }
//
//
//            });
//

//        }


        @Override
        public void onClick(View view) {
            if (opened == getAdapterPosition()) {
                //????????????item??????????????????, ?????????.
                opened = -1;
                notifyItemChanged(getAdapterPosition());
            } else {
                int oldOpened = opened;
                opened = getAdapterPosition();
                notifyItemChanged(oldOpened);


                notifyItemChanged(opened);
            }
        }
    }


    public static String timestamp2Date(String str_num, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(Long.parseLong(str_num)));

            return date;
        } else {
            String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));

            return date;
        }
    }


    protected void saveReportNameToSp(String key, String val) {
        SharedPreferences sp = context.getSharedPreferences("reportName", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.putString(key, val);
        editor.commit();
    }


    protected String getReportNameToSp(String key, String val) {
        SharedPreferences sp1 = context.getSharedPreferences("reportName", MODE_PRIVATE);
        String reportName = sp1.getString("reportName", "??????reportName");


        return reportName;
    }


    protected String getTokenToSp(String key, String val) {
        SharedPreferences sp1 = context.getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "??????token");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 = context.getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "??????uid");


        return uid;
    }


    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = context.getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "??????hosid");
        return hosid;
    }


}
