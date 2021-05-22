package com.example.bajiesleep.fragment.recyclerview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.CustomDialogMessage;
import com.example.bajiesleep.CustomDialogPhone;
import com.example.bajiesleep.DialogTokenIntent;
import com.example.bajiesleep.DialogWarning;
import com.example.bajiesleep.OnMultiClickListener;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.entity.DeviceListResponse;
import com.example.bajiesleep.entity.DeviceListResponseV2;
import com.example.bajiesleep.entity.EquipmentResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.InnerHolder> {
    private Context context;
    private  List<DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean> dataBeans;

    CustomDialogPhone customDialogPhone  ;
    CustomDialogMessage customDialogMessage = null ;
    private OnItemClickListener mClickListener;

    //设置回调接口
    public interface OnItemClickListener{


        void onItemClick(int reportStatus);
    }

    public void setOnItemClickLitener(OnItemClickListener listener){
        this.mClickListener  = listener;
    }
//    private String mTitles[] = {
//            "全部",
//            "万康体检",
//            "明州医院",
//            "其他医院"};
    public ListViewAdapter(List<DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean> dataBeans,Context context) {
        this.dataBeans =dataBeans;
        this.context = context;
    }




    /**
     * 这个方法用于创建条目View
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ListViewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的这个view其实就是条目的界面
        //两个步骤
        //1.拿到veiw
        //2.创建InnerHolder

        View view = View.inflate(parent.getContext(), R.layout.recycler_item_userl_info, null);
        return new InnerHolder(view);
    }
    /**
     * 这个方法用于绑定holder，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter.InnerHolder holder, int position) {
        //在这里设置数据
//        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);

        holder.setData(dataBeans.get(position),context);

    }
    /**
     * 返回条目个数
     * @return
     */
    @Override
    public int getItemCount() {
        if(dataBeans != null){
            return dataBeans.size();
        }
        return 0;

    }



    public class InnerHolder extends RecyclerView.ViewHolder {
        private TextView mTVsn,mTvUserName,mTVDeviceState,mTVRingState,mTVElectricState,mTVRingSn,mTVHeartRate,
                mTVBreathing,mTVBloodOxygen,mTVRoomTemperature,mTvTime,mTvSim,mTvDeviceVersion,mTvRingVersion;
        private ImageView mIvDeviceState,mIvPhone,mIvMessage,mIvElectricState,mIvNoBloodOxygen,mIvChildren;
        private GifImageView MIvLight,mGiRingCharge;
        private Animation anim_in, anim_out;
        private RelativeLayout mRlDeviceSn,mRlDeviceSim,mRlDeviceVersion,mRlRingSn,mRlRingVersion;

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
            MIvLight =itemView.findViewById(R.id.recycler_iv_device_light);
            mTvTime = itemView.findViewById(R.id.recycler_tv_device_time);
            mIvNoBloodOxygen = itemView.findViewById(R.id.recycler_iv_device_no_blood_oxygen);
            mTvSim = itemView.findViewById(R.id.recycler_tv_device_sim);
            mTvDeviceVersion = itemView.findViewById(R.id.recycler_tv_device_versionno);
            mTvRingVersion = itemView.findViewById(R.id.recycler_tv_ring_version);
            mRlDeviceSn = itemView.findViewById(R.id.recycler_rl_device_sn);
            mRlDeviceVersion =itemView.findViewById(R.id.recycler_rl_device_versionno);
            mRlDeviceSim = itemView.findViewById(R.id.recycler_rl_device_sim);
            mGiRingCharge = itemView.findViewById(R.id.recycler_ring_charge);
            mIvChildren = itemView.findViewById(R.id.recycler_iv_children);


            mRlRingSn = itemView.findViewById(R.id.recycler_rl_ring_sn);
            mRlRingVersion = itemView.findViewById(R.id.recycler_rl_ring_version);
            // 加载进入动画
            anim_in = AnimationUtils.loadAnimation(context, R.anim.anim_tv_marquee_in);
            // 加载移除动画
            anim_out = AnimationUtils.loadAnimation(context, R.anim.anim_tv_marquee_out);


        }


        public void setData(final DeviceListResponseV2.DataBeanXX.DataBeanX.DataBean dataBeans, final Context context) {


            itemView.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    if (dataBeans.getReportStatus() == 1){
                        final DialogWarning dialogWarning = new DialogWarning(context,R.style.CustomDialog);
                        dialogWarning.setConfirm("我知道了", new DialogWarning.IOnConfirmListener() {
                            @Override
                            public void OnConfirm(DialogWarning dialog) {
                                dialogWarning.dismiss();
                            }
                        });
                        dialogWarning.show();
                        dialogWarning.setCanceledOnTouchOutside(false);
                    }
                }
            });
            Log.d("dataBeans", String.valueOf(dataBeans.getPowerStatus()));
            mIvChildren.setVisibility(View.GONE);
            mTVsn.setText(dataBeans.getSn());
            mTvSim.setText(dataBeans.getSim());
            mTvDeviceVersion.setText("固件版本："+dataBeans.getVersionno());
            mTVRingSn.setText(dataBeans.getRingsn());
            mTvRingVersion.setText("固件版本："+dataBeans.getSwversion());
            mTvRingVersion.setVisibility(View.GONE);
            mTvSim.setVisibility(View.GONE);
            mTvDeviceVersion.setVisibility(View.GONE);

            mRlRingSn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mTVRingSn.startAnimation(anim_out);
