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
     * ??????for????????????Button???????????????
     * ??????????????????????????????button?????????
     *
     * @return
     */
    public int getButtonPadding() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // ????????????????????????
        float density = dm.density;         // ????????????
        int buttonPadding = (int) (10 * density);
        return buttonPadding;
    }

    /**
     * ??????for????????????Button???????????????
     * ??????????????????????????????button?????????
     *
     * @return
     */
    public int getButtonHeight() {
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // ????????????????????????
        float density = dm.density;         // ????????????
        int buttonHeight = (int) (36 * density);
        return buttonHeight;
    }

    /**
     * ??????for????????????Button???????????????
     * ??????????????????????????????button???margin
     *
     * @return
     */
    public int getMarginRight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // ????????????????????????
        float density = dm.density;         // ????????????
        int buttonMarginRight = (int) (19 * density);
        return buttonMarginRight;
    }

    protected String getTokenToSp(String key, String val) {
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "??????token");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "??????uid");


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
            Log.d("for????????????buttons??????", String.valueOf(k));
            buttonList.get(k).setBackgroundResource(R.drawable.home_fragment_btn_title_background2);
            buttonList.get(k).setTextColor(Color.parseColor("#998fa2"));

        }
        btn.setBackgroundResource(R.drawable.home_fragment_btn_title_background1);
        btn.setTextColor(Color.parseColor("#ffffff"));
    }

    public void getResHosList(String url) {


        //1.??????okhttp??????
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.??????request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
                .build();
        //3.???request?????????call
        Call call = okHttpClient.newCall(request);
        //4.??????call
//        ????????????
//        Response response = call.execute();

        //????????????
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   " + e.getMessage());
                e.printStackTrace();

               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(PushSetActivity.this,"??????????????????");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                String res = response.body().string();
                L.e(res);


                //??????java??????


                final ArrayList<String> nameBeans = new ArrayList<>();
                final ArrayList<String> hospitalidBeans = new ArrayList<>();
                final HospitalListResponsePushSet hospitalListResponsePushSet = new HospitalListResponsePushSet();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //???????????????
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
                         * ???????????????????????????
                         * ??????for????????????button??????
                         */
                        if (hospitalListResponsePushSet.getCode() == 0){

                            String[] hosid = hospitalidBeans.toArray(new String[hospitalidBeans.size()]);
                            Log.d("hosid1", Arrays.toString(hosid));


//                            String[] allButton = {"??????"};
                            String[] stringArray = nameBeans.toArray(new String[nameBeans.size()]);
//                            String[] nameButton = new String[allButton.length + stringArray.length];
//                            System.arraycopy(allButton, 0, nameButton, 0, allButton.length);
//                            System.arraycopy(stringArray, 0, nameButton, allButton.length, stringArray.length);
                            buttons = new Button[stringArray.length];

                            Log.d("stringArray", Arrays.toString(stringArray));

                            for (int i = 0; i < stringArray.length; i++) {
                                //????????????button????????????
                                buttons[i] = new Button(PushSetActivity.this);
                                buttons[i].setBackgroundResource(R.drawable.home_fragment_btn_title_background2);
                                buttons[i].setText(stringArray[i]);//??????button???text??????
                                buttons[i].setId(Integer.parseInt(hosid[i]));//??????button???id
                                buttons[i].setTextSize(12);//??????button???????????????
                                buttons[i].setTextColor(Color.parseColor("#998fa2"));
                                buttons[i].setTypeface(Typeface.DEFAULT_BOLD);//??????button?????????????????????
                                buttons[i].setGravity(Gravity.CENTER);//??????button???????????????
                                buttons[i].setEnabled(true);
                                buttons[i].setPadding(getButtonPadding(), 0, getButtonPadding(), 0);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getButtonHeight());//??????button??????
                                layoutParams.setMargins(0, 0, getMarginRight(), 0);//4???????????????????????????????????????
                                buttons[i].setLayoutParams(layoutParams);//???????????????????????????button????????????button
                                mLinearTitles.addView(buttons[i]);//??????button???view???mLinearLayoutTitles?????????

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
                            dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(PushSetActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);

//                            ToastUtils.showTextToast(getContext(),"??????????????????????????????????????????????????????!");
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


        //1.??????okhttp??????
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.??????request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
                .build();
        //3.???request?????????call
        Call call = okHttpClient.newCall(request);
        //4.??????call
//        ????????????
//        Response response = call.execute();

        //????????????
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   " + e.getMessage());
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(PushSetActivity.this,"??????????????????");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                String res = response.body().string();
                L.e(res);

                Log.d("asdas4daw", res);
                //??????java??????


                final PushSetResponse pushSetResponse = new PushSetResponse();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //???????????????
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
                         * Glide??????????????????,???????????????????????????????????????????????????????????????????????????
                         */
                        Glide.with(PushSetActivity.this).load(dataBean.getHosHeadpic())
                                .error(R.drawable.push_set_hospital_pic_icon)//???????????????????????????
                                .fallback(R.drawable.push_set_hospital_pic_icon)//url???????????????,???????????????
                                .placeholder(R.drawable.push_set_hospital_pic_icon)//??????????????????????????????
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
                        dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
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
        //1.??????okhttp??????
        OkHttpClient okHttpClient = new OkHttpClient();


        //2.??????request
        //2.1??????requestbody

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
        //3.???request?????????call
        Call call =   okHttpClient.newCall(request);
        L.e(String.valueOf(call));
        //4.??????call
//        ????????????
//        Response response = call.execute();

        //????????????
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("OnFailure   "+e.getMessage());
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast2(PushSetActivity.this,"??????????????????");
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


                            ToastUtils.showTextToast(PushSetActivity.this,"??????????????????");
                            Log.d("swithpush",switchPushResponse.getMsg());

//


                        } else if (switchPushResponse.getCode() == 10006){

                            ToastUtils.showTextToast(PushSetActivity.this,"????????????");
                        }else if (switchPushResponse.getCode() == 10010 || switchPushResponse.getCode()==10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(PushSetActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
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