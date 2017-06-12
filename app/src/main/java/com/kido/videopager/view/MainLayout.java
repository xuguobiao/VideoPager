package com.kido.videopager.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kido.videopager.R;
import com.kido.videopager.utils.Utils;
import com.kido.videopager.widget.tablayout.FixedTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kido
 */

public class MainLayout extends FrameLayout {

    private static final String[] TAB_TITLES = {"频道", "有料", "播单"};

    private Context mContext;
    private FixedTabLayout mTabLayout;
    private FrameLayout mContentFLayout;

    public MainLayout(Context context) {
        this(context, null);
    }

    public MainLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initAction();
    }

    private void initView() {
        LinearLayout topLLayout = new LinearLayout(mContext);
        topLLayout.setOrientation(LinearLayout.VERTICAL);
        topLLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // title layout start
        FrameLayout titleFLayout = new FrameLayout(mContext);
        titleFLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        titleFLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTabLayout = new FixedTabLayout(mContext);
        mTabLayout.setIconVisible(false);
        mTabLayout.setIndicatorCornerRadius(1);
        mTabLayout.setIndicatorHeight(2);
        mTabLayout.setIndicatorWidth(10);
        mTabLayout.setTextSelectColor(Color.parseColor("#ffffff"));
        mTabLayout.setTextUnselectColor(Color.parseColor("#55ffffff"));
        mTabLayout.setTextsize(16);
        mTabLayout.setLayoutParams(new FrameLayout.LayoutParams(Utils.dp2px(mContext, 200), Utils.dp2px(mContext, 48)));
        titleFLayout.addView(mTabLayout);
        // title layout end

        // content layout start
        mContentFLayout = new FrameLayout(mContext);
        mContentFLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ChannelLayout channelLayout = new ChannelLayout(mContext);
        channelLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        YouLiaoLayout youLiaoLayout = new YouLiaoLayout(mContext);
        youLiaoLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        PlayListLayout playListLayout = new PlayListLayout(mContext);
        playListLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mContentFLayout.addView(channelLayout);
        mContentFLayout.addView(youLiaoLayout);
        mContentFLayout.addView(playListLayout);
        // content layout end

        topLLayout.addView(titleFLayout);
        topLLayout.addView(mContentFLayout);

        addView(topLLayout);
    }

    private void initAction() {
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
//                showToast("onTabSelect-> position=" + position + ", title=" + TAB_TITLES[position]);
                switchLayout(position);
            }

            @Override
            public void onTabReselect(int position) {
//                showToast("onTabReselect-> position=" + position + ", title=" + TAB_TITLES[position]);
            }
        });
    }

    private void switchLayout(int pos) {
        for (int i = 0; i < mContentFLayout.getChildCount(); i++) {
            int visibility = i == pos ? View.VISIBLE : View.GONE;
            mContentFLayout.getChildAt(i).setVisibility(visibility);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