//                    mTVRingSn.setVisibility(View.GONE);
                    mRlRingSn.setVisibility(View.GONE);
                    mRlRingVersion.setVisibility(View.VISIBLE);
//                    mTvRingVersion.startAnimation(anim_in);
                    mTvRingVersion.setVisibility(View.VISIBLE);

                }
            });

            mRlRingVersion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mTvRingVersion.startAnimation(anim_out);
//                    mTvRingVersion.setVisibility(View.GONE);
                    mRlRingVersion.setVisibility(View.GONE);
                    mRlRingSn.setVisibility(View.VISIBLE);
//                    mTVRingSn.startAnimation(anim_in);
                    mTVRingSn.setVisibility(View.VISIBLE);
                }
            });

            if (dataBeans.getModeType().equals("1")){
                mIvChildren.setVisibility(View.VISIBLE);
            }else if (dataBeans.getModeType().equals("0")){
                mIvChildren.setVisibility(View.GONE);
            }

//            mTVRingSn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mTVRingSn.startAnimation(anim_out);
//                    mTVRingSn.setVisibility(View.GONE);
//                    mTvRingVersion.startAnimation(anim_in);
//                    mTvRingVersion.setVisibility(View.VISIBLE);
//
//                }
//            });

//            mTvRingVersion.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mTvRingVersion.startAnimation(anim_out);
//                    mTvRingVersion.setVisibility(View.GONE);
//                    mTVRingSn.startAnimation(anim_in);
//                    mTVRingSn.setVisibility(View.VISIBLE);
//                }
//            });


            mRlDeviceSn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mTVsn.startAnimation(anim_out);
//                    mTVsn.setVisibility(View.GONE);
                    mRlDeviceSn.setVisibility(View.GONE);
                    mRlDeviceVersion.setVisibility(View.VISIBLE);
//                    mTvDeviceVersion.startAnimation(anim_in);
                    mTvDeviceVersion.setVisibility(View.VISIBLE);
                    mTvSim.setVisibility(View.GONE);

                }
            });
//            mTVsn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mTVsn.startAnimation(anim_out);
//                    mTVsn.setVisibility(View.GONE);
//                    mTvDeviceVersion.startAnimation(anim_in);
//                    mTvDeviceVersion.setVisibility(View.VISIBLE);
//                    mTvSim.setVisibility(View.GONE);
//
//                }
//            });

            mRlDeviceVersion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mTvDeviceVersion.startAnimation(anim_out);
