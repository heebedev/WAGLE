package com.androidlec.wagle.JH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlec.wagle.R;

import java.util.ArrayList;

public class RankAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Rank> data = null;
    private LayoutInflater inflater = null;

    public RankAdapter(Context mContext, int layout, ArrayList<Rank> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = inflater.inflate(this.layout, viewGroup, false);
        }


        TextView tv_rank = view.findViewById(R.id.mypage_tv_rank);
        TextView tv_name = view.findViewById(R.id.mypage_tv_name);
        TextView tv_waglenum = view.findViewById(R.id.mypage_tv_waglenum);
        TextView tv_reportnum = view.findViewById(R.id.mypage_tv_reportnum);
        TextView tv_totalscore = view.findViewById(R.id.mypage_tv_totalscore);
        ImageView iv_rank = view.findViewById(R.id.mypage_iv_rank);


        tv_rank.setText((i+1) + "위");
        tv_name.setText(data.get(i).getName());
        tv_waglenum.setText(data.get(i).getWaglenum()+" 와글");
        tv_reportnum.setText(data.get(i).getMuWagleReport()+" 독후감");
        tv_totalscore.setText(data.get(i).getMuWagleScore()+" 점");

        switch (i){
            case 0:
                iv_rank.setImageResource(R.drawable.number1);
                break;
            case 1:
                iv_rank.setImageResource(R.drawable.number2);
                break;
            case 2:
                iv_rank.setImageResource(R.drawable.number3);
                break;
            default:
                break;
        }

        return view;
    }
}//----
