package com.kido.videopager.widget.coverflow.util;

import android.os.Build;
import android.view.View;

/**
 * @author Kido
 */


public class ViewHelper {
    /**
     * Horizontal layout direction of this view is from Left to Right.
     */
    public static final int LAYOUT_DIRECTION_LTR = 0;

    /**
     * Horizontal layout direction of this view is from Right to Left.
     */
    public static final int LAYOUT_DIRECTION_RTL = 1;


    private static final int SIXTY_FPS_INTERVAL = 1000 / 60;

    public static void postOnAnimation(View view, Runnable runnable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, SIXTY_FPS_INTERVAL); // or view.post(runnable)?
        }
    }

    public static void postInvalidateOnAnimation(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.postInvalidateOnAnimation();
        } else {
            view.invalidate();
        }
    }

    public static boolean isLayoutRtl(View view) {
        int layoutDirection = LAYOUT_DIRECTION_LTR;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            layoutDirection = view.getLayoutDirection();
        }
        return layoutDirection == LAYOUT_DIRECTION_RTL;
    }

    public static void setElevation(View view, float elevation) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            view.setElevation(elevation);
        }
    }
}
