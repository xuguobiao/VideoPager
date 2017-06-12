package com.kido.videopager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kido.videopager.R;
import com.kido.videopager.VideoData;
import com.kido.videopager.utils.Utils;
import com.kido.videopager.widget.ShadowLayout;
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
        ViewHolder holder = createView(position);

        holder.itemView.setTag(holder);
        container.addView(holder.itemView);

        holder.imageView.setImageResource(data.videoThumb);

        return holder.itemView;
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
        public View itemView;
        public View imageLayout;
        public RoundedImageView imageView;
        public View belowLayout;

        public ViewHolder() {
        }

        public void reset() {
            itemView = null;
            imageLayout = null;
            imageView = null;
            belowLayout = null;
        }
    }

    private ViewHolder createView(int pos) {

        VideoData data = datas.get(pos);
        boolean isVer = data.height >= data.width;


        RelativeLayout rootRLayout = new RelativeLayout(mContext);
        rootRLayout.setLayoutParams(new ViewPager.LayoutParams());

        LinearLayout lLayout = new LinearLayout(mContext);
        lLayout.setOrientation(LinearLayout.VERTICAL);
        lLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        RelativeLayout.LayoutParams lLayoutParams = new RelativeLayout.LayoutParams(Utils.dp2px(mContext, 300), ViewGroup.LayoutParams.WRAP_CONTENT);
        lLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lLayout.setLayoutParams(lLayoutParams);

        // shadow image layout start
        ShadowLayout shadowLayout = new ShadowLayout(mContext);
        shadowLayout.setCornerRadius(mContext.getResources().getDimensionPixelSize(R.dimen.card_corner_radius));
        shadowLayout.setShadowRadius(10, 0, 12);
        shadowLayout.setShadowColor(Color.parseColor("#2a000000"));
        LinearLayout.LayoutParams shadowLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        shadowLayoutParams.topMargin = isVer ? mContext.getResources().getDimensionPixelSize(R.dimen.card_image_margin_top_ver)
                : mContext.getResources().getDimensionPixelSize(R.dimen.card_image_margin_top_hor);
        shadowLayout.setLayoutParams(shadowLayoutParams);

        RoundedImageView roundedImageView = new RoundedImageView(mContext);
        roundedImageView.setBackgroundDrawable(isVer ? mContext.getResources().getDrawable(R.drawable.bg_black_corner) : null);
        roundedImageView.setScaleType(isVer ? ImageView.ScaleType.FIT_CENTER : ImageView.ScaleType.CENTER_CROP);
        roundedImageView.setCornerRadius(isVer ? 0 : mContext.getResources().getDimensionPixelSize(R.dimen.card_corner_radius));

        ShadowLayout.LayoutParams roundedImageViewParams = new ShadowLayout.LayoutParams(Utils.dp2px(mContext, 280), Utils.dp2px(mContext, 280));
        roundedImageViewParams.height = isVer ? mContext.getResources().getDimensionPixelSize(R.dimen.card_image_height_ver)
                : mContext.getResources().getDimensionPixelSize(R.dimen.card_image_height_hor);
        roundedImageView.setLayoutParams(roundedImageViewParams);

        shadowLayout.addView(roundedImageView);
        // shadow image layout end

        View belowLayout = mInflater.inflate(R.layout.item_page_inner_below, null);

        lLayout.addView(shadowLayout);
        lLayout.addView(belowLayout);

        rootRLayout.addView(lLayout);

        ViewHolder holder = new ViewHolder();
        holder.itemView = rootRLayout;
        holder.imageLayout = shadowLayout;
        holder.imageView = roundedImageView;
        holder.belowLayout = belowLayout;

        return holder;

    }


    /*************************************/

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

            float pagerMargin = page.getContext().getResources().getDimensionPixelSize(R.dimen.pager_margin);
            float realPagerMargin = position * (pagerMargin);
            holder.imageLayout.setTranslationX(realPagerMargin);
        }
    }

    private static float getFloat(float value, float minValue, float maxValue) {
        return Math.min(maxValue, Math.max(minValue, value));
    }

}
