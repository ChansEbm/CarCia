package com.app.CarCia.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * Created by ChanZeeBm on 2015/10/8.
 */
public abstract class CommonExpandableAdapter<T> extends BaseExpandableListAdapter {

    private List<T> group;
    private List<List<T>> child;
    private Context context;
    private int groupRes;
    private int childRes;
    private boolean clickable;

    /**
     *
     * @param group 标签
     * @param child 内容
     * @param context 上下文
     * @param groupRes 标签布局
     * @param childRes 内容布局
     */
    public CommonExpandableAdapter(List<T> group, List<List<T>> child, Context context, int
            groupRes, int childRes) {
        this.group = group;
        this.child = child;
        this.context = context;
        this.groupRes = groupRes;
        this.childRes = childRes;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public T getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public T getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
            parent) {
        ViewHolder viewHolder = getViewHolder(convertView, parent, groupRes, groupPosition);
        group(viewHolder, getGroup(groupPosition));
        viewHolder.getConvertView().setClickable(clickable);
        return viewHolder.getConvertView();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        ViewHolder viewHolder = getViewHolder(convertView, parent, childRes, childPosition);
        child(viewHolder, getChild(groupPosition, childPosition));
        return viewHolder.getConvertView();
    }

    public abstract void group(ViewHolder viewHolder, T t);

    public abstract void child(ViewHolder viewHolder, T t);

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public ViewHolder getViewHolder(View convertView, ViewGroup viewGroup, int resId, int
            position) {
        return ViewHolder.get(context, convertView, viewGroup, resId, position);
    }

    public void setGroupClickable(boolean clickable) {
        this.clickable = clickable;
        notifyDataSetChanged();
    }
}