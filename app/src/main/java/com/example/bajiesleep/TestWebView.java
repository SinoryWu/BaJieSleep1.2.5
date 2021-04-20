package com.example.bajiesleep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;





public class TestWebView extends Activity {
    private WebView testWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_web_view);
        testWebView=findViewById(R.id.test_webview);
        testWebView.setWebChromeClient(new WebChromeClient());
//
        WebSettings webSettings = testWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setAllowFileAccess(true);

        webSettings.setAllowFileAccessFromFileURLs(true);

        webSettings.setAllowUniversalAccessFromFileURLs(true);


//        displayFromFile("https://bajie-sleep.oss-cn-hangzhou.aliyuncs.com/formal/5f8f5dedf21f8d6577a230d3.pdf","5f8f5dedf21f8d6577a230d3.pdf");
        testWebView.loadUrl("http://docs.google.com/gview?url=https://bajie-sleep.oss-cn-hangzhou.aliyuncs.com/formal/5f8f5dedf21f8d6577a230d3.pdf");


    }


}