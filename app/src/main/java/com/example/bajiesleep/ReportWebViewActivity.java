package com.example.bajiesleep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class ReportWebViewActivity extends AppCompatActivity {

    private WebView mWvReport;
    private LinearLayout linearLeft;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_web_view);



        final Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#f7f7f7"));

        mWvReport = findViewById(R.id.report_webview);
        linearLeft=findViewById(R.id.recover_liner_left_web);

        Intent intent = getIntent();
        String reportUrl = intent.getStringExtra("reportUrl");
        Log.d("reportUrl",reportUrl);
        mWvReport.loadUrl(reportUrl);
        mWvReport.getSettings().setJavaScriptEnabled(true);
        mWvReport.getSettings().setBlockNetworkImage(false);


        mWvReport.setWebChromeClient(new WebChromeClient());
        mWvReport.getSettings().setJavaScriptEnabled(true);
        mWvReport.getSettings().setSupportZoom(true);
        mWvReport.getSettings().setBuiltInZoomControls(true);
        mWvReport.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWvReport.getSettings().setLoadWithOverviewMode(true);
        mWvReport.getSettings().setDisplayZoomControls(false);



        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }
}