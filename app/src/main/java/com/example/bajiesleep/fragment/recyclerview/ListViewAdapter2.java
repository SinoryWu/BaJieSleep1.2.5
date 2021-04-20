package com.example.bajiesleep.fragment.recyclerview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bajiesleep.CustomDialogMessage;
import com.example.bajiesleep.CustomDialogPhone;
import com.example.bajiesleep.R;
import com.example.bajiesleep.ToastUtils;
import com.example.bajiesleep.entity.EquipmentResponse;
import com.example.bajiesleep.entity.TestBean;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ListViewAdapter2 extends RecyclerView.Adapter<ListViewAdapter2.InnerHolder> implements Filterable {

//    private final List<TestBean> dataBeans;
    private List<String> mSourceList = new ArrayList<>();
    private List<String> mFilterList = new ArrayList<>();
    private Context mContext;
    private OnItemClickLitener   mClickListener;

    //设置回调接口
    public interface OnItemClickLitener{


        void onItemClick(String data,int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener listener){
        this.mClickListener  = listener;
    }
//    public void appendList(Context context,List<String> list) {
//        this.mSourceList = list;
//        this.mContext =context;
//        //这里需要初始化filterList
//        mFilterList = list;
//
//    }

//    public void setListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }


    public ListViewAdapter2(List<String> list,Context context) {
        this.mSourceList = list;
//        this.mContext =context;
        this.mContext=context;
        mFilterList = list;

    }

//    public void setListener(OnItemClickListener listener) {
//        this.mListener = listener;
//    }
//    public interface OnItemClickListener {
//        void onItemClick(String data);
//    }



    /**
     * 这个方法用于创建条目View
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ListViewAdapter2.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的这个view其实就是条目的界面
        //两个步骤
        //1.拿到veiw
        //2.创建InnerHolder

        View view = View.inflate(parent.getContext(), R.layout.recycyler_item1, null);
        return new InnerHolder(view);
    }
    /**
     * 这个方法用于绑定holder，一般用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter2.InnerHolder holder, final int position) {
        //在这里设置数据
//        EquipmentResponse.DataBean dataBean = dataBeanList.get(position);

//        holder.setData(dataBeans.get(position));

        holder.mTvSn.setText(mFilterList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener!=null){
                    mClickListener.onItemClick(mFilterList.get(position),position);
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
//        if (dataBeans != null) {
//            return dataBeans.size();
//        }
//        return 0;

            return mFilterList.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            //执行过滤操作
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    //没有过滤的内容，则使用源数据
                    mFilterList = mSourceList;
                } else {
                    List<String> filteredList = new ArrayList<>();
                    for (String str : mSourceList) {
                        //这里根据需求，添加匹配规则
                        if (str.contains(charString)) {
                            filteredList.add(str);
                        }
                    }

                    mFilterList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }
            //把过滤后的值返回出来
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (List<String>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class InnerHolder extends RecyclerView.ViewHolder {


        private TextView mTvSn ;
        private EditText editText;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
          mTvSn=itemView.findViewById(R.id.rv1_sn);
            editText = itemView.findViewById(R.id.search_1);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    onItemClickListener.onItemClick(view,mSourceList.get(getLayoutPosition()));
//                    Toast.makeText(mContext,mFilterList.get(getLayoutPosition()),Toast.LENGTH_SHORT).show();
//                }
//            });

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });



        }




    }
//    public interface OnItemClickListener {
//        void onItemClick(View view,String data);
//    }
//
//    public void setListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }

}
