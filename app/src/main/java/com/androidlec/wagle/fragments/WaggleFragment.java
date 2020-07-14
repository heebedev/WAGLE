package com.androidlec.wagle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlec.wagle.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaggleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaggleFragment extends Fragment {

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



        return v;
    }
}