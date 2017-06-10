package com.kido.videopager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kido.videopager.R;
import com.kido.videopager.VideoData;
import com.kido.videopager.widget.coverflow.CoverFlowLayout;
import com.kido.videopager.widget.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * @author Kido
 */

public class VideoPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<VideoData> datas;
    private LayoutInflater mInflater;

    public VideoPagerAdapter(Context context, List<VideoData> datas) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        VideoData data = datas.get(position);
        int layoutId = data.height >= data.width ? R.layout.item_page : R.layout.item_page_hor;
        View view = mInflater.inflate(layoutId, null);
        RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.imageView);
//        if (data.height >= data.width) {
//            imageView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_black_corner));
//            imageView.setCornerRadius(0);
//        } else {
//            imageView.setBackground(null);
//            imageView.setCornerRadius(mContext.getResources().getDimensionPixelSize(R.dimen.card_corner_radius));
//        }
        imageView.setImageResource(data.videoThumb);
//        TextView title = (TextView) view.findViewById(R.id.title);
//        title.setText(data.title);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public static class VideoPagerTransformer implements CoverFlowLayout.Transformer {

        @Override
        public void transform(View page, float position) {

        }
    }
}
