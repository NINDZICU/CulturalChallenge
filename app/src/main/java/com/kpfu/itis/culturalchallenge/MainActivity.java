package com.kpfu.itis.culturalchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;

public class MainActivity extends AppCompatActivity {
    private TextView textOfFact;
    private TextView taskForToday;
    private RecyclerView tasksRecyclerView;
    private TasksRecyclerAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        textOfFact = (TextView) findViewById(R.id.text_of_fact);
        taskForToday = (TextView) findViewById(R.id.task_for_today);
        tasksRecyclerView = (RecyclerView) findViewById(R.id.tasks_recycler_view);

        taskAdapter = new TasksRecyclerAdapter(MainActivity.this, this);

        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerView.setAdapter(taskAdapter);

    }
}
