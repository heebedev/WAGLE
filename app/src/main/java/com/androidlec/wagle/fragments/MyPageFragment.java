package com.androidlec.wagle.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androidlec.wagle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Layout (findViewById 를 사용하기위해) 선언
    ViewGroup rootView;
    String IP = "192.168.0.82";

    public MyPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_page, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ImageView rankingIcon = rootView.findViewById(R.id.fragment_my_page_RankingIcon);
        TextView myName = rootView.findViewById(R.id.fragment_my_page_Text_Name);
        TextView rankGrade = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Grade);

        TextView wagleNum = rootView.findViewById(R.id.fragment_my_page_Text_WagleNum);
        TextView bookReportNum = rootView.findViewById(R.id.fragment_my_page_Text_BookReportNum);

        TextView rankNum1 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num1);
        TextView rankNum2 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num2);
        TextView rankNum3 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num3);
        TextView rankNum4 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num4);
        TextView rankNum5 = rootView.findViewById(R.id.fragment_my_page_Text_Rank_Num5);

        ListView wagleListView = rootView.findViewById(R.id.fragment_my_page_Wagle_ListVIew);
        ListView bookReportListView = rootView.findViewById(R.id.fragment_my_page_BookReport_ListVIew);



    }
}