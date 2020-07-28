package com.androidlec.wagle.CS.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.CS.Activities.BoardActivity;
import com.androidlec.wagle.CS.Activities.BoardListActivity;
import com.androidlec.wagle.CS.Model.BoardList;
import com.androidlec.wagle.R;

import java.util.ArrayList;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.mViewHolder> {

    Context mContext;
    ArrayList<BoardList> data;

    public BoardListAdapter(Context mContext, ArrayList<BoardList> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_board_cs, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        setHolder(holder, position);
    }

    private void setHolder(@NonNull mViewHolder holder, int position) {
        holder.tv_boardList.setText(data.get(position).getPcTitle());

        holder.tv_boardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BoardActivity.class);
                intent.putExtra("boardTitle", BoardListActivity.BOARD_TITLE);
                intent.putExtra("title", data.get(position).getPcTitle());
                intent.putExtra("contents", data.get(position).getPcContent());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        TextView tv_boardList;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_boardList = itemView.findViewById(R.id.item_board_cs_tv_title);
        }
    }

}//------


