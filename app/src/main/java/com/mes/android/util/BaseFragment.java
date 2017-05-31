package com.mes.android.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;


/**
 * Created by monkey on 2017/5/27.
 */

public class BaseFragment extends Fragment {
    private Activity activity;

    public Context getContext(){
        if(activity == null){
            return MyApplication.getContext();
        }
        return activity;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity = getActivity();
    }
}
