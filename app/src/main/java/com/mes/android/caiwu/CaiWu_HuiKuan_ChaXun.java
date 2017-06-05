package com.mes.android.caiwu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mes.android.R;
import com.mes.android.ShowData;
import com.mes.android.liebiao.List_OnlyMc;
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
    private EditText yeWuLeiXingText;
    private EditText jieSuanFangShiText;
    private EditText keHuText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_caiwu_huikuan_chaxun, container, false);

        final Button okButton = (Button) view.findViewById(R.id.bt_caiwu_huikuan_chaxun_ok);
        Button noButton = (Button) view.findViewById(R.id.bt_caiwu_huikuan_chaxun_no);

        startDate = (EditText) view.findViewById(R.id.et_caiwu_huikuan_chaxun_startdate);
        endDate = (EditText) view.findViewById(R.id.et_caiwu_huikuan_chaxun_enddate);
        yeWuLeiXingText= (EditText) view.findViewById(R.id.et_caiwu_huikuan_chaxun_yiewuleixing);
        jieSuanFangShiText= (EditText) view.findViewById(R.id.et_caiwu_huikuan_chaxun_jiesuanfangshi);
        keHuText= (EditText) view.findViewById(R.id.et_caiwu_huikuan_chaxun_kehu);

        startDate.setInputType(InputType.TYPE_NULL);
        endDate.setInputType(InputType.TYPE_NULL);
        yeWuLeiXingText.setInputType(InputType.TYPE_NULL);
        jieSuanFangShiText.setInputType(InputType.TYPE_NULL);
        keHuText.setInputType(InputType.TYPE_NULL);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        yeWuLeiXingText.setOnClickListener(this);
        jieSuanFangShiText.setOnClickListener(this);
        keHuText.setOnClickListener(this);
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
                showData.mDrawerLayout.closeDrawers();
                showData.process(getTiaojian());
                break;
            case R.id.bt_caiwu_huikuan_chaxun_no:
                showData.mDrawerLayout.closeDrawers();
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
            break;
            case R.id.et_caiwu_huikuan_chaxun_yiewuleixing:
                intent = new Intent(getActivity(), List_OnlyMc.class);
                intent.putExtra("commId","111");
                intent.putExtra("biaoti","业务类型");
                startActivityForResult(intent, 102);
                break;
            case R.id.et_caiwu_huikuan_chaxun_jiesuanfangshi:
                intent = new Intent(getActivity(), List_OnlyMc.class);
                intent.putExtra("commId","72");
                intent.putExtra("biaoti","结算方式");
                startActivityForResult(intent, 103);
                break;
            case R.id.et_caiwu_huikuan_chaxun_kehu:
                intent = new Intent(getActivity(), List_OnlyMc.class);
                intent.putExtra("commId","62");
                intent.putExtra("biaoti","客户");
                startActivityForResult(intent, 104);
                break;
            case R.id.bt_caiwu_huikuan_chaxun_chongzhi:
                chongZhiTiaoJian();
            default:
                break;
        }
    }

    private void chongZhiTiaoJian() {
        startDate.setText("");
        endDate.setText("");
        yeWuLeiXingText.setText("");
        jieSuanFangShiText.setText("");
        keHuText.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && !data.getStringExtra("date_return").isEmpty()) {
            String returnString=data.getStringExtra("date_return");
            switch (requestCode) {
                case 100:
                        startDate.setText(returnString);
                    break;
                case 101:
                        endDate.setText(returnString);
                    break;
                case 102:
                        yeWuLeiXingText.setText(returnString);
                    break;
                case 103:
                        jieSuanFangShiText.setText(returnString);
                    break;
                case 104:
                        keHuText.setText(returnString);
                    break;
                default:
                    break;
            }
        }
    }

    public String getTiaojian() {
        String tiaojian="";
        if(!startDate.getText().toString().isEmpty()){
            tiaojian += " and [riqi]>='" + startDate.getText().toString() + "'";
        }
        if(!endDate.getText().toString().isEmpty()){
            tiaojian += " and [riqi]<='" + endDate.getText().toString() + "'";
        }
        if(!jieSuanFangShiText.getText().toString().isEmpty()){
            tiaojian += " and [jiesuanfangshi]='" +jieSuanFangShiText.getText().toString()+ "'";
        }
        if(!keHuText.getText().toString().isEmpty()){
            tiaojian += " and [kehu]='" +keHuText.getText().toString() + "'";
        }
        if(!yeWuLeiXingText.getText().toString().isEmpty()){
            tiaojian += " and [leixing]='" +yeWuLeiXingText.getText().toString() + "'";
        }
        return tiaojian.isEmpty()?"":tiaojian.substring(5);
//        return tiaojian;
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