//                    mTvDeviceVersion.setVisibility(View.GONE);
                    mRlDeviceVersion.setVisibility(View.GONE);
                    mRlDeviceSim.setVisibility(View.VISIBLE);
//                    mTvSim.startAnimation(anim_in);
                    mTvSim.setVisibility(View.VISIBLE);
                    mTVsn.setVisibility(View.GONE);



                }
            });

//            mTvDeviceVersion.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mTvDeviceVersion.startAnimation(anim_out);
//                    mTvDeviceVersion.setVisibility(View.GONE);
//                    mTvSim.startAnimation(anim_in);
//                    mTvSim.setVisibility(View.VISIBLE);
//                    mTVsn.setVisibility(View.GONE);
//                }
//            });

            mRlDeviceSim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mTvSim.startAnimation(anim_out);
//                    mTvSim.setVisibility(View.GONE);
                    mRlDeviceSim.setVisibility(View.GONE);
                    mRlDeviceSn.setVisibility(View.VISIBLE);
//                    mTVsn.startAnimation(anim_in);
                    mTVsn.setVisibility(View.VISIBLE);
                    mTvDeviceVersion.setVisibility(View.GONE);
                }
            });

//            mTvSim.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mTvSim.startAnimation(anim_out);
//                    mTvSim.setVisibility(View.GONE);
//                    mTVsn.startAnimation(anim_in);
//                    mTVsn.setVisibility(View.VISIBLE);
//                    mTvDeviceVersion.setVisibility(View.GONE);
//                }
//            });



            mTVElectricState.setText(dataBeans.getBattery()+"%");
            String date =  timestamp2Date(String.valueOf(dataBeans.getLastUpdateTime()),"MM-dd HH:mm");
            mTvTime.setText(date);
            if (dataBeans.getRingsn() != null){
                mTVRingSn.setText(dataBeans.getRingsn());
            }else {
                mTVRingSn.setText("0");
            }

            if ((dataBeans.getBattery()) >= 80 && (dataBeans.getBattery()) <= 100 ){
                mIvElectricState.setImageResource(R.drawable.electric80_icon);
            }else if ((dataBeans.getBattery()) >= 60 && (dataBeans.getBattery()) < 80){
                mIvElectricState.setImageResource(R.drawable.electric60_icon);
            }else if ((dataBeans.getBattery()) >= 40 && (dataBeans.getBattery()) < 60){
                mIvElectricState.setImageResource(R.drawable.electric40_icon);
            }else if ((dataBeans.getBattery()) >= 20 && (dataBeans.getBattery()) < 40){
                mIvElectricState.setImageResource(R.drawable.electric20_icon);
            }else {
                mIvElectricState.setImageResource(R.drawable.electric0_icon);
            }

            if (dataBeans.getStatus() == 1){
                mTvUserName.setText("闲置");
                mIvDeviceState.setImageResource(R.drawable.unuse_icon);

                if (dataBeans.getDevStatus() == 1){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.GONE);
                    mTVDeviceState.setText("未连接");
                    mTVHeartRate.setText("心率：");
                    mTVBreathing.setText("呼吸：");
                    mTVBloodOxygen.setText("血氧：");
                    mTVRoomTemperature.setText("室温：");


                }else if (dataBeans.getDevStatus() == 2){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已连接，未监测");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());

                }else if (dataBeans.getDevStatus() == 3){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已离床");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 4){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("监测中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 5){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("活动中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }

            }else if (dataBeans.getStatus() == 5){
                mTvUserName.setText("维修中");
                mIvDeviceState.setImageResource(R.drawable.repair_icon);
                if (dataBeans.getDevStatus() == 1){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.GONE);
                    mTVDeviceState.setText("未连接");
                    mTVHeartRate.setText("心率：");
                    mTVBreathing.setText("呼吸：");
                    mTVBloodOxygen.setText("血氧：");
                    mTVRoomTemperature.setText("室温：");


                }else if (dataBeans.getDevStatus() == 2){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已连接，未监测");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());

                }else if (dataBeans.getDevStatus() == 3){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已离床");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 4){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("监测中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 5){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("活动中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }
            }else if (dataBeans.getStatus() == 10){
                mTvUserName.setText("维保中");
                mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
                if (dataBeans.getDevStatus() == 1){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.GONE);
                    mTVDeviceState.setText("未连接");
                    mTVHeartRate.setText("心率：");
                    mTVBreathing.setText("呼吸：");
                    mTVBloodOxygen.setText("血氧：");
                    mTVRoomTemperature.setText("室温：");


                }else if (dataBeans.getDevStatus() == 2){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已连接，未监测");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());

                }else if (dataBeans.getDevStatus() == 3){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已离床");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 4){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("监测中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 5){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("活动中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }
            }else if (dataBeans.getStatus() == 15){
                if (dataBeans.getOutTime() == 0){
                    mTvUserName.setText(dataBeans.getTruename()+"待归还");
                }else {
                    mTvUserName.setText(dataBeans.getTruename()+" 逾期"+dataBeans.getOutTime()+"天");
                }

                if (dataBeans.getDevStatus() == 1){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.not_connected_icon);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.GONE);
                    mTVDeviceState.setText("未连接");
                    mTVHeartRate.setText("心率：");
                    mTVBreathing.setText("呼吸：");
                    mTVBloodOxygen.setText("血氧：");
                    mTVRoomTemperature.setText("室温：");


                }else if (dataBeans.getDevStatus() == 2){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已连接，未监测");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());

                }else if (dataBeans.getDevStatus() == 3){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.leave_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.leave_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已离床");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 4){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("监测中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 5){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.in_action_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.in_action_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("活动中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }
            }else if(dataBeans.getStatus() == 20){
                mTvUserName.setText(dataBeans.getTruename());
                if (dataBeans.getDevStatus() == 1){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.not_connected_icon);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.GONE);
                    mTVDeviceState.setText("未连接");
                    mTVHeartRate.setText("心率：");
                    mTVBreathing.setText("呼吸：");
                    mTVBloodOxygen.setText("血氧：");
                    mTVRoomTemperature.setText("室温：");


                }else if (dataBeans.getDevStatus() == 2){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.connected_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已连接，未监测");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());

                }else if (dataBeans.getDevStatus() == 3){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.leave_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.leave_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("已离床");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 4){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("监测中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }else if (dataBeans.getDevStatus() == 5){
                    if (dataBeans.getRingStatus() == 0){
                        mTVRingState.setText("戒指：未连接");
                        mTVRingSn.setVisibility(View.GONE);
                        mIvElectricState.setVisibility(View.GONE);
                        mTVElectricState.setVisibility(View.GONE);
                        mTVRingSn.setVisibility(View.GONE);
                        mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
                        mGiRingCharge.setVisibility(View.GONE);
                    }else if (dataBeans.getRingStatus() == 1){
                        mTVRingState.setText("戒指：已连接,未监测");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.in_action_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }else if (dataBeans.getRingStatus() == 2){
                        mTVRingState.setText("戒指：已连接,监测中");
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mTVElectricState.setVisibility(View.VISIBLE);
                        mTVRingSn.setVisibility(View.VISIBLE);
                        mIvDeviceState.setImageResource(R.drawable.in_action_icon);
                        if (dataBeans.getPowerStatus() == 1 ){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        } else if ( dataBeans.getPowerStatus() == 2){
                            mGiRingCharge.setVisibility(View.VISIBLE);
                            mIvElectricState.setVisibility(View.GONE);
                        }else {
                            mIvElectricState.setVisibility(View.VISIBLE);
                            mGiRingCharge.setVisibility(View.GONE);
                        }
                    }
                    MIvLight.setVisibility(View.VISIBLE);
                    mTVDeviceState.setText("活动中");
                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen());
                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature());
                }
            }

            if (dataBeans.getStatus() == 20 || dataBeans.getStatus()==15){
                mIvPhone.setVisibility(View.VISIBLE);
                mIvMessage.setVisibility(View.VISIBLE);
            }else {
                mIvPhone.setVisibility(View.GONE);
                mIvMessage.setVisibility(View.GONE);
            }

            if (dataBeans.getReportStatus() == 1){
                mIvNoBloodOxygen.setVisibility(View.VISIBLE);
            }else {
                mIvNoBloodOxygen.setVisibility(View.GONE);
            }

