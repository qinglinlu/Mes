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

    /*
    * onAttach(Context) is not called on pre API 23 versions of Android and onAttach(Activity) is deprecated
    * Use onAttachToContext instead
    */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /*
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        //do something
        activity=getActivity();
    }
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        activity = getActivity();
//    }
}
