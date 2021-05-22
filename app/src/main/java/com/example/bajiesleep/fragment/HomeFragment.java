package com.example.bajiesleep.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bajiesleep.Api;
import com.example.bajiesleep.DialogTokenIntent;
import com.example.bajiesleep.HosListDialog;
import com.example.bajiesleep.L;
import com.example.bajiesleep.LoginActivity;
import com.example.bajiesleep.OnMultiClickListener;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.entity.DeviceListResponseV2;
import com.example.bajiesleep.entity.EquipmentResponse;
import com.example.bajiesleep.entity.HospitalListResponse1;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

//    java.util.Timer timer = new java.util.Timer(true);// 实例化一个Timer对象用于定时执行

    public String date;
    public LinearLayout mLinearTitles;
    public TextView mTvTotalDevice, mTvOnLineDevice;
    public ImageView mIvToTalDevice, mIvOnLineDevice;
    public RelativeLayout mRlSearchClean, mRlHomeHosList;
    public EditText mEtSearch;
    public PullLoadMoreRecyclerView mRecyclerInfo;

    private RelativeLayout mRlSleepSlices;
    private Button mBtnLoad;
    CharSequence chars;

    //    public String[] mTitles= {"全部","全部"};
    public Button[] buttons;
    public SwipeRefreshLayout refreshLayout;
    public List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
    DeviceListResponseV2.DataBeanXX.DataBeanX dataBeanX = new DeviceListResponseV2.DataBeanXX.DataBeanX();
    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();


    public List<DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean> dataBeansv2 = new ArrayList<>();


    String s;
    String hospitalsId ;
    public ListViewAdapter adapter;
    int d;
    public int t;
    public int k;
    public int performNum = 1;
    public Timer timer;
    Integer[] ids1;

    DialogTokenIntent dialogTokenIntent = null;

    /**
     * 动态for循环生成Button的屏幕适配
     * 根据屏幕的密度来设置button的宽度
     *
     * @return
     */
    public int getButtonPadding() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
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
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
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
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度
        int buttonMarginRight = (int) (19 * density);
        return buttonMarginRight;
    }

    protected String getTokenToSp(String key, String val) {
        SharedPreferences sp1 = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "");


        return uid;
    }

    public void cleanTokenToSp(String key, String val) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        sp.edit().clear().commit();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
//                adapter.notifyDataSetChanged();


                if (mEtSearch.getText().toString().equals("")) {
//
                    if (hospitalsId ==null) {


                        getResEquipmentNumber(Api.URL + "/v2/device?limit=10");
                    } else {

                        getResEquipmentHosIdtNumber(Api.URL + "/v2/device" + "?limit=" + 10 + "&hospitalid=" + hospitalsId);
                    }

                } else {
                    String text = String.valueOf(mEtSearch.getText().toString()).replaceAll(" ", "");
                    if (hospitalsId== null) {

                        getResEquipmentSearchNumber(Api.URL + "/v2/device?limit=10" + "&keywords=" + text);
                    } else {

                        getResEquipmentHosIdSearchNumber(Api.URL + "/v2/device?limit=10" + "&keywords=" + text + "&hospitalid=" + hospitalsId);
                    }

                }

            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("HomeFragment", "onCreateView");
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("asdasdasda", getTokenToSp("token",""));
        Log.d("asdasdasda", getUidToSp("uid",""));
        mLinearTitles = getActivity().findViewById(R.id.home_fragment_horizontal_linear);
        mRecyclerInfo = getActivity().findViewById(R.id.rv_info);
        mTvTotalDevice = getActivity().findViewById(R.id.home_total_device);
        mTvOnLineDevice = getActivity().findViewById(R.id.home_online_device);
        mIvToTalDevice = getActivity().findViewById(R.id.home_iv_total_device);
        mIvOnLineDevice = getActivity().findViewById(R.id.home_iv_online_device);
        mEtSearch = getActivity().findViewById(R.id.home_search);
        mRlSearchClean = getActivity().findViewById(R.id.home_search_clean);
        mRlHomeHosList = getActivity().findViewById(R.id.home_fragment_hoslist);
        mRlSearchClean.setVisibility(View.GONE);
        mRlSleepSlices = getActivity().findViewById(R.id.home_fragment_sleep_slices);
        mBtnLoad = getActivity().findViewById(R.id.sleep_slices_btn_home);
        if (getTokenToSp("token", "").isEmpty()) {
            s = "1";
        } else {
            s = "2";
        }

        mRlSleepSlices.setVisibility(View.GONE);
        mBtnLoad.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                mRlSleepSlices.setVisibility(View.GONE);
                mRecyclerInfo.setVisibility(View.VISIBLE);
                getResEquipmentNumber(Api.URL + "/v2/device" + "?limit=" + 10);

                timer = new Timer();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        t++;
                        if (t >= 30) {
                            Message message = new Message();
                            message.what = 0;
                            mHandler.sendMessage(message);
                            t = 0;
                        }
                        Log.d("timtttttttt", String.valueOf(t));
                    }
                }, 0, 1000);
            }
        });


        if (s.equals("1")) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            ((Activity) getContext()).finish();

        } else if (s.equals("2")) {
            getResEquipmentNumber(Api.URL + "/v2/device" + "?limit=" + 10);

            timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    t++;
                    if (t >= 30) {
                        Message message = new Message();
                        message.what = 0;
                        mHandler.sendMessage(message);
                        t = 0;
                    }
                    Log.d("timtttttttt", String.valueOf(t));
                }
            }, 0, 1000);
        }

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = String.valueOf(charSequence);
                chars = charSequence;
                if (text.equals("")) {
                    mRlSearchClean.setVisibility(View.GONE);
                } else {
                    mRlSearchClean.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mRlSearchClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mRlSearchClean.setVisibility(View.GONE);
                mEtSearch.setText("");
                View v = getActivity().getCurrentFocus();




                if (hospitalsId== null) {
                    Log.d("asd23swd", "null1");

                    getResEquipmentNumber(Api.URL + "/v2/device?limit=10");

//                    getResEquipmentTotal(Api.URL + "/v2/device?limit=10");
                } else {
                    Log.d("asd23swd", "null");
                    Log.d("asd23swd", String.valueOf(performNum));
                    getResEquipmentHosIdtNumber(Api.URL + "/v2/device?limit=10" + "&hospitalid=" + hospitalsId);
                }

                if (v == null) {
                    Log.d("asd23swd", "kong");
                } else {
                    Log.d("asd23swd", "bukong");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                    }
                    v.clearFocus();
                }

            }
        });

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String text = String.valueOf(textView.getText().toString()).replaceAll(" ", "");

                if (i == EditorInfo.IME_ACTION_SEARCH) {//EditorInfo.IME_ACTION_SEARCH、EditorInfo.IME_ACTION_SEND等分别对应EditText的imeOptions属性

                    View v = getActivity().getCurrentFocus();

                    if (hospitalsId == null) {

//                        Log.d("23ad", "o");
//                        adapter.notifyDataSetChanged();


                        getResEquipmentSearchNumber(Api.URL + "/v2/device?limit=10&keywords="+text);

                    } else {
                        Log.d("23ad", "p");

                        getResEquipmentHosIdSearchNumber(Api.URL + "/v2/device?limit=10&keywords=" + text + "&hospitalid=" + hospitalsId);

                    }

                    if (v == null) {
                        Log.d("mEtSearch GETTOKEN", "kong");
                    } else {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm.isActive()) {
                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        }
                        v.clearFocus();
                    }

                }
                return false;
            }
        });


        mRlHomeHosList.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                final HosListDialog hosListDialog = new HosListDialog(getContext(), R.style.dialog);
                hosListDialog.setTitle("选择机构").setConfirm("确定", new HosListDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(HosListDialog dialog, String hospitalName, String hospitalId) {
                        mEtSearch.setHint("搜索 " + hospitalName);

                        hospitalsId = hospitalId;

                        String text = String.valueOf(mEtSearch.getText().toString()).replaceAll(" ", "");
//                        Log.d("asdas23", hospitalId);
                        if (hospitalId != null) {
//                            adapter.notifyDataSetChanged();
                            mEtSearch.setText("");
                            mRlSearchClean.setVisibility(View.GONE);
                            dataBeansv2.clear();
                            getResEquipmentHosIdtNumber(Api.URL + "/v2/device?hospitalid=" + hospitalId + "&limit=10");
                        }



                    }
                }).setCancel("取消", new HosListDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(HosListDialog dialog) {

                    }
                }).show();
