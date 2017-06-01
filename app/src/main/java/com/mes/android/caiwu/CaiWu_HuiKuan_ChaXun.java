package com.mes.android.caiwu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mes.android.R;
import com.mes.android.ShowData;
import com.mes.android.util.BaseFragment;

/**
 * Created by monkey on 2017/5/31.
 */

public class CaiWu_HuiKuan_ChaXun extends BaseFragment implements View.OnClickListener {
    /**
     * 用来与外部activity交互的
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_caiwu_huikuan_chaxun,container,false);

        Button okButton=(Button)view.findViewById(R.id.bt_caiwu_huikuan_chaxun_ok);
        Button noButton=(Button)view.findViewById(R.id.bt_caiwu_huikuan_chaxun_no);
        okButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        ShowData showData=(ShowData)getActivity();
        switch (v.getId()){
            case R.id.bt_caiwu_huikuan_chaxun_ok:
                showData.mDrawerLayout.closeDrawers();
                showData.process("Ok按钮");
                break;
            case R.id.bt_caiwu_huikuan_chaxun_no:
                showData.mDrawerLayout.closeDrawers();
                showData.process("No按钮");
                break;
            default:
                break;
        }
    }
}
