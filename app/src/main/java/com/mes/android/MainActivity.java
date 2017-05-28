package com.mes.android;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mes.android.adapter.MainFragmentPagerAdapter;
import com.mes.android.gson.MainData;
import com.mes.android.util.ResponseData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LoadDataTask mAuthTask = null;
    private String serverAddress = "";
    private View mProgressView;
    private View mZhuTiFormView;
    SharedPreferences pref;
    private String mUserId;
    private String mUserName;
    private String mJueSeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_main_toolbar);
        setSupportActionBar(toolbar);
//        mUserId=getIntent().getStringExtra("bh");
//        mUserName=getIntent().getStringExtra("name");
//        mJueSeId=getIntent().getStringExtra("jueseid");
        mUserId = "01";
        mUserName = "卢君华";
        mJueSeId = "3";
        this.setTitle(mUserName);

        mZhuTiFormView = findViewById(R.id.ll_main_zhuti);
        mProgressView = findViewById(R.id.pb_main_progress);
        String commd = "[{\"name\":\"userId\",\"value\":\"01\"}]";
        showProgress(true);
        mAuthTask = new LoadDataTask("Get_JueSePage", commd);
        mAuthTask.execute((Void) null);
    }

    /**
     * 加载对应的角色权限界面
     *
     * @param mData
     */
    private void loadPage(String mData) {
        Gson gson = new Gson();
        List<MainData> mainDatas = gson.fromJson(mData, new TypeToken<List<MainData>>() {
        }.getType());

        if (mainDatas.size() > 0) {
            List<Fragment> fragmentList = new ArrayList<>();//创建Fragment碎片List
            List<String> titles = new ArrayList<>();
            for (MainData md : mainDatas) {
                if (md.mc.equals("首页")) {
                    titles.add(md.mc);
                    HomeFragment homeFragment = new HomeFragment();//初始化首页
                    //为碎片传值
                    Bundle bdl = new Bundle();
                    bdl.putString("shuid", md.shuid);
                    bdl.putString("jueseid", mJueSeId);
                    homeFragment.setArguments(bdl);
                    fragmentList.add(homeFragment);//把碎片添加到List
                }
            }

            if (titles.size() > 0) {
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
                MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titles);
                viewPager.setAdapter(adapter);

                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
                tabLayout.setupWithViewPager(viewPager);
            }

        } else {
            Toast.makeText(MainActivity.this, "没有获取到权限", Toast.LENGTH_SHORT).show();
        }
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

            mZhuTiFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mZhuTiFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mZhuTiFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mZhuTiFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
                    loadPage(success);
//Log.d("MainActivity",success);
//                    JSONArray jsonArray = new JSONArray(success);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);//
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("userId", jsonObject.getString("bh"));
//                    intent.putExtra("userName", jsonObject.getString("name"));
//                    startActivity(intent);
//                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
//                mPassword.setError("密码错误");
//                mPassword.requestFocus();
                if (success.indexOf("[") != -1) {
                    Toast.makeText(MainActivity.this, "未获取到数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, success, Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * 绑定菜单toolbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    /**
     * 设置菜单点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mu_main_about:
                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mu_main_update:
                startActivity(new Intent(this, Setting.class));
//                Toast.makeText(this, "about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mu_main_exit:
                finish();
                System.exit(0);
                break;
            default:
                break;
        }
        return true;
    }
}
