package com.kido.videopager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kido.videopager.R;

/**
 * @author Kido
 * @email everlastxgb@gmail.com
 * @create_time 17/6/11 23:27
 */

public class PDFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pd, null);
        bindViews(v);
        return v;
    }


    private void bindViews(View view) {

    }
}