//                hosListDialog.show();
            }
        });


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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象


                final ArrayList<String> nameBeans = new ArrayList<>();
                final ArrayList<String> hospitalidBeans = new ArrayList<>();
                final HospitalListResponse1 hospitalListResponse1 = new HospitalListResponse1();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONArray data = jsonObject.optJSONArray("data");
                    String msg = jsonObject.optString("msg");

                    hospitalListResponse1.setCode(code);
                    hospitalListResponse1.setMsg(msg);

                    if (data != null) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);

                            if (jsonObject1 != null) {
                                String hospitalid = jsonObject1.optString("hospitalid");
                                String name = jsonObject1.optString("name");
                                HospitalListResponse1.DataBean dataBean = new HospitalListResponse1.DataBean(hospitalid, name);
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 创建医院名称的按钮
                         * 根据for循环创建button数组
                         */


                        if (hospitalListResponse1.getCode() == 0) {
                            String[] allid = {"0"};
                            String[] hosid1 = hospitalidBeans.toArray(new String[hospitalidBeans.size()]);
                            String[] hosid = new String[allid.length + hosid1.length];
                            System.arraycopy(allid, 0, hosid, 0, allid.length);
                            System.arraycopy(hosid1, 0, hosid, allid.length, hosid1.length);
//                            Log.d("hosid", Arrays.toString(hosid));

                            String[] allButton = {"全部"};
                            String[] stringArray = nameBeans.toArray(new String[nameBeans.size()]);
                            String[] nameButton = new String[allButton.length + stringArray.length];
                            System.arraycopy(allButton, 0, nameButton, 0, allButton.length);
                            System.arraycopy(stringArray, 0, nameButton, allButton.length, stringArray.length);
                            buttons = new Button[nameButton.length];

//                            Log.d("stringArray", Arrays.toString(stringArray));

                            for (int i = 0; i < nameButton.length; i++) {
                                //获取新的button数组实例
                                buttons[i] = new Button(getContext());
                                buttons[i].setBackgroundResource(R.drawable.home_fragment_btn_title_background2);
                                buttons[i].setText(nameButton[i]);//设置button的text名字
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

                                buttons[i].setOnClickListener(new OnMultiClickListener() {
                                    @Override
                                    public void onMultiClick(View view) {
                                        k = buttons[finalI].getId();


                                        Log.d("btnID", String.valueOf(k));
                                        setBackground(buttons[finalI], finalI);
                                        dataBeansv2.clear();
                                        adapter.notifyDataSetChanged();

//                                    adapter.notifyDataSetChanged();
//

//                                        getResEquipment4(Api.URL + "/v2/device"  + "?limit=" +15+ "&hospitalid=" + k);

                                        t = 0;
                                    }
                                });


                            }

                            buttons[0].performClick();
                        } else if (hospitalListResponse1.getCode() == 10004 || hospitalListResponse1.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }

                        } else {
                            ToastUtils.showTextToast(getContext(), hospitalListResponse1.getMsg());
                        }


                    }
                });

            }


        });
    }

    private void getResEquipmentHosIdtNumber(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                            @Override
                            public void onRefresh() {
//                                adapter.notifyDataSetChanged();
                                dataBeansv2.clear();


//                                    mRecyclerInfo.setPullLoadMoreCompleted();
                                getResEquipmentHosIdtNumber(Api.URL + "/v2/device" + "?limit=" + 10 + "&hospitalid=" + hospitalsId);


                            }

                            @Override
                            public void onLoadMore() {

                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");

                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");

                        if (dataxx != null) {
                            deviceListResponseV2.setOnlineNum(onlineNum);
                            deviceListResponseV2.setTotalNum(totalNum);
                            dataBeanXX.setOnlineNum(onlineNum);
                            dataBeanXX.setTotalNum(totalNum);
                            dataBeanXX.setData(dataBeanX);

                            int current_page = datax.optInt("current_page");
                            String first_page_url = datax.optString("first_page_url");
                            int from = datax.optInt("from");
                            int last_page = datax.optInt("last_page");
                            String last_page_url = datax.optString("last_page_url");
                            String next_page_url = datax.optString("next_page_url");
                            String path = datax.optString("path");
                            int per_page = datax.optInt("per_page");
                            String prev_page_url = datax.optString("prev_page_url");
                            int to = datax.optInt("to");
                            int total = datax.optInt("total");
                            JSONArray data = datax.optJSONArray("data");

                            dataBeanX.setCurrent_page(current_page);
                            dataBeanX.setFirst_page_url(first_page_url);
                            dataBeanX.setFrom(from);
                            dataBeanX.setLast_page(last_page);
                            dataBeanX.setLast_page_url(last_page_url);
                            dataBeanX.setNext_page_url(next_page_url);
                            dataBeanX.setPath(path);
                            dataBeanX.setPer_page(per_page);
                            dataBeanX.setPrev_page_url(prev_page_url);
                            dataBeanX.setTo(to);
                            dataBeanX.setTotal(total);
                            dataBeanX.setData(dataBeansv2);

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                if (jsonObject1 != null) {

                                    int outTime = jsonObject1.optInt("outTime");
                                    int breathrate = jsonObject1.optInt("breathrate");
                                    int heartrate = jsonObject1.optInt("heartrate");
                                    int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                    int tempetature = jsonObject1.optInt("tempetature");
                                    String ringsn = jsonObject1.optString("ringsn");
                                    int battery = jsonObject1.optInt("battery");
                                    int devStatus = jsonObject1.optInt("devStatus");
                                    int ringStatus = jsonObject1.optInt("ringStatus");
                                    int powerStatus = jsonObject1.optInt("powerStatus");
                                    String versionno = jsonObject1.optString("versionno");
                                    String swversion = jsonObject1.optString("swversion");
                                    String sim = jsonObject1.optString("sim");
                                    int reportStatus = jsonObject1.optInt("reportStatus");
                                    int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                    String truename = jsonObject1.optString("truename");
                                    String telephone = jsonObject1.optString("telephone");
                                    String sn = jsonObject1.optString("sn");
                                    int status = jsonObject1.optInt("status");
                                    String modeType = jsonObject1.optString("modeType");


                                    DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                    dataBean.setOutTime(outTime);
                                    dataBean.setBreathrate(breathrate);
                                    dataBean.setBloodoxygen(bloodoxygen);
                                    dataBean.setTempetature(tempetature);
                                    dataBean.setRingsn(ringsn);
                                    dataBean.setPowerStatus(powerStatus);
                                    dataBean.setVersionno(versionno);
                                    dataBean.setSwversion(swversion);
                                    dataBean.setSim(sim);
                                    dataBean.setReportStatus(reportStatus);
                                    dataBean.setLastUpdateTime(lastUpdateTime);
                                    dataBean.setTruename(truename);
                                    dataBean.setTelephone(telephone);
                                    dataBean.setSn(sn);
                                    dataBean.setStatus(status);
                                    dataBean.setBattery(battery);
                                    dataBean.setHeartrate(heartrate);
                                    dataBean.setDevStatus(devStatus);
                                    dataBean.setRingStatus(ringStatus);
                                    dataBean.setModeType(modeType);
                                    dataBeansv2.add(dataBean);

                                }

                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {


                            Log.d("asdhkhasd", String.valueOf(performNum));


                            mRecyclerInfo.setPullLoadMoreCompleted();
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");

                            mTvTotalDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    performNum = 1;
                                    t = 0;
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                    dataBeansv2.clear();

//                                    adapter.notifyDataSetChanged();
                                    getResEquipmentHosIdTotal(Api.URL + "/v2/device?hospitalid=" + hospitalsId + "&limit=10");
                                }
                            });

                            mTvOnLineDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    t = 0;
                                    performNum = 2;
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
                                    dataBeansv2.clear();
//                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
                                    getResEquipmentHosIdOnline(Api.URL + "/v2/device?hospitalid=" + hospitalsId + "&devStatus=1&limit=10");
                                }
                            });
                            Log.d("performNum", String.valueOf(performNum));

                            if (performNum == 1) {
                                performNum = 1;
                                t = 0;
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                dataBeansv2.clear();

//                                    adapter.notifyDataSetChanged();
                                getResEquipmentHosIdTotal(Api.URL + "/v2/device?hospitalid=" + hospitalsId + "&limit=10");
                            } else if (performNum == 2) {
                                t = 0;
                                performNum = 2;
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
                                dataBeansv2.clear();
//                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
                                getResEquipmentHosIdOnline(Api.URL + "/v2/device?hospitalid=" + hospitalsId + "&devStatus=1&limit=10");
                            }


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentHosIdTotal(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);



                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");


                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {


                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);
