package com.androidlec.wagle.adapter;

import android.content.Context;
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
    private LayoutInflater inflater = null;

    TextView question;
    EditText answer;

    public QuestionListAdapter(Context mContext, int layout, ArrayList<SgstRptList> data) {
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
        return data.get(position).getSrSeqno();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);

        }

        question = convertView.findViewById(R.id.tv_dhglist_suggestion);
        answer = convertView.findViewById(R.id.et_dhglist_report);

        if (position == 0) {
            question.setText(data.get(position).getSContent());
        } else {
            question.setText("질문 "+ position + ") " + data.get(position).getSContent());
            if (data.get(position).getRContent() != null) {
                answer.setText(data.get(position).getRContent());
            }
        }

        return convertView;
    }
}
