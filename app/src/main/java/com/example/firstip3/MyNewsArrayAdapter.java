package com.example.firstip3;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyNewsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mNews;
    private String[] mCuisines;

    public MyNewsArrayAdapter( Context mContext, int resource, String[] mNews) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mNews = mNews;

    }

    @Override
    public int getCount() {
        return mNews.length;
    }

    @Override
    public Object getItem(int position) {
        String restaurant = mNews[position];
        String cuisine = mCuisines[position];
        return String.format("%s \n Serves great: %s", restaurant, cuisine);
    }
}