//                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
//                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");

                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {

                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentHosIdOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10 + "&hospitalid=" + hospitalsId);


                                }

                                @Override
                                public void onLoadMore() {

//                                    adapter.notifyDataSetChanged();
                                    getResEquipmentHosIdOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1) + "&hospitalid=" + hospitalsId);



                                }
                            });

//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentHosIdOnline(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析


                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");


                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {
                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);
//                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
//                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");
                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {

                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentHosIdOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10 + "&hospitalid=" + hospitalsId + "&devStatus=1");
                                }

                                @Override
                                public void onLoadMore() {
                                    getResEquipmentHosIdOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1) + "&hospitalid=" + hospitalsId + "&devStatus=1");
                                }
                            });


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentHosIdOnLoad(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析
                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");


                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                        if (deviceListResponseV2.getCode() == 0) {
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");
                            adapter.notifyDataSetChanged();

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


                    }
                });


            }


        });


    }

    private void getResEquipmentHosIdOnRefresh(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        mRecyclerInfo.setPullLoadMoreCompleted();
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");


                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (deviceListResponseV2.getCode() == 0) {
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");
                            adapter.notifyDataSetChanged();

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }

                    }
                });


            }


        });


    }


    private void getResEquipmentHosIdSearchNumber(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                            @Override
                            public void onRefresh() {
//                                adapter.notifyDataSetChanged();
                                dataBeansv2.clear();


                                String text = String.valueOf(mEtSearch.getText().toString()).replaceAll(" ", "");
                                getResEquipmentHosIdSearchNumber(Api.URL + "/v2/device?limit=10" + "&keywords=" + text + "&hospitalid=" + hospitalsId);


                            }

                            @Override
                            public void onLoadMore() {

                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");

                        if (dataxx != null) {
                            deviceListResponseV2.setTotalNum(totalNum);
                            deviceListResponseV2.setOnlineNum(onlineNum);
                            dataBeanXX.setOnlineNum(onlineNum);
                            dataBeanXX.setTotalNum(totalNum);
                            dataBeanXX.setData(dataBeanX);

                            int current_page = datax.optInt("current_page");
                            String first_page_url = datax.optString("first_page_url");
                            int from = datax.optInt("from");
                            int last_page = datax.optInt("last_page");
                            String last_page_url = datax.optString("last_page_url");
                            String next_page_url = datax.optString("next_page_url");
                            String path = datax.optString("path");
                            int per_page = datax.optInt("per_page");
                            String prev_page_url = datax.optString("prev_page_url");
                            int to = datax.optInt("to");
                            int total = datax.optInt("total");
                            JSONArray data = datax.optJSONArray("data");

                            dataBeanX.setCurrent_page(current_page);
                            dataBeanX.setFirst_page_url(first_page_url);
                            dataBeanX.setFrom(from);
                            dataBeanX.setLast_page(last_page);
                            dataBeanX.setLast_page_url(last_page_url);
                            dataBeanX.setNext_page_url(next_page_url);
                            dataBeanX.setPath(path);
                            dataBeanX.setPer_page(per_page);
                            dataBeanX.setPrev_page_url(prev_page_url);
                            dataBeanX.setTo(to);
                            dataBeanX.setTotal(total);
                            dataBeanX.setData(dataBeansv2);

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                if (jsonObject1 != null) {

                                    int outTime = jsonObject1.optInt("outTime");
                                    int breathrate = jsonObject1.optInt("breathrate");
                                    int heartrate = jsonObject1.optInt("heartrate");
                                    int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                    int tempetature = jsonObject1.optInt("tempetature");
                                    String ringsn = jsonObject1.optString("ringsn");
                                    int battery = jsonObject1.optInt("battery");
                                    int devStatus = jsonObject1.optInt("devStatus");
                                    int ringStatus = jsonObject1.optInt("ringStatus");
                                    int powerStatus = jsonObject1.optInt("powerStatus");
                                    String versionno = jsonObject1.optString("versionno");
                                    String swversion = jsonObject1.optString("swversion");
                                    String sim = jsonObject1.optString("sim");
                                    int reportStatus = jsonObject1.optInt("reportStatus");
                                    int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                    String truename = jsonObject1.optString("truename");
                                    String telephone = jsonObject1.optString("telephone");
                                    String sn = jsonObject1.optString("sn");
                                    int status = jsonObject1.optInt("status");
                                    String modeType = jsonObject1.optString("modeType");

                                    DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                    dataBean.setOutTime(outTime);
                                    dataBean.setBreathrate(breathrate);
                                    dataBean.setBloodoxygen(bloodoxygen);
                                    dataBean.setTempetature(tempetature);
                                    dataBean.setRingsn(ringsn);
                                    dataBean.setPowerStatus(powerStatus);
                                    dataBean.setVersionno(versionno);
                                    dataBean.setSwversion(swversion);
                                    dataBean.setSim(sim);
                                    dataBean.setReportStatus(reportStatus);
                                    dataBean.setLastUpdateTime(lastUpdateTime);
                                    dataBean.setTruename(truename);
                                    dataBean.setTelephone(telephone);
                                    dataBean.setSn(sn);
                                    dataBean.setStatus(status);
                                    dataBean.setBattery(battery);
                                    dataBean.setHeartrate(heartrate);
                                    dataBean.setDevStatus(devStatus);
                                    dataBean.setRingStatus(ringStatus);
                                    dataBean.setModeType(modeType);
                                    dataBeansv2.add(dataBean);

                                }

                            }
                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {

                            mRecyclerInfo.setPullLoadMoreCompleted();
                            final String text = String.valueOf(mEtSearch.getText().toString()).replaceAll(" ", "");

                            Log.d("charstext", text);

                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");

                            mTvTotalDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    performNum = 1;
                                    t = 0;
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();

                                    getResEquipmentHosIdSearchTotal(Api.URL + "/v2/device?limit=10" + "&keywords=" + text + "&hospitalid=" + hospitalsId);
                                }
                            });

                            mTvOnLineDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    t = 0;
                                    performNum = 2;
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
                                    dataBeansv2.clear();
