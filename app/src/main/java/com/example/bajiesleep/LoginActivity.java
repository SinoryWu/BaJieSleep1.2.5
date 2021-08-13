package com.example.bajiesleep;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bajiesleep.entity.LoginDataResponse;
import com.example.bajiesleep.entity.LoginResponse;
import com.example.bajiesleep.entity.SendMessageResponse;
import com.example.bajiesleep.txt.PrivateLinkActivity;
import com.example.bajiesleep.txt.UserLinkActivity;
import com.example.bajiesleep.util.GetShp;
import com.example.jpushdemo.ExampleUtil;
import com.example.jpushdemo.MyReceiver;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.i.IFileDownloadIPCCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private Button mBtnLogin, mBtnCatchInformation,mBtnGetRegId;
    private CardView mCvLogin;
    private ImageView mIvTitle;
    private EditText mEtAccount, mEtVerificationCode;
    private TimeCount time;
    private TextView mTvPrivateLink, mTvUserAgreeLink;
    private CheckBox mCb1;
//    HookMacAddressUtils hookMacAddressUtils;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private PushReceiverViewModel pushReceiverViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        String useragent = new WebView(this).getSettings().getUserAgentString();

//        Log.d("userAgent234234", useragent);
//        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo winfo = wifi.getConnectionInfo();
//
//        String mac =  winfo.getMacAddress();
//        String mac =  winfo.getMacAddress();
//        Log.d("mac", mac);
//        hookMacAddressUtils = HookMacAddressUtils.INSTANCE;
//        hookMacAddressUtils.hookMacAddress(getApplicationContext());
//        hookMacAddressUtils.closeHookMacAddress();
        String userAgent = System.getProperty("http.agent");

        saveStringToSp("user_agent",userAgent);
//        Log.d("userAgent", GetShp.getUserAgent(this));
        pushReceiverViewModel = new ViewModelProvider(LoginActivity.this,new ViewModelProvider.NewInstanceFactory()).get(PushReceiverViewModel.class);
        initView();

        time = new TimeCount(60000, 1000);
        mBtnCatchInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCb1.isChecked()){
                    String account = mEtAccount.getText().toString().trim();

                    if (account.length() >0){
                        time.start();
                        codePost(account);
                    }else {
                        ToastUtils.showTextToast2(LoginActivity.this,"请输入手机号");
                    }

                }else {
                    ToastUtils.showTextToast2(LoginActivity.this, "请勾选阅读并同意《隐私声明》《用户协议》");

                }


            }
        });

        mCb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    pushReceiverViewModel.number.postValue(1);

                    JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
                    JPushInterface.init(getApplicationContext());
//                    myReceiver.onReceive(LoginActivity.this,getIntent());
                }else {
//                    pushReceiverViewModel.number.postValue(2);
                }
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCb1.isChecked()){
                    String account = mEtAccount.getText().toString().trim();
                    String code = mEtVerificationCode.getText().toString().trim();
                    String RegId = JPushInterface.getRegistrationID(getApplicationContext());
                    loginPost(account,code,RegId);
                    saveStringToJpush("jpushkey","1");
                    Log.d("RegId",RegId);
                }else {
                    ToastUtils.showTextToast2(LoginActivity.this, "请勾选阅读并同意《隐私声明》《用户协议》");
                }


