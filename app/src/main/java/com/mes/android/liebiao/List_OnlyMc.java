package com.mes.android.liebiao;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.mes.android.R;
import com.mes.android.util.ResponseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class List_OnlyMc extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private EditText mgjzEditText;
    private Button mokButton;
    private Button mnoButton;
    private Button mcxButton;
    private String mMoeth;
    private List<String> mDataList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private LoadDataTask mAuthTask = null;
    private String mXuanZhongString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_onlymc);
        mMoeth = getIntent().getStringExtra("commId");
        this.setTitle(getIntent().getStringExtra("biaoti"));
        mListView = (ListView) findViewById(R.id.lv_list_caiwu_yewuleixing_liebiao);
        mgjzEditText = (EditText) findViewById(R.id.et_list_caiwu_yewuleixing_gjz);
        mcxButton = (Button) findViewById(R.id.bt_list_caiwu_yewuleixing_cx);
        mokButton = (Button) findViewById(R.id.bt_list_caiwu_yewuleixing_ok);
        mnoButton = (Button) findViewById(R.id.bt_list_caiwu_yewuleixing_no);
        mcxButton.setOnClickListener(this);
        mokButton.setOnClickListener(this);
        mnoButton.setOnClickListener(this);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDataList);
        mListView.setAdapter(mAdapter);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mXuanZhongString = mDataList.get(position);
            }
        });
        LoadingBar.make(mListView).show();
        String commd = "[{\"name\":\"tj\",\"value\":\"" + mMoeth + ",''\"}]";
//                Log.d("list_yewuleixing", "onClick: "+commd);
        mAuthTask = new LoadDataTask(commd);
        mAuthTask.execute((Void) null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_list_caiwu_yewuleixing_cx:
                LoadingBar.make(mListView).show();
                String commd = "[{\"name\":\"tj\",\"value\":\"" + mMoeth + ",'" + (mgjzEditText.getText().toString().equals("") ? "" : "mc like ''%" + mgjzEditText.getText().toString() + "%''") + "'\"}]";
//                Log.d("list_yewuleixing", "onClick: "+commd);
                mAuthTask = new LoadDataTask(commd);
                mAuthTask.execute((Void) null);
                break;
            case R.id.bt_list_caiwu_yewuleixing_ok:
//                Log.d("list_yewuleixing", "onClick: "+mXuanZhongString);
                if (mXuanZhongString != null && !mXuanZhongString.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("date_return", mXuanZhongString);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(List_OnlyMc.this,"未选择任何数据",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_list_caiwu_yewuleixing_no:
                finish();
                break;
            default:
                break;
        }
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
            LoadingBar.cancel(mListView);
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
                    Toast.makeText(List_OnlyMc.this, "未获取到数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(List_OnlyMc.this, success, Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
//取消Loading
            LoadingBar.cancel(mListView);
        }
    }

    /**
     * 加载数据
     *
     * @param jsonString 获取到的json数据
     */
    private void initData(String jsonString) {
        try {
            mDataList.clear();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                mDataList.add(jo.getString("mc"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
//        mListView.setAdapter(mAdapter);
//        if(mDataList.size()>0){
////            mListView.requestFocus();
////            mListView.setItemChecked(0, true);
//            mListView.setSelection(0);
//        }
    }
}
