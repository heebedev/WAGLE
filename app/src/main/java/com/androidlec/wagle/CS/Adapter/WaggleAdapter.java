package com.androidlec.wagle.CS.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.JH.MyWagleActivity;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.ViewDetailWagleActivity;
import com.androidlec.wagle.networkTask.JH_IntNetworkTask;
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
                Intent intent = null;

                switch (chkJoinIn(Integer.parseInt(data.get(position).getWcSeqno()))){
                    case 1: // 와글 신청이 되었을 때.
                        intent = new Intent(mContext, MyWagleActivity.class);
                        UserInfo.WAGLESEQNO = data.get(position).getWcSeqno();
                        intent.putExtra("wcSeqno", data.get(position).getWcSeqno());
                        mContext.startActivity(intent);
                        break;
                    case 2: // 와글 신청이 안되었을 때.
                        intent = new Intent(mContext, ViewDetailWagleActivity.class);
                        intent.putExtra("data", data.get(position));
                        mContext.startActivity(intent);
                        break;
                    case 0: // 데이터베이스 연결이 안되었을 때.
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
                        break;
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

    // ----------------------수정된 부분입니다.---------------------------------
    private int chkJoinIn(int wcSeqno){
        int chk = 3;
        String uSeqno = String.valueOf(UserInfo.USEQNO);
        //uSeqno = "1"; // 임시 절대값. 위에꺼 쓰면 됨.
        String urlAddr = "http://192.168.0.178:8080/wagle/joininChk.jsp?";
        urlAddr = urlAddr + "wcSeqno=" + wcSeqno + "&User_uSeqno=" + uSeqno;
        try {
            JH_IntNetworkTask networkTask = new JH_IntNetworkTask(mContext, urlAddr);
            chk = networkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return chk;
    }
    //----------------------------------------------------------------------



}//------


