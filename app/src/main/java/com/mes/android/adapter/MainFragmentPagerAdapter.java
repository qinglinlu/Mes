package com.mes.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by monkey on 2017/5/27.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> titles;
    List<Fragment> fragmentList;


    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titlesList) {
        super(fm);
        this.titles = titlesList;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
