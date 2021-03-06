package com.example.bajiesleep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
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

import com.example.bajiesleep.util.GetFileSize;
import com.joanzapata.pdfview.PDFView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReportPdfView extends AppCompatActivity  {

    private WebView pdfView;
    private LinearLayout linearLeft;
    private PDFView pdfView1;
    String reportUrl;
    String reportTrueName;
    String reportCreateTime;
    private ImageView mIvDownload;
    private RelativeLayout mRlWeb, mRlError, mRlLoading, mRlButtonSlices,mRlSleepSlices,mRlProgressBar;
    private TextView loadingText;
    private Button mBtnSleepSlices;
    private TimeCount time;
    int i = 0;
    DialogProgress dialogProgress;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pdf_view);
        dialogProgress = new DialogProgress(ReportPdfView.this,R.style.CustomDialog);
        final Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#ffffff"));
        Intent intent = getIntent();
        reportUrl = intent.getStringExtra("reportUrl");
        reportTrueName = intent.getStringExtra("reportTrueName");
        reportCreateTime = intent.getStringExtra("reportCreateTime");
        initView();
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
        mRlProgressBar = findViewById(R.id.rl_web_progress);

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
//        mRlError.setVisibility(View.GONE);
//        mRlSleepSlices.setVisibility(View.GONE);
//        mRlLoading.setVisibility(View.GONE);
//        mRlWeb.setVisibility(View.GONE);


        Log.d("reportpdfview", reportUrl);
        Log.d("ReportPDF", "onCreate: ");
        //??????????????????
//        preView("file:///android_asset/demo.pdf");
        //?????????????????????????????????
//        preView(Api.REPORT_URL+reportUrl+".pdf");
        //?????????????????? ??????pdf????????????????????????
        download(Api.REPORT_URL+reportUrl+".pdf");
//        download("https://wkbjcloudbos.bdimg.com/v1/wenku73//87da5d018a895f0f655800800bd5e85f?responseContentDisposition=attachment%3B%20filename%3D%22%25E5%25BE%2588%25E5%2585%25A8%25E9%259D%25A2%25E9%25AB%2598%25E4%25B8%25AD%25E6%2595%25B0%25E5%25AD%25A6%25E5%2585%25AC%25E5%25BC%258F%25E6%2580%25BB%25E7%25BB%2593-%25E5%2585%258D%25E8%25B4%25B9%25E4%25B8%258B%25E8%25BD%25BD.pdf.doc%22%3B%20filename%2A%3Dutf-8%27%27%25E5%25BE%2588%25E5%2585%25A8%25E9%259D%25A2%25E9%25AB%2598%25E4%25B8%25AD%25E6%2595%25B0%25E5%25AD%25A6%25E5%2585%25AC%25E5%25BC%258F%25E6%2580%25BB%25E7%25BB%2593-%25E5%2585%258D%25E8%25B4%25B9%25E4%25B8%258B%25E8%25BD%25BD.pdf.doc&responseContentType=application%2Foctet-stream&responseCacheControl=max-age%3D3888000&responseExpires=Thu%2C%2001%20Jul%202021%2016%3A50%3A49%20%2B0800&authorization=bce-auth-v1%2Ffa1126e91489401fa7cc85045ce7179e%2F2021-05-17T08%3A50%3A49Z%2F3000%2Fhost%2F2cf8150e907a9e3da1fc0715af07cbdcef7b45dd01df38d26c4091692efa4d45&token=eyJ0eXAiOiJKSVQiLCJ2ZXIiOiIxLjAiLCJhbGciOiJIUzI1NiIsImV4cCI6MTYyMTI0NDQ0OSwidXJpIjp0cnVlLCJwYXJhbXMiOlsicmVzcG9uc2VDb250ZW50RGlzcG9zaXRpb24iLCJyZXNwb25zZUNvbnRlbnRUeXBlIiwicmVzcG9uc2VDYWNoZUNvbnRyb2wiLCJyZXNwb25zZUV4cGlyZXMiXX0%3D.cgxxh8YsTD6Ao5ezUHn%2BWzv4qPGcB8%2Fme7GxOuJZlUE%3D.1621244449");
        Log.d("TAG", Api.REPORT_URL + reportUrl + ".pdf");
