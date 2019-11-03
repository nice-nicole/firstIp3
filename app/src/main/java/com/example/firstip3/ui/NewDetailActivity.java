package com.example.firstip3.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.firstip3.R;
import com.example.firstip3.adapters.NewPagerAdapter;
import com.example.firstip3.models.Business;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewDetailActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private NewPagerAdapter adapterViewPager;
    List<Business> mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        ButterKnife.bind(this);

        mNews= Parcels.unwrap(getIntent().getParcelableExtra("news"));
        int startingPosition = getIntent().getIntExtra("position",0);

        adapterViewPager = new NewPagerAdapter(getSupportFragmentManager(), mNews);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}

