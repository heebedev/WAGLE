package com.androidlec.wagle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidlec.wagle.R;
import com.androidlec.wagle.dto.Moimlist;

import java.util.ArrayList;

public class MoimListAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Moimlist> data = null;
    private LayoutInflater inflater = null;

    public TextView moimName;

    public MoimListAdapter(Context mContext, int layout, ArrayList<Moimlist> data) {
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
    public Object getItem(int position) {
        return data.get(position).getMoimseq();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);

        }

        moimName = convertView.findViewById(R.id.tv_moimlist_moimname);

        moimName.setText(data.get(position).getMoimName());


        return convertView;
    }
}
