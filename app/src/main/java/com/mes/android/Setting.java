package com.mes.android;

import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Setting extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private EditText editText;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.login_settings_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button okButton = (Button) findViewById(R.id.bt_setting_OK);
        editText = (EditText) findViewById(R.id.et_setting_server_address);
//        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences pref = getSharedPreferences("server", MODE_PRIVATE);
        if(!pref.getString("serveraddress","").isEmpty()){
            editText.setText(pref.getString("serveraddress",""));
        }
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("server", MODE_PRIVATE).edit();

                editor.putString("serveraddress", editText.getText().toString());
                editor.apply();
                finish();
            }
        });

    }
}