//            mTvUserName.setText(dataBeans.getTruename()+" "+dataBeans.getDevStatus().getContent());
//            mTVsn.setText(dataBeans.getSn());
//
//            mTVRingState.setText("戒指："+dataBeans.getRingStatus());
//            mIvPhone.setImageResource(R.drawable.phone_icon);
//            mIvMessage.setImageResource(R.drawable.message_icon);
//            MIvLight.setImageResource(R.drawable.light_icon);

            mIvPhone.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(final View view) {
                    customDialogPhone = new CustomDialogPhone(view.getContext(), R.style.CustomDialog);

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
                    });

                    customDialogPhone.show();
                }
            });


            mIvMessage.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {

                    customDialogMessage = new CustomDialogMessage(view.getContext(),R.style.CustomDialog);
                    customDialogMessage.setPhoneNumber(dataBeans.getTelephone());

                    customDialogMessage.show();
                }
            });



//            mTVDeviceState.setText(dataBeans.getDevStatus().getDev_status());
//
//            if (dataBeans.getDevStatus().getContent().equals("闲置")){
//                mIvPhone.setVisibility(View.INVISIBLE);
//                mIvMessage.setVisibility(View.INVISIBLE);
//            }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
//                mIvPhone.setVisibility(View.INVISIBLE);
//                mIvMessage.setVisibility(View.INVISIBLE);
//            }else if (dataBeans.getDevStatus().getContent().equals("维保中")){
//                mIvPhone.setVisibility(View.INVISIBLE);
//                mIvMessage.setVisibility(View.INVISIBLE);
//            }else {
//                mIvPhone.setVisibility(View.VISIBLE);
//                mIvMessage.setVisibility(View.VISIBLE);
//            }






