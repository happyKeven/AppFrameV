package com.keven.library.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用的适配器，简化适配器的代码
 * @author keven
 * create at 2016/11/8 10:16
 */
public abstract class CustomCommAdapter<T> extends BaseAdapter{
    protected Context mContext;
    protected int mResId;
    protected List<T> mDataList;
    protected LayoutInflater mInflater;
    protected int mType;

    public CustomCommAdapter(Context context, int resId, List<T> dataList, int type) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mResId = resId;
        mType = type;
        if (null == dataList) {
            mDataList = getDataList();
        } else {
            mDataList = dataList;
        }
    }

    @Override
    public int getCount() {
        if (null != mDataList) {
            return mDataList.size();
        }
        return 0;
    }

    @Override
    public T getItem(int i) {
        if (null != mDataList) {
            return mDataList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            view = mInflater.inflate(mResId, viewGroup, false);
        }
        return view;
    }

    public void updateList(List<T> list) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public abstract List<T> getDataList();

}
