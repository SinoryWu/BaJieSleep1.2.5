package com.example.bajiesleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bajiesleep.BuildConfig;
import com.example.bajiesleep.R;

public class InfoActivity extends AppCompatActivity {
    /**
     * @return
     * 获取本地包
     */
    public int getVerCode()
    {
        int verCode = -1;
        try
        {
            verCode = getPackageManager().getPackageInfo(
                    "com.example.bajiesleep", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
        }
        return verCode;
    }

    public String getVerName()
    {
        String verName = "-1";
        try
        {
            verName = getPackageManager().getPackageInfo(
                    "com.example.bajiesleep", 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
        }
        return verName;
    }

    private LinearLayout linearLeft;

    private TextView mTvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        linearLeft=findViewById(R.id.liner_left_info);
        mTvVersion = findViewById(R.id.tv_info_versionName);
        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTvVersion.setText("当前版本：V "+getVerName());
    }
}