package com.androidlec.wagle.JH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidlec.wagle.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PaymentAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<Payment> data = null;
    private LayoutInflater inflater = null;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String result = "";

    public PaymentAdapter(Context mContext, int layout, ArrayList<Payment> data) {
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
        return data.get(i).getItem();
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

        TextView tv_item = view.findViewById(R.id.payment_tv_item);
        TextView tv_price = view.findViewById(R.id.payment_tv_price);

        result = decimalFormat.format(Double.parseDouble(Integer.toString(data.get(i).getPrice())));

        tv_item.setText(data.get(i).getItem());
        tv_price.setText(result + " 원");


        return view;
    }


}//----