//                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
//                                    Log.d("saasdsad", text);
//                                    Log.d("saasdsad", hospitalsId);
//                                    getResEquipmentHosIdSearchOnline(Api.URL + "/v2/device?limit=10&devStatus=1" + "&keywords=" + "1");
                                    getResEquipmentHosIdSearchOnline(Api.URL + "/v2/device?limit=10&devStatus=1" + "&keywords=" + text + "&hospitalid=" + hospitalsId);
                                }
                            });
                            Log.d("performNum", String.valueOf(performNum));

                            if (performNum == 1) {
                                performNum = 1;
                                t = 0;
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();

                                getResEquipmentHosIdSearchTotal(Api.URL + "/v2/device?limit=10" + "&keywords=" + text + "&hospitalid=" + hospitalsId);
                            } else if (performNum == 2) {
                                t = 0;
                                performNum = 2;
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
                                dataBeansv2.clear();
//                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
//                                    Log.d("saasdsad", text);
//                                    Log.d("saasdsad", hospitalsId);
//                                    getResEquipmentHosIdSearchOnline(Api.URL + "/v2/device?limit=10&devStatus=1" + "&keywords=" + "1");
                                getResEquipmentHosIdSearchOnline(Api.URL + "/v2/device?limit=10&devStatus=1" + "&keywords=" + text + "&hospitalid=" + hospitalsId);
                            }


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentHosIdSearchTotal(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析


                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {

                            final String text = String.valueOf(mEtSearch.getText()).replaceAll(" ", "");
                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);
//                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
//                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");
                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {

                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentHosIdSearchOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10 + "&keywords=" + text + "&hospitalid=" + hospitalsId);
                                }

                                @Override
                                public void onLoadMore() {
                                    getResEquipmentHosIdSearchOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1) + "&keywords=" + text + "&hospitalid=" + hospitalsId);
                                }
                            });

