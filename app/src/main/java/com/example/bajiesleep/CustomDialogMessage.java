package com.example.bajiesleep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bajiesleep.entity.EquipmentResponse;
import com.example.bajiesleep.entity.HospitalListResponse1;
import com.example.bajiesleep.entity.LoginDataResponse;
import com.example.bajiesleep.entity.LoginResponse;
import com.example.bajiesleep.entity.MessageResponse;
import com.example.bajiesleep.entity.SendMessageResponse;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter;
import com.example.bajiesleep.util.TokenCodeUtil;
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

import static android.content.Context.MODE_PRIVATE;


public class CustomDialogMessage extends Dialog {
    private TextView mTvScroll,mTvPhoneNumber;
    private Button mBtnCancel,mBtnSend;
    private Context context;
    private BaseActivity baseActivity;
    private String phoneNumber;
    private LinearLayout linearLayout;

//    String[] name = {"模版一","模版二","模版三"};

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomDialogMessage setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    private Context resources;

    private ArrayList<Integer> ids = new ArrayList<>();

//    String[] name1=nameBeans.toArray(new String[nameBeans.size()]);
    private Button[] buttons;

    public CustomDialogMessage(@NonNull Context context) {
        super(context);

        this.context =context;
    }

    public CustomDialogMessage(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context =context;
    }



    protected String getTokenToSp(String key, String val) {

        SharedPreferences sp1 = context.getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "没有token");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 =context.getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "没有uid");


        return uid;
    }

    public int getButtonWidth(){
        WindowManager wm = getWindow().getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;
        int buttonWidth = (int) (65*density);
        return buttonWidth;
    }

    public int getButtonHeight(){
        WindowManager wm = getWindow().getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;
        int buttonHeight = (int) (30*density);
        return buttonHeight;
    }

    public int getMarginRight(){
        WindowManager wm = getWindow().getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;
        int buttonMarginRight = (int) (17*density);
        return buttonMarginRight;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog_layout);
        mTvScroll = findViewById(R.id.tv_scroll);
        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnSend =findViewById(R.id.btn_send);
        linearLayout =findViewById(R.id.dialog_liner);
        mTvScroll.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvPhoneNumber = findViewById(R.id.tv_message_phone_number);

        getResMessage(Api.URL+"/v1/Jms/model");

        if (!TextUtils.isEmpty(phoneNumber)){
            mTvPhoneNumber.setText(phoneNumber);
        }

        mTvPhoneNumber.setVisibility(View.GONE);


    }



    private void setEnable(Button btn,int i){
        List<Button> buttonList = new ArrayList(Arrays.asList(buttons));
        if (buttonList.size()==0){
            buttonList.add(buttons[i]);
        }

        for (int k=0;k<buttonList.size();k++){
            Log.d("for循环新的buttons数组", String.valueOf(k));
            buttonList.get(k).setBackgroundResource(R.drawable.home_fragment_btn_title_background2);
            buttonList.get(k).setTextColor(Color.parseColor("#998fa2"));
        }

        btn.setBackgroundResource(R.drawable.home_fragment_btn_title_background1);
        btn.setTextColor(Color.parseColor("#ffffff"));
    }


    public void getResMessage(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token",getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
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
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        L.e(res);
                        ToastUtils.showTextToast(context,"网络请求错误");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                String res = response.body().string();
                Log.d("messageres",res);

                //封装java对象
                 final ArrayList<String> nameBeans = new ArrayList<>();
                 final ArrayList<String> contentBeans = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONArray data = jsonObject.optJSONArray("data");
                    String msg = jsonObject.optString("msg");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject1 = data.getJSONObject(i);

                        if (jsonObject1 != null) {
                            String name = jsonObject1.optString("name");
                            String content = jsonObject1.optString("content");
                            Integer id = jsonObject1.optInt("id");
                            MessageResponse.DataBean dataBean = new MessageResponse.DataBean();
                            dataBean.setName(name);
                            dataBean.setContent(content);
                            dataBean.setId(id);
                            nameBeans.add(name);
                            contentBeans.add(content);
                            ids.add(id);



                        }

                    }
                    Log.d("name1", String.valueOf(ids));



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String[] nameButton = nameBeans.toArray(new String[nameBeans.size()]);
                        final String[] contents = contentBeans.toArray(new String[3]);
                        final Integer[] id =  ids.toArray(new Integer[ids.size()]);
                        Log.d("ids", Arrays.toString(id));

                        Log.d("contents", Arrays.toString(contents));

                        buttons = new Button[nameButton.length];
                        for(int i=0;i<nameButton.length;i++){

                            buttons[i] = new Button(getContext());
                            buttons[i].setText(nameButton[i]);
                            buttons[i].setId(id[i]);
                            buttons[i].setTextSize(10);
                            buttons[i].setTypeface(Typeface.DEFAULT_BOLD);

                            buttons[i].setGravity(Gravity.CENTER);

                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getButtonWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
//                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0,0,getMarginRight(),0);//4个参数按顺序分别是左上右下
                            buttons[i].setLayoutParams(layoutParams);

                            linearLayout.addView(buttons[i]);

                            final int finalI = i;
                            buttons[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    setEnable(buttons[finalI],finalI);
                                    final int k;
                                    k = buttons[finalI].getId();

                                    final String l = String.valueOf(buttons[finalI].getId());
                                    Log.d("k_id", String.valueOf(k));
                                    mTvScroll.setText(contents[finalI]);


                                    mBtnSend.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            getResSendMessage(Api.URL+"/v1/Jms/sendmod?mod_id="+buttons[finalI].getId()+"&mobile="+phoneNumber);
                                            dismiss();
                                        }
                                    });
                                }
                            });



                        }
                        buttons[0].performClick();




                        mBtnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dismiss();
                            }
                        });
                    }
                });

            }


        });
    }


