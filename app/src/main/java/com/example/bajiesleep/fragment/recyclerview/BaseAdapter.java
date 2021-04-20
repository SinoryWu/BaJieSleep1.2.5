package com.example.bajiesleep.fragment.recyclerview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.CustomDialogMessage;
import com.example.bajiesleep.CustomDialogPhone;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.entity.EquipmentResponse;

import java.util.List;

public abstract  class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final List<EquipmentResponse.DataBean> dataBeans;
    protected Context context;

    public BaseAdapter(List<EquipmentResponse.DataBean> dataBeans, Context context) {
        this.dataBeans =dataBeans;
        this.context =context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent,viewType);
        return new InnerHolder(view);
    }

    protected abstract View getSubView(ViewGroup parent,int viewType);

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        //在这里设置数据
////        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);
//
//        ((InnerHolder)holder).setData(dataBeans.get(position),context);
//
//    }

    @Override
    public int getItemCount() {
        if(dataBeans != null){
            return dataBeans.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        private TextView mTVsn,mTvUserName,mTVDeviceState,mTVRingState,mTVElectricState,mTVRingSn,mTVHeartRate,
                mTVBreathing,mTVBloodOxygen,mTVRoomTemperature;
        private ImageView mIvDeviceState,mIvPhone,mIvMessage,mIvElectricState;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTVsn = itemView.findViewById(R.id.recycler_tv_device_sn);
            mTvUserName = itemView.findViewById(R.id.recycler_tv_user_name);
            mTVDeviceState = itemView.findViewById(R.id.recycler_tv_device_state);
            mTVRingState = itemView.findViewById(R.id.recycler_tv_ring_state);
            mTVElectricState = itemView.findViewById(R.id.recycler_tv_electric_state);
            mTVRingSn = itemView.findViewById(R.id.recycler_tv_ring_sn);
            mTVHeartRate = itemView.findViewById(R.id.recycler_tv_heart_rate);
            mTVBreathing = itemView.findViewById(R.id.recycler_tv_breathing);
            mTVBloodOxygen = itemView.findViewById(R.id.recycler_tv_blood_oxygen);
            mTVRoomTemperature = itemView.findViewById(R.id.recycler_tv_room_temperature);
            mIvDeviceState = itemView.findViewById(R.id.recycler_iv_device_state);
            mIvPhone = itemView.findViewById(R.id.iv_phone);
            mIvMessage = itemView.findViewById(R.id.iv_message);
            mIvElectricState = itemView.findViewById(R.id.recycler_electric_state_icon);



//            mIvPhone.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View view) {
//                    final CustomDialogPhone customDialogPhone = new CustomDialogPhone(view.getContext(),R.style.CustomDialog);
//                    customDialogPhone.setUserName("李晓明").setPhoneNumber("18955566896").setCall("呼叫", new CustomDialogPhone.IOnCallListener() {
//                        @Override
//                        public void OnCall(CustomDialogPhone dialog) {
//                            callPhone("18955566896");
//                            customDialogPhone.dismiss();
//                        }
//                    }).setCopy("复制", new CustomDialogPhone.IOnCopyListener() {
//                        @Override
//                        public void OnCopy(CustomDialogPhone dialog) {
//                            copy("18955566896");
//                            ToastUtils.showTextToast(view.getContext(),"复制成功");
//                            customDialogPhone.dismiss();
//
//                        }
//                    }).show();
//                }
//            });
//
            mIvMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialogMessage customDialogMessage = new CustomDialogMessage(view.getContext(),R.style.CustomDialog);
                    customDialogMessage.show();
                }
            });

        }


        public void setData(final EquipmentResponse.DataBean dataBeans,Context context) {
            mTvUserName.setText(dataBeans.getTruename()+" "+dataBeans.getDevStatus().getContent());
            mTVsn.setText(dataBeans.getSn());

            mTVRingState.setText("戒指："+dataBeans.getRingStatus());
            mIvPhone.setImageResource(R.drawable.phone_icon);
            mIvMessage.setImageResource(R.drawable.message_icon);

            mIvPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final CustomDialogPhone customDialogPhone = new CustomDialogPhone(view.getContext(),R.style.CustomDialog);
                    customDialogPhone.setUserName(dataBeans.getTruename()).setPhoneNumber(dataBeans.getTelephone()).setCall("呼叫", new CustomDialogPhone.IOnCallListener() {
                        @Override
                        public void OnCall(CustomDialogPhone dialog) {

                            callPhone(dataBeans.getTelephone());
                            Log.d("tel11111",dataBeans.getTelephone());
                            customDialogPhone.dismiss();
                        }
                    }).setCopy("复制", new CustomDialogPhone.IOnCopyListener() {
                        @Override
                        public void OnCopy(CustomDialogPhone dialog) {
                            Log.d("tel11111",dataBeans.getTelephone());
                            copy(dataBeans.getTelephone());
                            ToastUtils.showTextToast(view.getContext(),"复制成功");
                            customDialogPhone.dismiss();

                        }
                    }).show();
                }
            });


            mTVDeviceState.setText(dataBeans.getDevStatus().getDev_status());

            if (dataBeans.getDevStatus().getContent().equals("闲置")){
                mIvPhone.setVisibility(View.INVISIBLE);
                mIvMessage.setVisibility(View.INVISIBLE);
            }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
                mIvPhone.setVisibility(View.INVISIBLE);
                mIvMessage.setVisibility(View.INVISIBLE);
            }else if (dataBeans.getDevStatus().getContent().equals("维保中")){
                mIvPhone.setVisibility(View.INVISIBLE);
                mIvMessage.setVisibility(View.INVISIBLE);
            }else {
                mIvPhone.setVisibility(View.VISIBLE);
                mIvMessage.setVisibility(View.VISIBLE);
            }






            if (dataBeans.getDevStatus().getDev_status().equals("未连接")){
                mTVHeartRate.setText("心率：");
                mTVBreathing.setText("呼吸：");
                mTVBloodOxygen.setText("血氧：");
                mTVRoomTemperature.setText("室温：");
            }else {
                if ((Integer)(dataBeans.getHeartrate()) == 0){
                    mTVHeartRate.setText("心率：");
                }else {
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                }
                if ((Integer)(dataBeans.getBreathrate()) == 0){
                    mTVBreathing.setText("呼吸：");
                }else {
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                }
                if ((Integer)(dataBeans.getBloodoxygen()) == 0){
                    mTVBloodOxygen.setText("血氧：");
                }else {
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen()+"%");
                }
                if ((Integer)(dataBeans.getTempetature()) == 0){
                    mTVRoomTemperature.setText("室温：");
                }else {
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }
            }


            if (dataBeans.getRingStatus().equals("未连接")){
                mTVElectricState.setText(dataBeans.getBattery()+"%");
                mTVElectricState.setVisibility(View.INVISIBLE);
                mTVRingSn.setText(dataBeans.getRingsn());
                mTVRingSn.setVisibility(View.INVISIBLE);
                mIvElectricState.setVisibility(View.INVISIBLE);
            }else {
                mTVElectricState.setText(dataBeans.getBattery()+"%");
                mTVElectricState.setVisibility(View.VISIBLE);
                mTVRingSn.setText(dataBeans.getRingsn());
                mTVRingSn.setVisibility(View.VISIBLE);
                mIvElectricState.setVisibility(View.VISIBLE);
            }

            if ((Integer)(dataBeans.getBattery()) >= 80 && (Integer)(dataBeans.getBattery()) <= 100 ){
                mIvElectricState.setImageResource(R.drawable.electric80_icon);
            }else if ((Integer)(dataBeans.getBattery()) >= 60 && (Integer)(dataBeans.getBattery()) < 80){
                mIvElectricState.setImageResource(R.drawable.electric60_icon);
            }else if ((Integer)(dataBeans.getBattery()) >= 40 && (Integer)(dataBeans.getBattery()) < 60){
                mIvElectricState.setImageResource(R.drawable.electric40_icon);
            }else if ((Integer)(dataBeans.getBattery()) >= 20 && (Integer)(dataBeans.getBattery()) < 40){
                mIvElectricState.setImageResource(R.drawable.electric20_icon);
            }else {
                mIvElectricState.setImageResource(R.drawable.electric0_icon);
            }

            if (dataBeans.getDevStatus().getDev_status().equals("监测中")){
                if (dataBeans.getDevStatus().getContent().equals("维保中")){
                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
                }else {
                    mIvDeviceState.setImageResource(R.drawable.monitor_icon);
                }
            }else if (dataBeans.getDevStatus().getDev_status().equals("已离床")){
                if (dataBeans.getDevStatus().getContent().equals("维保中")){
                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
                }else {
                    mIvDeviceState.setImageResource(R.drawable.leave_icon);
                }
            }else if (dataBeans.getDevStatus().getDev_status().equals("监测中但戒指未连接")){
                if (dataBeans.getDevStatus().getContent().equals("维保中")){
                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
                }else {
                    mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                }

            }else if (dataBeans.getDevStatus().getDev_status().equals("活动中")){
                if (dataBeans.getDevStatus().getContent().equals("维保中")){
                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
                }else {
                    mIvDeviceState.setImageResource(R.drawable.in_action_icon);
                }

            }else if (dataBeans.getDevStatus().getDev_status().equals("已连接")){
                if (dataBeans.getDevStatus().getContent().equals("维保中")){
                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
                }else {
                    mIvDeviceState.setImageResource(R.drawable.connected_icon);
                }

            }else if (dataBeans.getDevStatus().getDev_status().equals("未连接")){
                if (dataBeans.getDevStatus().getContent().equals("维保中")){
                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
                }else {
                    mIvDeviceState.setImageResource(R.drawable.not_connected_icon);
                }

            }

        }

    }



    public void callPhone(String str) {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + str));
        context.startActivity(intent);
    }

    private boolean copy(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
