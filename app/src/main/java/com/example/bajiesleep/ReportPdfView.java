package com.example.bajiesleep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
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

import com.example.bajiesleep.util.GetFileSize;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import pl.droidsonroids.gif.GifImageView;

public class ReportPdfView extends AppCompatActivity  {

    private WebView pdfView;
    private LinearLayout linearLeft;
    private PDFView pdfView1;
    String reportUrl;
    String reportTrueName;
    String reportCreateTime;
    private ImageView mIvDownload;
    private RelativeLayout mRlWeb, mRlError, mRlLoading, mRlButtonSlices,mRlSleepSlices;
    private TextView loadingText;
    private Button mBtnSleepSlices;
    private TimeCount time;
    int i = 0;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pdf_view);
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

//        mRlError.setVisibility(View.GONE);
//        mRlSleepSlices.setVisibility(View.GONE);
//        mRlLoading.setVisibility(View.GONE);
//        mRlWeb.setVisibility(View.GONE);


        Log.d("reportpdfview", reportUrl);
        Log.d("ReportPDF", "onCreate: ");
        //加载本地文件
//        preView("file:///android_asset/demo.pdf");
        //加载允许跨域访问的文件
//        preView(Api.REPORT_URL+reportUrl+".pdf");
        //跨域加载文件 先将pdf下载到本地在加载
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
     * 实现点击获取验证码按钮倒计时效果
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
            loadingText.setText("正在重新加载…" + millisUntilFinished / 1000 + "s");
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
     * 下载pdf文件到本地
     *
     * @param url 文件url
     */
    private void download(String url) {
        DownloadUtil.download(url, getCacheDir() + "/" + reportTrueName + " " + reportCreateTime + ".pdf",
                new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(final String path) {
                        Log.d("TAG", "onDownloadSuccess: " + path);
                        //判断文件大小
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
                                        ToastUtils.showTextToast(ReportPdfView.this, "加载失败");
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
//                                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//给临时权限
//                                        sharingIntent.setType("application/pdf");//根据文件类型设定type
//                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                            sharingIntent.putExtra(Intent.EXTRA_STREAM,
//                                                    FileProvider.getUriForFile(getBaseContext(), getPackageName() + ".fileProvider",
//                                                            new File(path)));
//                                        } else {
//                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
//                                        }
//
//                                        startActivity(Intent.createChooser(sharingIntent, "分享"));
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
     * 预览pdf
     *
     * @param pdfUrl url或者本地文件路径
     */
    private void preView(String pdfUrl) {
        //1.只使用pdf.js渲染功能，自定义预览UI界面
        pdfView.loadUrl("file:///android_asset/index.html?" + pdfUrl);
        //2.使用mozilla官方demo加载在线pdf
//        pdfView.loadUrl("http://mozilla.github.io/pdf.js/web/viewer.html?file=" + pdfUrl);
        //3.pdf.js放到本地
//        pdfView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + pdfUrl);
        //4.使用谷歌文档服务
//        pdfView.loadUrl("http://docs.google.com/gviewembedded=true&url=" + pdfUrl);
    }

//    private void displayFromFile(File file) {
//        pdfView1.fromFile(file)   //设置pdf文件地址
//                .defaultPage(0)         //设置默认显示第1页
//                .onPageChange(this)     //设置翻页监听
//                .onLoad(this)           //设置加载监听
//                .onDraw(this)            //绘图监听
//                .showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
//                .swipeVertical(true)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
//                .enableSwipe(true)   //是否允许翻页，默认是允许翻
//                // .pages( 2 ，5  )  //把2  5 过滤掉
//                .load();
//        Log.d("file", String.valueOf(file));
//    }

    public int getImageHeight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度
        int buttonHeight = (int) (65 * density);
        return buttonHeight;
    }

    public int getImageWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度
        int buttonHeight = (int) (65 * density);
        return buttonHeight;
    }




}