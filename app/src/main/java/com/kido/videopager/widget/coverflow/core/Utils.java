package com.kido.videopager.widget.coverflow.core;

import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.kido.videopager.widget.coverflow.pager.ViewPager;

import java.lang.reflect.Field;

/**
 * @author Kido
 */
public class Utils {

    public static int isInNonTappableRegion(int containerWidth, int pagerWidth, float oldX, float newX) {
        int tappableWidth = pagerWidth;
        int totalWidth = containerWidth;
        int nonTappableWidth = (totalWidth - tappableWidth) / 2;
        if (oldX < nonTappableWidth && newX < nonTappableWidth) {
            return -(int) Math.ceil((nonTappableWidth - newX) / (float) tappableWidth);
        }
        nonTappableWidth = (totalWidth + tappableWidth) / 2;
        if (oldX > nonTappableWidth && newX > nonTappableWidth) {
            return (int) Math.ceil((newX - nonTappableWidth) / (float) tappableWidth);
        }
        return 0;
    }

    public static float getFloat(float value, float minValue, float maxValue) {
        return Math.min(maxValue, Math.max(minValue, value));
    }

    /**
     * y = beta * x + alpha
     * <p>
     * destDuration = oriDuration * beta + alpha
     */
    public static void setViewPagerScroller(ViewPager viewPager, final float beta, final float alpha) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(viewPager.getContext(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, (int) (duration * beta + alpha));
                }
            };
            scrollerField.set(viewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

}
