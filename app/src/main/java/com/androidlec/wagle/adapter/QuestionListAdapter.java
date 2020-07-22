package com.androidlec.wagle.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.androidlec.wagle.R;
import com.androidlec.wagle.dto.SgstRptList;

import java.util.ArrayList;

public class QuestionListAdapter extends BaseAdapter {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<SgstRptList> data = null;
    public ArrayList<SgstRptList> EditData = null;
    private LayoutInflater inflater = null;

    // 뷰 홀더 선언
    public class ViewHolder {
        TextView question;
        EditText answer;
        int ref;
    }

    public QuestionListAdapter(Context mContext, int layout, ArrayList<SgstRptList> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        EditData = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getsSeqno();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            //                           내가 그린 레이아웃, 부모의 리스트뷰
            convertView = inflater.inflate(this.layout, parent, false);

            holder.question = convertView.findViewById(R.id.tv_dhglist_suggestion);
            holder.answer = convertView.findViewById(R.id.et_dhglist_report);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.ref = position;

        if (position == 0) {
            holder.question.setText("<서문> " + data.get(position).getsContent());
            holder.question.setEnabled(true);
        } else {
            holder.question.setText("질문 "+ position + ") " + data.get(position).getsContent());
        }

        holder.answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EditData.get(holder.ref).setaContent(s.toString());
            }
        });

        return convertView;
    }
}