//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentHosIdSearchOnline(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {

                            Log.d("searchonline", res);
                            final String text = String.valueOf(mEtSearch.getText()).replaceAll(" ", "");
                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);
//                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
//                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");
                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {

                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentHosIdSearchOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10 + "&keywords=" + text + "&devStatus=1" + "&hospitalid=" + hospitalsId);
                                }

                                @Override
                                public void onLoadMore() {
                                    getResEquipmentHosIdSearchOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1) + "&keywords=" + text + "&devStatus=1" + "&hospitalid=" + hospitalsId);
                                }
                            });


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentHosIdSearchOnLoad(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (deviceListResponseV2.getCode() == 0) {
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");
                            adapter.notifyDataSetChanged();

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


                    }
                });


            }


        });


    }

    private void getResEquipmentHosIdSearchOnRefresh(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (deviceListResponseV2.getCode() == 0) {
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");

                            adapter.notifyDataSetChanged();

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }

                    }
                });


            }


        });


    }


    private void getResEquipmentNumber(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                            @Override
                            public void onRefresh() {

                                dataBeansv2.clear();


//                                    mRecyclerInfo.setPullLoadMoreCompleted();
                                getResEquipmentNumber(Api.URL + "/v2/device" + "?limit=" + 10);


                            }

                            @Override
                            public void onLoadMore() {

                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");

                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析


                    if (dataxx != null) {
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");

                        deviceListResponseV2.setOnlineNum(onlineNum);
                        deviceListResponseV2.setTotalNum(totalNum);
                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            Log.d("asdhkhasd", String.valueOf(dataBeanXX.getTotalNum()));


                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");

                            mTvTotalDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    performNum = 1;
                                    t = 0;
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();

                                    getResEquipmentTotal(Api.URL + "/v2/device?limit=10");
                                }
                            });

                            mTvOnLineDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    t = 0;
                                    performNum = 2;
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
//                                    dataBeansv2.clear();
//                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentOnline(Api.URL + "/v2/device?limit=10&devStatus=1");

                                }
                            });
                            Log.d("performNum", String.valueOf(performNum));

                            if (performNum == 1) {
                                performNum = 1;
                                t = 0;
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();

                                getResEquipmentTotal(Api.URL + "/v2/device?limit=10");
                            } else if (performNum == 2) {
                                t = 0;
                                performNum = 2;
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
                                dataBeansv2.clear();
                                getResEquipmentOnline(Api.URL + "/v2/device?limit=10&devStatus=1");
                            }


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentTotal(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析


                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);


                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {


                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);


                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {
//                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();


//                                    mRecyclerInfo.setPullLoadMoreCompleted();
                                    getResEquipmentOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10);


                                }

                                @Override
                                public void onLoadMore() {
                                    getResEquipmentOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1));
                                }
                            });

//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        }else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentOnline(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);


                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {
                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);
