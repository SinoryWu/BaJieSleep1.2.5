package com.example.bajiesleep.fragment.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.R;
import com.example.bajiesleep.entity.SearchUserInfoResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListViewAdapter3 extends RecyclerView.Adapter<ListViewAdapter3.InnerHolder> {
    private Context context;
    private  List<SearchUserInfoResponse.DataBeanX.DataBean> dataBeans;
    private List<String> trueName = new ArrayList<>();
    private List<String> mobile = new ArrayList<>();
    private List<String> uid = new ArrayList<>();
    private ListViewAdapter3.OnItemClickLitener3 mClickListener3;



//    private String mTitles[] = {
//            "全部",
//            "万康体检",
//            "明州医院",
//            "其他医院"};
    public ListViewAdapter3(List<SearchUserInfoResponse.DataBeanX.DataBean> dataBeans, Context context) {
        this.dataBeans =dataBeans;
        this.context = context;
    }

    //设置回调接口
    public interface OnItemClickLitener3{


        void onItemClick3(String trueName, String mobile, String uid, int position);
    }

    public void setOnItemClickLitener3(ListViewAdapter3.OnItemClickLitener3 listener){
        this.mClickListener3  = listener;
    }


    /**
     * 这个方法用于创建条目View
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ListViewAdapter3.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的这个view其实就是条目的界面
        //两个步骤
        //1.拿到veiw
        //2.创建InnerHolder

        View view = View.inflate(parent.getContext(), R.layout.recycyler_item2, null);
        return new InnerHolder(view);
    }
    /**
     * 这个方法用于绑定holder，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter3.InnerHolder holder, final int position) {
        //在这里设置数据
//        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);

        holder.setData(dataBeans.get(position),context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener3 != null){
                    mClickListener3.onItemClick3(trueName.get(position),mobile.get(position),uid.get(position),position );
                }
            }
        });
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

        private TextView searchName,searchSex,searchTime;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            searchName = itemView.findViewById(R.id.search_user_name);
            searchSex = itemView.findViewById(R.id.search_user_sex);
            searchTime = itemView.findViewById(R.id.search_user_time);



        }


        public void setData(final SearchUserInfoResponse.DataBeanX.DataBean dataBeans, Context context) {
            searchName.setText(dataBeans.getTruename());
            trueName.add(dataBeans.getTruename());
            mobile.add(dataBeans.getMobile());
            uid.add(String.valueOf(dataBeans.getUid()));

            if (dataBeans.getSex().equals("1")){
                searchSex.setText("男");
            }else {
                searchSex.setText("女");
            }
            String date1 = timestamp2Date(String.valueOf(dataBeans.getCreate_time()),"yyyy-MM-dd");
            searchTime.setText("添加时间："+date1);
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