//        FileDownloader.setupOnApplicationOnCreate(getApplication());
//        DownLoadFile(Api.REPORT_URL + reportUrl + ".pdf", getCacheDir() + "/" + reportTrueName + " " + reportCreateTime + ".pdf");
//        DownLoadFile("https://bajie-sleep.oss-cn-hangzhou.aliyuncs.com/formal/604941792238fd71a39a36.pdf", getCacheDir() + "/" + reportTrueName + " " + reportCreateTime + ".pdf");




        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnSleepSlices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRlSleepSlices.setVisibility(View.GONE);
                download(Api.REPORT_URL+reportUrl+".pdf");
            }
        });

        mRlButtonSlices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ");
                mRlError.setVisibility(View.GONE);
                mRlLoading.setVisibility(View.VISIBLE);
                time = new ReportPdfView.TimeCount(16000, 1000);
                time.start();
            }
        });
    }


    private void initView() {







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

            linearLeft.setVisibility(View.GONE);
            mRlWeb.setVisibility(View.GONE);
            mRlLoading.setVisibility(View.VISIBLE);
            mRlError.setVisibility(View.GONE);
            loadingText.setText("?????????????????????" + millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            mRlWeb.setVisibility(View.VISIBLE);
            mRlLoading.setVisibility(View.GONE);
            mRlError.setVisibility(View.GONE);
            linearLeft.setVisibility(View.VISIBLE);


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
                        long size = l / 1024;
                        i = 3;
                        Log.d("TAG", String.valueOf(i));
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
                                        ToastUtils.showTextToast(ReportPdfView.this, "????????????");
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                preView(path);

//                                displayFromFile( new File( path));
                                mIvDownload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mRlWeb.setVisibility(View.VISIBLE);
                                        mRlError.setVisibility(View.GONE);
                                        mRlSleepSlices.setVisibility(View.GONE);
                                        mRlLoading.setVisibility(View.GONE);
//                                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
////
//                                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//???????????????
//                                        sharingIntent.setType("application/pdf");//????????????????????????type
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                            sharingIntent.putExtra(Intent.EXTRA_STREAM,
//                                                    FileProvider.getUriForFile(getBaseContext(), getPackageName() + ".fileProvider",
//                                                            new File(path)));
//                                        } else {
//                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
//                                        }
//
//                                        startActivity(Intent.createChooser(sharingIntent, "??????"));
                                    }
                                });
                            }
                        });

                        if (size > 200){
                            mRlWeb.setVisibility(View.VISIBLE);
                            mRlError.setVisibility(View.GONE);
                            mRlSleepSlices.setVisibility(View.GONE);
                            mRlLoading.setVisibility(View.GONE);
                        }else {
//                            mRlError.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.d("TAG", "onDownloading: " + progress);
                    }

                    @Override
                    public void onDownloadFailed(String msg) {


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

//    private void displayFromFile(File file) {
//        pdfView1.fromFile(file)   //??????pdf????????????
//                .defaultPage(0)         //?????????????????????1???
//                .onPageChange(this)     //??????????????????
//                .onLoad(this)           //??????????????????
//                .onDraw(this)            //????????????
//                .showMinimap(false)     //pdf????????????????????????????????????????????????????????????
//                .swipeVertical(true)  //pdf???????????????????????????????????????????????????????????????
//                .enableSwipe(true)   //???????????????????????????????????????
//                // .pages( 2 ???5  )  //???2  5 ?????????
//                .load();
//        Log.d("file", String.valueOf(file));
//    }

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




}