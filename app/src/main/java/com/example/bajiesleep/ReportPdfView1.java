package com.example.bajiesleep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bajiesleep.entity.AppointReportResponse;
import com.example.bajiesleep.entity.EditReortResponse;
import com.example.bajiesleep.util.GetFileSize;
import com.example.bajiesleep.util.GetShp;
import com.joanzapata.pdfview.PDFView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReportPdfView1 extends AppCompatActivity {

    private WebView pdfView;
    private LinearLayout linearLeft;
    AppointReportResponse.DataBean dataBean;
    String reportUrl;
    String reportTrueName;
    String reportCreateTime;
    String reportID;
    String hospitalId;
    private ImageView mIvDownload;
    private RelativeLayout mRlWeb, mRlError, mRlLoading, mRlButtonSlices,mRlSleepSlices,mRlProgressBar;
    private TextView loadingText;
    private Button mBtnSleepSlices;
    private TimeCount time;
//    DialogProgress dialogProgress ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pdf_view1);

//        dialogProgress = new DialogProgress(ReportPdfView1.this,R.style.CustomDialog);
//        dialogProgress.show();

        Log.d("?????????url", getTokenToSp("token",""));
        Log.d("?????????url", getUidToSp("uid",""));
        final Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#ffffff"));
        Intent intent = getIntent();
        reportUrl = intent.getStringExtra("reportUrl");
        reportTrueName = intent.getStringExtra("reportTrueName");
        reportCreateTime = intent.getStringExtra("reportCreateTime");
        reportID = intent.getStringExtra("reportID");


        hospitalId = intent.getStringExtra("hospitalId");
        mIvDownload = findViewById(R.id.report_btn_send);
        pdfView = findViewById(R.id.pdf_webview);
        linearLeft = findViewById(R.id.pdf_liner_left);
        mRlWeb = findViewById(R.id.rl_web);
        mRlError = findViewById(R.id.rl_error);
        mRlLoading = findViewById(R.id.rl_loading);
        mRlWeb = findViewById(R.id.rl_web);
        mRlButtonSlices = findViewById(R.id.pdf_rl_button_slices);
        loadingText = findViewById(R.id.tv_loading);
        mRlSleepSlices= findViewById(R.id.rl_gone);
        mBtnSleepSlices = findViewById(R.id.sleep_slices_btn);
        mRlProgressBar = findViewById(R.id.rl_web1_progress);
        pdfView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = pdfView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

//        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(false);
        pdfView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mRlProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRlProgressBar.setVisibility(View.GONE);
                    }
                },1500);
//                dialogProgress.dismiss();
            }
        });


        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRlWeb.setVisibility(View.GONE);
        mRlError.setVisibility(View.GONE);
        mRlSleepSlices.setVisibility(View.GONE);
        mRlLoading.setVisibility(View.GONE);
        loadingText.setText("");
        time = new ReportPdfView1.TimeCount(15000, 1000);
        download(Api.REPORT_URL+reportUrl+".pdf");
//        download("http://kyy.nuaa.edu.cn/_upload/article/files/5d/41/08aaa276426b86fce47fd0a7b99b/dbd7a31f-f8ed-4692-a0cf-72cb9df136fb.pdf");

//        Log.d("??????id", reportID);

        if (reportID == null){
            Log.d("??????id", "???");
        }else {
            Log.d("??????id", reportID);
        }
        mRlButtonSlices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                getResAppointReport(Api.URL+"/v1/report/"+"asdsad123");
                getResAppointReport(Api.URL+"/v1/report/"+reportID);
