package com.mes.android.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mes.android.R;
import com.mes.android.gson.HomeData;

import java.util.List;

/**
 * Created by monkey on 2017/5/27.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    private Context mContext;
    private List<HomeData> mHome;
    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView biaotiText;
        TextView neirongText;
        TextView shujuText;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            biaotiText=(TextView)view.findViewById(R.id.tv_home_item_biaoti);
            neirongText=(TextView)view.findViewById(R.id.tv_home_item_meirong);
            shujuText=(TextView)view.findViewById(R.id.tv_home_item_shuju);
        }
    }
    public HomePageAdapter(List<HomeData> parhome){
        mHome=parhome;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_home_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String leixin="";
        String shuju="";
        holder.biaotiText.setText(mHome.get(position).mc);
        if(mHome.get(position).qx.size()>0){
            for(HomeData.myqx qx:mHome.get(position).qx){
                leixin+=qx.name+"\n";
                shuju+=qx.sl+"\n";
            }
        }
        holder.neirongText.setText(leixin);
        holder.shujuText.setText(shuju);
    }

    @Override
    public int getItemCount() {
        return mHome.size();
    }
}
