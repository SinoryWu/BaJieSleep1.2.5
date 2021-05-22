package com.example.bajiesleep.fragment.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.Api;
import com.example.bajiesleep.DownloadUtil;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ReportPdfView;
import com.example.bajiesleep.ReportPdfView1;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.UserListAcivity;
import com.example.bajiesleep.entity.UserListInfoResponse;
import com.example.bajiesleep.entity.UserListResponse;
import com.example.bajiesleep.entity.UserNewPdfResponse;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UserListInfoAdapter extends RecyclerView.Adapter<UserListInfoAdapter.InnerHolder> {
    private Context context;
     private List<UserListInfoResponse.DataBeanX.DataBean.ReportBean> reportBeans;
    private  List<UserListInfoResponse.DataBeanX.DataBean> dataBeans;
    UserListAdapter userListAdapter;




//    private String mTitles[] = {
//            "全部",
//            "万康体检",
//            "明州医院",
//            "其他医院"};
//    public UserListInfoAdapter(List<UserListInfoResponse.DataBeanX.DataBean.ReportBean> reportBeans, Context context) {
//        this.reportBeans =reportBeans;
//        this.dataBeans = dataBeans;
//        this.context = context;
//    }

    public UserListInfoAdapter(List<UserListInfoResponse.DataBeanX.DataBean.ReportBean> reportBeans, Context context) {
        this.reportBeans =reportBeans;
        this.dataBeans = dataBeans;
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
    public UserListInfoAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的这个view其实就是条目的界面
        //两个步骤
        //1.拿到veiw
        //2.创建InnerHolder

        View view = View.inflate(parent.getContext(), R.layout.user_report_list_item, null);
        return new InnerHolder(view);
    }
    /**
     * 这个方法用于绑定holder，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull UserListInfoAdapter.InnerHolder holder, int position) {
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
        private TextView mTvUserReportData,mTvUserReportQuality;
        private ImageView mTvUserReportDownload;
        private LinearLayout mLlUserReportDownload;
        UserListAdapter userListAdapter;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            mTvUserReportData = itemView.findViewById(R.id.user_list_report_item_data);
            mTvUserReportQuality  = itemView.findViewById(R.id.user_list_report_item_quality);
            mTvUserReportDownload = itemView.findViewById(R.id.user_list_report_item_download);
            mLlUserReportDownload = itemView.findViewById(R.id.user_list_report_item_download_linear);


        }


        public void setData(final UserListInfoResponse.DataBeanX.DataBean.ReportBean reportBeans ,final Context context) {

            String data = timestamp2Date(String.valueOf(reportBeans.getCreateTime()),"yyyy-MM-dd");
            mTvUserReportData.setText(data);
            final String data1 = timestamp2Date(String.valueOf(reportBeans.getCreateTime()),"MM月 dd日 HH:mm");


            Log.d("reporturlbeans",reportBeans.getReportUrl());

            Log.d("getname",getReportNameToSp("reportName",""));
            mLlUserReportDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    download(Api.REPORT_URL+reportBeans.getReportUrl()+".pdf");
                }
            });



            if (reportBeans.getQuality() == 1){
                mTvUserReportQuality.setText("有效报告");
                mTvUserReportQuality.setTextColor(Color.parseColor("#352641"));
                mTvUserReportDownload.setVisibility(View.VISIBLE);
                mTvUserReportData.setTextColor(Color.parseColor("#6cc291"));
                mTvUserReportData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ReportPdfView1.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("reportUrl",reportBeans.getReportUrl());
                        bundle.putString("reportTrueName",getReportNameToSp("reportName",""));
                        bundle.putString("reportCreateTime",data1);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
            }else if (reportBeans.getQuality() == 2){
                mTvUserReportQuality.setText("缺少血氧");
                mTvUserReportQuality.setTextColor(Color.parseColor("#f45c50"));
                mTvUserReportDownload.setVisibility(View.GONE);
                mTvUserReportData.setTextColor(Color.parseColor("#f45c50"));
                mTvUserReportData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showTextToast(context,"缺少血氧");
                    }
                });

            }else if (reportBeans.getQuality() == 3){
                mTvUserReportQuality.setText("无效报告");
                mTvUserReportQuality.setTextColor(Color.parseColor("#f45c50"));
                mTvUserReportDownload.setVisibility(View.GONE);
                mTvUserReportData.setTextColor(Color.parseColor("#f45c50"));
                mTvUserReportData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showTextToast(context,"无效报告");
                    }
                });

            }


        }



        /**
         * 下载pdf文件到本地
         *
         * @param url 文件url
         */
        private void download(String url) {
            DownloadUtil.download(url, context.getCacheDir() + "/"+getReportNameToSp("reportName","")+" "+timestamp2Date(String.valueOf(reportBeans.get(getAdapterPosition()).getCreateTime()),"MM月 dd日 HH:mm")+".pdf",
                    new DownloadUtil.OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess(final String path) {
                            Log.d("MainActivity", "onDownloadSuccess: " + path);
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//
                                            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//给临时权限
                                            sharingIntent.setType("application/pdf");//根据文件类型设定type
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                sharingIntent.putExtra(Intent.EXTRA_STREAM,
                                                        FileProvider.getUriForFile(context, context.getPackageName() +".fileProvider",
                                                                new File(path)));
                                            }else {
                                                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
                                            }

                                            context.startActivity(Intent.createChooser(sharingIntent, "分享"));


                                }
                            });
                        }

                        @Override
                        public void onDownloading(int progress) {
                            Log.d("MainActivity", "onDownloading: " + progress);
                        }

                        @Override
                        public void onDownloadFailed(String msg) {
                            Log.d("MainActivity", "onDownloadFailed: " + msg);
                        }
                    });
        }

    }

    protected String getReportNameToSp(String key, String val) {
        SharedPreferences sp1 = context.getSharedPreferences("reportName", MODE_PRIVATE);
        String reportName = sp1.getString("reportName", "没有reportName");


        return reportName;
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
