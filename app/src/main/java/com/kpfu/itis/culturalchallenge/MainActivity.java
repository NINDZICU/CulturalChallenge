package com.kpfu.itis.culturalchallenge;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.custom.TabViewWrapper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabViewWrapper.TabListener{

    @BindView(R.id.text_of_fact)
    TextView textOfFact;
    @BindView(R.id.task_for_today)
    TextView taskForToday;
    @BindView(R.id.tasks_recycler_view)
    RecyclerView tasksRecyclerView;
    @BindView(R.id.tabs)
    LinearLayout mTabLayout;
    private TasksRecyclerAdapter taskAdapter;
    private TabViewWrapper mTabViewWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.bind(this);
        mTabViewWrapper=new TabViewWrapper(mTabLayout);
        mTabViewWrapper.setSelectedTab(0);
        mTabViewWrapper.setSelectColor(R.color.button_pressed);
        mTabViewWrapper.setTabListenerClick(this);
        taskAdapter = new TasksRecyclerAdapter(this);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void onTabClick(int position, View tab) {

    }
}