//    protected void postResSend(String url, HashMap<String, String> map) {
//        //1.拿到okhttp对象
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//
//        //2.构造request
//        //2.1构造requestbody
//
//        HashMap<String, Object> params = new HashMap<String, Object>();
//
//        Log.e("params:",String.valueOf(params));
//        Set<String> keys = map.keySet();
//        for (String key:keys)
//        {
//            params.put(key,map.get(key));
//
//        }
//
//
//        JSONObject jsonObject = new JSONObject(params);
//        String jsonStr = jsonObject.toString();
//
//        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonStr);
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBodyJson)
//                .addHeader("token",getTokenToSp("token",""))
//                .addHeader("uid",getUidToSp("uid",""))
//                .build();
//        //3.将request封装为call
//        Call call =   okHttpClient.newCall(request);
//        L.e(String.valueOf(call));
//        //4.执行call
////        同步执行
////        Response response = call.execute();
//
//        //异步执行
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                L.e("OnFailure   "+e.getMessage());
//                e.printStackTrace();
//
//
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        L.e(res);
//                        ToastUtils.showTextToast(context,"网络请求错误");
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                L.e("OnResponse");
//                final String res = response.body().string();
//                Log.d("send",res);
//
//                ((Activity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        L.e(res);
//
//                        Gson gson = new Gson();
//
//                        SendMessageResponse sendMessageResponse = gson.fromJson(res, SendMessageResponse.class);
//                        if (sendMessageResponse.getCode() == 0) {
//                            ToastUtils.showTextToast(context,"发送成功");
//
//                        }else {
//                            ToastUtils.showTextToast(context,"发送失败  请重试");
//                        }
//                    }
//                });
//
//            }
//        });
//    }
//
//
//    private void sendMessage(String mobile, String mod_id) {
//
//        HashMap<String,String> map = new HashMap<>();
//        map.put("mobile",mobile);//18158188052
//        map.put("mod_id",mod_id);//111
////        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);
//        String url = "https://transmit.daoqihz.com/v1/Jms/sendmod";
//
//        Log.e("tags", String.valueOf(map));
//
//
//
//        postResSend(url,map);
////       navigateTo(SecondActivity3.class);
//
//    }

    private void getResSendMessage(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token", ""))
                .addHeader("uid", getUidToSp("uid", ""))
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
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast(getContext(),"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);



                //封装java对象

                final SendMessageResponse sendMessageResponse = new SendMessageResponse();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");
                    //第一层封装
                    sendMessageResponse.setCode(code);
                    sendMessageResponse.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();





                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


//                        Log.d("sendMessageResponse",sendMessageResponse.getMsg());
                        if (sendMessageResponse.getCode() == 0) {
                            ToastUtils.showTextToast(context,"发送成功");

                        }else {
                            ToastUtils.showTextToast(context,"发送失败,请重试!");
                        }



//                        mRecyclerInfo.setLinearLayout();




                    }
                });

            }


        });



    }

}
