package com.mes.android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.google.gson.Gson;
import com.mes.android.caiwu.CaiWu_HuiKuan_ChaXun;
import com.mes.android.liebiao.List_OnlyMc;
import com.mes.android.util.ListDateTime;
import com.mes.android.util.ResponseData;

import org.ksoap2.serialization.SoapObject;

public class ShowData extends AppCompatActivity {
    private String mJueseid;
    private String mShuid;
    private String mMc;
    private LoadDataTask mAuthTask = null;
    public DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private String mMoeth="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDrawerLayout=(DrawerLayout)findViewById(R.id.dl_showdata);
        mMc=getIntent().getStringExtra("mc");
        this.setTitle(mMc);
        mJueseid=getIntent().getStringExtra("jueseid");
        mShuid=getIntent().getStringExtra("shuid");

        replaceFragment(new CaiWu_HuiKuan_ChaXun());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editbar,menu);
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case 100:
//                if(resultCode==RESULT_OK){
//                    if(mMc.equals("回款管理")){
//                        CaiWu_HuiKuan_ChaXun.
//                    }
//
////                    String returnData=data.getStringExtra("date_return");
////                    Log.d("showdata", returnData+",result_ok"+String.valueOf(RESULT_OK));
//                }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edut_search:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_edut_add:
//                Intent intent=new Intent(ShowData.this, ListDateTime.class);
//                startActivityForResult(intent,100);
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
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
            LoadingBar.cancel(mRecyclerView);
            if (success.indexOf("{") != -1) {
                //i添加在历史帐号信息
                try {
                    String jsonString = success.replaceAll("\\\\", "");//去除字符串中的 \
                    jsonString = jsonString.replaceAll("\"\\[", "\\[");//替换字符串中的" [为 [
                    jsonString = jsonString.replaceAll("\\]\"", "\\]");//替换字符串中的 ]" 为 ]
                    initData(jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
//                mPassword.setError("密码错误");
//                mPassword.requestFocus();
                if (success.indexOf("[") != -1) {
                    Toast.makeText(ShowData.this, "未获取到数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowData.this, success, Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
//取消Loading
            LoadingBar.cancel(mRecyclerView);
        }
    }

    /**
     * 显示数据
     * @param jsonString 获取到的JSON数据
     */
    private void initData(String jsonString) {
        Gson gson=new Gson();

    }

    /**
     * 替换查询条件界面
     */
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.fm_showdata_chaxun,fragment);
        transaction.commit();
    }
    /**
     * 给Fragment调用
     * @param str
     */
    public void process(String str) {
        if (str != null && !str.isEmpty()) {
//            Log.d("ShowData","返回的文本是"+str);
            LoadingBar.make(mRecyclerView).show();
            String commd = "[{\"name\":\"tj\",\"value\":\"" + mMoeth + ",'"+str.replace("'","''")+"'\"}]";
//                Log.d("list_yewuleixing", "onClick: "+commd);
            mAuthTask = new LoadDataTask(commd);
            mAuthTask.execute((Void) null);
        }
    }
}