//                Intent intent = new Intent(LoginActivity.this,UserInterfaceActivity.class);
//                startActivity(intent);

            }
        });

        mTvPrivateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PrivateLinkActivity.class);
                startActivity(intent);

            }
        });

        mTvUserAgreeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, UserLinkActivity.class);
                startActivity(intent);
            }
        });

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }






    }

    public void initView() {
        setContentView(R.layout.activity_login);
        mBtnLogin = findViewById(R.id.btn_login);
        mCvLogin = findViewById(R.id.cv_login);
        mIvTitle = findViewById(R.id.login_title);
        mEtAccount = findViewById(R.id.login_et_account);
        mEtVerificationCode = findViewById(R.id.login_et_verification_code);
        mBtnCatchInformation = findViewById(R.id.btn_catch_information);
        mTvPrivateLink = findViewById(R.id.login_tv_private_link);
        mTvUserAgreeLink = findViewById(R.id.login_tv_user_agreement_link);
        mCb1 = findViewById(R.id.cb_1);


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

            mBtnCatchInformation.setClickable(false);
            mBtnCatchInformation.setText("" + millisUntilFinished / 1000 + "秒");
            mBtnCatchInformation.setBackgroundResource(R.drawable.catch_information_button_background_catch);
        }

        @Override
        public void onFinish() {
            mBtnCatchInformation.setText("重新获取验证码");
            mBtnCatchInformation.setClickable(true);
            mBtnCatchInformation.setBackgroundResource(R.drawable.catch_information_button_background_normal);
        }
    }




    private void codePost(String account){
        HashMap<String,String> map = new HashMap<>();
        map.put("mobile",account);//18158188
        String url =Api.URL+"/v1/Jms";
        postResCode(url,map);
    }

    protected void postResCode(String url, HashMap<String, String> map) {
        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();


        //2.构造request
        //2.1构造requestbody

        HashMap<String, Object> params = new HashMap<String, Object>();

        Log.e("params:", String.valueOf(params));
        Set<String> keys = map.keySet();
        for (String key : keys) {
            params.put(key, map.get(key));

        }


        JSONObject jsonObject = new JSONObject(params);
        String jsonStr = jsonObject.toString();

        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBodyJson)
                .build();
        //3.将request封装为call
        Call call = okHttpClient.newCall(request);
        //4.执行call
//        同步执行
//        Response response = call.execute();

        //异步执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   " + e.getMessage());
                e.printStackTrace();



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(LoginActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        SendMessageResponse sendMessageResponse = gson.fromJson(res, SendMessageResponse.class);
                        String msg = sendMessageResponse.getMsg();


                        ToastUtils.showTextToast2(LoginActivity.this,msg);

                    }
                });



            }
        });




    }

    private void loginPost(String account, String code,String regId) {


        if (account.length() <= 0 && code.length() <= 0) {
            ToastUtils.showTextToast2(LoginActivity.this, "请输入手机号和验证码");

        } else if (account.length() <= 0) {
            ToastUtils.showTextToast2(LoginActivity.this, "请输入手机号");
        } else if (code.length() <= 0) {
            ToastUtils.showTextToast2(LoginActivity.this, "请输入验证码");
        } else if (!mCb1.isChecked()) {
            ToastUtils.showTextToast2(LoginActivity.this, "请勾选阅读并同意《隐私声明》《用户协议》");
        } else if (account != null && code != null) {
            HashMap<String, String> map = new HashMap<>();
            map.put("mobile", account);//18158188052
            map.put("code", code);//111
            map.put("reg_id",regId);
//        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);
            String url = Api.URL+"/v2/login/check";
            L.e( String.valueOf(map));

            postResLogin(url, map);
        }



    }

    protected void postResLogin(String url, HashMap<String, String> map) {
        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();


        //2.构造request
        //2.1构造requestbody

        HashMap<String, Object> params = new HashMap<String, Object>();

        Log.e("params:",String.valueOf(params));
        Set<String> keys = map.keySet();
        for (String key:keys)
        {
            params.put(key,map.get(key));

        }


        JSONObject jsonObject = new JSONObject(params);
        String jsonStr = jsonObject.toString();

        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);

        Log.d("useragent", GetShp.getUserAgent(getApplicationContext()));
        Request request = new Request.Builder()
                .addHeader("user-agent",GetShp.getUserAgent(getApplicationContext()))
                .url(url)
                .post(requestBodyJson)
                .build();
        //3.将request封装为call
        Call call =   okHttpClient.newCall(request);
        L.e(String.valueOf(call));
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
                        ToastUtils.showTextToast2(LoginActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        L.e(res);

                        Gson gson = new Gson();

                        LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);

                        if (loginResponse.getCode() == 0) {

                            final String data = loginResponse.getData().toString();
                            LoginDataResponse loginDataResponse = gson.fromJson(data, LoginDataResponse.class);
                            String token = loginDataResponse.getToken();
                            int uid = loginDataResponse.getUid();

                            saveStringToSp("token", token);
                            saveStringToSp("uid", String.valueOf(uid));

                            Intent intent = new Intent(LoginActivity.this,UserInterfaceActivity.class);

                            startActivity(intent);
                            finish();

//
                        } else {
                            String msg = loginResponse.getMsg();
                            ToastUtils.showTextToast2(LoginActivity.this,msg);
                        }
                    }
                });

            }
        });
    }

    protected void saveStringToJpush(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("loginactivity","onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("loginactivity","onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("loginactivity","onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("loginactivity","onStop");

    }

    @Override
    protected void onPause() {

        super.onPause();
    }
}
