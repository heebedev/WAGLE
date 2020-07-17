package com.androidlec.wagle.jhj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlec.wagle.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Jhj_MyMoim_CustomDialog_List_Adapter extends BaseAdapter {

    private Context mContext = null;
    private int layout;
    private ArrayList<Jhj_MyMoim_DTO> data = null;
    private LayoutInflater layoutInflater = null;

    // JSP 연결 IP
    private final static String IP = "192.168.0.82";

    public Jhj_MyMoim_CustomDialog_List_Adapter(Context mContext, int layout, ArrayList<Jhj_MyMoim_DTO> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
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
            convertView = layoutInflater.inflate(this.layout, parent, false);
        }

        ImageView customDialog_list_Icon = convertView.findViewById(R.id.jhj_mymoiminfo_customdialog_list_item_Image_Icon);
        TextView customDialog_list_Name = convertView.findViewById(R.id.jhj_mymoiminfo_customdialog_list_item_Text_Name);
        TextView customDialog_list_Grade = convertView.findViewById(R.id.jhj_mymoiminfo_customdialog_list_item_Text_Grade);
        TextView customDialog_list_Email = convertView.findViewById(R.id.jhj_mymoiminfo_customdialog_list_item_Text_Email);

        customDialog_list_Name.setText(data.get(position).getuName());
        customDialog_list_Grade.setText(data.get(position).getMaGrade());
        customDialog_list_Email.setText(data.get(position).getuEmail());

        //         Context                 URL              ImageView
        Glide.with(convertView).load("http://" + IP + ":8080/wagle/userImgs/" + data.get(position).getuImageName()).into(customDialog_list_Icon);

        return convertView;

    }
}
