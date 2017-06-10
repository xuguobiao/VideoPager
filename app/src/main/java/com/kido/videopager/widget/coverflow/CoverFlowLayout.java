package com.kido.videopager.widget.coverflow;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kido
 */
public class CoverFlowLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private final static float DURATION_FACTOR_BETA = 1.5f;
    private final static float DURATION_FACTOR_ALPHA = 0f;
    private ViewPager mPager;
    boolean mNeedsRedraw = false;
    boolean isOverlapEnabled = false;

    public CoverFlowLayout(Context context) {
        super(context);
        init();
    }

    public CoverFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CoverFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //Disable clipping of children so non-selected pages are visible
        setClipChildren(false);

        //Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        //You need to set this value here if using hardware acceleration in an
        // application targeted at these releases.
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setOverlapEnabled(boolean overlapEnabled) {
        isOverlapEnabled = overlapEnabled;
    }

    @Override
    protected void onFinishInflate() {
        try {
            mPager = (ViewPager) getChildAt(0);
            mPager.setClipChildren(false);
            mPager.setOverScrollMode(OVER_SCROLL_NEVER);
            mPager.addOnPageChangeListener(this);
            setViewPagerScroller(mPager, DURATION_FACTOR_BETA, DURATION_FACTOR_ALPHA);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return mPager;
    }

    private Point mCenter = new Point();
    private Point mInitialTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int) ev.getX();
                mInitialTouch.y = (int) ev.getY();
//                ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int delta = isInNonTappableRegion(getWidth(), mPager.getWidth(), mInitialTouch.x, ev.getX());
                if (delta != 0) {
                    int preItem = mPager.getCurrentItem();
                    int currentItem = preItem + delta;
                    mPager.setCurrentItem(currentItem, true);
//                    ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                }
                break;
        }

        return mPager.dispatchTouchEvent(ev);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (mNeedsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (isOverlapEnabled) {
            //Counter for loop
            int loopCounter = 0;
            int PAGER_LOOP_THRESHOLD = 2;

            //SET THE START POINT back 2 views
            if (position >= PAGER_LOOP_THRESHOLD) {
                loopCounter = position - PAGER_LOOP_THRESHOLD;
            }
            do {
                if (loopCounter < mPager.getAdapter().getCount()) {

                    Object object = mPager.getAdapter().instantiateItem(mPager, loopCounter);
                    //Elevate the Center View if it's the selected position and de-elevate the left and right fragment

                    if (object instanceof Fragment) {
                        Fragment fragment = (Fragment) object;
                        if (loopCounter == position) {
                            ViewCompat.setElevation(fragment.getView(), 8.0f);
                        } else {
                            ViewCompat.setElevation(fragment.getView(), 0.0f);
                        }
                    } else {
                        ViewGroup view = (ViewGroup) object;
                        if (loopCounter == position) {
                            ViewCompat.setElevation(view, 8.0f);
                        } else {
                            ViewCompat.setElevation(view, 0.0f);
                        }
                    }
                }
                loopCounter++;
            } while (loopCounter < position + PAGER_LOOP_THRESHOLD);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }

    public interface Transformer {
        /**
         * Apply a property transformation to the given page.
         *
         * @param page     Apply the transformation to this page
         * @param position Position of page relative to the current front-and-center
         *                 position of the pager. 0 is front and center. 1 is one full
         *                 page position to the right, and -1 is one page position to the left.
         */
        void transform(View page, float position);
    }


    public static class Builder {
        private float scaleValue;
        private float pagerMargin;
        private float spaceSize;
        private float rotationY;
        private List<Transformer> childTransformers;

        public Builder() {
            childTransformers = new ArrayList<>();
        }

        public CoverFlowLayout.Builder scale(float scaleValue) {
            this.scaleValue = scaleValue;
            return this;
        }

        public CoverFlowLayout.Builder pagerMargin(float pagerMargin) {
            this.pagerMargin = pagerMargin;
            return this;
        }

        public CoverFlowLayout.Builder spaceSize(float spaceSize) {
            this.spaceSize = spaceSize;
            return this;
        }

        public CoverFlowLayout.Builder rotationY(float rotationY) {
            this.rotationY = rotationY;
            return this;
        }

        public CoverFlowLayout.Builder addChildTransformer(int childId, Transformer transformer) {
            this.childTransformers.add(transformer);
            return this;
        }


        public CoverFlowLayout.Builder build() {
            return this;
        }
    }

    public void config(CoverFlowLayout.Builder builder) {
        if (null == builder) {
            throw new IllegalArgumentException("A non-null CoverFlowLayout.Builde must be provided");
        }

        if (this.mPager != null) {
            this.mPager.setPageTransformer(false,
                    new CoverTransformer(builder.scaleValue, builder.pagerMargin, builder.spaceSize, builder.rotationY, builder.childTransformers));
        }
    }


    private static int isInNonTappableRegion(int containerWidth, int pagerWidth, float oldX, float newX) {
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

    /**
     * y = beta * x + alpha
     * <p>
     * destDuration = oriDuration * beta + alpha
     */
    private static void setViewPagerScroller(ViewPager viewPager, final float beta, final float alpha) {
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