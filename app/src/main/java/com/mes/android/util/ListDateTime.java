package com.mes.android.util;

import android.content.Intent;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.mes.android.Setting;
import com.mes.android.util.PickerView.onSelectListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mes.android.R;

public class ListDateTime extends AppCompatActivity {
    private PickerView pv_nian;
    private PickerView pv_yue;
    private PickerView pv_ri;
    private PickerView pv_shi;
    private PickerView pv_fen;
    private PickerView pv_miao;
//    private String mNian;
//    private String mYue;
//    private String mRi;
//    private String mShi;
//    private String mFen;
//    private String mMiao;
    private TextView dateTimeShowText;
    private Button okButton;
    private Button noButton;
    private static final String TAG = "ListDateTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_datetime);
        this.setTitle("请选择时间");
        pv_nian = (PickerView) findViewById(R.id.pv_listdatetime_nian);
        pv_yue = (PickerView) findViewById(R.id.pv_listdatetime_yue);
        pv_ri = (PickerView) findViewById(R.id.pv_listdatetime_ri);
        pv_shi = (PickerView) findViewById(R.id.pv_listdatetime_shi);
        pv_fen = (PickerView) findViewById(R.id.pv_listdatetime_fen);
        pv_miao = (PickerView) findViewById(R.id.pv_listdatetime_miao);
        okButton = (Button) findViewById(R.id.bt_listdatetime_ok);
        noButton=(Button)findViewById(R.id.bt_listdatetime_no);
        dateTimeShowText = (TextView) findViewById(R.id.tv_listdatetime_showtime);


        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.ENGLISH);
//        mNian = String.valueOf(c.get(Calendar.YEAR));
//        mYue = String.valueOf(c.get(Calendar.MONTH) + 1).length() == 1 ? "0" + String.valueOf(c.get(Calendar.MONTH) + 1) : String.valueOf(c.get(Calendar.MONTH) + 1);
//        mRi = String.valueOf(c.get(Calendar.DATE)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.DATE)) : String.valueOf(c.get(Calendar.DATE));
//        mShi = String.valueOf(c.get(Calendar.HOUR_OF_DAY)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.HOUR_OF_DAY)) : String.valueOf(c.get(Calendar.HOUR_OF_DAY));
//        mFen = String.valueOf(c.get(Calendar.MINUTE)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.MINUTE)) : String.valueOf(c.get(Calendar.MINUTE));
////        mMiao=String.valueOf(c.get(Calendar.SECOND)).length()==1?"0"+String.valueOf(c.get(Calendar.SECOND)):String.valueOf(c.get(Calendar.SECOND));;
//        mMiao = "00";
        addData(100, c.get(Calendar.YEAR) - 50, pv_nian, String.valueOf(c.get(Calendar.YEAR)));
        addData(12, 1, pv_yue, String.valueOf(c.get(Calendar.MONTH) + 1).length() == 1 ? "0" + String.valueOf(c.get(Calendar.MONTH) + 1) : String.valueOf(c.get(Calendar.MONTH) + 1));
        addData(c.getActualMaximum(Calendar.DAY_OF_MONTH), 1, pv_ri, String.valueOf(c.get(Calendar.DATE)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.DATE)) : String.valueOf(c.get(Calendar.DATE)));
        addData(24, 0, pv_shi, String.valueOf(c.get(Calendar.HOUR_OF_DAY)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.HOUR_OF_DAY)) : String.valueOf(c.get(Calendar.HOUR_OF_DAY)));
        addData(60, 0, pv_fen, String.valueOf(c.get(Calendar.MINUTE)).length() == 1 ? "0" + String.valueOf(c.get(Calendar.MINUTE)) : String.valueOf(c.get(Calendar.MINUTE)));
        addData(60, 0, pv_miao, "00");
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("date_return",dateTimeShowText.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
//                addData(31, 1, pv_ri, String.valueOf(Calendar.getInstance().DATE));
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showSelectDatetime();
        pv_nian.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                showSelectDatetime();
            }
        });
        pv_yue.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {

                Calendar mc = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = format.parse(pv_nian.getShowData() + "-" + pv_yue.getShowData() + "-01");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mc.setTime(date);//年月日时分秒（月份0代表1月）
                addData(mc.getActualMaximum(Calendar.DAY_OF_MONTH), 1, pv_ri, pv_ri.getShowData());
                showSelectDatetime();
            }
        });
        pv_ri.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                showSelectDatetime();
            }
        });
        pv_shi.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                showSelectDatetime();
            }
        });
        pv_fen.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                showSelectDatetime();
            }
        });
        pv_miao.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                showSelectDatetime();
            }
        });
    }

    private void showSelectDatetime() {
        dateTimeShowText.setText(pv_nian.getShowData() + "-" + pv_yue.getShowData() + "-" + pv_ri.getShowData() + " " + pv_shi.getShowData() + ":" + pv_fen.getShowData() + ":" + pv_miao.getShowData());
    }

//    private String getSelectDatetime() {
//        return pv_nian.getShowData() + "-" + pv_yue.getShowData() + "-" + pv_ri.getShowData() + " " + pv_shi.getShowData() + ":" + pv_fen.getShowData() + ":" + pv_miao.getShowData();
//    }

    /**
     * 为滚动控件加载 数据
     *
     * @param parmsl    要加载 的数量
     * @param pramstart 起始数量
     * @param parmpv    要加载数据的控件
     * @param parselect 加载完后要跳转到哪个数据
     */
    private void addData(int parmsl, int pramstart, PickerView parmpv, String parselect) {
        List<String> mylist = new ArrayList<>();
        boolean findbool = false;
        int selectint = 0;
        String mylselect = parselect;
        for (int i = pramstart; i < parmsl + pramstart; i++) {
            mylist.add(i < 10 ? "0" + i : "" + i);
        }

        if (parselect.length() == 1) {
            mylselect = "0" + mylselect;
        }
        for (String ss : mylist) {
            if (ss.equals(mylselect)) {
                findbool = true;
                break;
            }
            selectint++;
        }
        if (!findbool) {
            selectint = mylist.size() - 1;
        }
//        Log.d(TAG, "要寻找的数据: " + parselect + ",是否已找到" + String.valueOf(findbool) + "找到的位置是：" + String.valueOf(selectint) + ",填充数量：" + String.valueOf(parmsl));
        parmpv.setData(mylist, selectint);
    }
}
