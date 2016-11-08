package com.xcqwan.sample;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thanks on 16/9/22.
 */

public abstract class LDBaseAdapter<T> extends BaseAdapter {
    private ArrayList<T> mData;

    public LDBaseAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        if (position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData() {
        mData.clear();
    }

    public void addData(T data) {
        mData.add(data);
    }

    public void addData(List<T> dataList) {
        mData.addAll(dataList);
    }

    public Drawable getDrawable(Context context, int rid) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(rid, context.getTheme());
        } else {
            return context.getResources().getDrawable(rid);
        }
    }
}
