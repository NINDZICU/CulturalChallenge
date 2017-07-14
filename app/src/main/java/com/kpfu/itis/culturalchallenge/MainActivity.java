package com.kpfu.itis.culturalchallenge;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.kpfu.itis.culturalchallenge.adapters.TabPagerAdapter;
import com.kpfu.itis.culturalchallenge.fragments.Fragment2;
import com.kpfu.itis.culturalchallenge.fragments.Fragment3;
import com.kpfu.itis.culturalchallenge.fragments.Fragment4;
import com.kpfu.itis.culturalchallenge.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import com.kpfu.itis.culturalchallenge.custom.TabViewWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabViewWrapper.TabListener{

    @BindView(R.id.tab_pager)
    ViewPager mTabPager;
    @BindView(R.id.tabs)
    LinearLayout mTabLayout;
    private TabViewWrapper mTabViewWrapper;
    private TabPagerAdapter mTabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTabViewWrapper=new TabViewWrapper(mTabLayout);
        mTabViewWrapper.setSelectedTab(0);
        mTabViewWrapper.setSelectColor(R.color.button_pressed);
        mTabViewWrapper.setTabListenerClick(this);
        mTabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager());
        List<Fragment> tabFragment=new ArrayList<>();
        tabFragment.add(HomeFragment.getInstance());
        tabFragment.add(Fragment2.getInstance());
        tabFragment.add(Fragment3.getInstance());
        tabFragment.add(Fragment4.getInstance());
        mTabPagerAdapter.setFragmentList(tabFragment);
        mTabPager.setAdapter(mTabPagerAdapter);

    }

    @Override
    public void onTabClick(int position, View tab) {
        mTabPager.setCurrentItem(position,true);
    }
}
