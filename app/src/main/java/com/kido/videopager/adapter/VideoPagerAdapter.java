package com.kido.videopager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kido.videopager.R;
import com.kido.videopager.widget.coverflow.pager.PagerAdapter;

/**
 * @author Kido
 */

public class VideoPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] images;
    private LayoutInflater mInflater;

    public VideoPagerAdapter(Context context, int[] images) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.images = images;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mInflater.inflate(R.layout.item_page, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
