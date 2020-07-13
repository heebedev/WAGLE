package com.androidlec.wagle.jhj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidlec.wagle.R;

public class Jhj_Make_Moim_Spinner_Adapter extends BaseAdapter {

    Context mContext;
    int layout;
    String[] data;
    LayoutInflater layoutInflater;

    public Jhj_Make_Moim_Spinner_Adapter(Context mContext, int layout, String[] data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(this.layout, parent, false);
        }

        TextView make_moim_spinner_TextView = convertView.findViewById(R.id.jhj_make_moim_spinner_Text);
        make_moim_spinner_TextView.setText(data[position]);

        return convertView;
    }
}
