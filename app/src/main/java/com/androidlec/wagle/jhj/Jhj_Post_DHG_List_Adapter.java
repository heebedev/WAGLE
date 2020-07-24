package com.androidlec.wagle.jhj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Jhj_Post_DHG_List_Adapter extends RecyclerView.Adapter<Jhj_Post_DHG_List_Adapter.ViewHolder> {


    Context context;
    private ArrayList<Jhj_BookReport_DTO> data;

    // Activity에서 클릭이벤트를 사용하기위한 준비
    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }

    private Jhj_Post_DHG_List_Adapter.OnItemClickListener mListener = null;
    private Jhj_Post_DHG_List_Adapter.OnItemLongClickListener mLongListener = null;

    public void setOnItemClickListener(Jhj_Post_DHG_List_Adapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(Jhj_Post_DHG_List_Adapter.OnItemLongClickListener listener) {
        this.mLongListener = listener;
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1 ;

        ViewHolder(View itemView) {
            super(itemView) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 선택한 position 값 구하기 (번호)
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClickListener(v, position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (mLongListener != null) {
                            mLongListener.onItemLongClickListener(v, position);
                            return true;
                        }
                    }

                    return false;
                }
            });

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.Post_Notice_List_Item_TextView);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    Jhj_Post_DHG_List_Adapter(ArrayList<Jhj_BookReport_DTO> list) {
        data = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public Jhj_Post_DHG_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.jhj_post_notice_list_item, parent, false) ;
        Jhj_Post_DHG_List_Adapter.ViewHolder vh = new Jhj_Post_DHG_List_Adapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(Jhj_Post_DHG_List_Adapter.ViewHolder holder, int position) {
        String ListItem = data.get(position).getWcName() + " - " + data.get(position).getuName();
        holder.textView1.setText(ListItem) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return data.size() ;
    }
}