package com.example.bajiesleep.fragment.recyclerview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.bajiesleep.RecoverReportPdfView;
import com.example.bajiesleep.ReportPdfView;
import com.example.bajiesleep.ReportWebViewActivity;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.entity.DeviceRecoverResponse;
import com.example.bajiesleep.entity.EquipmentResponse;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ListViewAdapter4 extends RecyclerView.Adapter<ListViewAdapter4.InnerHolder> {
    private Context context;
    private  List<DeviceRecoverResponse.DataBean.ReportBean> reportBeans;


//    private String mTitles[] = {
//            "全部",
//            "万康体检",
//            "明州医院",
//            "其他医院"};
    public ListViewAdapter4(List<DeviceRecoverResponse.DataBean.ReportBean> reportBeans, Context context) {
        this.reportBeans =reportBeans;
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
    public ListViewAdapter4.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的这个view其实就是条目的界面
        //两个步骤
        //1.拿到veiw
        //2.创建InnerHolder

        View view = View.inflate(parent.getContext(), R.layout.recycler_report_item, null);
        return new InnerHolder(view);
    }
    /**
     * 这个方法用于绑定holder，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter4.InnerHolder holder, int position) {
        //在这里设置数据
//        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);

        holder.setData(reportBeans.get(position),context);

    }
    /**
     * 返回条目个数
     * @return
     */
    @Override
    public int getItemCount() {
        if(reportBeans != null){
            return reportBeans.size();
        }
        return 0;

    }



    public class InnerHolder extends RecyclerView.ViewHolder {
        private TextView mTvReportId,mTvReportState;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            mTvReportId = itemView.findViewById(R.id.rv_item_report_id);
            mTvReportState = itemView.findViewById(R.id.rv_item_report_state);


        }


        public void setData(final DeviceRecoverResponse.DataBean.ReportBean reportBeans, final Context context) {



            if (reportBeans.getQuality() == 1){
                mTvReportId.setText(reportBeans.getReport_id());
                mTvReportId.setTextColor(Color.parseColor("#6cc291"));
                mTvReportState.setText("有效");
                mTvReportId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), RecoverReportPdfView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("reportUrl",reportBeans.getReportUrl());
                        bundle.putString("reportId",reportBeans.getReport_id());

                        intent.putExtras(bundle);
                        view.getContext().startActivity(intent);


//                        ToastUtils.showTextToast(view.getContext(),"跳转id网页");
                    }
                });
            }else if (reportBeans.getQuality() == 2){
                mTvReportId.setText(reportBeans.getReport_id());
                mTvReportId.setTextColor(Color.parseColor("#f45c50"));
                mTvReportState.setText("缺少血氧");
                mTvReportId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), RecoverReportPdfView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("reportUrl",reportBeans.getReportUrl());
                        bundle.putString("reportId",reportBeans.getReport_id());
                        intent.putExtras(bundle);
                        view.getContext().startActivity(intent);
//                        ToastUtils.showTextToast(view.getContext(),"跳转id网页");
                    }
                });
            }else {
                mTvReportId.setText(reportBeans.getReport_id());
                mTvReportId.setTextColor(Color.parseColor("#f45c50"));
                mTvReportState.setText("无效报告");
            }



        }

    }


}
