package com.kpfu.itis.culturalchallenge;

import android.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.kpfu.itis.culturalchallenge.adapters.TabPagerAdapter;
import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.custom.TabViewWrapper;
import com.kpfu.itis.culturalchallenge.fragments.Fragment2;
import com.kpfu.itis.culturalchallenge.fragments.Fragment3;
import com.kpfu.itis.culturalchallenge.fragments.Fragment4;
import com.kpfu.itis.culturalchallenge.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabViewWrapper.TabListener {

    @BindView(R.id.tab_pager)
    ViewPager mTabPager;
    @BindView(R.id.tabs)
    LinearLayout mTabLayout;
    private TasksRecyclerAdapter taskAdapter;
    private TabViewWrapper mTabViewWrapper;
    private TabPagerAdapter mTabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTabViewWrapper = new TabViewWrapper(mTabLayout);
        mTabViewWrapper.setSelectedTab(0);
        mTabViewWrapper.setSelectColor(R.color.button_pressed);
        mTabViewWrapper.setTabListenerClick(this);
        mTabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        List<Fragment> tabFragment = new ArrayList<>();
        HomeFragment homeFragment=HomeFragment.getInstance();
        tabFragment.add(homeFragment);
        Fragment2 fragment2=Fragment2.getInstance();
        fragment2.setUpdateData(()->homeFragment.notifyDataSetChanged());
        tabFragment.add(fragment2);
        tabFragment.add(Fragment3.getInstance());
        tabFragment.add(Fragment4.getInstance());
        mTabPagerAdapter.setFragmentList(tabFragment);
        mTabPager.setAdapter(mTabPagerAdapter);
        mTabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabViewWrapper.setSelectedTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},0);
        }
    }

    @Override
    public void onTabClick(int position, View tab) {
        mTabPager.setCurrentItem(position, true);
    }

}
