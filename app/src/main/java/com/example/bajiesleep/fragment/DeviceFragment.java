package com.example.bajiesleep.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bajiesleep.AddPatient;
import com.example.bajiesleep.Api;
import com.example.bajiesleep.DeviceListActivity;
import com.example.bajiesleep.DialogTokenIntent;
import com.example.bajiesleep.L;
import com.example.bajiesleep.LendActivity;
import com.example.bajiesleep.LoginActivity;
import com.example.bajiesleep.OnMultiClickListener;
import com.example.bajiesleep.ProgressBar1;
import com.example.bajiesleep.ProgressBar2;
import com.example.bajiesleep.ProgressBar3;
import com.example.bajiesleep.R;
import com.example.bajiesleep.RecoverDeviceActivity;
import com.example.bajiesleep.ReportListActivity;
import com.example.bajiesleep.ScanActivity;
import com.example.bajiesleep.TestActivity;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.UserListAcivity;
import com.example.bajiesleep.entity.EquipmentResponse;
import com.example.bajiesleep.entity.HospitalDeviceResponse;
import com.example.bajiesleep.entity.HospitalListResponse1;
import com.example.bajiesleep.entity.SearchDeviceScanResponse;
import com.example.bajiesleep.util.TokenCodeUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
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

public class DeviceFragment extends Fragment {


//    private MyListener myListener;
//

//    public interface MyListener{
//        public void sendMessage(final String str);
//    }


    public int t;
    public Timer timer;
    public Button[] buttons;
    public int k;
    public LinearLayout mLinearTitles,mLinearReportRight,mLinearUserRight,mLinearDeviceRight;
    public List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
    public List<SearchDeviceScanResponse.DataBean> dataBeanSearch = new ArrayList<>();
    HospitalDeviceResponse.DataBean dataBean1 = new HospitalDeviceResponse.DataBean();

    public EquipmentResponse.DataBean.DevStatusBean devStatusBean;
    private TextView mDevices1,mDevices3,mReport3,mReport2,mReport1,testDevice,mTvMale,mTvFemale,mTvUserNumber;
    ProgressBar1 progressBar1;
    SearchDeviceScanResponse searchDeviceScanResponse = new SearchDeviceScanResponse();
    ProgressBar2 progressBar2;
    ProgressBar3 progressBar3;
    SearchDeviceScanResponse.DataBean dataBean2;
    int devStatus ;
    public Button mBtnDevice,mBtnAddPatient,mBtnLoad;
    private RelativeLayout mRlDevice,mRlNull;
    public TextView testResult;

    public HorizontalScrollView horizontalScrollView;


    public CardView mCvDevice,mCvReport,mCvUser;


    IntentResult result;

    String[] sns;

