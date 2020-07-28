package com.androidlec.wagle.jhj;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidlec.wagle.R;

import java.util.ArrayList;

public class Jhj_MyMoim_CustomDialog extends Dialog {

    // custom ClickEvent 생성 준비
    public interface CustomDialogClickListener {
        void onCancleClick();
    }

    private static Context mContext;
    private static CustomDialogClickListener customDialogClickListener;
    private static ArrayList<Jhj_MyMoim_DTO> data;
    private static String title;

    // JSP 연결 IP
    private final static String IP = "192.168.0.82";

    public Jhj_MyMoim_CustomDialog(Context context, ArrayList<Jhj_MyMoim_DTO> data, String title, CustomDialogClickListener customDialogClickListener) {
        super(context);

        this.mContext = context;
        this.data = data;
        this.title = title;
        this.customDialogClickListener = customDialogClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jhj_mymoiminfo_customdialog_layout);

        setCustomDialogTitle(title);

        // Custom ClickEvent 사용 -------------------------------------------------------------------
        findViewById(R.id.mymoiminfo_customdialog_Btn_Cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogClickListener.onCancleClick();
                dismiss();
            }
        });
        // -----------------------------------------------------------------------------------------

        Jhj_MyMoim_CustomDialog_List_Adapter adapter = new Jhj_MyMoim_CustomDialog_List_Adapter(mContext, R.layout.jhj_mymoiminfo_customdialog_list_item, data);

        ListView listView = findViewById(R.id.mymoiminfo_customdialog_Listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(mymoiminfo_customDialog_listView_OnItemClickListener);

    }

    ListView.OnItemClickListener mymoiminfo_customDialog_listView_OnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String urlAddr;

            if (data.get(position).getMaGrade().equals("W")) {
                urlAddr = "http://" + IP + ":8080/wagle/Moim_MyMoim_Update.jsp?maGrade=S&muSeqno=" + data.get(position).getMuSeqno();
            } else {
                urlAddr = "http://" + IP + ":8080/wagle/Moim_MyMoim_Update.jsp?maGrade=W&muSeqno=" + data.get(position).getMuSeqno();
            }
            connectionUpdateData(urlAddr);
        }
    };

    private void connectionUpdateData(String urlAddr) {
        // Jsp 서버 전송
        try {
            Jhj_MySql_Insert_Delete_Update_NetworkTask insNetworkTask = new Jhj_MySql_Insert_Delete_Update_NetworkTask(mContext, urlAddr);
            insNetworkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCustomDialogTitle(String title) {
        TextView textView = findViewById(R.id.mymoiminfo_customdialog_Text_Title);
        textView.setText(title);
    }

}
