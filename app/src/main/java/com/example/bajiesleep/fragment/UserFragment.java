package com.example.bajiesleep.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bajiesleep.Api;
import com.example.bajiesleep.BaseActivity;
import com.example.bajiesleep.DialogTokenIntent;
import com.example.bajiesleep.InfoActivity;
import com.example.bajiesleep.L;
import com.example.bajiesleep.LoginActivity;
import com.example.bajiesleep.PushSetActivity;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.UserInterfaceActivity;
import com.example.bajiesleep.entity.LoginDataResponse;
import com.example.bajiesleep.entity.LoginOutResponse;
import com.example.bajiesleep.entity.LoginResponse;
import com.example.bajiesleep.entity.PersonResponse;
import com.example.bajiesleep.util.TokenCodeUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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

public class UserFragment extends Fragment  {
    private TextView mTvUserPath,mTvUserName,mTvHospitalName,mTvUserInfo,mTvUserPush,mTvUserVersion;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment,container,false);
        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getResPerson(Api.URL+"/v1/auser/"+getUidToSp("uid",""));
        mTvUserPath = getActivity().findViewById(R.id.tv_fragment_user_path);
        mTvUserName = getActivity().findViewById(R.id.fragment_user_name);
        mTvHospitalName = getActivity().findViewById(R.id.fragment_hospital_name);
        mTvUserInfo=getActivity().findViewById(R.id.tv_fragment_user_info);
        mTvUserVersion= getActivity().findViewById(R.id.tv_fragment_user_version);
        mTvUserPush = getActivity().findViewById(R.id.tv_fragment_user_push);

        mTvUserPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginOut(Api.URL+"/v1/logout");
            }
        });

        mTvUserVersion.setText("版本 "+getVerName());

        mTvUserPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PushSetActivity.class);
                startActivity(intent);
            }
        });
        mTvUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InfoActivity.class);
                startActivity(intent);
            }
        });


    }


    protected void getResPerson(String url) {


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
                        ToastUtils.showTextToast2(getContext(),"网络请求失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

//                parseJSONWithGSON(res);

                //封装java对象
               final PersonResponse personResponse = new PersonResponse();
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    //第一层解析
                    int code = jsonObject.optInt("code");
                    JSONObject data = jsonObject.optJSONObject("data");
                    String msg = jsonObject.optString("msg");

                    //第一层封装
                    personResponse.setCode(code);
                    personResponse.setMsg(msg);
                    PersonResponse.DataBean dataBean = new PersonResponse.DataBean();
                    personResponse.setData(dataBean);


//                    第二层解析
                    if (data != null){
                        String truename = data.optString("truename");
                        String hospitalname = data.optString("hospitalname");

                        dataBean.setTruename(truename);
                        dataBean.setHospitalname(hospitalname);
                    }
//




                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (personResponse.getCode() == 0){

                            Log.d("gettruename",personResponse.getData().getTruename());
                            mTvUserName.setText(personResponse.getData().getTruename());
                            mTvHospitalName.setText(personResponse.getData().getHospitalname());
                        }else if(personResponse.getCode() == 10004 || personResponse.getCode() == 10010){
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
                            ToastUtils.showTextToast(getContext(),personResponse.getMsg());
                        }


                    }
                });


            }
        });
    }

    protected String getTokenToSp(String key, String val){
        SharedPreferences sp1 = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String token = sp1.getString("token","没有token");


        return token;
    }

    protected String getUidToSp(String key, String val){
        SharedPreferences sp1 =  getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        String uid = sp1.getString("uid","没有uid");


        return uid;
    }

    public void cleanTokenToSp(String key, String val){
        SharedPreferences sp = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
        sp.edit().clear().commit();
    }


    private void loginOut(String url) {

        HashMap<String,String> map = new HashMap<>();

//        String url = (ApiConfig.BASE_URl+ApiConfig.LOGIN);

        Log.e("tags", String.valueOf(map));



        postResLoginOut(url,map);
//       navigateTo(SecondActivity3.class);

    }


    protected void postResLoginOut(String url, HashMap<String, String> map) {
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
                .post(requestBodyJson)
                .addHeader("token",getTokenToSp("token",""))
                .addHeader("uid",getUidToSp("uid",""))
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"网络请求失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }



            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("OnResponse");
                final String res = response.body().string();
                L.e(res);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        L.e(res);

                        Gson gson = new Gson();

                        LoginOutResponse loginOutResponse = gson.fromJson(res, LoginOutResponse.class);
                        if (loginOutResponse.getCode() == 0) {

                            final String msg = loginOutResponse.getMsg();

                            Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();

                            cleanTokenToSp("token","");

                            Intent intent = new Intent(getContext(),LoginActivity.class);
                            getActivity().finish();
                            startActivity(intent);


//
                        } else if(loginOutResponse.getCode() == 10004 || loginOutResponse.getCode() == 10010){
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
                            Toast.makeText(getContext(),"失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public String getVerName()
    {
        String verName = "-1";
        try
        {
            verName = getContext().getPackageManager().getPackageInfo(
                    "com.example.bajiesleep", 0).versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
        }
        return verName;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("UsrFragment","onStop");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("UsrFragment","onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("UsrFragment","onDestroyView");
    }


}
