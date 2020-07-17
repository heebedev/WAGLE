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
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.ViewDetailWagleActivity;
import com.androidlec.wagle.networkTask.JH_VoidNetworkTask;

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
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_waggle_cs, parent, false);
        return new mViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.tv_title.setText(data.get(position).getWcName());
        if (data.get(position).getWcStartDate().equals(data.get(position).getWcEndDate())) {
            holder.tv_date.setText("일시 : " + data.get(position).getWcStartDate());
        } else {
            holder.tv_date.setText("일시 : " + data.get(position).getWcStartDate() + " ~ " + data.get(position).getWcEndDate());
        }
        holder.tv_location.setText("장소 : " + data.get(position).getWcLocate());
        holder.tv_fee.setText("참가비 : " + data.get(position).getWcEntryFee());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chkJoinIn(Integer.parseInt(data.get(position).getWcSeqno())) == 2){
                    Intent intent = new Intent(mContext, ViewDetailWagleActivity.class);
                    intent.putExtra("data", data.get(position));
                    mContext.startActivity(intent);
                } else {
                    // 와글 홈 페이지() // 여기 수정해야함!@!!!!!!!!! 종학 !!!!!
                }

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

    private int chkJoinIn(int wcSeqno){
        int chkJoinIn = 3;
        String uSeqno = String.valueOf(UserInfo.USEQNO);
        String urlAddr = "http://192.168.0.178:8080/wagle/joinInWagle.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno + "&uSeqno=" + uSeqno;
        try {
            JH_VoidNetworkTask networkTask = new JH_VoidNetworkTask(mContext, urlAddr);
            networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }

        return chkJoinIn;
    }




}//------