//                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
//                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");
                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {

//                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10 + "&devStatus=1");
                                }

                                @Override
                                public void onLoadMore() {
                                    getResEquipmentOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1) + "&devStatus=1");
                                }
                            });


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentOnLoad(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析


                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);


                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (deviceListResponseV2.getCode() == 0) {
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");
                            adapter.notifyDataSetChanged();

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


                    }
                });


            }


        });


    }

    private void getResEquipmentOnRefresh(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();

                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析



                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);


                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("sad231321", String.valueOf(deviceListResponseV2.getCode()));
                        Log.d("sad231321", String.valueOf(deviceListResponseV2.getMsg()));
                        Log.d("sad231321", String.valueOf(deviceListResponseV2));
                        if (deviceListResponseV2.getCode() == 0) {
//
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");
                            adapter.notifyDataSetChanged();



                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        }else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }

                    }
                });


            }


        });


    }


    private void getResEquipmentSearchNumber(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                            @Override
                            public void onRefresh() {

                                dataBeansv2.clear();


                             String text = String.valueOf(mEtSearch.getText().toString()).replaceAll(" ", "");
                             getResEquipmentSearchNumber(Api.URL + "/v2/device?limit=10" + "&keywords=" + text);


                            }

                            @Override
                            public void onLoadMore() {

                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        if (dataxx != null) {
                            deviceListResponseV2.setTotalNum(totalNum);
                            deviceListResponseV2.setOnlineNum(onlineNum);
                            dataBeanXX.setOnlineNum(onlineNum);
                            dataBeanXX.setTotalNum(totalNum);
                            dataBeanXX.setData(dataBeanX);

                            int current_page = datax.optInt("current_page");
                            String first_page_url = datax.optString("first_page_url");
                            int from = datax.optInt("from");
                            int last_page = datax.optInt("last_page");
                            String last_page_url = datax.optString("last_page_url");
                            String next_page_url = datax.optString("next_page_url");
                            String path = datax.optString("path");
                            int per_page = datax.optInt("per_page");
                            String prev_page_url = datax.optString("prev_page_url");
                            int to = datax.optInt("to");
                            int total = datax.optInt("total");
                            JSONArray data = datax.optJSONArray("data");

                            dataBeanX.setCurrent_page(current_page);
                            dataBeanX.setFirst_page_url(first_page_url);
                            dataBeanX.setFrom(from);
                            dataBeanX.setLast_page(last_page);
                            dataBeanX.setLast_page_url(last_page_url);
                            dataBeanX.setNext_page_url(next_page_url);
                            dataBeanX.setPath(path);
                            dataBeanX.setPer_page(per_page);
                            dataBeanX.setPrev_page_url(prev_page_url);
                            dataBeanX.setTo(to);
                            dataBeanX.setTotal(total);
                            dataBeanX.setData(dataBeansv2);

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                if (jsonObject1 != null) {

                                    int outTime = jsonObject1.optInt("outTime");
                                    int breathrate = jsonObject1.optInt("breathrate");
                                    int heartrate = jsonObject1.optInt("heartrate");
                                    int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                    int tempetature = jsonObject1.optInt("tempetature");
                                    String ringsn = jsonObject1.optString("ringsn");
                                    int battery = jsonObject1.optInt("battery");
                                    int devStatus = jsonObject1.optInt("devStatus");
                                    int ringStatus = jsonObject1.optInt("ringStatus");
                                    int powerStatus = jsonObject1.optInt("powerStatus");
                                    String versionno = jsonObject1.optString("versionno");
                                    String swversion = jsonObject1.optString("swversion");
                                    String sim = jsonObject1.optString("sim");
                                    int reportStatus = jsonObject1.optInt("reportStatus");
                                    int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                    String truename = jsonObject1.optString("truename");
                                    String telephone = jsonObject1.optString("telephone");
                                    String sn = jsonObject1.optString("sn");
                                    int status = jsonObject1.optInt("status");
                                    String modeType = jsonObject1.optString("modeType");

                                    DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                    dataBean.setOutTime(outTime);
                                    dataBean.setBreathrate(breathrate);
                                    dataBean.setBloodoxygen(bloodoxygen);
                                    dataBean.setTempetature(tempetature);
                                    dataBean.setRingsn(ringsn);
                                    dataBean.setPowerStatus(powerStatus);
                                    dataBean.setVersionno(versionno);
                                    dataBean.setSwversion(swversion);
                                    dataBean.setSim(sim);
                                    dataBean.setReportStatus(reportStatus);
                                    dataBean.setLastUpdateTime(lastUpdateTime);
                                    dataBean.setTruename(truename);
                                    dataBean.setTelephone(telephone);
                                    dataBean.setSn(sn);
                                    dataBean.setStatus(status);
                                    dataBean.setBattery(battery);
                                    dataBean.setHeartrate(heartrate);
                                    dataBean.setDevStatus(devStatus);
                                    dataBean.setRingStatus(ringStatus);
                                    dataBean.setModeType(modeType);
                                    dataBeansv2.add(dataBean);

                                }

                            }

                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {
//                            adapter.notifyDataSetChanged();
                            mRecyclerInfo.setPullLoadMoreCompleted();
                            final String text = String.valueOf(mEtSearch.getText().toString()).replaceAll(" ", "");



                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");

                            mTvTotalDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    performNum = 1;
                                    t = 0;
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
                                    getResEquipmentSearchTotal(Api.URL + "/v2/device?limit=10" + "&keywords=" + text);
                                }
                            });

                            mTvOnLineDevice.setOnClickListener(new OnMultiClickListener() {
                                @Override
                                public void onMultiClick(View view) {
                                    t = 0;
                                    performNum = 2;
                                    Log.d("performNum123123141", String.valueOf(performNum));
                                    mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                    mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
                                    dataBeansv2.clear();
//                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
                                    getResEquipmentSearchOnline(Api.URL + "/v2/device?limit=10&devStatus=1" + "&keywords=" + text);
                                }
                            });



                            if (performNum == 1) {
                                performNum = 1;
                                t = 0;
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_false);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_true);
                                dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
                                getResEquipmentSearchTotal(Api.URL + "/v2/device?limit=10" + "&keywords=" + text);
                            } else if (performNum == 2) {
                                t = 0;
                                performNum = 2;
                                Log.d("performNum123123141", String.valueOf(performNum));
                                mIvOnLineDevice.setImageResource(R.drawable.home_radio_button_true);
                                mIvToTalDevice.setImageResource(R.drawable.home_radio_button_false);
                                dataBeansv2.clear();
//                                    dataBeansv2.clear();
//                                    adapter.notifyDataSetChanged();
                                getResEquipmentSearchOnline(Api.URL + "/v2/device?limit=10&devStatus=1" + "&keywords=" + text);
                            }


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentSearchTotal(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {

                            final String text = String.valueOf(mEtSearch.getText()).replaceAll(" ", "");
                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);
//                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
//                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");
                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {

                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentSearchOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10 + "&keywords=" + text);
                                }

                                @Override
                                public void onLoadMore() {
                                    getResEquipmentSearchOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1) + "&keywords=" + text);
                                }
                            });

//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentSearchOnline(String url) {

        //1.拿到okhttp对象
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.构造request
        if (getTokenToSp("token", "") != null) {

        }
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        ToastUtils.showTextToast(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);


                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();

                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析
                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (res.contains("授权验证失败")) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        }


                        if (deviceListResponseV2.getCode() == 0) {

                            final String text = String.valueOf(mEtSearch.getText()).replaceAll(" ", "");
                            adapter = new ListViewAdapter(dataBeansv2, getContext());
                            mRecyclerInfo.setLinearLayout();
                            mRecyclerInfo.setAdapter(adapter);
//                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getTotalNum() + "台");
//                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getOnlineNum() + "台");
                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
                                @Override
                                public void onRefresh() {
                                    adapter.notifyDataSetChanged();
                                    dataBeansv2.clear();
                                    getResEquipmentSearchOnRefresh(Api.URL + "/v2/device" + "?limit=" + 10 + "&keywords=" + text + "&devStatus=1");
                                }

                                @Override
                                public void onLoadMore() {
                                    getResEquipmentSearchOnLoad(Api.URL + "/v2/device" + "?limit=" + 10 + "&page=" + (dataBeanX.getCurrent_page() + 1) + "&keywords=" + text + "&devStatus=1");
                                }
                            });


//                            getResHosList(Api.URL + "/v1/hospital/resHosList");

                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }


                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


