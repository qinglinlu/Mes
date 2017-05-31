package com.mes.android.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mes.android.R;
import com.mes.android.gson.QuanXianData;

import java.util.List;


/**
 * Created by monkey on 2017/5/29.
 */

public class CaiWuPageAdapter extends BaseExpandableListAdapter {
    private List<QuanXianData> mQuanXianDataList;
    private Context mContext;
    //声明一个布局管理器对象
    LayoutInflater mInflater;

    /**
     * 自定义适配器的构造函数
     * @param context 上下文
     * @param quanXianDataList  元素集合
     */
    public CaiWuPageAdapter(Context context, List<QuanXianData> quanXianDataList) {
        this.mQuanXianDataList = quanXianDataList;
        this.mContext = context;
        //初始化布局管理器对象
        mInflater = LayoutInflater.from(context);
    }
//    /**
//     * ExpandableListAdapter里面的所有条目
//     * 都可用吗？如果是yes，就意味着所有条目可以选择和点击了。
//     * 返回值：返回True表示所有条目均可用。
//     */
//    @Override
//    public boolean areAllItemsEnabled() {
//        // TODO Auto-generated method stub
//        return true;
//    }
    //  获得父项的数量
    @Override
    public int getGroupCount() {
        return mQuanXianDataList.size();
    }

    //  获得某个父项的某个子项
    @Override
    public int getChildrenCount(int groupPosition) {
        return mQuanXianDataList.get(groupPosition).qx.size();
    }

    //  获得某个父项
    @Override
    public Object getGroup(int groupPosition) {
        return mQuanXianDataList.get(groupPosition);
    }

    //  获得某个父项的子项数目
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mQuanXianDataList.get(groupPosition).qx.get(childPosition);
    }

    /**
     * 获取组ID
     * @param groupId 组ID
     * @return :组ID
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取指定组中的指定子元素ID，这个ID在组里一定是唯一的。联合ID（getCombinedChildId(long, long)）在所有条目（所有组和所有元素）中也是唯一的。
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @return 返回一个Boolean类型的值，如果为TRUE，意味着相同的ID永远引用相同的对象
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取指定组的视图对象
     * @param groupPosition:组位置（决定返回哪个视图）
     * @param isExpanded:改组是展开状态还是伸缩状态
     * @param convertView:重用已有的视图对象
     * @return 返回指定组的视图对象
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolder;
        if (convertView == null) {
            //初始化控件管理器对象
            viewHolder = new ViewHolderGroup();
            //重新加载布局
            convertView = mInflater.inflate(R.layout.activity_caiwu_exgroup, null);
            //给组元素绑定ID
            viewHolder.logo_tv = (TextView) convertView.findViewById(R.id.tv_caiwu_exgroup_biaoti);
            //给组元素箭头绑定ID
            viewHolder.arrow = (ImageView) convertView.findViewById(R.id.iv_caiwu_exgroup_jiantou);
            //convertView将viewHolder设置到Tag中，以便再次绘制ExpandableListView时从Tag中取出viewHolder;
            convertView.setTag(viewHolder);
        }else {//如果convertView不为空，即getScrapView得到废弃已缓存的view
            //从Tag中取出之前存入的viewHolder
            viewHolder = (ViewHolderGroup) convertView.getTag();
        }
//设置组值
        viewHolder.logo_tv.setText(mQuanXianDataList.get(groupPosition).mc);
        //如果没有子项就不显示前头
        if(mQuanXianDataList.get(groupPosition).qx.size()>0){
            viewHolder.arrow.setVisibility(View.VISIBLE);
        }else {
            viewHolder.arrow.setVisibility(View.GONE);
        }

        //如果组是展开状态
        if (isExpanded) {
            //箭头向下
            viewHolder.arrow.setImageResource(R.mipmap.ic_gengduo2);
        }else{//如果组是伸缩状态
            //箭头向右
            viewHolder.arrow.setImageResource(R.mipmap.ic_gengduo);
        }
        //返回得到的指定组的视图对象
        return convertView;
    }

    //  获得子项显示的view
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild;
        if (convertView == null) {
            //重新加载布局
            convertView = mInflater.inflate(R.layout.activity_caiwu_exitem, null);
            //初始化控件管理器（自己命名的）
            viewHolderChild = new ViewHolderChild();
            //绑定控件id
            viewHolderChild.tv = (TextView) convertView.findViewById(R.id.tv_caiwu_exitem_biaoti);
            /*convertView的setTag将viewHolderChild设置到Tag中，以便系统第二次绘制
                ExpandableListView时从Tag中取出
            */
            convertView.setTag(viewHolderChild);
        } else {
            //当convertView不为空时，从Tag中取出viewHolderChild
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }
//给子元素的TextView设置值
        viewHolderChild.tv.setText(mQuanXianDataList.get(groupPosition).qx.get(childPosition).qxmc);
        //返回视图对象，这里是childPostion处的视图
        return convertView;
    }

    //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 组控件管理器
     *
     * @author Administrator
     */
    class ViewHolderGroup {
        TextView logo_tv;
        ImageView arrow;
    }

    /**
     * 子控件管理器
     *
     * @author Administrator
     */
    class ViewHolderChild {
        TextView tv;
    }
}
