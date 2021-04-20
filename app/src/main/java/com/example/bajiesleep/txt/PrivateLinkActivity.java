package com.example.bajiesleep.txt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bajiesleep.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class PrivateLinkActivity extends AppCompatActivity {
    private TextView mTvPrivateLink;
    private LinearLayout linearLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_link);
        mTvPrivateLink =findViewById(R.id.private_tv_private_link);
        linearLeft=findViewById(R.id.liner_left_private_link);
        mTvPrivateLink.setMovementMethod(new ScrollingMovementMethod());

        InputStream inputStream = getResources().openRawResource(R.raw.private_link);
        String string = TxtReader.getString(inputStream);
        mTvPrivateLink.setText(string);

        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




    public static class TxtReader {


        /**
         * 通过一个InputStream获取内容
         *
         * @param inputStream
         * @return
         */
        public static String getString(InputStream inputStream) {
            InputStreamReader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer("");
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }


        /**
         * 通过txt文件的路径获取其内容
         *
         * @param filepath
         * @return
         */
        public static String getString(String filepath) {
            File file = new File(filepath);
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return getString(fileInputStream);
        }
    }



}