package com.kido.videopager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.kido.videopager.view.YouLiaoLayout;
import com.kido.videopager.widget.tablayout.FixedTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private FixedTabLayout mTabLayout;
    private YouLiaoLayout mYouLiaoLayout;

    private static final String[] TAB_TITLES = {"频道", "有料", "播单"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        mYouLiaoLayout = (YouLiaoLayout) findViewById(R.id.youliao_layout);
        mTabLayout = (FixedTabLayout) findViewById(R.id.tabLayout);

        List<FixedTabLayout.TabItem> tabItems = new ArrayList<>();
        for (String title : TAB_TITLES) {
            tabItems.add(new FixedTabLayout.TabItem(title));
        }

        mTabLayout.setTabData(tabItems);
        mTabLayout.setCurrentTab(1);
        mTabLayout.setOnTabSelectListener(new FixedTabLayout.OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showToast("onTabSelect-> position=" + position + ", title=" + TAB_TITLES[position]);
            }

            @Override
            public void onTabReselect(int position) {
                showToast("onTabReselect-> position=" + position + ", title=" + TAB_TITLES[position]);
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
