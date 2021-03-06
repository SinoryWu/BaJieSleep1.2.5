package com.example.bajiesleep.fragment.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.DeviceEditDialog;
import com.example.bajiesleep.DeviceRecoverDialog;
import com.example.bajiesleep.DeviceStopDialog;
import com.example.bajiesleep.LendActivity;
import com.example.bajiesleep.OnMultiClickListener;
import com.example.bajiesleep.R;
import com.example.bajiesleep.RecoverDeviceActivity;
import com.example.bajiesleep.ReportListDialog;
import com.example.bajiesleep.entity.DeviceListResponse;
import com.example.bajiesleep.entity.ReportListResponse;
import com.example.bajiesleep.entity.UserListResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.InnerHolder> {
    private Context context;
    private List<DeviceListResponse.DataBean> dataBeans;
    private DeviceListAdapter.OnItemClickListener mClickListener;

    DeviceStopDialog deviceStopDialog;
    DeviceRecoverDialog deviceRecoverDialog;
    DeviceEditDialog deviceEditDialog;


    public DeviceListAdapter(List<DeviceListResponse.DataBean> dataBeans, Context context) {

        this.dataBeans = dataBeans;
        this.context = context;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {


        void onItemClick(String url, String truename, String reportCreateTime);
    }

    public void setOnItemClickListener(DeviceListAdapter.OnItemClickListener listener) {
        this.mClickListener = listener;
    }


    /**
     * ??????????????????????????????View
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public DeviceListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //??????????????????view???????????????????????????
        //????????????
        //1.??????veiw
        //2.??????InnerHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_item, parent, false);
//        View view = View.inflate(parent.getContext(), R.layout.report_list_item, null);
        return new InnerHolder(view);
    }

    /**
     * ????????????????????????holder???????????????????????????
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull DeviceListAdapter.InnerHolder holder, final int position) {
        //?????????????????????
//        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);

        holder.setData(position, dataBeans.get(position), context);


    }


    /**
     * ??????????????????
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (dataBeans != null) {
            return dataBeans.size();
        }
        return 0;

    }


    public class InnerHolder extends RecyclerView.ViewHolder {

        private TextView mDeviceSn, mUserName, mDeviceState, mHospitalName, mTvDeviceState;
        private ImageView mDeviceActionLend, mDeviceActionRecover, mDeviceActionEdit, mDeviceActionStop;
        private String DeviceStopState;
        private String DeviceEditState;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mDeviceSn = itemView.findViewById(R.id.device_list_item_sn);
            mUserName = itemView.findViewById(R.id.device_list_item_name);
            mDeviceState = itemView.findViewById(R.id.device_list_item_state);
            mHospitalName = itemView.findViewById(R.id.device_list_item_hopitalname);
            mDeviceActionLend = itemView.findViewById(R.id.device_list_item_action_lend);
            mDeviceActionRecover = itemView.findViewById(R.id.device_list_item_action_recover);
            mDeviceActionEdit = itemView.findViewById(R.id.device_list_item_action_edit);
            mDeviceActionStop = itemView.findViewById(R.id.device_list_item_action_stop);
            mTvDeviceState = itemView.findViewById(R.id.tv_device_list_item_device_state);


        }


        public void setData(int position, final DeviceListResponse.DataBean dataBean, final Context context) {


            mDeviceSn.setText(dataBean.getSn());
            mTvDeviceState.setVisibility(View.GONE);
            if (dataBean.getTruename().isEmpty()) {
                mUserName.setText("?????????--");
            } else {
                mUserName.setText("?????????" + dataBean.getTruename());
            }

            mDeviceState.setText("?????????" + dataBean.getDevStatus());


            mHospitalName.setText(getDeviceHosNameToSp("deviceHospitalName", ""));

            mDeviceActionLend.setVisibility(View.GONE);
            mDeviceActionRecover.setVisibility(View.GONE);
            mDeviceActionEdit.setVisibility(View.GONE);
            mDeviceActionStop.setVisibility(View.GONE);

            if (dataBean.getDevStatus().equals("??????")) {

                mDeviceActionLend.setVisibility(View.VISIBLE);
                mDeviceActionRecover.setVisibility(View.GONE);
                mDeviceActionEdit.setVisibility(View.GONE);
                mDeviceActionStop.setVisibility(View.GONE);



            } else if (dataBean.getDevStatus().equals("?????????")) {
                mDeviceActionLend.setVisibility(View.GONE);
                mDeviceActionRecover.setVisibility(View.GONE);
                mDeviceActionEdit.setVisibility(View.GONE);
                mDeviceActionStop.setVisibility(View.VISIBLE);

            } else if (dataBean.getDevStatus().equals("??????") || dataBean.getDevStatus().equals("??????")) {
                mDeviceActionLend.setVisibility(View.GONE);
                mDeviceActionRecover.setVisibility(View.GONE);
                mDeviceActionEdit.setVisibility(View.VISIBLE);
                mDeviceActionStop.setVisibility(View.GONE);
            } else {
                mDeviceActionLend.setVisibility(View.GONE);
                mDeviceActionRecover.setVisibility(View.VISIBLE);
                mDeviceActionEdit.setVisibility(View.GONE);
                mDeviceActionStop.setVisibility(View.GONE);

            }


            mDeviceActionStop.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {

                    deviceStopDialog = new DeviceStopDialog(context, dataBean.getSn(), R.style.CustomDialog);
                    deviceStopDialog.setConfirm("??????", new DeviceStopDialog.IOnConfirmListener() {
                        @Override
                        public void onConfirm(DeviceStopDialog dialog, String state) {

                               mTvDeviceState.setText(state);
                               mDeviceActionLend.setVisibility(View.GONE);
                               mDeviceActionRecover.setVisibility(View.VISIBLE);
                               mDeviceActionEdit.setVisibility(View.GONE);
                               mDeviceActionStop.setVisibility(View.GONE);
                               mDeviceState.setText("??????????????????" );

                        }
                    });
                    deviceStopDialog.setCanceledOnTouchOutside(false);

                    deviceStopDialog.show();
                }
            });

            mDeviceActionRecover.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    deviceRecoverDialog = new DeviceRecoverDialog(context, dataBean.getSn(), R.style.CustomDialog);

                    deviceRecoverDialog.show();
                    deviceRecoverDialog.setCanceledOnTouchOutside(false);
                }
            });
            mDeviceActionLend.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    Intent intent = new Intent(context,LendActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("sn",dataBean.getSn());
                    bundle.putString("deviceState","lendDevice");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
//                    ((Activity) context).finish();
                }
            });

            DeviceEditState = String.valueOf(mDeviceState.getText());
            mDeviceActionEdit.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    Log.d("asduh130",DeviceEditState);
                    deviceEditDialog = new DeviceEditDialog(context, dataBean.getSn(),DeviceEditState, R.style.CustomDialog);
                    deviceEditDialog.setConfirm("??????", new DeviceEditDialog.IOnConfirmListener() {
                        @Override
                        public void onConfirm(DeviceEditDialog dialog, String state) {
                            if (state.equals("1")){
                                mDeviceActionLend.setVisibility(View.VISIBLE);
                                mDeviceActionRecover.setVisibility(View.GONE);
                                mDeviceActionEdit.setVisibility(View.GONE);
                                mDeviceActionStop.setVisibility(View.GONE);
                                mDeviceState.setText("???????????????" );

                            }else if(state.equals("5")){
                                mDeviceActionLend.setVisibility(View.GONE);
                                mDeviceActionRecover.setVisibility(View.GONE);
                                mDeviceActionEdit.setVisibility(View.VISIBLE);
                                mDeviceActionStop.setVisibility(View.GONE);
                                mDeviceState.setText("???????????????" );
                                DeviceEditState = String.valueOf(mDeviceState.getText());
                            }else if (state.equals("10")){
                                mDeviceActionLend.setVisibility(View.GONE);
                                mDeviceActionRecover.setVisibility(View.GONE);
                                mDeviceActionEdit.setVisibility(View.VISIBLE);
                                mDeviceActionStop.setVisibility(View.GONE);
                                mDeviceState.setText("???????????????" );
                                DeviceEditState = String.valueOf(mDeviceState.getText());
                            }
                        }
                    });

                    deviceEditDialog.show();
                }
            });
//            mDeviceActionLend.setOnClickListener(new OnMultiClickListener() {
//                @Override
//                public void onMultiClick(View view) {
//                    Intent intent = new Intent(context, LendActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("sn",dataBean.getSn());
//                    intent.putExtras(bundle);
//                    context.startActivity(intent);
//                }
//            });


//            if (getLendDeviceStateToSp("lendDeviceState","").equals("ok")){
//                mDeviceActionLend.setVisibility(View.GONE);
//                mDeviceActionRecover.setVisibility(View.GONE);
//                mDeviceActionEdit.setVisibility(View.GONE);
//                mDeviceActionStop.setVisibility(View.VISIBLE);
//                mDeviceState.setText("??????????????????" );
//            }


        }

        public String getStopDeviceStateToSp(String key, String val) {
            SharedPreferences sp = context.getSharedPreferences("stopDeviceState", MODE_PRIVATE);
            String stopDeviceState = sp.getString("stopDeviceState", "??????stopDeviceState");
            return stopDeviceState;
        }


    }


    protected String getDeviceHosNameToSp(String key, String val) {
        SharedPreferences sp1 = context.getSharedPreferences("deviceHospitalName", MODE_PRIVATE);
        String reportName = sp1.getString("deviceHospitalName", "??????deviceHospitalName");


        return reportName;
    }
    public String getStopDeviceStateToSp(String key, String val){
        SharedPreferences sp = context.getSharedPreferences("stopDeviceState", MODE_PRIVATE);
        String stopDeviceState = sp.getString("stopDeviceState","??????stopDeviceState");
        return stopDeviceState;
    }




}
