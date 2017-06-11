package com.kido.videopager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.kido.videopager.adapter.MyPagerAdapter;
import com.kido.videopager.fragment.BDFragment;
import com.kido.videopager.fragment.PDFragment;
import com.kido.videopager.fragment.YLFragment;
import com.kido.videopager.widget.CustomViewPager;
import com.kido.videopager.widget.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private final String[] mTitles = {
            "频道", "有料", "播单"
    };
    private List<Fragment> mFragments = new ArrayList<>();

    private SlidingTabLayout mTabLayout;
    private CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tabLayout);
        mViewPager = (CustomViewPager) findViewById(R.id.viewPager);

        mFragments.add(new PDFragment());
        mFragments.add(new YLFragment());
        mFragments.add(new BDFragment());

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        mViewPager.setPagingEnabled(false);

        mTabLayout.setViewPager(mViewPager);
        mTabLayout.setCurrentTab(1, false);
    }

}
