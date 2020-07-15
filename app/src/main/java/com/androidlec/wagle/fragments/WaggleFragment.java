package com.androidlec.wagle.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidlec.wagle.CS.Adapter.WaggleAdapter;
import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.CS.Network.WGNetworkTask;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.androidlec.wagle.activity.wagleSub.AddWagleActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaggleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaggleFragment extends Fragment {

    private RecyclerView rv_wagleList;
    private TextView tv_noWagleList;
    private TextView tvFindMyWaggle;
    private FloatingActionButton fab_addWagle;

    private WaggleAdapter adapter;
    private ArrayList<WagleList> data;

    public WaggleFragment() {
        // Required empty public constructor
    }

    public static WaggleFragment newInstance(String param1, String param2) {
        WaggleFragment fragment = new WaggleFragment();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_waggle, container, false);

        init(v);
        getData();

        if(data.size() == 0){
            tv_noWagleList.setVisibility(View.VISIBLE);
            rv_wagleList.setVisibility(View.GONE);
        } else {
            tv_noWagleList.setVisibility(View.GONE);
            rv_wagleList.setVisibility(View.VISIBLE);
            adapter = new WaggleAdapter(getActivity(), data);
            rv_wagleList.setAdapter(adapter);
        }

        return v;
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
    }

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()){
            case R.id.wagle_fab_addwagle:
                startActivity(new Intent(getActivity(), AddWagleActivity.class));
                break;
            case R.id.tvFindMyWaggle:
                break;

        }
    };
}