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

import java.util.List;

/**
 * Created by monkey on 2017/5/27.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mHome;
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
    public HomePageAdapter(List<String> parhome){
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
        String home=mHome.get(position);
        holder.biaotiText.setText(home);
    }

    @Override
    public int getItemCount() {
        return mHome.size();
    }
}
