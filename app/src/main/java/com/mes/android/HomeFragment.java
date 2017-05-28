package com.mes.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mes.android.adapter.HomePageAdapter;
import com.mes.android.gson.HomeData;
import com.mes.android.util.BaseFragment;
import com.mes.android.util.ResponseData;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkey on 2017/5/27.
 */

public class HomeFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomePageAdapter mAdapter;
    private View mProgressView;
    private LoadDataTask mAuthTask = null;
    private String mShuid;
private String mJueseid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShuid = getArguments().getString("shuid");
        mJueseid=getArguments().getString("jueseid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);
        mProgressView =view.findViewById(R.id.pb_home_progress);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home_recyclerview);
        GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);


        String commd = "[{\"name\":\"tj\",\"value\":\"199,'[jueseid]=''" + mJueseid + "'' and [shuid]=''"+ mShuid + "'''\"}]";
        showProgress(true);
        mAuthTask = new LoadDataTask("Get_Data", commd);
        mAuthTask.execute((Void) null);

        return view;
    }
    protected void initData(String jsonString)
    {
//        Log.d("Home",jsonString);
        Gson gson = new Gson();
        List<HomeData> homeDatas = gson.fromJson(jsonString, new TypeToken<List<HomeData>>() {
        }.getType());
        Log.d("Home",String.valueOf(homeDatas.size()));
//
        mRecyclerView.setAdapter(new HomePageAdapter(homeDatas));
//        mDatas = new ArrayList<String>();
//        mDatas.add("客户");
//        mDatas.add("成品");
//        mDatas.add("缝纫");
//        mDatas.add("半成品");
//        mDatas.add("原料");
//        mDatas.add("辅料");
//        mDatas.add("余料");
    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRecyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRecyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 异步加载数据
     */
    private class LoadDataTask extends AsyncTask<Void, Void, String> {
        private final String mMethodName;
        private final String mComm;

        LoadDataTask(String methodName, String commParameter) {
            mMethodName = methodName;
            mComm = commParameter;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // 开始验证登陆信息，如成功应该更新动态匹配信息

                //把查询条件拼成JSON数据以方便多查询参数时使用，记住参数名(也就是name的值)必须和WCF方法中的参数名一致
//                String comm = "[{\"name\":\"tj\",\"value\":\"bh='" + mUserName + "' and password='" + mpassword + "'\"}]";
//                ResponseData rd=new ResponseData(LoginActivity.this,"Get_JiChu_UserTable",comm);
                ResponseData rd = new ResponseData(mMethodName, mComm);
                SoapObject soapObject = rd.LoadResult();
                String result = soapObject == null ? "网络连接失败!" : soapObject.getProperty(0).toString();
//                Log.d("loginactivity", result);
                return result;
            } catch (Exception e) {

                e.printStackTrace();
                return "未知错误";
            }
        }

        @Override
        protected void onPostExecute(final String success) {
            mAuthTask = null;
            showProgress(false);

            if (success.indexOf("{") != -1) {
                //i添加在历史帐号信息
                try {
                    String jsonString=success.replaceAll("\\\\","");
                    jsonString=jsonString.replaceAll("\"\\[","\\[");
                    jsonString=jsonString.replaceAll("\\]\"","\\]");
                    initData(jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
//                mPassword.setError("密码错误");
//                mPassword.requestFocus();
                if (success.indexOf("[") != -1) {
                    Toast.makeText(getContext(), "未获取到数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), success, Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}