//                Log.d("ReportPDF", "onClick: ");



            }
        });

        mBtnSleepSlices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                download(Api.REPORT_URL+reportUrl+".pdf");
            }
        });
    }

    /**
     * ????????????????????????????????????????????????
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int milis = (int) (millisUntilFinished / 1000);
            if (milis == 15){
                milis = 14;
            }

            linearLeft.setVisibility(View.GONE);
            mRlWeb.setVisibility(View.GONE);
            mRlLoading.setVisibility(View.VISIBLE);
            mRlError.setVisibility(View.GONE);
            loadingText.setText("?????????????????????" + (milis +1) + "s");

        }

        @Override
        public void onFinish() {
            loadingText.setText("");
            linearLeft.setVisibility(View.VISIBLE);
            download(Api.REPORT_URL+reportUrl+".pdf");


        }
    }

    /**
     * ??????pdf???????????????
     *
     * @param url ??????url
     */
    private void download(String url) {
        DownloadUtil.download(url, getCacheDir() + "/" + reportTrueName + " " + reportCreateTime + ".pdf",
                new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(final String path) {
                        Log.d("TAG", "onDownloadSuccess: " + path);
                        //??????????????????
                        GetFileSize getFileSize = new GetFileSize();
                        File ff = new File(path);
                        long l = 0;
                        try {
                            l = getFileSize.getFileSizes(ff);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        final long size = l / 1024;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                FileReader file = null;
                                try {
                                    file = new FileReader(getCacheDir() + "/" + reportTrueName + " " + reportCreateTime + ".pdf");

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    if (file.read() == 60) {
                                        mRlWeb.setVisibility(View.GONE);
                                        mRlError.setVisibility(View.VISIBLE);
                                        mRlSleepSlices.setVisibility(View.GONE);
                                        mRlLoading.setVisibility(View.GONE);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (size > 200){

                                    //??????pdf ?????????????????????
                                    mRlWeb.setVisibility(View.VISIBLE);
                                    mRlError.setVisibility(View.GONE);
                                    mRlSleepSlices.setVisibility(View.GONE);
                                    mRlLoading.setVisibility(View.GONE);
                                    preView(path);
                                } else {
                                    //?????????pdf ??????????????????
                                    mRlWeb.setVisibility(View.GONE);
                                    mRlError.setVisibility(View.VISIBLE);
                                    mRlSleepSlices.setVisibility(View.GONE);
                                    mRlLoading.setVisibility(View.GONE);
                                }


//                                displayFromFile( new File( path));
                                mIvDownload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
//                                        mRlWeb.setVisibility(View.GONE);
//                                        mRlError.setVisibility(View.VISIBLE);
//                                        mRlSleepSlices.setVisibility(View.GONE);
//                                        mRlLoading.setVisibility(View.GONE);
                                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//
                                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//???????????????
                                        sharingIntent.setType("application/pdf");//????????????????????????type
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM,
                                                    FileProvider.getUriForFile(getBaseContext(), getPackageName() + ".fileProvider",
                                                            new File(path)));
                                        } else {
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                                        }

                                        startActivity(Intent.createChooser(sharingIntent, "??????"));
                                    }
                                });
                            }
                        });



                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.d("TAG", "onDownloading: " + progress);
                    }

                    @Override
                    public void onDownloadFailed(String msg) {
                        mRlWeb.setVisibility(View.GONE);
                        mRlError.setVisibility(View.GONE);
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRlLoading.setVisibility(View.GONE);
                    }

                });
    }



    /**
     * ??????pdf
     *
     * @param pdfUrl url????????????????????????
     */
    private void preView(String pdfUrl) {
        //1.?????????pdf.js??????????????????????????????UI??????
        pdfView.loadUrl("file:///android_asset/index.html?" + pdfUrl);
        //2.??????mozilla??????demo????????????pdf
//        pdfView.loadUrl("http://mozilla.github.io/pdf.js/web/viewer.html?file=" + pdfUrl);
        //3.pdf.js????????????
//        pdfView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl);
        //4.????????????????????????
//        pdfView.loadUrl("http://docs.google.com/gviewembedded=true&url=" + pdfUrl);
    }

    protected void getResEditReport(String url) {


        //1.??????okhttp??????
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.??????request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
                .build();
        //3.???request?????????call
        Call call =   okHttpClient.newCall(request);
        //4.??????call
//        ????????????
//        Response response = call.execute();

        //????????????
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   "+e.getMessage());
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(ReportPdfView1.this,"??????????????????");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

//                parseJSONWithGSON(res);

                //??????java??????
                final EditReortResponse editReortResponse = new EditReortResponse();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //???????????????
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");

                    //???????????????
                    editReortResponse.setCode(code);
                    editReortResponse.setMsg(msg);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (editReortResponse.getCode() == 0){
//                            ToastUtils.showTextToast(ReportPdfView1.this,editReortResponse.getMsg());
                            Log.d("ReportPdfView1", "?????????????????????");
                            time.start();

                        }else if(editReortResponse.getCode() == 10004 || editReortResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(ReportPdfView1.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(ReportPdfView1.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(ReportPdfView1.this,editReortResponse.getMsg());
                        }


                    }
                });


            }
        });
    }

    protected void getResAppointReport(String url) {


        //1.??????okhttp??????
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.??????request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
                .build();
        //3.???request?????????call
        Call call =   okHttpClient.newCall(request);
        //4.??????call
//        ????????????
//        Response response = call.execute();

        //????????????
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   "+e.getMessage());
                e.printStackTrace();

               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(ReportPdfView1.this,"??????????????????");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);
                Log.d("ReportPdfView1123123", res);
//                parseJSONWithGSON(res);

                //??????java??????
                final AppointReportResponse appointReportResponse = new AppointReportResponse();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //???????????????
                    int code = jsonObject.optInt("code");
                    JSONObject data = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //???????????????
                    appointReportResponse.setCode(code);
                    appointReportResponse.setMsg(msg);
                    if (data!=null){
                        dataBean = new AppointReportResponse.DataBean();
                        appointReportResponse.setData(dataBean);



//                    ???????????????
                        String truename = data.optString("truename");
                        String examine = data.optString("examine");

                        dataBean.setTruename(truename);
                        dataBean.setExamine(examine);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

//                        Log.d("??????????????????", res);
                        if (appointReportResponse.getCode() == 0){
//                            ToastUtils.showTextToast(ReportPdfView1.this,"???????????????????????????");
                            Log.d("ReportPdfView1", "??????????????????");

                            mRlWeb.setVisibility(View.GONE);
                            mRlError.setVisibility(View.GONE);
                            mRlError.setVisibility(View.GONE);
                            mRlLoading.setVisibility(View.VISIBLE);
                            getResEditReport(Api.URL+"/v2/report/"+reportID+"/edit?hospitalid="+hospitalId);
                        }else if(appointReportResponse.getCode() == 10004 || appointReportResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(ReportPdfView1.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(ReportPdfView1.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(ReportPdfView1.this,appointReportResponse.getMsg());
                        }


                    }
                });


            }
        });
    }

    protected String getTokenToSp(String key, String val) {

        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "??????token");
        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 =getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "??????uid");

        return uid;
    }

}