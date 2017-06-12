package com.kido.videopager;

import android.app.Activity;
import android.os.Bundle;

import com.kido.videopager.view.YouLiaoLayout;

public class MainActivity extends Activity {

    private YouLiaoLayout mYouLiaoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
    }

    private void bindViews() {
        mYouLiaoLayout = (YouLiaoLayout) findViewById(R.id.youliao_layout);
    }

}
