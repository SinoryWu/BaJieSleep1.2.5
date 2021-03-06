package com.example.bajiesleep;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.entity.HospitalListResponse1;
import com.example.bajiesleep.entity.SearchDeviceScanResponse;
import com.example.bajiesleep.entity.SearchDeviceScanResponse1;
import com.example.bajiesleep.entity.TestBean;
import com.example.bajiesleep.fragment.recyclerview.ListViewAdapter2;
import com.example.bajiesleep.util.GetShp;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.view.View.VISIBLE;


public class ScanActivity extends AppCompatActivity {
    private CaptureManager capture;
    private Button buttonSwitchInput,buttonSwitchScan;
    private DecoratedBarcodeView barcodeScannerView;
    private TextView mTvZxingStatus;

    private RelativeLayout rlSearch;
//    private boolean bTorch = false;
    private LinearLayout mLinearLeft,mLinearLeft2;
    private EditText Search1;

    String chars;
    String bigChars;
    private List<TestBean> databeans;
    private List<String> testbean = new ArrayList<>();
    private List<String> mList = new ArrayList<>();
//    private ArrayAdapter<String> mAutoCompleteAdapter;

    private RecyclerView recyclerView;
    ListViewAdapter2 listViewAdapter2;
    public List<SearchDeviceScanResponse1.DataBean> dataBeanSearch = new ArrayList<>();

    int devStatus ;
    HospitalListResponse1.DataBean dataBean  =new HospitalListResponse1.DataBean();

//    public int hosid;
    String[] sns ;
//    IntentResult result;

    SearchDeviceScanResponse1 searchDeviceScanResponse1 = new SearchDeviceScanResponse1();
    SearchDeviceScanResponse1.DataBean dataBean1;
    private String[] str = new String[]{
            "??????1",
            "????????????1",
            "????????????1",
            "????????????1",
            "??????2",
            "????????????2",
            "????????????2",
            "????????????2",
            "??????3",
            "????????????3",
            "????????????3",
            "????????????3"
    };

    private String mTitles[] = {
            "??????1",
            "????????????1",
            "????????????1",
            "????????????1",
            "??????2",
            "????????????2",
            "????????????2",
            "????????????2",
            "??????3",
            "????????????3",
            "????????????3",
            "????????????3"};


    public String getHosIdToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp3", MODE_PRIVATE);
        String hosid = sp.getString("hosid", "??????hosid");
        return hosid;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String hosid = dataBean.getHospitalid();
//        Log.d("hosid",hosid);

        final String hosid = getHosIdToSp("hosid","");
        getResSearchDeviceScan(Api.URL+"/v1/splDev?hospitalid="+hosid+"&keywords=0");
        Log.d("hosid",hosid);
        initData();

         final Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#000000"));
        barcodeScannerView = initializeContent();
        buttonSwitchInput = findViewById(R.id.button_switch);
        mTvZxingStatus = findViewById(R.id.zxing_status_view);
        mLinearLeft=findViewById(R.id.liner_left);

        rlSearch = findViewById(R.id.rl_search);
        mLinearLeft2 = findViewById(R.id.liner_left2);
        rlSearch.setVisibility(View.GONE);
        Search1=findViewById(R.id.search_1);
        buttonSwitchScan = findViewById(R.id.button_switch2);
        recyclerView = findViewById(R.id.scan_recycler_view);

//        Intent intent = new Intent(ScanActivity.this, CaptureActivity.class);
//        startActivity(intent);

        capture = new CaptureManager(this, barcodeScannerView);

        capture.initializeFromIntent(getIntent(), savedInstanceState);

        capture.decode();




        buttonSwitchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSwitchInput.setVisibility(View.GONE);
                barcodeScannerView.setVisibility(View.GONE);
                mTvZxingStatus.setVisibility(View.GONE);
//                rlSearch.setVisibility(View.GONE);
                rlSearch.setVisibility(VISIBLE);
                window.setStatusBarColor(Color.parseColor("#383838"));
//                recyclerView.setVisibility(View.GONE);
//                SystemBarTintManager tintManager = new SystemBarTintManager(ScanActivity.this);
                Log.d("sns",Arrays.toString(sns));




            }
        });
        buttonSwitchScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSwitchInput.setVisibility(VISIBLE);
                barcodeScannerView.setVisibility(VISIBLE);
                mTvZxingStatus.setVisibility(VISIBLE);
                rlSearch.setVisibility(View.GONE);
                window.setStatusBarColor(Color.parseColor("#000000"));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(Search1.getWindowToken(),0);



            }
        });

        mLinearLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mLinearLeft2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        Search1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                listViewAdapter2.notifyDataSetChanged();
                chars = String.valueOf(charSequence);
                bigChars = chars.toUpperCase();

                Log.d("onTextChanged", bigChars);
                listViewAdapter2.getFilter().filter(bigChars);
//                if (!"".equals(charSequence.toString())){
//                    recyclerView.setVisibility(VISIBLE);
//                }else {
//                    recyclerView.setVisibility(View.GONE);
//                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });



    }



    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.activity_scan);
        return (DecoratedBarcodeView)findViewById(R.id.dbv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
        barcodeScannerView.setTorchOff();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }


    /**
     * ??????????????????????????????
     */
    private void initData() {
        //List<DataBea>---->Adapter------>setAdapter------>???????????????
        //??????????????????
//        databeans = new ArrayList<>();

        //??????????????????
        for (int i = 0; i < str.length; i++) {
//            //??????????????????
//            TestBean data = new TestBean();
//            data.sn = mTitles[i];
            //?????????????????????
            mList.add(str[i]);
        }
    }

