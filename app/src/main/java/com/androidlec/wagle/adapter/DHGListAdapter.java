package com.androidlec.wagle.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.androidlec.wagle.R;
import com.androidlec.wagle.jhj.Jhj_BookReport_DTO;

import java.util.ArrayList;

public class DHGListAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Jhj_BookReport_DTO> data = null;
    private LayoutInflater inflater = null;

    public TextView bookList;

    public DHGListAdapter(Context mContext, int layout, ArrayList<Jhj_BookReport_DTO> data) {
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
        return data.get(position).getBrSeqno();
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

        bookList = convertView.findViewById(R.id.Post_Notice_List_Item_TextView);

        Log.e("dhglistAdapter", data.get(position).getWcName());
        Log.e("dhglistAdapter", data.get(position).getuName());

        bookList.setText(data.get(position).getWcName() + " - " + data.get(position).getuName());


        return convertView;
    }
}
