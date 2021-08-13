package com.example.bajiesleep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bajiesleep.entity.AppointReportResponse;
import com.example.bajiesleep.entity.EditReortResponse;
import com.example.bajiesleep.util.GetShp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


public class ReportListDialog extends Dialog {

    private Context context;
    private EditText mEtReport,mEtReview;
    private Button mBtnSave;
    private TextView mTvId;
    AppointReportResponse.DataBean dataBean;
    String Id;

    String truename;
    String examine;
    private IOnConfirmListener confirmListener;

    public ReportListDialog setConfirm( IOnConfirmListener listener) {
        this.confirmListener = listener;
        return this;
    }
    public void setHospitalId(String Id) {
        this.Id = Id;
    }

    public ReportListDialog(@NonNull Context context) {
        super(context);
    }

    public ReportListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context =context;
    }

    protected ReportListDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context =context;
    }

    protected String getTokenToSp(String key, String val) {

        SharedPreferences sp1 = context.getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "没有token");
        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 =context.getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "没有uid");

        return uid;
    }

    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = context.getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "没有hosid");
        return hosid;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_dialog_eidt_layout);
        mEtReport = findViewById(R.id.report_dialog_et_report);
        mEtReview = findViewById(R.id.report_dialog_et_review);
        mBtnSave = findViewById(R.id.report_dialog_btn_save);
        mTvId =  findViewById(R.id.report_dialog_tv_id);

        if (!TextUtils.isEmpty(Id)){
            mTvId.setText(Id);
        }



        getResAppointReport(Api.URL+"/v1/report/"+Id);


        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                dismiss();
                Log.d("mEtReport", String.valueOf(mEtReport.getText()));
                getResEditReport(Api.URL+"/v2/report/"+Id+"/edit?truename="+mEtReport.getText()+"&hospitalid="+getHosIdToSp("hosid","")+"&examine="+mEtReview.getText());

//

            }
        });

    }


    protected void getResAppointReport(String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(context))
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

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(getContext(),"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

//                parseJSONWithGSON(res);

                //封装java对象
                final AppointReportResponse appointReportResponse = new AppointReportResponse();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject data = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    appointReportResponse.setCode(code);
                    appointReportResponse.setMsg(msg);
                    dataBean = new AppointReportResponse.DataBean();
                    appointReportResponse.setData(dataBean);

                    if (data !=null){
                        //                    第二层解析
                        String truename = data.optString("truename");
                        String examine = data.optString("examine");

                        dataBean.setTruename(truename);
                        dataBean.setExamine(examine);
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("报告信息接口", res);
                        if (appointReportResponse.getCode() == 0){

                            truename = dataBean.getTruename();
                            examine = dataBean.getExamine();
                            mEtReport.setText(dataBean.getTruename());
                            mEtReview.setText(dataBean.getExamine());
                        }else if(appointReportResponse.getCode() == 10004 || appointReportResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(getContext(),R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(getContext(),LoginActivity.class);
                                    ((Activity)context).finish();
                                    context.startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(getContext(),appointReportResponse.getMsg());
                        }


                    }
                });


            }
        });
    }




    protected void getResEditReport(String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(context))
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

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(getContext(),"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

//                parseJSONWithGSON(res);

                //封装java对象
                final EditReortResponse editReortResponse = new EditReortResponse();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    editReortResponse.setCode(code);
                    editReortResponse.setMsg(msg);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (editReortResponse.getCode() == 0){

                            ToastUtils.showTextToast(getContext(),editReortResponse.getMsg());

                        }else if(editReortResponse.getCode() == 10004 || editReortResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(getContext(),R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(getContext(),LoginActivity.class);
                                    ((Activity)context).finish();
                                    context.startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(getContext(),editReortResponse.getMsg());
                        }


                    }
                });


            }
        });
    }


    public interface IOnConfirmListener{
        void OnConfirm(DialogTokenIntent dialog);
    }


}