    String refresh = "00000";


    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    //    public int getK() {
//        return k;
//    }
//
//    public void setK(int k) {
//        this.k = k;
//    }



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
        String token = sp1.getString("token", "没有token");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "没有uid");


        return uid;
    }

    public void cleanTokenToSp(String key, String val){
        SharedPreferences sp = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        sp.edit().clear().commit();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.device_fragment,container,false);
        return view;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mLinearTitles = getActivity().findViewById(R.id.device_fragment_horizontal_linear);
        mLinearReportRight = getActivity().findViewById(R.id.report_liner_right);
        mLinearUserRight = getActivity().findViewById(R.id.user_liner_right);
        mLinearDeviceRight = getActivity().findViewById(R.id.device_liner_right);
        mDevices1 = getActivity().findViewById(R.id.tv_devices1);
        mDevices3 = getActivity().findViewById(R.id.tv_devices3);
        progressBar1 = getActivity().findViewById(R.id.progress_bar1);
        progressBar2 = getActivity().findViewById(R.id.progress_bar2);
        progressBar3 = getActivity().findViewById(R.id.progress_bar3);

        mReport3= getActivity().findViewById(R.id.validation_report);
        mReport2= getActivity().findViewById(R.id.tv_report3);
        mReport1 = getActivity().findViewById(R.id.tv_report1);
        mBtnAddPatient = getActivity().findViewById(R.id.btn_add_patient);

        mBtnDevice=getActivity().findViewById(R.id.btn_device);
        testResult = getActivity().findViewById(R.id.testResult);
        testResult.setVisibility(View.GONE);
        testDevice=getActivity().findViewById(R.id.testdevStatus);

        mTvUserNumber = getActivity().findViewById(R.id.validation_user);
        mTvMale = getActivity().findViewById(R.id.tv_men);
        mTvFemale = getActivity().findViewById(R.id.tv_women);

        mCvDevice = getActivity().findViewById(R.id.cv_fragment_device_device);
        mCvReport = getActivity().findViewById(R.id.cv_fragment_device_device2);
        mCvUser = getActivity().findViewById(R.id.cv_fragment_device_device3);

        horizontalScrollView = getActivity().findViewById(R.id.hs_titles);
        mRlDevice  = getActivity().findViewById(R.id.device_fragment_view);
        mRlNull  = getActivity().findViewById(R.id.device_fragment_null);
        mBtnLoad = getActivity().findViewById(R.id.sleep_slices_btn_device);
        mRlNull.setVisibility(View.GONE);
        mBtnLoad.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View view) {
                mRlDevice.setVisibility(View.VISIBLE);
                mRlNull.setVisibility(View.GONE);
                getResHosList(Api.URL+"/v1/hospital/resHosList");
            }
        });
        getResHosList(Api.URL+"/v1/hospital/resHosList");



        mBtnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddPatient.class);
                startActivity(intent);
            }
        });


        mBtnDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requsetPermission();
                /*以下是启动我们自定义的扫描活动*/
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setBeepEnabled(true);
                /*设置启动我们自定义的扫描活动，若不设置，将启动默认活动*/
//                intentIntegrator.setRequestCode(k);

//                Bundle bundle = new Bundle();
//                bundle.putString("hosid", String.valueOf(k));
//                intentIntegrator.setCameraId(bundle);

                intentIntegrator.setCaptureActivity(ScanActivity.class);



                intentIntegrator.initiateScan();

            }
        });


        mLinearDeviceRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DeviceListActivity.class);
                startActivity(intent);
            }
        });



        mLinearReportRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReportListActivity.class);
                startActivity(intent);
            }
        });

        mLinearUserRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserListAcivity.class);
                startActivity(intent);
            }
        });
    }


    private void requsetPermission() {
        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.CAMERA}, 1);

            } else {

            }
        } else {

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以

                }else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    Toast.makeText(getContext(),"请手动打开相机权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
//                Log.d("testResult", result.getContents());
//                testResult.setText( result.getContents());
//
//                ToastUtils.showTextToast(getContext(), String.valueOf(k));
//                ToastUtils.showTextToast(getContext(), String.valueOf(devStatus));
                String sn = result.getContents();


                String sn2 = sn.substring(sn.length()-2,sn.length());
                Log.d("dataBeanSearch1", getHosIdToSp("hosid",""));

                getResSearchDeviceScan(Api.URL+"/v1/splDev?hospitalid="+getHosIdToSp("hosid","")+"&keywords="+sn2);



//                if (devStatus == 1){
//                    Intent intent = new Intent(getContext(), LendActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("sn",result.getContents() );
//                    intent.putExtras(bundle);
//                    getContext().startActivity(intent);
//
//                }else if (devStatus == 2){
//                    Toast.makeText(getContext(),"不可操作",Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getContext(),"跳转到回收设备",Toast.LENGTH_SHORT).show();
//                }



//                myListener.sendContent(result.getContents());
            }


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void getResSearchDeviceScan(String url) {


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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRlDevice.setVisibility(View.GONE);
                        mRlNull.setVisibility(View.VISIBLE);
                        ToastUtils.showTextToast2(getContext(),"网络请求失败");
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


                final ArrayList<String> snBeans = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");
                    //第一层封装
                    searchDeviceScanResponse.setCode(code);
                    searchDeviceScanResponse.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    searchDeviceScanResponse.setData(dataBeanSearch);
                    //第二层解析

                    if (data != null){
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                String sn = jsonObject1.optString("sn");
                                String truename = jsonObject1.optString("truename");
                                String devStatus = jsonObject1.optString("devStatus");
                                //第二层封装
                                dataBean2 = new SearchDeviceScanResponse.DataBean(sn,truename,devStatus);
                                dataBean2.setSn(sn);
                                dataBean2.setDevStatus(devStatus);
                                dataBean2.setTruename(truename);
                                dataBeanSearch.add(dataBean2);
                                if (!truename.equals("")){
                                    snBeans.add(sn+"  "+truename+" "+devStatus);
                                }else {
                                    snBeans.add(sn+" "+truename+" "+devStatus);
                                }

                                Log.d("snBeans", String.valueOf(snBeans));



                            }

                        }
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        sns = snBeans.toArray(new String[snBeans.size()]);
                        Log.d("sns",Arrays.toString(sns));


