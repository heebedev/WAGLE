package com.androidlec.wagle.CS.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.R;

import java.util.ArrayList;

public class WaggleAdapter extends RecyclerView.Adapter<WaggleAdapter.mViewHolder> {

    Context mContext;
    ArrayList<WagleList> data;

    public WaggleAdapter(Context mContext, ArrayList<WagleList> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_waggle, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.tv_title.setText(data.get(position).getTitle());
        holder.tv_date.setText(data.get(position).getDate());
        holder.tv_location.setText(data.get(position).getLocation());
        holder.tv_fee.setText(data.get(position).getFee());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mContext.startActivity(new Intent(mContext, ));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tv_title, tv_date, tv_location, tv_fee;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.item_waggle_cs_cv_main);
            tv_title = itemView.findViewById(R.id.item_waggle_cs_tv_title);
            tv_date = itemView.findViewById(R.id.item_waggle_cs_tv_date);
            tv_location = itemView.findViewById(R.id.item_waggle_cs_tv_location);
            tv_fee = itemView.findViewById(R.id.item_waggle_cs_tv_fee);
        }
    }
}
