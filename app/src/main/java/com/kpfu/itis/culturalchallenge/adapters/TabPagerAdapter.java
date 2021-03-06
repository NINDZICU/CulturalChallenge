package com.kpfu.itis.culturalchallenge.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rage on 14.07.2017.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentList= Collections.emptyList();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        mFragmentList = fragmentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