//    /**
//     * ????????????????????????ListView???????????????
//     */
//    private void showList(boolean isVertical, boolean isReverse) {
//        //RecyclerView??????????????????,?????????????????????????????????
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        //??????????????????????????????
//        //????????????????????????
//        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
//        //????????????(??????)???????????????
//        layoutManager.setReverseLayout(isReverse);
//
//        recyclerView.setLayoutManager(layoutManager);
//        //???????????????
//        mAdapter = new ListViewAdapter2(databeans,this);
//
//        //?????????RecyclerView??????
//        recyclerView.setAdapter(mAdapter);
//
//
//
//
//    }
//    /**
//     * @param view    ?????????????????????itemView
//     */
//    public void myItemClick(View view){
//        // ??????itemView?????????
//        int position = recyclerView.getChildLayoutPosition(view);
//        String text = mList.get(position);
//        Search1.setText(text);
//        Search1.setSelection(text.length());
//
////        recyclerView.setVisibility(View.GONE);
//    }


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


    public void getResSearchDeviceScan(String url) {


        //1.??????okhttp??????
        OkHttpClient okHttpClient = new OkHttpClient();

        //2.??????request
        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("token", getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
                .addHeader("user-agent", GetShp.getUserAgent(getApplicationContext()))
                .build();
        //3.???request?????????call
        Call call =   okHttpClient.newCall(request);
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
                        ToastUtils.showTextToast2(ScanActivity.this,"??????????????????");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                Log.d("searchres",res);

//                parseJSONWithGSON(res);


                //??????java??????


                final ArrayList<String> snBeans = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //???????????????
                    int code = jsonObject.optInt("code");
                    String msg = jsonObject.optString("msg");
                    JSONArray data = jsonObject.getJSONArray("data");
                    //???????????????
                    searchDeviceScanResponse1.setCode(code);
                    searchDeviceScanResponse1.setMsg(msg);
//                    List<EquipmentResponse.DataBean> dataBeans = new ArrayList<>();
                    searchDeviceScanResponse1.setData(dataBeanSearch);
                    //???????????????

                    if (data != null){
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            if (jsonObject1 != null) {

                                String sn = jsonObject1.optString("sn");
                                String truename = jsonObject1.optString("truename");
                                String devStatus = jsonObject1.optString("devStatus");
                                //???????????????
                                dataBean1 = new SearchDeviceScanResponse1.DataBean(sn,truename,devStatus);
                                dataBean1.setSn(sn);
                                dataBean1.setDevStatus(devStatus);
                                dataBean1.setTruename(truename);
                                dataBeanSearch.add(dataBean1);
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

           runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        sns = snBeans.toArray(new String[snBeans.size()]);

//
////                        if (sns.length == 0){
////                            ToastUtils.showTextToast(getContext(),"?????????sn");
////                        }
//                        if (searchDeviceScanResponse1.getCode() == 0 ){
////                            String[] dataBeanSearch1 = dataBeanSearch.toArray(new String[dataBeanSearch.size()]);
////                            Log.d("dataBeanSearch1", String.valueOf(dataBeanSearch1.length));
//                            if (dataBean1 == null){
//                                ToastUtils.showTextToast(ScanActivity.this,"?????????sn");
//                            }else {
//
//                                if (dataBean1.getDevStatus().equals("??????")){
//                                    devStatus =1;
//
//                                }else if (dataBean1.getDevStatus().equals("?????????") || dataBean1.getDevStatus().equals("??????")|| dataBean1.getDevStatus().equals("??????")){
//                                    devStatus =2;
//                                }else {
//                                    devStatus =3;
//                                }
//                            }
//
//
//                        }else {
//                            ToastUtils.showTextToast(ScanActivity.this, searchDeviceScanResponse1.getMsg());
//                        }
//
//                        Log.d("devStatus1", String.valueOf(devStatus));



                        if (searchDeviceScanResponse1.getCode() ==0){
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ScanActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            listViewAdapter2 = new ListViewAdapter2(Arrays.asList(sns),ScanActivity.this);

//        recyclerView.setAdapter(listViewAdapter2);
                            recyclerView.setAdapter(listViewAdapter2);
//                            recyclerView.setVisibility(View.GONE);
//                        recyclerView.setVisibility(VISIBLE);

                            listViewAdapter2.setOnItemClickLitener(new ListViewAdapter2.OnItemClickLitener() {
                                @Override
                                public void onItemClick(String data, int position) {
//                                Toast.makeText(ScanActivity.this,data,Toast.LENGTH_SHORT).show();
                                    String datasn = data.substring(0,14);
                                    Search1.setText(data);
                                    if (data.contains("??????")){
                                        Intent intent = new Intent(ScanActivity.this, LendActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("sn",datasn );
                                        bundle.putString("deviceState","scanStop");
                                        intent.putExtras(bundle);
                                        startActivity(intent);
//                                    finish();

                                    }else if (data.contains("?????????") ||data.contains("??????")||data.contains("??????")){
                                        ToastUtils.showTextToast(ScanActivity.this,"????????????");
                                    }else {
                                        Intent intent =  new Intent(ScanActivity.this, RecoverDeviceActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("sn2",datasn );
                                        bundle.putString("deviceState","scanStop" );
                                        intent.putExtras(bundle);
                                        startActivity(intent);
//                                    finish();
                                    }


                                }
                            });
                        }else if (searchDeviceScanResponse1.getCode()==10010 || searchDeviceScanResponse1.getCode() == 10004){
                            DialogTokenIntent dialogTokenIntent = new DialogTokenIntent(ScanActivity.this,R.style.CustomDialog);
                            dialogTokenIntent.setTitle("??????").setMessage("??????????????????????????????????????????????????????!").setConfirm("??????", new DialogTokenIntent.IOnConfirmListener() {
                                @Override
                                public void OnConfirm(DialogTokenIntent dialog) {
                                    Intent intent = new Intent(ScanActivity.this,LoginActivity.class);
                                    finish();
                                    startActivity(intent);

                                }
                            }).show();

                            dialogTokenIntent.setCanceledOnTouchOutside(false);
                            dialogTokenIntent.setCancelable(false);
                        }else {
                            ToastUtils.showTextToast(ScanActivity.this,searchDeviceScanResponse1.getMsg());
                        }








                    }
                });

            }


        });
    }


}
