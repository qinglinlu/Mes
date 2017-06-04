package com.mes.android.caiwu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mes.android.R;
import com.mes.android.ShowData;
import com.mes.android.util.BaseFragment;
import com.mes.android.util.ListDateTime;

import java.util.Calendar;

/**
 * Created by monkey on 2017/5/31.
 */

public class CaiWu_HuiKuan_ChaXun extends BaseFragment implements View.OnClickListener {
    private DatePickerDialog dateDialog;
    private String dateStr;
    private EditText startDate;
    private EditText endDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_caiwu_huikuan_chaxun, container, false);

        final Button okButton = (Button) view.findViewById(R.id.bt_caiwu_huikuan_chaxun_ok);
        Button noButton = (Button) view.findViewById(R.id.bt_caiwu_huikuan_chaxun_no);

        startDate = (EditText) view.findViewById(R.id.et_caiwu_huikuan_chaxun_startdate);
        endDate = (EditText) view.findViewById(R.id.et_caiwu_huikuan_chaxun_enddate);
        startDate.setInputType(InputType.TYPE_NULL);
        endDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        okButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        startDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startDate.setText("");
                return true;
            }
        });
        endDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                endDate.setText("");
                return true;
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        ShowData showData = (ShowData) getActivity();
        Calendar c = Calendar.getInstance();
        Intent intent;
        dateStr = "";
        switch (v.getId()) {
            case R.id.bt_caiwu_huikuan_chaxun_ok:
//                showData.mDrawerLayout.closeDrawers();
//                ShowDatePickDailog();
                break;
            case R.id.bt_caiwu_huikuan_chaxun_no:
                showData.mDrawerLayout.closeDrawers();
                showData.process("No按钮");
                break;
            case R.id.et_caiwu_huikuan_chaxun_startdate:
                intent = new Intent(getActivity(), ListDateTime.class);
                startActivityForResult(intent, 100);
//
//                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                        dateStr = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
//                        startDate.setText(dateStr);
////                        okButton.requestFocus();
//                    }
//
//                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.et_caiwu_huikuan_chaxun_enddate:
                intent = new Intent(getActivity(), ListDateTime.class);
                startActivityForResult(intent, 101);
//                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        // TODO Auto-generated method stub
//                        endDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
////                        okButton.requestFocus();
//                    }
//                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == -1) {
                    startDate.setText(data.getStringExtra("date_return"));
                }
                break;
            case 101:
                if (resultCode == -1) {
                    endDate.setText(data.getStringExtra("date_return"));
                }
                break;
            default:
                break;
        }

    }
//    private void ShowDatePickDailog(String l) {
//
//// TODO Auto-generated method stub
//dateStr="";
//        final Calendar calendar1 = Calendar.getInstance();
//        dateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//// TODO Auto-generated method stub
//// Year = year;
//                String mm = "";
//                String dd = "";
//                if (monthOfYear <= 9) {
//                    mm = "0" + (monthOfYear + 1);
//                } else {
//                    mm = String.valueOf(monthOfYear + 1);
//                }
//                if (dayOfMonth <= 9) {
//                    dd = "0" + dayOfMonth;
//                } else {
//                    dd = String.valueOf(dayOfMonth);
//                }
//                dateStr=String.valueOf(year) + "-" + mm + "-" + dd;
//            }
//        }, calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH));
//////设置时间范围
////        dateDialog.getDatePicker().setMinDate(CalendarUtil.GetLastMonthDate().getTimeInMillis());
////        dateDialog.getDatePicker().setMaxDate(calendar1.getTimeInMillis());
////DatePickerDialog默认有一个“完成”按钮，当这里手动设置两个setButton时，有的手机系统会出现3个按钮，而设一个setButton的话就都只有一个
//        dateDialog.onClick(DialogInterface dialog, int which);
//        dateDialog.setButton("确定", new DialogInterface.OnClickListener(){
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//// TODO Auto-generated method stub
//                dateDialog.dismiss();
//                tvProcessTitle.setText(dateStr+"排程：");
//                tvProcessName.setText("");
//            }});
//        dateDialog.show();
//    }
}