//            if (dataBeans.getDevStatus().getDev_status().equals("未连接")){
//                mTVHeartRate.setText("心率：");
//                mTVBreathing.setText("呼吸：");
//                mTVBloodOxygen.setText("血氧：");
//                mTVRoomTemperature.setText("室温：");
//                MIvLight.setVisibility(View.GONE);
//            }else {
//                MIvLight.setVisibility(View.VISIBLE);
//                if ((Integer)(dataBeans.getHeartrate()) == 0){
//                    mTVHeartRate.setText("心率：");
//                }else {
//                    mTVHeartRate.setText("心率："+dataBeans.getHeartrate());
//                }
//                if ((Integer)(dataBeans.getBreathrate()) == 0){
//                    mTVBreathing.setText("呼吸：");
//                }else {
//                    mTVBreathing.setText("呼吸："+dataBeans.getBreathrate());
//                }
//                if ((Integer)(dataBeans.getBloodoxygen()) == 0){
//                    mTVBloodOxygen.setText("血氧：");
//                }else {
//                    mTVBloodOxygen.setText("血氧："+dataBeans.getBloodoxygen()+"%");
//                }
//                if ((Integer)(dataBeans.getTempetature()) == 0){
//                    mTVRoomTemperature.setText("室温：");
//                }else {
//                    mTVRoomTemperature.setText("室温："+dataBeans.getTempetature()+"℃");
//                }
//            }


