package com.example.bajiesleep;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnDrawListener;
import com.joanzapata.pdfview.listener.OnLoadCompleteListener;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;

public class RecoverReportPdfView extends AppCompatActivity  implements OnPageChangeListener, OnLoadCompleteListener, OnDrawListener {

    private WebView pdfView;
    private LinearLayout linearLeft;
    private PDFView pdfView1;
    String reportUrl;
    String reportId;
    String reportCreateTime;
    private ImageView mIvDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_pdf_view);

        Intent intent=getIntent();
        reportUrl=intent.getStringExtra("reportUrl");

        reportId = intent.getStringExtra("reportId");
        initView();

        //加载本地文件
//        preView("file:///android_asset/demo.pdf");
        //加载允许跨域访问的文件
//        preView(Api.REPORT_URL+reportUrl+".pdf");
        //跨域加载文件 先将pdf下载到本地在加载
        download(Api.REPORT_URL+reportUrl+".pdf");


        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mIvDownload = findViewById(R.id.report_btn_send);


        pdfView = findViewById(R.id.pdf_webview);
        linearLeft = findViewById(R.id.pdf_liner_left);
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



    }


    /**
     * 下载pdf文件到本地
     *
     * @param url 文件url
     */
    private void download(String url) {
        DownloadUtil.download(url, getCacheDir() + "/"+reportId+".pdf",
                new DownloadUtil.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess(final String path) {
                        Log.d("MainActivity", "onDownloadSuccess: " + path);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                preView(path);
//                                displayFromFile( new File( path));
                                mIvDownload.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//
                                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//给临时权限
                                        sharingIntent.setType("application/pdf");//根据文件类型设定type
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM,
                                                    FileProvider.getUriForFile(getBaseContext(), getPackageName() +".fileProvider",
                                                            new File(path)));
                                        }else {
                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                                        }

                                        startActivity(Intent.createChooser(sharingIntent, "分享"));
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
                        Log.d("MainActivity", "onDownloadFailed: " + msg);
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

    private void displayFromFile( File file ) {
        pdfView1.fromFile(file)   //设置pdf文件地址
                .defaultPage(0)         //设置默认显示第1页
                .onPageChange(this)     //设置翻页监听
                .onLoad(this)           //设置加载监听
                .onDraw(this)            //绘图监听
                .showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
                .swipeVertical( true )  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
                .enableSwipe(true)   //是否允许翻页，默认是允许翻
                // .pages( 2 ，5  )  //把2  5 过滤掉
                .load();
        Log.d("file", String.valueOf(file));
    }
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