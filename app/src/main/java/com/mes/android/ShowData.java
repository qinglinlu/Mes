package com.mes.android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mes.android.caiwu.CaiWu_HuiKuan_ChaXun;

public class ShowData extends AppCompatActivity {
    private String mJueseid;
    private String mShuid;
    public DrawerLayout mDrawerLayout;
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
        this.setTitle(getIntent().getStringExtra("mc"));
        mJueseid=getIntent().getStringExtra("jueseid");
        mShuid=getIntent().getStringExtra("shuid");

        replaceFragment(new CaiWu_HuiKuan_ChaXun());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edut_search:
                mDrawerLayout.openDrawer(GravityCompat.START);
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
        if (str != null) {
            Log.d("ShowData","返回的文本是"+str);
        }
    }
}
