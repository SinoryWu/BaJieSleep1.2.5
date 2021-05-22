package com.example.bajiesleep.fragment.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.CustomDialogMessage;
import com.example.bajiesleep.DialogTokenIntent;
import com.example.bajiesleep.OnMultiClickListener;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ReportListActivity;
import com.example.bajiesleep.ReportListDialog;
import com.example.bajiesleep.ReportPdfView;
import com.example.bajiesleep.ReportPdfView1;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.UserListAcivity;
import com.example.bajiesleep.entity.ReportListResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportListAdapter extends RecyclerView.Adapter<ReportListAdapter.InnerHolder> {
    private Context context;
    private  List<ReportListResponse.DataBeanX.DataBean> dataBeans;
    private ReportListAdapter.OnItemClickListener mClickListener;
    private List<String> url = new ArrayList<>();
    private List<String> truename = new ArrayList<>();
    private List<String> reportCreateTime = new ArrayList<>();
    ReportListDialog reportListDialog ;


    public ReportListAdapter(List<ReportListResponse.DataBeanX.DataBean> dataBeans, Context context) {

        this.dataBeans =dataBeans;
        this.context = context;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener{

        void onItemClick(String url,String truename,String reportCreateTime);
    }

    public void setOnItemClickListener(ReportListAdapter.OnItemClickListener listener){
        this.mClickListener  = listener;
    }

    /**
     * 标记展开的item
     */
    private int opened = -1;

    /**
     * 这个方法用于创建条目View
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ReportListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的这个view其实就是条目的界面
        //两个步骤
        //1.拿到veiw
        //2.创建InnerHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list_item2,parent,false);
//        View view = View.inflate(parent.getContext(), R.layout.report_list_item, null);
        return new InnerHolder(view);
    }
    /**
     * 这个方法用于绑定holder，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ReportListAdapter.InnerHolder holder, final int position) {
        //在这里设置数据
//        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);

        holder.setData(position,dataBeans.get(position),context);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mClickListener != null){
//                    mClickListener.onItemClick(url.get(position),truename.get(position),reportCreateTime.get(position));
//                }
//            }
//        });
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



    public class InnerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvUserName,mTvAhi,mTvDate,mTvSn,mTvSleepTime,mTvCreateTime,mTvQuality,mTvHospitalName;
        private RelativeLayout mRlListInfo,mRlEdit,mRlDownLoad,mRlDown,mRlUp,mRlItem;
        private ImageView down,up,mIvChildren;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            mTvUserName = itemView.findViewById(R.id.report_item_userName);
            mTvAhi = itemView.findViewById(R.id.report_item_ahi);
            mTvDate = itemView.findViewById(R.id.report_item_date);
            mTvSn = itemView.findViewById(R.id.report_item_sn);
            mTvSleepTime = itemView.findViewById(R.id.report_item_sleeptime);
            mTvCreateTime = itemView.findViewById(R.id.report_item_createTime);
            mTvQuality = itemView.findViewById(R.id.report_item_quality);
            mTvHospitalName= itemView.findViewById(R.id.report_item_hospitalName);
            mRlListInfo = itemView.findViewById(R.id.report_list_rlInfo);
            mRlEdit  =itemView.findViewById(R.id.report_item_edit);
            mRlDownLoad = itemView.findViewById(R.id.report_item_download);
            mRlDown = itemView.findViewById(R.id.report_item_down);
            mRlUp = itemView.findViewById(R.id.report_item_up);
            mRlItem = itemView.findViewById(R.id.rl_report_item);
            down = itemView.findViewById(R.id.report_item_down1);
            up = itemView.findViewById(R.id.report_item_up1);
            mIvChildren = itemView.findViewById(R.id.report_item_children);
            mRlListInfo.setVisibility(View.GONE);
            mRlUp.setVisibility(View.GONE);
            mRlEdit.setVisibility(View.GONE);
            mRlDownLoad.setVisibility(View.GONE);
            mRlDown.setVisibility(View.VISIBLE);


//            reportCard = itemView.findViewById(R.id.cv_report_item);

            mRlDown.setOnClickListener(this);
            mRlUp.setOnClickListener(this);
        }


        public void setData(int position, final ReportListResponse.DataBeanX.DataBean dataBeans, final Context context) {

            mIvChildren.setVisibility(View.GONE);
            if (dataBeans.getModeType() == 1){
                mIvChildren.setVisibility(View.VISIBLE);
            }else if (dataBeans.getModeType() == 0){
                mIvChildren.setVisibility(View.GONE);
            }

            mRlEdit.setOnClickListener(new OnMultiClickListener() {
                @Override
                public void onMultiClick(View view) {
                    reportListDialog = new ReportListDialog(view.getContext(),R.style.CustomDialog);
                    reportListDialog.setHospitalId(String.valueOf(dataBeans.getId()));
                    reportListDialog.show();
                }
            });





//            mRlDown.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick( View view) {
//                    RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
//                    linearParams.width = getCardWith();
//                    linearParams.height = getCardHeight2();
//                    cardView.setLayoutParams(linearParams);
//                    mRlListInfo.setVisibility(View.VISIBLE);
//                    mRlUp.setVisibility(View.VISIBLE);
//                    mRlEdit.setVisibility(View.VISIBLE);
//                    mRlDownLoad.setVisibility(View.VISIBLE);
//                    mRlDown.setVisibility(View.GONE);
//                    Toast.makeText(context,"展开"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
//
//                }
//            });
//
//            mRlUp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick( View view) {
//                    RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
//                    linearParams.width = getCardWith();
//                    linearParams.height = getCardHeight();
//                    cardView.setLayoutParams(linearParams);
//
//                    mRlListInfo.setVisibility(View.GONE);
//                    mRlUp.setVisibility(View.GONE);
//                    mRlEdit.setVisibility(View.GONE);
//                    mRlDownLoad.setVisibility(View.GONE);
//
//                    mRlDown.setVisibility(View.VISIBLE);
//                    Toast.makeText(context,"收起"+getAdapterPosition(),Toast.LENGTH_SHORT).show();
//                }
//            });





            truename.add(dataBeans.getTruename());
            url.add(dataBeans.getReportUrl());
            reportCreateTime.add(timestamp2Date(String.valueOf(dataBeans.getCreateTime()),"MM月 dd日 HH:mm"));
            mTvUserName.setText(dataBeans.getTruename());


            String data = timestamp2Date(String.valueOf(dataBeans.getCreateTime()),"yyyy-MM-dd");
            mTvDate.setText(data);

            mTvSn.setText(dataBeans.getSn());

            String sleepTime = dataBeans.getSleeptime();
            sleepTime = sleepTime.replace('-','~');
            StringBuilder sleepTime1 = new StringBuilder(sleepTime);
            sleepTime1.insert(5," ");
            sleepTime1.insert(7," ");
            sleepTime = sleepTime1.toString();
            mTvSleepTime.setText("睡眠监测时段："+sleepTime);


            String createtime = timestamp2Date(String.valueOf(dataBeans.getCreateTime()),"yyyy-MM-dd HH:mm:ss");
            String reportcreatetime = timestamp2Date(String.valueOf(dataBeans.getCreateTime()),"MM月 dd日 HH:mm");
            mTvCreateTime.setText("报告生成时间："+createtime);

            float ahi = Float.parseFloat(dataBeans.getAhi());




            if(dataBeans.getQuality() == 0){

                mTvQuality.setText("无报报告");
                mTvQuality.setTextColor(Color.parseColor("#f45c50"));
                mRlEdit.setVisibility(View.GONE);
                mRlDownLoad.setVisibility(View.GONE);
                mTvAhi.setText("AHI:--");
                mTvAhi.setTextColor(Color.parseColor("#817889"));
                mRlItem.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onMultiClick(View view) {
                        ToastUtils.showTextToast(context,"无报报告");
                    }
                });

            }else if (dataBeans.getQuality() == 1){
                if (dataBeans.getAhiLevel() == 1){
                    mTvAhi.setTextColor(Color.parseColor("#6cc291"));
                }else if (dataBeans.getAhiLevel() == 2 ){
                    mTvAhi.setTextColor(Color.parseColor("#596afd"));
                }else if (dataBeans.getAhiLevel() == 3){
                    mTvAhi.setTextColor(Color.parseColor("#f39920"));
                }else if (dataBeans.getAhiLevel() == 4) {
                    mTvAhi.setTextColor(Color.parseColor("#f45c50"));
                }
                mTvQuality.setText("正常报告");
                mTvQuality.setTextColor(Color.parseColor("#817889"));
                mTvAhi.setText("AHI:"+dataBeans.getAhi());
                mRlItem.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onMultiClick(View view) {

                        Intent intent = new Intent(context, ReportPdfView1.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("reportUrl",dataBeans.getReportUrl());
                        bundle.putString("reportTrueName",dataBeans.getTruename());
                        bundle.putString("reportCreateTime",timestamp2Date(String.valueOf(dataBeans.getCreateTime()),"MM月 dd日 HH:mm"));
                        bundle.putString("reportID", String.valueOf(dataBeans.getId()));
                        bundle.putString("hospitalId", String.valueOf(dataBeans.getHospitalid()));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }else if(dataBeans.getQuality() == 2){
                mTvQuality.setText("缺少血氧");
                mTvQuality.setTextColor(Color.parseColor("#f45c50"));
                mRlEdit.setVisibility(View.GONE);
                mRlDownLoad.setVisibility(View.GONE);
                mTvAhi.setTextColor(Color.parseColor("#817889"));
                mTvAhi.setText("AHI:--");
                mRlItem.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onMultiClick(View view) {
                        ToastUtils.showTextToast(context,"缺少血氧");
                    }
                });

            }else if (dataBeans.getQuality() == 3){
                mTvQuality.setText("无效报告");
                mTvQuality.setTextColor(Color.parseColor("#f45c50"));
                mRlEdit.setVisibility(View.GONE);
                mRlDownLoad.setVisibility(View.GONE);
                mTvAhi.setTextColor(Color.parseColor("#817889"));
                mTvAhi.setText("AHI:--");
                mRlItem.setOnClickListener(new OnMultiClickListener() {
                    @Override
                    public void onMultiClick(View view) {
                        ToastUtils.showTextToast(context,"无效报告");
                    }
                });

            }


            mTvHospitalName.setText("机构："+dataBeans.getHospitalName());


            if (position == opened){

                mRlListInfo.setVisibility(View.VISIBLE);

                mRlUp.setVisibility(View.VISIBLE);
                mRlDown.setVisibility(View.GONE);

                if (dataBeans.getQuality() == 0 || dataBeans.getQuality()==3 || dataBeans.getQuality() == 2){
                    mRlEdit.setVisibility(View.GONE);
                    mRlDownLoad.setVisibility(View.GONE);
                }else {
                    mRlEdit.setVisibility(View.VISIBLE);
                    mRlDownLoad.setVisibility(View.VISIBLE);
                }

            }else {

                mRlListInfo.setVisibility(View.GONE);
                mRlUp.setVisibility(View.GONE);
                mRlEdit.setVisibility(View.GONE);
                mRlDownLoad.setVisibility(View.GONE);
                mRlDown.setVisibility(View.VISIBLE);
            }

        }


        @Override
        public void onClick(View view) {
            if (opened == getAdapterPosition()){
                //当点击的item已经被展开了, 就关闭.
                opened = -1;
                notifyItemChanged(getAdapterPosition());
            }
            else {
                int oldOpened = opened;
                opened = getAdapterPosition();
                notifyItemChanged(oldOpened);






                notifyItemChanged(opened);
            }
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
