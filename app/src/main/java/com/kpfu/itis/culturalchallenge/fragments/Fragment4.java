package com.kpfu.itis.culturalchallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpfu.itis.culturalchallenge.R;

/**
 * Created by Rage on 14.07.2017.
 */

public class Fragment4 extends Fragment {

    public static Fragment4 getInstance(){
        return new Fragment4();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_4,container,false);
    }
}
