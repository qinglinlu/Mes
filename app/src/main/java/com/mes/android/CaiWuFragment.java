package com.mes.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mes.android.adapter.CaiWuPageAdapter;
import com.mes.android.gson.QuanXianData;
import com.mes.android.util.BaseFragment;
import com.mes.android.util.ResponseData;

import org.ksoap2.serialization.SoapObject;

import java.util.List;

/**
 * Created by monkey on 2017/5/28.
 */

public class CaiWuFragment extends BaseFragment {
    private ExpandableListView expandableListView;
    private List<String> mDatas;
    private CaiWuPageAdapter mAdapter;
    private View mProgressView;
    private LoadDataTask mAuthTask = null;
    private String mShuid;
    private String mJueseid;
    private List<QuanXianData> homeDatas;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShuid = getArguments().getString("shuid");
        mJueseid=getArguments().getString("jueseid");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_caiwu,container,false);
//        mProgressView =view.findViewById(R.id.pb_caiwu_progress);
        expandableListView = (ExpandableListView) view.findViewById(R.id.el_caiwu_liebiao);
//        GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),2);
//        mRecyclerView.setLayoutManager(layoutManager);

        String commd = "[{\"name\":\"tj\",\"value\":\"201,'[jueseid]=''" + mJueseid + "'' and [fid]=''"+ mShuid + "'''\"}]";
        mAuthTask = new LoadDataTask(commd);
        mAuthTask.execute((Void) null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(homeDatas.get(groupPosition).qx.size()==0){
                    Toast.makeText(getContext(),homeDatas.get(groupPosition).mc, Toast.LENGTH_SHORT).show();
                    return true;
                }else
                {
                    return false;
                }

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view,
                                        int parentPos, int childPos, long l) {
                Toast.makeText(getContext(),homeDatas.get(parentPos).qx.get(childPos).qxmc, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return view;
    }
    protected void initData(String jsonString)
    {
        Log.d("Caiwu",jsonString);
        Gson gson = new Gson();
        homeDatas = gson.fromJson(jsonString, new TypeToken<List<QuanXianData>>() {
        }.getType());
//        Log.d("Caiwu",String.valueOf(homeDatas.size()));
//        for(QuanXianData qxdt:homeDatas){
//            Log.d("Caiwu",qxdt.mc);
//            for(QuanXianData.myqx qxx:qxdt.qx){
//                Log.d("Caiwu.mx",qxx.qxmc);
//            }
//        }
//
        expandableListView.setAdapter(new CaiWuPageAdapter(getActivity(),homeDatas));

        //可伸展的列表视图加载adapter
//        expandableListView.setAdapter(adapter);
    }
    /**
     * 异步加载数据
     */
    private class LoadDataTask extends AsyncTask<Void, Void, String> {
        private final String mComm;

        LoadDataTask(String commParameter) {

            mComm = commParameter;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                // 开始验证登陆信息，如成功应该更新动态匹配信息

                //把查询条件拼成JSON数据以方便多查询参数时使用，记住参数名(也就是name的值)必须和WCF方法中的参数名一致
//                String comm = "[{\"name\":\"tj\",\"value\":\"bh='" + mUserName + "' and password='" + mpassword + "'\"}]";
//                ResponseData rd=new ResponseData(LoginActivity.this,"Get_JiChu_UserTable",comm);
                ResponseData rd = new ResponseData("Get_Data", mComm);
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
//            showProgress(false);

            if (success.indexOf("{") != -1) {
                //i添加在历史帐号信息
                try {
                    String jsonString=success.replaceAll("\\\\","");//去除字符串中的 \
                    jsonString=jsonString.replaceAll("\"\\[","\\[");//替换字符串中的" [为 [
                    jsonString=jsonString.replaceAll("\\]\"","\\]");//替换字符串中的 ]" 为 ]
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
//            showProgress(false);
        }
    }
}
