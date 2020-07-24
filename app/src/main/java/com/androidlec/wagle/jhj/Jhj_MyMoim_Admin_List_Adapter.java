package com.androidlec.wagle.jhj;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

public class Jhj_MyMoim_Admin_List_Adapter extends RecyclerView.Adapter<Jhj_MyMoim_Admin_List_Adapter.ViewHolder> {

    private static Context context;
    private static ArrayList<Jhj_MyMoim_DTO> data;
    private static RequestManager manager;

    private final static String IP = "192.168.0.82";

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mymoim_icon_image;
        TextView mymoim_user_name;
        TextView mymoim_user_grade;

        ViewHolder(View itemView) {
            super(itemView);

            mymoim_icon_image = itemView.findViewById(R.id.mymoiminfo_admin_list_item_user_image);
            mymoim_user_name = itemView.findViewById(R.id.mymoiminfo_admin_list_item_user_name);
            mymoim_user_grade = itemView.findViewById(R.id.mymoiminfo_admin_list_item_user_grade);
        }
    }

    public Jhj_MyMoim_Admin_List_Adapter(ArrayList<Jhj_MyMoim_DTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Jhj_MyMoim_Admin_List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.jhj_mymoiminfo_admin_list_item, parent, false);
        Jhj_MyMoim_Admin_List_Adapter.ViewHolder viewHolder = new Jhj_MyMoim_Admin_List_Adapter.ViewHolder(view);

        manager = Glide.with(context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Jhj_MyMoim_Admin_List_Adapter.ViewHolder holder, int position) {
        manager.load("http://" + IP + ":8080/wagle/userImgs/" + data.get(position).getuImageName()).into(holder.mymoim_icon_image);
        holder.mymoim_user_name.setText(data.get(position).getuName());
        holder.mymoim_user_grade.setText("운영진");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
