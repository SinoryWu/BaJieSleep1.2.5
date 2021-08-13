package com.example.bajiesleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.bajiesleep.entity.HospitalListResponsePushSet;
import com.example.bajiesleep.entity.PushSetResponse;
import com.example.bajiesleep.entity.SwitchPushResponse;
import com.example.bajiesleep.util.GetShp;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PushSetActivity extends AppCompatActivity {
    private LinearLayout linearLeft,mLinearTitles;
    private ImageView mIvPushDeviceFalse, mIvPushRingFalse, mIvPushReportFalse,mIvPushDeviceTrue, mIvPushRingTrue, mIvPushReportTrue,mIvPushHosPic;
    public Button[] buttons;
    public int k;
    public String devicePush,ringPush,reportPush;


    String devOnline,ringOnline,getReport;
    PushSetResponse.DataBean dataBean = new PushSetResponse.DataBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_set);



        linearLeft = findViewById(R.id.push_set_liner_left);
        mIvPushDeviceFalse =findViewById(R.id.push_set_iv_device_false);
        mIvPushRingFalse =findViewById(R.id.push_set_iv_ring_false);
        mIvPushReportFalse =findViewById(R.id.push_set_iv_report_false);

        mIvPushDeviceTrue = findViewById(R.id.push_set_iv_device_true);
        mIvPushRingTrue = findViewById(R.id.push_set_iv_ring_true);
        mIvPushReportTrue = findViewById(R.id.push_set_iv_report_true);

        mLinearTitles = findViewById(R.id.push_set_horizontal_linear);

        mIvPushHosPic = findViewById(R.id.push_set_iv_hospital_icon);


        getResHosList(Api.URL+"/v1/hospital/resHosList");






        linearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /**
     * 动态for循环生成Button的屏幕适配
     * 根据屏幕的密度来设置button的宽度
     *
     * @return
     */
    public int getButtonPadding() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度
        int buttonPadding = (int) (10 * density);
        return buttonPadding;
    }

    /**
     * 动态for循环生成Button的屏幕适配
     * 根据屏幕的密度来设置button的高度
     *
     * @return
     */
    public int getButtonHeight() {
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度
        int buttonHeight = (int) (36 * density);
        return buttonHeight;
    }

    /**
     * 动态for循环生成Button的屏幕适配
     * 根据屏幕的密度来设置button的margin
     *
     * @return
     */
    public int getMarginRight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度
        int buttonMarginRight = (int) (19 * density);
        return buttonMarginRight;
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

    public void cleanTokenToSp(String key, String val){
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    public void savePushHosIdToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("pushHosId", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getPushHosIdToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("pushHosId", MODE_PRIVATE);
        String hosid = sp.getString("pushHosId", "");
        return hosid;
    }

    private void setBackground(Button btn, int i) {
        List<Button> buttonList = new ArrayList(Arrays.asList(buttons));
        if (buttonList.size() == 0) {

            buttonList.add(buttons[i]);

        }

        for (int k = 0; k < buttonList.size(); k++) {
            Log.d("for循环新的buttons数组", String.valueOf(k));
            buttonList.get(k).setBackgroundResource(R.drawable.home_fragment_btn_title_background2);
            buttonList.get(k).setTextColor(Color.parseColor("#998fa2"));

        }
        btn.setBackgroundResource(R.drawable.home_fragment_btn_title_background1);
        btn.setTextColor(Color.parseColor("#ffffff"));
    }

    public void getResHosList(String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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
                        ToastUtils.showTextToast2(PushSetActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                String res = response.body().string();
                L.e(res);


                //封装java对象


                final ArrayList<String> nameBeans = new ArrayList<>();
                final ArrayList<String> hospitalidBeans = new ArrayList<>();
                final HospitalListResponsePushSet hospitalListResponsePushSet = new HospitalListResponsePushSet();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONArray data = jsonObject.optJSONArray("data");
                    String msg = jsonObject.optString("msg");

                    hospitalListResponsePushSet.setCode(code);
                    hospitalListResponsePushSet.setMsg(msg);


                    if (data != null){
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);

                            if (jsonObject1 != null) {
                                String hospitalid = jsonObject1.optString("hospitalid");
                                String name = jsonObject1.optString("name");
                                HospitalListResponsePushSet.DataBean dataBean = new HospitalListResponsePushSet.DataBean(hospitalid, name);
                                dataBean.setName(name);
                                dataBean.setHospitalid(hospitalid);
                                nameBeans.add(name);
                                hospitalidBeans.add(hospitalid);
                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 创建医院名称的按钮
                         * 根据for循环创建button数组
                         */
                        if (hospitalListResponsePushSet.getCode() == 0){

                            String[] hosid = hospitalidBeans.toArray(new String[hospitalidBeans.size()]);
                            Log.d("hosid1", Arrays.toString(hosid));


//                            String[] allButton = {"全部"};
                            String[] stringArray = nameBeans.toArray(new String[nameBeans.size()]);
//                            String[] nameButton = new String[allButton.length + stringArray.length];
//                            System.arraycopy(allButton, 0, nameButton, 0, allButton.length);
//                            System.arraycopy(stringArray, 0, nameButton, allButton.length, stringArray.length);
                            buttons = new Button[stringArray.length];

                            Log.d("stringArray", Arrays.toString(stringArray));

                            for (int i = 0; i < stringArray.length; i++) {
                                //获取新的button数组实例
                                buttons[i] = new Button(PushSetActivity.this);
                                buttons[i].setBackgroundResource(R.drawable.home_fragment_btn_title_background2);
                                buttons[i].setText(stringArray[i]);//设置button的text名字
                                buttons[i].setId(Integer.parseInt(hosid[i]));//设置button的id
                                buttons[i].setTextSize(12);//设置button字体的大小
                                buttons[i].setTextColor(Color.parseColor("#998fa2"));
                                buttons[i].setTypeface(Typeface.DEFAULT_BOLD);//设置button字体样式为加粗
                                buttons[i].setGravity(Gravity.CENTER);//设置button内容为居中
                                buttons[i].setEnabled(true);
                                buttons[i].setPadding(getButtonPadding(), 0, getButtonPadding(), 0);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getButtonHeight());//设置button宽高
                                layoutParams.setMargins(0, 0, getMarginRight(), 0);//4个参数按顺序分别是左上右下
                                buttons[i].setLayoutParams(layoutParams);//将设置的宽高定义给button数组里的button
                                mLinearTitles.addView(buttons[i]);//添加button的view到mLinearLayoutTitles布局中

                                final int finalI = i;
                                Log.d("finalI", String.valueOf(finalI));

                                buttons[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        k = buttons[finalI].getId();
                                        String hospitalName = String.valueOf(buttons[finalI].getText());
                                        savePushHosIdToSp("pushHosId", String.valueOf(k));
                                        Log.d("btnIds", String.valueOf(k));

                                        setBackground(buttons[finalI], finalI);

                                        getResPushList(Api.URL+"/v2/pushManage/"+k);
                                    }
                                });
//                                buttons[i].setOnClickListener(new OnMultiClickListener() {
//                                    @Override
//                                    public void onMultiClick(View view) {
//
//
//
//
////
//
//                                    }
//                                });


                            }

                            buttons[0].performClick();
                        }else if(hospitalListResponsePushSet.getCode() == 10004 || hospitalListResponsePushSet.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(PushSetActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(PushSetActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);

//                            ToastUtils.showTextToast(getContext(),"您好，您的登陆信息已过期，请重新登陆!");
                        }
                        else {
                            ToastUtils.showTextToast(PushSetActivity.this,hospitalListResponsePushSet.getMsg());
                        }


                    }
                });

            }


        });
    }

    public void getResPushList(final String url) {


        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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
                        ToastUtils.showTextToast2(PushSetActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                String res = response.body().string();
                L.e(res);

                Log.d("asdas4daw", res);
                //封装java对象


                final PushSetResponse pushSetResponse = new PushSetResponse();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject data = jsonObject.optJSONObject("data");


                    pushSetResponse.setCode(code);
                    pushSetResponse.setMsg(msg);
                    pushSetResponse.setData(dataBean);

                    if (data!=null){
                        int devOnline = data.optInt("devOnline");
                        int ringOnline = data.optInt("ringOnline");
                        int getReport = data.optInt("getReport");
                        String hosHeadpic = data.optString("hosHeadpic");



                        dataBean.setDevOnline(devOnline);
                        dataBean.setRingOnline(ringOnline);
                        dataBean.setGetReport(getReport);
                        dataBean.setHosHeadpic(hosHeadpic);
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                    if (pushSetResponse.getCode() == 0){
                        if (dataBean.getDevOnline() == 1){
                            mIvPushDeviceFalse.setVisibility(View.GONE);
                            mIvPushDeviceTrue.setVisibility(View.VISIBLE);


                        }else if (dataBean.getDevOnline() == 0){
                            mIvPushDeviceFalse.setVisibility(View.VISIBLE);
                            mIvPushDeviceTrue.setVisibility(View.GONE);

                        }

                        if (dataBean.getRingOnline() == 1){
                            mIvPushRingFalse.setVisibility(View.GONE);
                            mIvPushRingTrue.setVisibility(View.VISIBLE);

                        }else if (dataBean.getRingOnline() == 0){
                            mIvPushRingFalse.setVisibility(View.VISIBLE);
                            mIvPushRingTrue.setVisibility(View.GONE);


                        }

                        if (dataBean.getGetReport() == 1){
                            mIvPushReportFalse.setVisibility(View.GONE);
                            mIvPushReportTrue.setVisibility(View.VISIBLE);

                        }else if (dataBean.getDevOnline() == 0){
                            mIvPushReportFalse.setVisibility(View.VISIBLE);
                            mIvPushReportTrue.setVisibility(View.GONE);

                        }
                        /**
                         * Glide异步加载图片,设置默认图片，加载错误时图片，加载成功前显示的图片
                         */
                        Glide.with(PushSetActivity.this).load(dataBean.getHosHeadpic())
                                .error(R.drawable.push_set_hospital_pic_icon)//异常时候显示的图片
                                .fallback(R.drawable.push_set_hospital_pic_icon)//url为空的时候,显示的图片
                                .placeholder(R.drawable.push_set_hospital_pic_icon)//加载成功前显示的图片
                                .into(mIvPushHosPic);
//                        if (dataBean.getHosHeadpic().equals("")){
//                            mIvPushHosPic.setImageResource(R.drawable.push_set_hospital_pic_icon);
//                        }else {

//                            if (null!=mIvPushHosPic.getDrawable()){
//                                Glide.with(PushSetActivity.this).load(dataBean.getHosHeadpic()).into(mIvPushHosPic);
//                            }else {
//                                mIvPushHosPic.setImageResource(R.drawable.push_set_hospital_pic_icon);
//                            }

//                        }
                        devicePush = String.valueOf(dataBean.getDevOnline());
                        ringPush = String.valueOf(dataBean.getRingOnline());
                        reportPush = String.valueOf(dataBean.getGetReport());

                        mIvPushDeviceFalse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                devicePush = "1";
                                mIvPushDeviceFalse.setVisibility(View.GONE);
                                mIvPushDeviceTrue.setVisibility(View.VISIBLE);

                                SwitchPushPut(devicePush,ringPush,reportPush);
                            }
                        });

                        mIvPushDeviceTrue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                devicePush = "0";
                                mIvPushDeviceFalse.setVisibility(View.VISIBLE);
                                mIvPushDeviceTrue.setVisibility(View.GONE);
                                SwitchPushPut(devicePush,ringPush,reportPush);
                            }
                        });

                        mIvPushRingFalse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ringPush = "1";
                                mIvPushRingFalse.setVisibility(View.GONE);
                                mIvPushRingTrue.setVisibility(View.VISIBLE);
                                SwitchPushPut(devicePush,ringPush,reportPush);
                            }
                        });

                        mIvPushRingTrue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ringPush = "0";
                                mIvPushRingTrue.setVisibility(View.GONE);
                                mIvPushRingFalse.setVisibility(View.VISIBLE);
                                SwitchPushPut(devicePush,ringPush,reportPush);
                            }
                        });

                        mIvPushReportFalse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                reportPush = "1";
                                mIvPushReportFalse.setVisibility(View.GONE);
                                mIvPushReportTrue.setVisibility(View.VISIBLE);
                                SwitchPushPut(devicePush,ringPush,reportPush);
                            }
                        });

                        mIvPushReportTrue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                reportPush = "0";
                                mIvPushReportTrue.setVisibility(View.GONE);
                                mIvPushReportFalse.setVisibility(View.VISIBLE);
                                SwitchPushPut(devicePush,ringPush,reportPush);
                            }
                        });


                    }else if(pushSetResponse.getCode() == 10010 || pushSetResponse.getCode() == 10004){
                        DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(PushSetActivity.this,R.style.CustomDialog);
                        dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                            @Override
                            public void OnConfirm(DialogTokenIntent dialog) {
                                Intent intent = new Intent(PushSetActivity.this,LoginActivity.class);
                                finish();
                                startActivity(intent);

                            }
                        }).show();

                        dialogTokenIntent.setCanceledOnTouchOutside(false);
                        dialogTokenIntent.setCancelable(false);
                    }




                    }
                });

            }


        });
    }


    private void SwitchPushPut( String devOnline,String ringOnline, String getReport) {

        HashMap<String, String> map = new HashMap<>();
        map.put("devOnline", devOnline);//111
        map.put("ringOnline", devOnline);
        map.put("getReport", devOnline);


//        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);
        String url = Api.URL+"/v2/pushManage/"+getPushHosIdToSp("pushHosId","")+"?devOnline="+devOnline+"&ringOnline="+ringOnline+"&getReport="+getReport;


        putResSwitchPush(url, map);




    }

    protected void putResSwitchPush(String url, HashMap<String, String> map) {
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

        Request request = new Request.Builder()
                .url(url)
                .put(requestBodyJson)
                .addHeader("token",getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
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
                        ToastUtils.showTextToast2(PushSetActivity.this,"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);
                Gson gson = new Gson();

                final SwitchPushResponse switchPushResponse = gson.fromJson(res, SwitchPushResponse.class);




               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (switchPushResponse.getCode() == 0) {


                            ToastUtils.showTextToast(PushSetActivity.this,"推送设置成功");
                            Log.d("swithpush",switchPushResponse.getMsg());

//


                        } else if (switchPushResponse.getCode() == 10006){

                            ToastUtils.showTextToast(PushSetActivity.this,"操作失败");
                        }else if (switchPushResponse.getCode() == 10010 || switchPushResponse.getCode()==10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(PushSetActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(PushSetActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }
                        else {
                            String msg = switchPushResponse.getMsg();
                            ToastUtils.showTextToast2(PushSetActivity.this,msg);
                        }
                    }
                });


            }
        });
    }


}