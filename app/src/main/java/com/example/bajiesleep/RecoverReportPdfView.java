package com.example.bajiesleep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.bajiesleep.entity.AppointReportResponse;
import com.example.bajiesleep.entity.EditReortResponse;
import com.example.bajiesleep.util.GetFileSize;
import com.example.bajiesleep.util.GetShp;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

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

public class RecoverReportPdfView extends AppCompatActivity  implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {

    private WebView pdfView;
    private PDFView pdfView1;
    private LinearLayout linearLeft;
    AppointReportResponse.DataBean dataBean;
    String reportUrl;
    String reportId,reportID;
    String hospitalId;
    private ImageView mIvDownload;
    private RelativeLayout mRlWeb, mRlError, mRlLoading, mRlButtonSlices,mRlSleepSlices;
    private TextView loadingText;
    private Button mBtnSleepSlices;
    private TimeCount time;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pdf_view1);
        final Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#ffffff"));
        Intent intent=getIntent();
        reportUrl=intent.getStringExtra("reportUrl");
        hospitalId = intent.getStringExtra("hospitalId");
        reportId = intent.getStringExtra("reportId");
        reportID = intent.getStringExtra("reportID");
        initView();
        mRlWeb.setVisibility(View.GONE);
        mRlError.setVisibility(View.GONE);
        mRlSleepSlices.setVisibility(View.GONE);
        mRlLoading.setVisibility(View.GONE);
        Log.d("reportid", reportId);
        //??????????????????
//        preView("file:///android_asset/demo.pdf");
        //?????????????????????????????????
//        preView(Api.REPORT_URL+reportUrl+".pdf");
        //?????????????????? ??????pdf????????????????????????
        loadingText.setText("");
        time = new RecoverReportPdfView.TimeCount(15000, 1000);
        download(Api.REPORT_URL+reportUrl+".pdf");
//        download("http://kyy.nuaa.edu.cn/_upload/article/files/5d/41/08aaa276426b86fce47fd0a7b99b/dbd7a31f-f8ed-4692-a0cf-72cb9df136fb.pdf");
        Log.d("RecoverReportPDF", "onCreate: ");
        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRlButtonSlices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRlWeb.setVisibility(View.GONE);
                mRlError.setVisibility(View.GONE);
                mRlError.setVisibility(View.GONE);
                mRlLoading.setVisibility(View.VISIBLE);

                getResAppointReport(Api.URL+"/v1/report/"+reportID);



            }
        });

        mBtnSleepSlices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download(Api.REPORT_URL+reportUrl+".pdf");
            }
        });
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
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
        pdfView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = pdfView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);


        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(false);



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
            loadingText.setText("?????????????????????" + (milis +1)  + "s");
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
        DownloadUtil.download(url, getCacheDir() + "/"+reportId+".pdf",
                new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(final String path) {
                        Log.d("MainActivity", "onDownloadSuccess: " + path);
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
                                    file = new FileReader(getCacheDir() + "/"+reportId+".pdf");

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
                                    mRlWeb.setVisibility(View.VISIBLE);
                                    mRlError.setVisibility(View.GONE);
                                    mRlSleepSlices.setVisibility(View.GONE);
                                    mRlLoading.setVisibility(View.GONE);
                                    preView(path);
                                }else {
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
                                                    FileProvider.getUriForFile(getBaseContext(), getPackageName() +".fileProvider",
                                                            new File(path)));
                                        }else {
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
                        Log.d("MainActivity", "onDownloading: " + progress);
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
                        ToastUtils.showTextToast2(RecoverReportPdfView.this,"??????????????????");
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

                            time.start();

                        }else if(editReortResponse.getCode() == 10004 || editReortResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(RecoverReportPdfView.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(RecoverReportPdfView.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(RecoverReportPdfView.this,editReortResponse.getMsg());
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
                        ToastUtils.showTextToast2(RecoverReportPdfView.this,"??????????????????");
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
                    dataBean = new AppointReportResponse.DataBean();
                    appointReportResponse.setData(dataBean);

                    if (data !=null){
//                        ???????????????
                        String truename = data.optString("truename");
                        String examine = data.optString("examine");

                        dataBean.setTruename(truename);
                        dataBean.setExamine(examine);
                    }


//



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
                            getResEditReport(Api.URL+"/v2/report/"+reportID+"/edit?hospitalid="+hospitalId);
                        }else if(appointReportResponse.getCode() == 10004 || appointReportResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(RecoverReportPdfView.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(RecoverReportPdfView.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(RecoverReportPdfView.this,appointReportResponse.getMsg());
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

    private void displayFromFile( File file ) {
        pdfView1.fromFile(file)   //??????pdf????????????
                .defaultPage(0)         //?????????????????????1???
                .onPageChange(this)     //??????????????????
                .onLoad(this)           //??????????????????
                .onDraw(this)            //????????????
                .showMinimap(false)     //pdf????????????????????????????????????????????????????????????
                .swipeVertical( true )  //pdf???????????????????????????????????????????????????????????????
                .enableSwipe(true)   //???????????????????????????????????????
                // .pages( 2 ???5  )  //???2  5 ?????????
                .load();
        Log.d("file", String.valueOf(file));
    }
    public int getImageHeight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // ????????????????????????
        float density = dm.density;         // ????????????
        int buttonHeight = (int) (65 * density);
        return buttonHeight;
    }

    public int getImageWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // ????????????????????????
        float density = dm.density;         // ????????????
        int buttonHeight = (int) (65 * density);
        return buttonHeight;
    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}