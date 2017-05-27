package com.mes.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mes.android.adapter.HomePageAdapter;
import com.mes.android.util.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monkey on 2017/5/27.
 */

public class HomeFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomePageAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);
        initData();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home_recyclerview);
        GridLayoutManager layoutManager=new GridLayoutManager(view.getContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter = new HomePageAdapter(mDatas));
        return view;
    }
    protected void initData()
    {
        mDatas = new ArrayList<String>();
        mDatas.add("客户");
        mDatas.add("成品");
        mDatas.add("缝纫");
        mDatas.add("半成品");
        mDatas.add("原料");
        mDatas.add("辅料");
        mDatas.add("余料");
    }
}
