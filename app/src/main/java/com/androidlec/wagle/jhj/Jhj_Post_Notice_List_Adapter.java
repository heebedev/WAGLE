package com.androidlec.wagle.jhj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.R;

import java.util.ArrayList;

public class Jhj_Post_Notice_List_Adapter extends RecyclerView.Adapter<Jhj_Post_Notice_List_Adapter.ViewHolder> {

    // 지워야할것.
    String seqno = "1";

    Context context;
    private ArrayList<Jhj_Notice_DTO> data;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1 ;

        ViewHolder(View itemView) {
            super(itemView) ;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, Jhj_Post_Write_Notice.class);
                    intent.putExtra("Title", data.get(position).getNoticeTitle());
                    intent.putExtra("Content", data.get(position).getNoticeContent());
                    if (data.get(position).getPostUserSeqno().equals(seqno)) {
                        intent.putExtra("Type", "NW");
                        intent.putExtra("Seqno", data.get(position).getNoticeSeqno());
                    } else {
                        intent.putExtra("Type", "NR");
                    }
                    context.startActivity(intent);
                }
            });

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.Post_Notice_List_Item_TextView);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    Jhj_Post_Notice_List_Adapter(ArrayList<Jhj_Notice_DTO> list) {
        data = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public Jhj_Post_Notice_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.jhj_post_notice_list_item, parent, false) ;
        Jhj_Post_Notice_List_Adapter.ViewHolder vh = new Jhj_Post_Notice_List_Adapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(Jhj_Post_Notice_List_Adapter.ViewHolder holder, int position) {
        String ListItem = data.get(position).getNoticeTitle();
        holder.textView1.setText(ListItem) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return data.size() ;
    }

}
