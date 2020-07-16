package com.androidlec.wagle.jhj;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlec.wagle.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Jhj_MyMoim_User_List_Adapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Jhj_MyMoim_DTO> data = null;
    private LayoutInflater inflater = null;


    // JSP 연결 IP
    private final static String IP = "192.168.0.82";

    public Jhj_MyMoim_User_List_Adapter(Context mContext, int layout, ArrayList<Jhj_MyMoim_DTO> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        Log.v("MoimListAdapter", String.valueOf(data.size()));
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getuName();
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

        ImageView mymoiminfo_user_imageIcon = convertView.findViewById(R.id.mymoiminfo_user_list_item_image);
        TextView mymoiminfo_user_name = convertView.findViewById(R.id.mymoiminfo_user_list_item_name);
        TextView mymoiminfo_user_grade = convertView.findViewById(R.id.mymoiminfo_user_list_item_grade);

        //         Context                 URL              ImageView
        Glide.with(convertView).load("http://" + IP + ":8080/wagle/userImgs/" + data.get(position).getuImageName()).into(mymoiminfo_user_imageIcon);

        mymoiminfo_user_name.setText(data.get(position).getuName());
        mymoiminfo_user_grade.setText(data.get(position).getMaGrade());

        return convertView;
    }
}
