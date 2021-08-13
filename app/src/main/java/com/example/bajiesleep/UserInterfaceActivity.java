package com.example.bajiesleep;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bajiesleep.fragment.DeviceFragment;
import com.example.bajiesleep.fragment.HomeFragment;
import com.example.bajiesleep.fragment.UserFragment;
import com.example.bajiesleep.util.HookUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UserInterfaceActivity extends FragmentActivity implements View.OnClickListener {

    public static String choice = null;

    private RelativeLayout mRlTabHome,mRlTabUser,mRlTabDevice;

    private Fragment mHomeFragment,mUserFragment,mDeviceFragment,currentFragment,fragment;

    private ImageView mIvTabHome,mIvTabUser,mIvTabDevice;
    private List<Fragment> fragmentList = new ArrayList<>();
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





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HookUtils.hookMacAddress("Z-Application",getApplicationContext());
//        HookUtils.hookMacAddress("Z-Activity",UserInterfaceActivity.this);
        String userAgent = System.getProperty("http.agent");
//        Log.d("userAgent", userAgent);
        saveStringToSp("user_agent",userAgent);
        if (getTokenToSp("token","") == null){
            Intent intent = new Intent(UserInterfaceActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        initView();
        if (savedInstanceState != null) {
            //不为空说明缓存视图中有fragment实例，通过tag取出来
            /*获取保存的fragment  没有的话返回null*/
            mHomeFragment = (HomeFragment) getSupportFragmentManager().getFragment(savedInstanceState, "HomeFragment");
            mDeviceFragment = (DeviceFragment) getSupportFragmentManager().getFragment(savedInstanceState, "DeviceFragment");
            mUserFragment = (UserFragment) getSupportFragmentManager().getFragment(savedInstanceState, "UserFragment");

            addToList(mHomeFragment);
            addToList(mDeviceFragment);
            addToList(mUserFragment);

        }else{
            initFragment();
        }
//        initTab();


    }


     private void initView(){
        setContentView(R.layout.activity_user_interface);
        mRlTabHome = findViewById(R.id.rl_tab_home);
        mRlTabUser  =findViewById(R.id.rl_tab_user);
        mRlTabDevice = findViewById(R.id.rl_tab_device);
        mIvTabHome = findViewById(R.id.iv_tab_home);
        mIvTabUser = findViewById(R.id.iv_tab_user);
        mIvTabDevice = findViewById(R.id.iv_tab_device);

         mRlTabHome.setOnClickListener(this);
         mRlTabUser.setOnClickListener(this);
         mRlTabDevice.setOnClickListener(this);
    }

    private void addToList(Fragment fragment) {
        if (fragment != null) {
            fragmentList.add(fragment);
        }


    }
    private void initFragment() {
        /* 默认显示home  fragment*/
        mHomeFragment = new HomeFragment();
        addFragment(mHomeFragment);
        showFragment(mHomeFragment);
        mIvTabHome.setImageResource(R.drawable.tab_home_icon_focus);
        mIvTabUser.setImageResource(R.drawable.tab_user_icon_normal);
        mIvTabDevice.setImageResource(R.drawable.tab_device_icon_normal);

    }

    /*添加fragment*/
    private void addFragment(Fragment fragment) {

        /*判断该fragment是否已经被添加过  如果没有被添加  则添加*/
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, fragment).commit();
            /*添加到 fragmentList*/
            fragmentList.add(fragment);
        }


    }

    /*显示fragment*/
    private void showFragment(Fragment fragment) {



        for (Fragment frag : fragmentList) {

            if (frag != fragment) {
                /*先隐藏其他fragment*/

                getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

    }

    private void initTab(){
        if (mHomeFragment == null){
            mHomeFragment = new HomeFragment();
        }
        if (!mHomeFragment.isAdded()){
            // 提交事务
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, mHomeFragment).commit();
            // 记录当前Fragment
            currentFragment = mHomeFragment;
            // 设置图片的变化
            mIvTabHome.setImageResource(R.drawable.tab_home_icon_focus);
            mIvTabUser.setImageResource(R.drawable.tab_user_icon_normal);
            mIvTabDevice.setImageResource(R.drawable.tab_device_icon_normal);
        }
    }

    /**
     * 点击第一个tabHome
     */
    private void clickTabHomeLayout(){
        if (mHomeFragment == null){
            mHomeFragment = new HomeFragment();

        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),mHomeFragment);
        //设置底部tab变化
        mIvTabHome.setImageResource(R.drawable.tab_home_icon_focus);
        mIvTabUser.setImageResource(R.drawable.tab_user_icon_normal);
        mIvTabDevice.setImageResource(R.drawable.tab_device_icon_normal);
    }

    /**
     * 点击第三个tabUser
     */
    private void clickTabUserLayout(){
        if (mUserFragment == null){
            mUserFragment = new UserFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),mUserFragment);
        //设置底部tab变化
        mIvTabHome.setImageResource(R.drawable.tab_home_icon_normal);
        mIvTabDevice.setImageResource(R.drawable.tab_device_icon_normal);
        mIvTabUser.setImageResource(R.drawable.tab_user_icon_focus);


    }

    /**
     * 点击第二个tabDevice
     */
    private void clickTabDeviceLayout(){
        if (mDeviceFragment == null){
            mDeviceFragment = new DeviceFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(),mDeviceFragment);
        //设置底部tab变化
        mIvTabHome.setImageResource(R.drawable.tab_home_icon_normal);
        mIvTabUser.setImageResource(R.drawable.tab_user_icon_normal);
        mIvTabDevice.setImageResource(R.drawable.tab_device_icon_focus);
    }

    /**
     * 添加或者显示碎片
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction, Fragment fragment){
        if (currentFragment == fragment){
            return ;
        }
        if (!fragment.isAdded()){// 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.content_layout, fragment).commit();

        }else {
            transaction.hide(currentFragment).show(fragment).commit();
//           getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
        }

        currentFragment = fragment;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_tab_home: // Home
                if (mHomeFragment == null){
                    mHomeFragment = new HomeFragment();

                }
                addFragment(mHomeFragment);
                showFragment(mHomeFragment);
                //设置底部tab变化
                mIvTabHome.setImageResource(R.drawable.tab_home_icon_focus);
                mIvTabUser.setImageResource(R.drawable.tab_user_icon_normal);
                mIvTabDevice.setImageResource(R.drawable.tab_device_icon_normal);

//                clickTabHomeLayout();
                break;
            case R.id.rl_tab_user: // User
                if (mUserFragment == null){
                    mUserFragment = new UserFragment();
                }
                addFragment(mUserFragment);
                showFragment(mUserFragment);
                //设置底部tab变化
                mIvTabHome.setImageResource(R.drawable.tab_home_icon_normal);
                mIvTabDevice.setImageResource(R.drawable.tab_device_icon_normal);
                mIvTabUser.setImageResource(R.drawable.tab_user_icon_focus);
//                clickTabUserLayout();
                break;
            case R.id.rl_tab_device: // Device
                if (mDeviceFragment == null){
                    mDeviceFragment = new DeviceFragment();
                }
                addFragment(mDeviceFragment);
                showFragment(mDeviceFragment);
                //设置底部tab变化
                mIvTabHome.setImageResource(R.drawable.tab_home_icon_normal);
                mIvTabUser.setImageResource(R.drawable.tab_user_icon_normal);
                mIvTabDevice.setImageResource(R.drawable.tab_device_icon_focus);
//                clickTabDeviceLayout();
                break;
            default:
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 按两次退出杀死进程
     */
    //声明一个long类型变量：用于存放上一点击“返回键”的时刻
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                System.exit(0);

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        /*fragment不为空时 保存*/
        if (mHomeFragment != null) {
            getSupportFragmentManager().putFragment(outState, "HomeFragment", mHomeFragment);
        }
        if (mDeviceFragment != null) {
            getSupportFragmentManager().putFragment(outState, "DeviceFragment", mDeviceFragment);
        }
        if (mUserFragment != null) {
            getSupportFragmentManager().putFragment(outState, "UserFragment", mUserFragment);
        }



        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("userinterface","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("userinterface","onResume");
//        loginActivity.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("userinterface","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("userinterface","onRestart");
    }
    protected void saveStringToSp(String key, String val) {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, val);
        editor.putString(key, val);
        editor.commit();
    }

}