//            if (dataBeans.getRingStatus().equals("未连接")){
//                mTVElectricState.setText(dataBeans.getBattery()+"%");
//                mTVElectricState.setVisibility(View.INVISIBLE);
//                mTVRingSn.setText(dataBeans.getRingsn());
//                mTVRingSn.setVisibility(View.INVISIBLE);
//                mIvElectricState.setVisibility(View.INVISIBLE);
//            }else if(dataBeans.getRingStatus().equals("连接中")){
//                mTVElectricState.setText(dataBeans.getBattery()+"%");
//                mTVElectricState.setVisibility(View.INVISIBLE);
//                mTVRingSn.setText(dataBeans.getRingsn());
//                mTVRingSn.setVisibility(View.INVISIBLE);
//                mIvElectricState.setVisibility(View.INVISIBLE);
//            }else{
//                mTVElectricState.setText(dataBeans.getBattery()+"%");
//                mTVElectricState.setVisibility(View.VISIBLE);
//                mTVRingSn.setText(dataBeans.getRingsn());
//                mTVRingSn.setVisibility(View.VISIBLE);
//                mIvElectricState.setVisibility(View.VISIBLE);
//            }
//
//            if ((Integer)(dataBeans.getBattery()) >= 80 && (Integer)(dataBeans.getBattery()) <= 100 ){
//                mIvElectricState.setImageResource(R.drawable.electric80_icon);
//            }else if ((Integer)(dataBeans.getBattery()) >= 60 && (Integer)(dataBeans.getBattery()) < 80){
//                mIvElectricState.setImageResource(R.drawable.electric60_icon);
//            }else if ((Integer)(dataBeans.getBattery()) >= 40 && (Integer)(dataBeans.getBattery()) < 60){
//                mIvElectricState.setImageResource(R.drawable.electric40_icon);
//            }else if ((Integer)(dataBeans.getBattery()) >= 20 && (Integer)(dataBeans.getBattery()) < 40){
//                mIvElectricState.setImageResource(R.drawable.electric20_icon);
//            }else {
//                mIvElectricState.setImageResource(R.drawable.electric0_icon);
//            }

//            if (dataBeans.getDevStatus().getDev_status().equals("监测中")){
//                if (dataBeans.getDevStatus().getContent().equals("维保中")){
//                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
//                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
//                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
//                }else {
//                    mIvDeviceState.setImageResource(R.drawable.monitor_icon);
//                }
//            }else if (dataBeans.getDevStatus().getDev_status().equals("已离床")){
//                if (dataBeans.getDevStatus().getContent().equals("维保中")){
//                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
//                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
//                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
//                }else {
//                    mIvDeviceState.setImageResource(R.drawable.leave_icon);
//                }
//            }else if (dataBeans.getDevStatus().getDev_status().equals("监测中但戒指未连接")){
//                if (dataBeans.getDevStatus().getContent().equals("维保中")){
//                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
//                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
//                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
//                }else {
//                    mIvDeviceState.setImageResource(R.drawable.monitor_not_ring);
//                }
//
//            }else if (dataBeans.getDevStatus().getDev_status().equals("活动中")){
//                if (dataBeans.getDevStatus().getContent().equals("维保中")){
//                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
//                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
//                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
//                }else {
//                    mIvDeviceState.setImageResource(R.drawable.in_action_icon);
//                }
//
//            }else if (dataBeans.getDevStatus().getDev_status().equals("已连接")){
//                if (dataBeans.getDevStatus().getContent().equals("维保中")){
//                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
//                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
//                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
//                }else {
//                    mIvDeviceState.setImageResource(R.drawable.connected_icon);
//                }
//
//            }else if (dataBeans.getDevStatus().getDev_status().equals("未连接")){
//                if (dataBeans.getDevStatus().getContent().equals("维保中")){
//                    mIvDeviceState.setImageResource(R.drawable.maintenance_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("维修中")){
//                    mIvDeviceState.setImageResource(R.drawable.repair_icon);
//                }else if (dataBeans.getDevStatus().getContent().equals("闲置")){
//                    mIvDeviceState.setImageResource(R.drawable.unuse_icon);
//                }else {
//                    mIvDeviceState.setImageResource(R.drawable.not_connected_icon);
//                }
//
//            }

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


    public static String timestamp2Date(String str_num,String format ) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(Long.parseLong(str_num)));

            return date;
        } else {
            String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));

            return date;
        }
    }
   
}
