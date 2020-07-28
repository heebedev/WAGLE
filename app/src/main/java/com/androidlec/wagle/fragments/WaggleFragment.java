package com.androidlec.wagle.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import java.util.zip.Inflater;

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

    private int REQUEST_TEST = 1;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == REQUEST_TEST) && (resultCode == Activity.RESULT_OK)) {
            Log.e("wagleFragment", "onActicity11");
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }

    private void getDataQueryDue() {
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetWagleListWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO;

        try {
            WGNetworkTask wgNetworkTask = new WGNetworkTask(getActivity(), urlAddr);
            data = wgNetworkTask.execute().get(); // doInBackground 의 리턴값
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // 마감순 정렬

    private void getDataQueryPopular() {
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetWagleListPopularWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO;

        try {
            WGNetworkTask wgNetworkTask = new WGNetworkTask(getActivity(), urlAddr);
            data = wgNetworkTask.execute().get(); // doInBackground 의 리턴값
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // 인기순 정렬

    private void getMyWagle() {
        String JH_IP = "192.168.0.178";
        String urlAddr = "http://" + JH_IP + ":8080/wagle/getMyWagleList.jsp?";

        urlAddr = urlAddr + "mSeqno=" + UserInfo.MOIMSEQNO + "&uSeqno=" + UserInfo.USEQNO;

        try {
            WGNetworkTask wgNetworkTask = new WGNetworkTask(getActivity(), urlAddr);
            data = wgNetworkTask.execute().get(); // doInBackground 의 리턴값
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // 인기순 정렬

    private void setAdapter() {
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

    private void init(View v) {
        rv_wagleList = v.findViewById(R.id.rv_wagleList);
        tv_noWagleList = v.findViewById(R.id.tv_noWagleList);
        tvFindMyWaggle = v.findViewById(R.id.tvFindMyWaggle);
        fab_addWagle = v.findViewById(R.id.wagle_fab_addwagle);

        tvFindMyWaggle.setOnClickListener(onClickListener);
        fab_addWagle.setOnClickListener(onClickListener);

        spinSearch = v.findViewById(R.id.sp_Wagle_ArrangeSpinner);

        spinList = new ArrayList<>();
        spinList.add("마감순");
        spinList.add("인기순");

        spinArrayAdapt = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinList);
        spinSearch.setAdapter(spinArrayAdapt);

        spinSearch.setOnItemSelectedListener(onItemSelectedListener);

    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0: // 마감순 정렬
                    getDataQueryDue();
                    break;
                case 1: // 인기순 정렬
                    getDataQueryPopular();
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {
            case R.id.wagle_fab_addwagle:
                if (UserInfo.MOIMMYGRADE.equals("O") || UserInfo.MOIMMYGRADE.equals("S")) {
                    Intent intent = new Intent(getActivity(), AddWagleActivity.class);
                    startActivityForResult(intent, REQUEST_TEST);
                } else {
                    Intent intent = new Intent(getActivity(), AddTodayWagleActivity.class);
                    startActivityForResult(intent, REQUEST_TEST);
                }
                break;
            case R.id.tvFindMyWaggle:
                getMyWagle();
                Toast.makeText(getActivity(), "내가 신청한 와글", Toast.LENGTH_SHORT).show();
                break;

        }
    };


}