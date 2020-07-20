package com.androidlec.wagle.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlec.wagle.CS.Adapter.WaggleAdapter;
import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.CS.Network.WGNetworkTask;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.activity.wagleSub.AddNormWagleActivity;
import com.androidlec.wagle.activity.wagleSub.AddTodayWagleActivity;
import com.androidlec.wagle.activity.wagleSub.AddWagleActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaggleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaggleFragment extends Fragment {

    //스피너
    private Spinner spinSearch;
    private ArrayList<String> spinList;
    private ArrayAdapter<String> spinArrayAdapt;

    //와글 목록
    private RecyclerView rv_wagleList;
    private TextView tv_noWagleList;
    private TextView tvFindMyWaggle;
    private FloatingActionButton fab_addWagle;

    private WaggleAdapter adapter;
    private ArrayList<WagleList> data;



    public WaggleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_waggle, container, false);

        init(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        getData();
        if (data.size() == 0) {
            tv_noWagleList.setVisibility(View.VISIBLE);
            rv_wagleList.setVisibility(View.GONE);
        } else {
            tv_noWagleList.setVisibility(View.GONE);
            rv_wagleList.setVisibility(View.VISIBLE);
            adapter = new WaggleAdapter(getActivity(), data);
            rv_wagleList.setAdapter(adapter);
        }

    }

    private void getData() {
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetWagleListWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO;

        try {
            WGNetworkTask wgNetworkTask = new WGNetworkTask(getActivity(), urlAddr);
            data = wgNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(View v) {
        rv_wagleList = v.findViewById(R.id.rv_wagleList);
        tv_noWagleList = v.findViewById(R.id.tv_noWagleList);
        tvFindMyWaggle = v.findViewById(R.id.tvFindMyWaggle);
        fab_addWagle = v.findViewById(R.id.wagle_fab_addwagle);

        fab_addWagle.setOnClickListener(onClickListener);

        spinSearch = v.findViewById(R.id.sp_Wagle_ArrangeSpinner);

        spinList = new ArrayList<String>();
        spinList.add("최신순");
        spinList.add("인기순");

        spinArrayAdapt = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinList);
        spinSearch.setAdapter(spinArrayAdapt);


    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.wagle_fab_addwagle:
                if (UserInfo.WAGLEMAGRADE.equals("O") || UserInfo.WAGLEMAGRADE.equals("S")) {
                    startActivity(new Intent(getActivity(), AddWagleActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), AddTodayWagleActivity.class));
                }
                break;
            case R.id.tvFindMyWaggle:
                break;

        }
    };
}