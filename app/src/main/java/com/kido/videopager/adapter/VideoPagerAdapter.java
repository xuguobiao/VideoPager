package com.kido.videopager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
//        int layoutId = data.height >= data.width ? R.layout.item_page : R.layout.item_page_hor;
        int layoutId = R.layout.item_page;
        View view = mInflater.inflate(layoutId, null);

        ViewHolder holder = new ViewHolder(view);
        adjustLayout(holder, position);

        holder.imageView.setImageResource(data.videoThumb);
        holder.title.setText(data.title);

        view.setTag(holder);

        container.addView(view);
        return view;
    }

    private void adjustLayout(ViewHolder holder, int position) {
        VideoData data = datas.get(position);
        if (data.height >= data.width) { // vertical
            holder.imageView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_black_corner));
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            holder.imageView.setCornerRadius(0);
            ((RelativeLayout.LayoutParams) holder.belowLayout.getLayoutParams()).topMargin = mContext.getResources().getDimensionPixelSize(R.dimen.card_below_top_margin_ver);
            ((RelativeLayout.LayoutParams) holder.imageView.getLayoutParams()).height = mContext.getResources().getDimensionPixelSize(R.dimen.card_image_height_ver);
        } else {
            holder.imageView.setBackgroundDrawable(null);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.imageView.setCornerRadius(mContext.getResources().getDimensionPixelSize(R.dimen.card_corner_radius));
            ((RelativeLayout.LayoutParams) holder.belowLayout.getLayoutParams()).topMargin = mContext.getResources().getDimensionPixelSize(R.dimen.card_below_top_margin_hor);
            ((RelativeLayout.LayoutParams) holder.imageView.getLayoutParams()).height = mContext.getResources().getDimensionPixelSize(R.dimen.card_image_height_hor);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        view.setTag(null);
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

    public static class ViewHolder { // just for setTag

        public View belowLayout;
        public View imageLayout;
        public RoundedImageView imageView;
        public TextView title;

        public ViewHolder(View view) {
            belowLayout = view.findViewById(R.id.below_layout);
            imageLayout = view.findViewById(R.id.image_layout);
            imageView = (RoundedImageView) view.findViewById(R.id.imageView);
            title = (TextView) view.findViewById(R.id.title);


        }
    }

    public static final float SCALE_MIN = 0.3f;
    public static final float SCALE_MAX = 1f;
    public static final float MARGIN_MIN = 0f;
    public static final float MARGIN_MAX = 50f;

    public static class VideoPagerTransformer implements CoverFlowLayout.Transformer {

        @Override
        public void transform(View page, float position) {
            ViewHolder holder = (ViewHolder) page.getTag();
            float realAlpha = getFloat(1 - Math.abs(position * 3.5f), 0, 1);
            holder.belowLayout.setAlpha(realAlpha);

            float realScale = getFloat(1 - Math.abs(position * 0.2f), SCALE_MIN, SCALE_MAX);
            holder.imageLayout.setScaleX(realScale);
            holder.imageLayout.setScaleY(realScale);
            holder.belowLayout.setScaleX(realScale);
            holder.belowLayout.setScaleX(realScale);

            float pagerMargin = page.getContext().getResources().getDimensionPixelSize(R.dimen.pager_margin_1);
            float realPagerMargin = position * (pagerMargin);
            holder.imageLayout.setTranslationX(realPagerMargin);
        }
    }

    private static float getFloat(float value, float minValue, float maxValue) {
        return Math.min(maxValue, Math.max(minValue, value));
    }

}
