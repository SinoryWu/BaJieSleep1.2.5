package com.example.bajiesleep;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bajiesleep.entity.HospitalListResponse1;
import com.wx.wheelview.widget.WheelView;

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

import static android.content.Context.MODE_PRIVATE;

public class HosListDialog extends Dialog implements View.OnClickListener {

    private String title, cancel, confirm;
    DialogTokenIntent dialogTokenIntent = null;
    WheelView myWheelView;
    private TextView mTvTitle, mTvCancel, mTvConfirm;
    private Context context;
    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;
    ArrayList<String> nameList;
    ArrayList<String> hosList;
    String [] namelist;
    String[] hosid;
    public HosListDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public HosListDialog setCancel(String cancel, IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener = listener;
        return this;
    }

    public HosListDialog setConfirm(String confirm, IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener = listener;
        return this;
    }

    String hospitalName;
    String hospitalId;
    public HosListDialog(@NonNull Context context) {
        super(context);

    }

    public HosListDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context =context;
    }

    protected HosListDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoslist_dialog);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int) (size.x * 0.8);

        getWindow().setAttributes(p);


        mTvTitle = findViewById(R.id.hoslist_tv_title);
        mTvCancel = findViewById(R.id.hoslist_tv_cancel);
        mTvConfirm = findViewById(R.id.hoslist_tv_confirm);

        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }

        if (!TextUtils.isEmpty(cancel)) {
            mTvCancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(confirm)) {
            mTvConfirm.setText(confirm);
        }

        myWheelView = findViewById(R.id.hoslist_wheelview);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.WHITE;
        style.textColor = Color.GRAY;
        style.selectedTextColor = Color.BLACK;
        myWheelView.setStyle(style);
        myWheelView.setWheelData(createArrays());


        myWheelView.setWheelAdapter(new MyWheelAdapter(getContext()));
        myWheelView.setWheelSize(5);

        myWheelView.setSkin(WheelView.Skin.Holo);

        myWheelView.setSelection(0);
        getResHosList(Api.URL + "/v1/hospital/resHosList");
        myWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {

                if (hosid != null){
                    hospitalName = String.valueOf(myWheelView.getSelectionItem());


                    hospitalId = hosid[position];
                    Log.d("asd213", hospitalId);
                    Log.d("myWheelView", hospitalName);
                }

            }
        });






        mTvCancel.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);
    }


    private ArrayList<String> createArrays() {
        final ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add("item" + i);
//        }
        list.add("全部");



        return list;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hoslist_tv_cancel:
                if (cancelListener != null) {
                    cancelListener.onCancel(this);
                }
                dismiss();
                break;

            case R.id.hoslist_tv_confirm:
                if (confirmListener != null) {
                    confirmListener.onConfirm(this, hospitalName,hospitalId);
                }
                dismiss();
                break;
        }
    }

    public interface IOnCancelListener {
        void onCancel(HosListDialog dialog);
    }

    public interface IOnConfirmListener {
        void onConfirm(HosListDialog dialog, String hospitalName,String hospitalId);
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

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (hospitalListResponse1.getCode() == 0) {
                            String[] allid = {"0"};
                            String[] hosid1 = hospitalidBeans.toArray(new String[hospitalidBeans.size()]);
                            hosid = new String[allid.length + hosid1.length];
                            System.arraycopy(allid, 0, hosid, 0, allid.length);
                            System.arraycopy(hosid1, 0, hosid, allid.length, hosid1.length);
                            Log.d("hosid", Arrays.toString(hosid));

                            String[] allButton = {"全部"};
                            String[] stringArray = nameBeans.toArray(new String[nameBeans.size()]);
                            String[] nameButton = new String[allButton.length + stringArray.length];
                            System.arraycopy(allButton, 0, nameButton, 0, allButton.length);
                            System.arraycopy(stringArray, 0, nameButton, allButton.length, stringArray.length);
                            nameList = new ArrayList<> (Arrays.asList(nameButton));
                            hosList = new ArrayList<>(Arrays.asList(hosid));
                            myWheelView.setWheelData(nameList);
                            Log.d("listasdhas", String.valueOf(nameList));





                        } else if (hospitalListResponse1.getCode() == 10004 || hospitalListResponse1.getCode() == 10010) {
                            if (dialogTokenIntent == null) {
                                dialogTokenIntent = new DialogTokenIntent(getContext(), R.style.CustomDialog);
                                dialogTokenIntent.setTitle("提示").setMessage("您好，您的登陆信息已过期，请重新登陆!").setConfirm("确认", new DialogTokenIntent.IOnConfirmListener() {
                                    @Override
                                    public void OnConfirm(DialogTokenIntent dialog) {
                                        Intent intent = new Intent(getContext(), LoginActivity.class);
                                        ((Activity) context).finish();
                                        getContext().startActivity(intent);

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

    protected String getTokenToSp(String key, String val) {
        SharedPreferences sp1 = getContext().getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token", "");


        return token;
    }

    protected String getUidToSp(String key, String val) {
        SharedPreferences sp1 = getContext().getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid", "");


        return uid;
    }

}
