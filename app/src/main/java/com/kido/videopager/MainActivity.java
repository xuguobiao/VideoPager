package com.kido.videopager;

import android.app.Activity;
import android.os.Bundle;

import com.kido.videopager.view.MainLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MainLayout(this));
    }

}
