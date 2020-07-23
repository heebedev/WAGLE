package com.androidlec.wagle.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.androidlec.wagle.CS.CalendarUtil.CSDrawableUtils;
import com.androidlec.wagle.CS.Model.WagleList;
import com.androidlec.wagle.CS.Network.WGNetworkTask;
import com.androidlec.wagle.R;
import com.androidlec.wagle.UserInfo;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class PlanFragment extends Fragment {

    private List<EventDay> events;
    private CalendarView calendarView;
    private ArrayList<WagleList> data;

    public PlanFragment() {
        // Required empty public constructor
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        init(v);

        getEventData();
        setEvents();

        calendarView.setOnDayClickListener(eventDay -> {
            int month = eventDay.getCalendar().getTime().getMonth()+1;
            int date = eventDay.getCalendar().getTime().getDate();
            if(eventDay.getImageDrawable() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(month+"월 "+date+"일");
                builder.setMessage(setPlanTextView(eventDay.getCalendar().getTime()));
                builder.show();
            }
        });

        return v;
    }

    private String setPlanTextView(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        String selectedDay = sdf.format(date);
        StringBuilder planList = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            if(selectedDay.equals(data.get(i).getWcStartDate())){
                planList.append("✔︎ ").append(data.get(i).getWcName()).append(" 시작일\n");
            }
            if(selectedDay.equals(data.get(i).getWcDueDate())){
                planList.append("✔︎ ").append(data.get(i).getWcName()).append(" 마감일\n");
            }
            if(selectedDay.equals(data.get(i).getWcEndDate()))
                planList.append("✔︎ ").append(data.get(i).getWcName()).append(" 종료일\n");
        }
        return planList.toString();
    }

    private void getEventData() {
        String urlAddr = "http://192.168.0.79:8080/wagle/csGetWagleListWAGLE.jsp?";

        urlAddr = urlAddr + "Moim_wmSeqno=" + UserInfo.MOIMSEQNO;

        try {
            WGNetworkTask wgNetworkTask = new WGNetworkTask(getActivity(), urlAddr);
            data = wgNetworkTask.execute().get(); // doInBackground 의 리턴값
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEvents() {
        Calendar calendar;
        String[] date;
        for (int i = 0; i < data.size(); i++) {
            calendar = Calendar.getInstance();
            date = data.get(i).getWcStartDate().split("\\.");
            calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
            calendar.set(Calendar.DATE, Integer.parseInt(date[2]));
            events.add(new EventDay(calendar, CSDrawableUtils.getCircleDrawableWithTextStart(getActivity())));

            calendar = Calendar.getInstance();
            date = data.get(i).getWcDueDate().split("\\.");
            calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
            calendar.set(Calendar.DATE, Integer.parseInt(date[2]));
            events.add(new EventDay(calendar, CSDrawableUtils.getCircleDrawableWithTextDue(getActivity())));

            calendar = Calendar.getInstance();
            date = data.get(i).getWcEndDate().split("\\.");
            calendar.set(Calendar.YEAR, Integer.parseInt(date[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
            calendar.set(Calendar.DATE, Integer.parseInt(date[2]));
            events.add(new EventDay(calendar, CSDrawableUtils.getCircleDrawableWithTextEnd(getActivity())));
        }

        calendarView.setEvents(events);
    }

    private void init(View v) {
        calendarView = v.findViewById(R.id.calendarView);

        events = new ArrayList<>();
    }

}