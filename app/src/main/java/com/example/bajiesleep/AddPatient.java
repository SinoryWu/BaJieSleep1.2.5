package com.example.bajiesleep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bajiesleep.entity.RecoverResponse;
import com.example.bajiesleep.entity.SearchDeviceScanResponse1;
import com.example.bajiesleep.fragment.DeviceFragment;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddPatient extends AppCompatActivity {

    private ImageView mIvWomen,mIvMen;
    private TextView mTvSex,mTvBtnId;
    private LinearLayout mLinarLeft;
    private EditText mEtName,mEtAge,mEtHeight,mEtWeight,mEtMobile;
    private Button mBtnAdd;
    DeviceFragment deviceFragment ;

    RecoverResponse recoverResponse = new RecoverResponse();
    int a ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        mIvWomen = findViewById(R.id.add_sex_woman);
        mIvMen =findViewById(R.id.add_sex_man);
        mTvSex = findViewById(R.id.tv_sex);
        mLinarLeft=findViewById(R.id.liner_left);
        mTvBtnId = findViewById(R.id.tv_btnid);
        mEtName = findViewById(R.id.add_et_name);
        mEtAge = findViewById(R.id.add_et_age);
        mEtHeight = findViewById(R.id.add_et_height);
        mEtWeight=findViewById(R.id.add_et_weight);
        mEtMobile = findViewById(R.id.add_et_phone);
        mBtnAdd=findViewById(R.id.add_add);



        mTvBtnId.setVisibility(View.GONE);
        mTvSex.setVisibility(View.GONE);
        deviceFragment = new DeviceFragment();
//       Log.d("BtnId", String.valueOf(deviceFragment.getK()));
        mLinarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mIvWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvWomen.setImageResource(R.drawable.sex_women_focus);
                mIvMen.setImageResource(R.drawable.sex_men_normal);
                mTvSex.setText("2");
            }
        });

        mIvMen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvWomen.setImageResource(R.drawable.sex_women_normal);
                mIvMen.setImageResource(R.drawable.sex_men_focus);
                mTvSex.setText("1");
            }
        });


        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtName.getText().toString())){
                    ToastUtils.showTextToast(AddPatient.this,"请输入姓名");
                }else if (TextUtils.isEmpty(mTvSex.getText().toString())){
                    ToastUtils.showTextToast(AddPatient.this,"请选择性别");
                }else if (TextUtils.isEmpty(mEtAge.getText().toString())){
                    ToastUtils.showTextToast(AddPatient.this,"请输入年龄");
                }else if (TextUtils.isEmpty(mEtHeight.getText().toString())){
                    ToastUtils.showTextToast(AddPatient.this,"请输入身高");
                }else if (TextUtils.isEmpty(mEtWeight.getText().toString())){
                    ToastUtils.showTextToast(AddPatient.this,"请输入体重");
                }else if (TextUtils.isEmpty(mEtMobile.getText().toString())){
                    ToastUtils.showTextToast(AddPatient.this,"请输入手机号");
                }else if (!TextUtils.isEmpty(mEtAge.getText().toString())  && !TextUtils.isEmpty(mEtHeight.getText().toString()) && !TextUtils.isEmpty(mEtWeight.getText().toString()) && !TextUtils.isEmpty(mEtName.getText().toString())
                  && !TextUtils.isEmpty(mEtMobile.getText().toString()) && !TextUtils.isEmpty(mTvSex.getText().toString())){

                    if (mEtMobile.getText().length() < 11){
                        ToastUtils.showTextToast(AddPatient.this,"请输入正确手机号");
                    }else {
                        getResRecover(Api.URL+"/v1/user/create?sex="+mTvSex.getText()+"&age="+mEtAge.getText().toString()+"&height="+mEtHeight.getText().toString()+"&weight="+mEtWeight.getText().toString()+"&hospitalid="+getHosIdToSp("hosid","")+"&truename="+mEtName.getText().toString()+"&mobile="+mEtMobile.getText().toString());
                    }

                }

            }
        });

    }

    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "没有hosid");
        return hosid;
    }

    protected String getTokenToSp(String key, String val) {
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "没有token");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "没有uid");


        return uid;
    }

    public void getResRecover(String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .build();
        //3.将request封装为call
        Call call =   okHttpClient.newCall(request);
        //4.执行call
//        同步执行
//        Response response = call.execute();

        //异步执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   "+e.getMessage());
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(AddPatient.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchres",res);

//                parseJSONWithGSON(res);


                //封装java对象


                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    recoverResponse.setCode(code);
                    recoverResponse.setMsg(msg);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (recoverResponse.getCode() == 0){
                            ToastUtils.showTextToast(AddPatient.this,"添加成功");
                            finish();
                        }else if (recoverResponse.getCode() == 10003){
                            ToastUtils.showTextToast(AddPatient.this,"请选择性别");
                        }else if (recoverResponse.getCode() == 10010 || recoverResponse.getCode() == 10004 ){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(AddPatient.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(AddPatient.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }

                        else {
                            ToastUtils.showTextToast(AddPatient.this,recoverResponse.getMsg());
                        }

                    }
                });

            }


        });
    }


}