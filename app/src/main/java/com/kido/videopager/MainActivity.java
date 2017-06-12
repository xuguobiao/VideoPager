package com.kido.videopager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kido.videopager.widget.tablayout.FixedTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private FixedTabLayout mTabLayout;
    private FrameLayout mPageContainer;

    private static final String[] TAB_TITLES = {"频道", "有料", "播单"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        mTabLayout = (FixedTabLayout) findViewById(R.id.tabLayout);
        mPageContainer = (FrameLayout) findViewById(R.id.page_container);

        List<FixedTabLayout.TabItem> tabItems = new ArrayList<>();
        for (String title : TAB_TITLES) {
            tabItems.add(new FixedTabLayout.TabItem(title));
        }

        mTabLayout.setTabData(tabItems);
        mTabLayout.setCurrentTab(1);
        switchLayout(1);
        mTabLayout.showDot(2);
        mTabLayout.setOnTabSelectListener(new FixedTabLayout.OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showToast("onTabSelect-> position=" + position + ", title=" + TAB_TITLES[position]);
                switchLayout(position);
            }

            @Override
            public void onTabReselect(int position) {
                showToast("onTabReselect-> position=" + position + ", title=" + TAB_TITLES[position]);
            }
        });

    }

    private void switchLayout(int pos) {
        for (int i = 0; i < mPageContainer.getChildCount(); i++) {
            int visibility = i == pos ? View.VISIBLE : View.GONE;
            mPageContainer.getChildAt(i).setVisibility(visibility);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
