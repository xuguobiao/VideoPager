package com.kido.videopager.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kido.videopager.R;
import com.kido.videopager.VideoData;
import com.kido.videopager.adapter.VideoPagerAdapter;
import com.kido.videopager.utils.TestData;
import com.kido.videopager.utils.Utils;
import com.kido.videopager.widget.CustomViewPager;
import com.kido.videopager.widget.coverflow.CoverFlowLayout;

import java.util.List;

/**
 * @author Kido
 */

public class YouLiaoLayout extends FrameLayout {

    private Context mContext;

    public YouLiaoLayout(Context context) {
        this(context, null);
    }

    public YouLiaoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YouLiaoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_youliao, this, true);
        bindViews(this);
    }


    private void bindViews(View view) {
        CoverFlowLayout mCoverFlowLayout = (CoverFlowLayout) view.findViewById(R.id.cover_flow_layout);
//        mCoverFlowLayout.setOverlapEnabled(true);

        final CustomViewPager pager = (CustomViewPager) mCoverFlowLayout.getViewPager();

        List<VideoData> mVideDatas = TestData.loadVideoData();
        PagerAdapter adapter = new VideoPagerAdapter(mContext, mVideDatas);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setPagingRect(Integer.MIN_VALUE, 0, Integer.MAX_VALUE, Utils.dp2px(getContext(), 300));
//        pager.setPageMargin();

        mCoverFlowLayout.config(new CoverFlowLayout.Builder()
//                .scale(0.2f)
//                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin_1))
//                .spaceSize(0f)
                        .addChildTransformer(new VideoPagerAdapter.VideoPagerTransformer())
                        .build()
        );

    }


}