//                        if (sns.length == 0){
//                            ToastUtils.showTextToast(getContext(),"无效的sn");
//                        }
                        if (searchDeviceScanResponse.getCode() == 0 ){
//                            String[] dataBeanSearch1 = dataBeanSearch.toArray(new String[dataBeanSearch.size()]);
//                            Log.d("dataBeanSearch1", String.valueOf(dataBeanSearch1.length));
                            if (dataBean2 == null){
                                ToastUtils.showTextToast(getContext(),"无效的sn");
                            }else {

                                if (dataBean2.getDevStatus().equals("闲置")){
                                    devStatus =1;

                                }else if (dataBean2.getDevStatus().equals("已借出") || dataBean2.getDevStatus().equals("维保")|| dataBean2.getDevStatus().equals("维修")){
                                    devStatus =2;
                                }else {
                                    devStatus =3;
                                }
                            }


                        }else if(searchDeviceScanResponse.getCode() == 10004 || searchDeviceScanResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(getContext(),R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(getContext(),LoginActivity.class);
                                    getActivity().finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }
                        else {
                            ToastUtils.showTextToast(getContext(),searchDeviceScanResponse.getMsg());
                        }

                        Log.d("devStatus1", String.valueOf(devStatus));

                        if (devStatus == 1){
                            Intent intent = new Intent(getContext(), LendActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("sn",result.getContents() );
                            bundle.putString("deviceState","scanStop");
                            intent.putExtras(bundle);
                            getContext().startActivity(intent);

                        }else if (devStatus == 2){
                            ToastUtils.showTextToast(getContext(),"不可操作");
                        }else if (devStatus == 3){
                            Intent intent =  new Intent(getContext(), RecoverDeviceActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("sn2",result.getContents() );
                            bundle.putString("deviceState","scanStop" );
                            intent.putExtras(bundle);
                            getContext().startActivity(intent);
                        }else {

                        }







                    }
                });

            }


        });
    }

    public void saveHosIdToSp(String key, String val) {
        SharedPreferences sp = getActivity().getSharedPreferences("sp3", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public void saveDeviceHosName(String key, String val) {
        SharedPreferences sp = getActivity().getSharedPreferences("deviceHospitalName", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = getContext().getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "没有hosid");
        return hosid;
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
                        ToastUtils.showTextToast2(getContext(),"网络请求失败");
                        refresh = "true";
                        mRlDevice.setVisibility(View.GONE);
                        mRlNull.setVisibility(View.VISIBLE);

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                String res = response.body().string();
                L.e(res);
                refresh = "false";
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


                    if (data != null){
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
                        if (hospitalListResponse1.getCode() == 0){

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
                                buttons[i] = new Button(getContext());
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

                                buttons[i].setOnClickListener(new OnMultiClickListener() {
                                    @Override
                                    public void onMultiClick(View view) {
                                        k = buttons[finalI].getId();
                                        String hospitalName = String.valueOf(buttons[finalI].getText());

                                        saveHosIdToSp("hosid", String.valueOf(k));
                                        Log.d("btnIds", hospitalName);
                                        saveDeviceHosName("deviceHospitalName",hospitalName);
                                        setBackground(buttons[finalI], finalI);
                                        dataBeans.clear();


                                        getResEquipment(Api.URL+"/v1/hospital/"+k);

//

                                    }
                                });


                            }

                            buttons[0].performClick();
                        }else if(hospitalListResponse1.getCode() == 10004 || hospitalListResponse1.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(getContext(),R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(getContext(),LoginActivity.class);
                                    getActivity().finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);

//                            ToastUtils.showTextToast(getContext(),"您好，您的登陆信息已过期，请重新登陆!");
                        }
                        else {
                            ToastUtils.showTextToast(getContext(),hospitalListResponse1.getMsg());
                        }


                    }
                });

            }


        });
    }



    private void getResEquipment(String url) {

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
                        mRlDevice.setVisibility(View.GONE);
                        mRlNull.setVisibility(View.VISIBLE);
                        ToastUtils.showTextToast2(getContext(),"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                //封装java对象

                final HospitalDeviceResponse hospitalDeviceResponse = new HospitalDeviceResponse();
//                final ProgressBar1 progressBar1 =  new ProgressBar1();
                final ArrayList<Integer> ids = new ArrayList<>();
                final ArrayList<String> totalBeans = new ArrayList<>();
                try {
                   JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject data = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    hospitalDeviceResponse.setCode(code);
                    hospitalDeviceResponse.setMsg(msg);

                    hospitalDeviceResponse.setData(dataBean1);


                    Log.d("datadevice", String.valueOf(data));

                    if (data != null){
                        //第二层解析
                        int free = data.optInt("free");
                        int total = data.optInt("total");

                        int getReport = data.optInt("getReport");
                        int needReport = data.optInt("needReport");
                        int totalReport = data.optInt("totalReport");
                        int male = data.optInt("male");
                        int female = data.optInt("female");
                        int totalMember  =data.optInt("totalMember");

                        //第二层封装
                        dataBean1.setFree(free);
                        dataBean1.setTotal(total);
                        dataBean1.setGetReport(getReport);
                        dataBean1.setNeedReport(needReport);
                        dataBean1.setTotalReport(totalReport);
                        dataBean1.setMale(male);
                        dataBean1.setFemale(female);
                        dataBean1.setTotalMember(totalMember);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (hospitalDeviceResponse.getCode() == 0){
                            String a = String.valueOf(dataBean1.getFree());
                            String b = String.valueOf(dataBean1.getTotal());

                            int c = dataBean1.getFree();
                            int d = dataBean1.getTotal();
//                        progressBar1= new ProgressBar1(getContext());

                            String e = String.valueOf(dataBean1.getTotalReport());
                            String f = String.valueOf(dataBean1.getNeedReport());
                            String g = String.valueOf(dataBean1.getGetReport());
                            String max = String.valueOf(dataBean1.getFemale()+dataBean1.getMale());
                            String male = String.valueOf(dataBean1.getMale());
                            String female = String.valueOf(dataBean1.getFemale());
                            String totalMember = String.valueOf(dataBean1.getTotalMember());

                            int h  = dataBean1.getNeedReport();
                            int j = dataBean1.getGetReport();
                            progressBar1.setMax(d);
                            progressBar1.setProgress(c);


                            progressBar2.setMax(h);
                            progressBar2.setProgress(j);

                            progressBar3.setMax(dataBean1.getFemale()+dataBean1.getMale());
                            progressBar3.setProgress(dataBean1.getMale());
//                        progressBar1.start();
                            mDevices1.setText(a);
                            mDevices3.setText(b);
                            mReport3.setText(e);
                            mReport2.setText(f);
                            mReport1.setText(g);

                            mTvUserNumber.setText(totalMember);
                            mTvMale.setText(male);
                            mTvFemale.setText(female);

                        }else if(hospitalDeviceResponse.getCode() == 10004 || hospitalDeviceResponse.getCode() == 10010){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(getContext(),R.style.CustomDialog);
                            dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(getContext(),LoginActivity.class);
                                    getActivity().finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(getContext(),hospitalDeviceResponse.getMsg());
                        }




//                        Log.d("conten1t",devStatusBean.getContent());


//                        for (int i = 0; i <= dataBeans.size(); i++){
//                            if (devStatusBean.getContent().equals("闲置")){
//                                i=1;
//                            }else {
//                                i=0;
//                            }
//
//                            mDevices1 .setText(i);
//                        }



                    }
                });


            }


        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("DeviceFragment","onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("DeviceFragment","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DeviceFragment","onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("DeviceFragment","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d("DeviceFragment","onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DeviceFragment","onDestroy");
    }
}
