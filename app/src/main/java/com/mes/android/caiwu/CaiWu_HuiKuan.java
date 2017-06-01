package com.mes.android.caiwu;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mes.android.R;
import com.mes.android.Setting;

public class CaiWu_HuiKuan extends AppCompatActivity {
    private String mJueseid;
    private String mShuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caiwu__huikuan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_caiwuhuikuan_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.setTitle(getIntent().getStringExtra("mc"));
        mJueseid = getIntent().getStringExtra("jueseid");
        mShuid = getIntent().getStringExtra("shuid");
        CaiWu_HuiKuan_ChaXun com = new CaiWu_HuiKuan_ChaXun();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_caiwu_huikuan_chaxun_tiaojian, com).commit();
    }

    /**
     * 绑定菜单toolbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editbar, menu);
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
            case R.id.menu_edut_search:
                Toast.makeText(this, "search:", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_edut_add:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