//                        mRecyclerInfo.setLinearLayout();


                    }
                });

            }


        });


    }

    private void getResEquipmentSearchOnLoad(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析
                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (deviceListResponseV2.getCode() == 0) {
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");
                            adapter.notifyDataSetChanged();

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }


                    }
                });


            }


        });


    }

    private void getResEquipmentSearchOnRefresh(String url) {

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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        mRlSleepSlices.setVisibility(View.VISIBLE);
                        mRecyclerInfo.setVisibility(View.GONE);
                        mRecyclerInfo.setPullLoadMoreCompleted();
                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONObject dataxx = jsonObject.optJSONObject("data");
                    //第一层封装
                    deviceListResponseV2.setCode(code);
                    deviceListResponseV2.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
                    deviceListResponseV2.setData(dataBeanXX);
                    //第二层解析

                    if (dataxx != null){
                        int onlineNum = dataxx.optInt("onlineNum");
                        int totalNum = dataxx.optInt("totalNum");
                        JSONObject datax = dataxx.optJSONObject("data");


                        dataBeanXX.setOnlineNum(onlineNum);
                        dataBeanXX.setTotalNum(totalNum);
                        dataBeanXX.setData(dataBeanX);

                        int current_page = datax.optInt("current_page");
                        String first_page_url = datax.optString("first_page_url");
                        int from = datax.optInt("from");
                        int last_page = datax.optInt("last_page");
                        String last_page_url = datax.optString("last_page_url");
                        String next_page_url = datax.optString("next_page_url");
                        String path = datax.optString("path");
                        int per_page = datax.optInt("per_page");
                        String prev_page_url = datax.optString("prev_page_url");
                        int to = datax.optInt("to");
                        int total = datax.optInt("total");
                        JSONArray data = datax.optJSONArray("data");

                        dataBeanX.setCurrent_page(current_page);
                        dataBeanX.setFirst_page_url(first_page_url);
                        dataBeanX.setFrom(from);
                        dataBeanX.setLast_page(last_page);
                        dataBeanX.setLast_page_url(last_page_url);
                        dataBeanX.setNext_page_url(next_page_url);
                        dataBeanX.setPath(path);
                        dataBeanX.setPer_page(per_page);
                        dataBeanX.setPrev_page_url(prev_page_url);
                        dataBeanX.setTo(to);
                        dataBeanX.setTotal(total);
                        dataBeanX.setData(dataBeansv2);

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                int outTime = jsonObject1.optInt("outTime");
                                int breathrate = jsonObject1.optInt("breathrate");
                                int heartrate = jsonObject1.optInt("heartrate");
                                int bloodoxygen = jsonObject1.optInt("bloodoxygen");
                                int tempetature = jsonObject1.optInt("tempetature");
                                String ringsn = jsonObject1.optString("ringsn");
                                int battery = jsonObject1.optInt("battery");
                                int devStatus = jsonObject1.optInt("devStatus");
                                int ringStatus = jsonObject1.optInt("ringStatus");
                                int powerStatus = jsonObject1.optInt("powerStatus");
                                String versionno = jsonObject1.optString("versionno");
                                String swversion = jsonObject1.optString("swversion");
                                String sim = jsonObject1.optString("sim");
                                int reportStatus = jsonObject1.optInt("reportStatus");
                                int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
                                String truename = jsonObject1.optString("truename");
                                String telephone = jsonObject1.optString("telephone");
                                String sn = jsonObject1.optString("sn");
                                int status = jsonObject1.optInt("status");
                                String modeType = jsonObject1.optString("modeType");

                                DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
                                dataBean.setOutTime(outTime);
                                dataBean.setBreathrate(breathrate);
                                dataBean.setBloodoxygen(bloodoxygen);
                                dataBean.setTempetature(tempetature);
                                dataBean.setRingsn(ringsn);
                                dataBean.setPowerStatus(powerStatus);
                                dataBean.setVersionno(versionno);
                                dataBean.setSwversion(swversion);
                                dataBean.setSim(sim);
                                dataBean.setReportStatus(reportStatus);
                                dataBean.setLastUpdateTime(lastUpdateTime);
                                dataBean.setTruename(truename);
                                dataBean.setTelephone(telephone);
                                dataBean.setSn(sn);
                                dataBean.setStatus(status);
                                dataBean.setBattery(battery);
                                dataBean.setHeartrate(heartrate);
                                dataBean.setDevStatus(devStatus);
                                dataBean.setRingStatus(ringStatus);
                                dataBean.setModeType(modeType);
                                dataBeansv2.add(dataBean);

                            }

                        }
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (deviceListResponseV2.getCode() == 0) {
                            mTvTotalDevice.setText("全部设备" + deviceListResponseV2.getData().getTotalNum() + "台");
                            mTvOnLineDevice.setText("在线设备" + deviceListResponseV2.getData().getOnlineNum() + "台");
                            adapter.notifyDataSetChanged();

                            mRecyclerInfo.setPullLoadMoreCompleted();

                            t = 0;
                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        getActivity().finish();
                                        startActivity(intent);

                                    }
                                }).show();

                                dialogTokenIntent.setCanceledOnTouchOutside(false);
                                dialogTokenIntent.setCancelable(false);
                            }
                        } else {
                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
                        }

                    }
                });


            }


        });


    }


