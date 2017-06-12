package com.kido.videopager.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kido.videopager.R;
import com.kido.videopager.VideoData;
import com.kido.videopager.adapter.VideoPagerAdapter;
import com.kido.videopager.widget.coverflow.CoverFlowLayout;

import java.util.ArrayList;
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

        final ViewPager pager = mCoverFlowLayout.getViewPager();

        List<VideoData> mVideDatas = loadData();
        PagerAdapter adapter = new VideoPagerAdapter(mContext, mVideDatas);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
//        pager.setPageMargin();

        mCoverFlowLayout.config(new CoverFlowLayout.Builder()
//                .scale(0.2f)
//                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin_1))
//                .spaceSize(0f)
                        .addChildTransformer(new VideoPagerAdapter.VideoPagerTransformer())
                        .build()
        );

    }


    public static final int[] VIDEO_THUMBS = {R.drawable.image_hor_1, R.drawable.image_ver_1, R.drawable.image_hor_2, R.drawable.image_hor_3,
            R.drawable.image_hor_4, R.drawable.image_ver_2, R.drawable.image_ver_3, R.drawable.image_ver_4};

    public static final String[] VIDEO_WIDTH_HEIGHT = {"400x300", "300x400", "400x300", "400x300",
            "400x300", "300x400", "300x400", "300x400",
    };

    private List<VideoData> loadData() {
        List<VideoData> datas = new ArrayList<>();
        for (int i = 0, z = VIDEO_THUMBS.length; i < z; i++) {
            VideoData data = new VideoData();
            data.videoThumb = VIDEO_THUMBS[i];
            data.title = "This is the video title.This is the video title.This is the video title. " + i;
            String[] widthHeight = VIDEO_WIDTH_HEIGHT[i].split("x");
            data.width = Integer.parseInt(widthHeight[0]);
            data.height = Integer.parseInt(widthHeight[1]);
            datas.add(data);
        }
        return datas;
    }

}
