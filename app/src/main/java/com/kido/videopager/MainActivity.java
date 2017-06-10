package com.kido.videopager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.kido.videopager.adapter.VideoPagerAdapter;
import com.kido.videopager.utils.Logger;
import com.kido.videopager.widget.coverflow.CoverFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private CoverFlowLayout mCoverFlowLayout;
    private List<VideoData> mVideDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        mCoverFlowLayout = (CoverFlowLayout) findViewById(R.id.cover_flow_layout);
//        mCoverFlowLayout.setOverlapEnabled(true);

        final ViewPager pager = mCoverFlowLayout.getViewPager();

        mVideDatas = loadData();
        PagerAdapter adapter = new VideoPagerAdapter(this, mVideDatas);
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

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Logger.d("kido", "onPageScrolled-> position=%s, positionOffset=%s, positionOffsetPixels=%s",
                        position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Logger.d("kido", "onPageSelected-> position=%s", position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.d("kido", "onPageScrollStateChanged-> state=%s", state);
            }
        });

    }

    private static float getFloat(float value, float minValue, float maxValue) {
        return Math.min(maxValue, Math.max(minValue, value));
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
