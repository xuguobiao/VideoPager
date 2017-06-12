package com.kido.videopager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author Kido
 */

public class ChannelLayout extends FrameLayout {

    private Context mContext;

    public ChannelLayout(Context context) {
        this(context, null);
    }

    public ChannelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChannelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setText("频道");
        textView.setTextSize(24f);
        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        addView(textView);
    }
}
