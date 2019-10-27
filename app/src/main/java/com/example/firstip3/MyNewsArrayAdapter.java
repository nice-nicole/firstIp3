package com.example.firstip3;

import android.content.Context;
import android.widget.ArrayAdapter;

public class MyNewsArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mNews;
    private String[] mCuisines;

    public MyRestaurantsArrayAdapter( Context mContext, int resource, String[] mRestaurants, String[] mCuisines) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mRestaurants = mRestaurants;
        this.mCuisines = mCuisines;
    }

    @Override
    public int getCount() {
        return mRestaurants.length;
    }

    @Override
    public Object getItem(int position) {
        String restaurant = mRestaurants[position];
        String cuisine = mCuisines[position];
        return String.format("%s \n Serves great: %s", restaurant, cuisine);
    }
}
