package com.example.bajiesleep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity extends Activity {
    TextView tvPushTime;
    private Button mBtnToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvPushTime = (TextView) findViewById(R.id.tvPushTime);
//        mBtnToken =findViewById(R.id.btn_token_intent1);

//        findViewById(R.id.setTimeLayout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimeRangePickerDialog dialog = new TimeRangePickerDialog(TestActivity.this, tvPushTime.getText().toString(), new TimeRangePickerDialog.ConfirmAction() {
//                    @Override
//                    public void onLeftClick() {
//                    }
//
//                    @Override
//                    public void onRightClick(String startHour, String starMinute, String endHour, String endMinute) {
//
//                    }
//
//
//                });
//
//                dialog.show();
//            }
//        });

        mBtnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(TestActivity.this,R.style.CustomDialog);
                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                    @Override
                    public void OnConfirm(DialogTokenIntent dialog) {
                        Intent intent = new Intent(TestActivity.this,LoginActivity.class);
                        finish();
                        startActivity(intent);

                    }
                }).show();

                dialogTokenIntent.setCanceledOnTouchOutside(false);
                dialogTokenIntent.setCancelable(false);
            }
        });

    }

}