//    private void getResEquipment4(String url) {
//
//        //1.拿到okhttp对象
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        //2.构造request
//        Request request = new Request.Builder()
//                .get()
//                .url(url)
//                .addHeader("token", getTokenToSp("token", ""))
//                .addHeader("uid", getUidToSp("uid", ""))
//                .build();
//        //3.将request封装为call
//        Call call = okHttpClient.newCall(request);
//        //4.执行call
////        同步执行
////        Response response = call.execute();
//
//        //异步执行
//        call.enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                L.e("OnFailure   " + e.getMessage());
//                e.printStackTrace();
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ToastUtils.showTextToast2(getContext(), "网络请求失败");
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                L.e("OnResponse");
//                final String res = response.body().string();
//                L.e(res);
//
//                //封装java对象
//
//                final DeviceListResponseV2 deviceListResponseV2 = new DeviceListResponseV2();
//
//                try {
//                    JSONObject jsonObject = new JSONObject(res);
//                    //第一层解析
//                    int code = jsonObject.optInt("code");
//                    String msg = jsonObject.optString("msg");
//                    JSONObject dataxx = jsonObject.optJSONObject("data");
//                    //第一层封装
//                    deviceListResponseV2.setCode(code);
//                    deviceListResponseV2.setMsg(msg);
////                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
//                    DeviceListResponseV2.DataBeanXX dataBeanXX = new DeviceListResponseV2.DataBeanXX();
//                    deviceListResponseV2.setData(dataBeanXX);
//                    //第二层解析
//
//
//                    int onlineNum = dataxx.optInt("onlineNum");
//                    int totalNum = dataxx.optInt("totalNum");
//
//                    JSONObject datax = dataxx.optJSONObject("data");
//
//
//
//                    dataBeanXX.setOnlineNum(onlineNum);
//                    dataBeanXX.setTotalNum(totalNum);
//                    dataBeanXX.setData(dataBeanX);
//
//                    int current_page = datax.optInt("current_page");
//                    String first_page_url = datax.optString("first_page_url");
//                    int from =  datax.optInt("from");
//                    int last_page = datax.optInt("last_page");
//                    String last_page_url = datax.optString("last_page_url");
//                    String next_page_url = datax.optString("next_page_url");
//                    String path  = datax.optString("path");
//                    int per_page = datax.optInt("per_page");
//                    String prev_page_url = datax.optString("prev_page_url");
//                    int to =  datax.optInt("to");
//                    int total  = datax.optInt("total");
//                    JSONArray data = datax.optJSONArray("data");
//
//                    dataBeanX.setCurrent_page(current_page);
//                    dataBeanX.setFirst_page_url(first_page_url);
//                    dataBeanX.setFrom(from);
//                    dataBeanX.setLast_page(last_page);
//                    dataBeanX.setLast_page_url(last_page_url);
//                    dataBeanX.setNext_page_url(next_page_url);
//                    dataBeanX.setPath(path);
//                    dataBeanX.setPer_page(per_page);
//                    dataBeanX.setPrev_page_url(prev_page_url);
//                    dataBeanX.setTo(to);
//                    dataBeanX.setTotal(total);
//                    dataBeanX.setData(dataBeansv2);
//
//                    for (int i = 0; i < data.length(); i++) {
//                        JSONObject jsonObject1 = data.getJSONObject(i);
//                        if (jsonObject1 != null) {
//
//                            int outTime = jsonObject1.optInt("outTime");
//                            int breathrate = jsonObject1.optInt("breathrate");
//                            int heartrate = jsonObject1.optInt("heartrate");
//                            int bloodoxygen = jsonObject1.optInt("bloodoxygen");
//                            int tempetature = jsonObject1.optInt("tempetature");
//                            String ringsn = jsonObject1.optString("ringsn");
//                            int battery = jsonObject1.optInt("battery");
//                            int devStatus = jsonObject1.optInt("devStatus");
//                            int ringStatus = jsonObject1.optInt("ringStatus");
//                            int powerStatus = jsonObject1.optInt("powerStatus");
//                            String versionno = jsonObject1.optString("versionno");
//                            String swversion = jsonObject1.optString("swversion");
//                            String sim = jsonObject1.optString("sim");
//                            int reportStatus = jsonObject1.optInt("reportStatus");
//                            int lastUpdateTime = jsonObject1.optInt("lastUpdateTime");
//                            String truename = jsonObject1.optString("truename");
//                            String telephone = jsonObject1.optString("telephone");
//                            String sn = jsonObject1.optString("sn");
//                            int status = jsonObject1.optInt("status");
//
//
//
//                            DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBean = new DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean();
//                            dataBean.setOutTime(outTime);
//                            dataBean.setBreathrate(breathrate);
//                            dataBean.setBloodoxygen(bloodoxygen);
//                            dataBean.setTempetature(tempetature);
//                            dataBean.setRingsn(ringsn);
//                            dataBean.setPowerStatus(powerStatus);
//                            dataBean.setVersionno(versionno);
//                            dataBean.setSwversion(swversion);
//                            dataBean.setSim(sim);
//                            dataBean.setReportStatus(reportStatus);
//                            dataBean.setLastUpdateTime(lastUpdateTime);
//                            dataBean.setTruename(truename);
//                            dataBean.setTelephone(telephone);
//                            dataBean.setSn(sn);
//                            dataBean.setStatus(status);
//                            dataBean.setBattery(battery);
//                            dataBean.setHeartrate(heartrate);
//                            dataBean.setDevStatus(devStatus);
//                            dataBean.setRingStatus(ringStatus);
//                            dataBeansv2.add(dataBean);
//
//                        }
//
//                    }
//
//
//
//
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//
//
//                        if (res.contains("授权验证失败")) {
//                            if (dialogTokenIntent == null){
//                                dialogTokenIntent = new DialogTokenIntent(getContext(),R.style.CustomDialog);
//                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
//                                    @Override
//                                    public void OnConfirm(DialogTokenIntent dialog) {
//                                        Intent intent = new Intent(getContext(),LoginActivity.class);
//                                        getActivity().finish();
//                                        startActivity(intent);
//
//                                    }
//                                }).show();
//
//                                dialogTokenIntent.setCanceledOnTouchOutside(false);
//                                dialogTokenIntent.setCancelable(false);
//                            }
//                        }
//
//                        if (deviceListResponseV2.getCode() == 0) {
//
//
////                        dataBeans.clear();
//
//
////                        adapter = new ListViewAdapter(dataBeansv2, getContext());
//                            mRecyclerInfo.setLinearLayout();
//                            mRecyclerInfo.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
//                                @Override
//                                public void onRefresh() {
//
//
////                                mRecyclerInfo.setAdapter(adapter);
//
//                                    dataBeansv2.clear();
//                                    getResEquipment2(Api.URL + "/v2/device" + "?hospitalid=" + k+"&limit="+15);
//                                    t = 0;
//                                }
//
//                                @Override
//                                public void onLoadMore() {
////                                    int id2 = ids1[ids1.length - 1];
////                                    adapter.notifyDataSetChanged();
//                                    getResEquipment3(Api.URL + "/v2/device" + "?page=" + (dataBeanX.getCurrent_page()+1) + "&hospitalid=" + k+"&limit="+15);
////                                    Log.d("id2", String.valueOf(id2));
//                                }
//                            });
//
//                            mRecyclerInfo.setPullLoadMoreCompleted();
//                            t = 0;
//                        } else if (deviceListResponseV2.getCode() == 10004 || deviceListResponseV2.getCode() == 10010) {
//                            if (dialogTokenIntent == null){
//                                dialogTokenIntent = new DialogTokenIntent(getContext(),R.style.CustomDialog);
//                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
//                                    @Override
//                                    public void OnConfirm(DialogTokenIntent dialog) {
//                                        Intent intent = new Intent(getContext(),LoginActivity.class);
//                                        getActivity().finish();
//                                        startActivity(intent);
//
//                                    }
//                                }).show();
//
//                                dialogTokenIntent.setCanceledOnTouchOutside(false);
//                                dialogTokenIntent.setCancelable(false);
//                            }
//                        } else {
//                            ToastUtils.showTextToast(getContext(), deviceListResponseV2.getMsg());
//                        }
//
//
//                    }
//                });
//
//
//            }
//
//
//        });
//
//
//    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("Fragment", "onStart");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Fragment", "onDestroy");
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d("Fragment", "onResume");

    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("Fragment", "onPause");
        timer.cancel();

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Fragment", "onStop");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d("Fragment", "onDestroyView");
    }
}
//根据医院的名字数量来定义一个Button的数组，Button数组的数量就是mTitles.length










