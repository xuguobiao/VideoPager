package com.kido.videopager;

import android.app.Activity;
import android.os.Bundle;

import com.kido.videopager.adapter.VideoPagerAdapter;
import com.kido.videopager.widget.coverflow.CoverFlow;
import com.kido.videopager.widget.coverflow.core.PagerContainer;
import com.kido.videopager.widget.coverflow.pager.PagerAdapter;
import com.kido.videopager.widget.coverflow.pager.ViewPager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        PagerContainer mContainer = (PagerContainer) findViewById(R.id.pager_container);
//        mContainer.setOverlapEnabled(true);

        final ViewPager pager = mContainer.getViewPager();

        PagerAdapter adapter = new VideoPagerAdapter(this, VIDEO_THUMBS);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());

        new CoverFlow.Builder()
                .with(pager)
                .scale(0.3f)
                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.pager_margin))
                .spaceSize(0f)
                .build();
    }

    public static final int[] VIDEO_THUMBS = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4,
            R.drawable.image_5, R.drawable.image_6, R.drawable.image_7, R.drawable.image_8};


}
