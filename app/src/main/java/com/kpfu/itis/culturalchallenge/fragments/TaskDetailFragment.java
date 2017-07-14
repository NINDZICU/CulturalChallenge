package com.kpfu.itis.culturalchallenge.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.entities.MyTasks;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.entities.Tasks;

/**
 * Created by Anatoly on 11.07.2017.
 */

public class TaskDetailFragment extends Fragment {
    private TextView tvTaskDetail;
    private TextView tvComplexity;
    private TextView tvDateOfEnd;
    private TextView tvTextConfirm;
    private TextView tvCustomer;
    private ImageButton btnDone;
    private ImageButton btnConfirm;

    public static TaskDetailFragment newInstance(Task task) {

        Bundle args = new Bundle();
        args.putSerializable("task", task);
        TaskDetailFragment fragment = new TaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.done, container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Task task = (Task) getArguments().getSerializable("task");

        tvTaskDetail = (TextView) view.findViewById(R.id.tv_task_detail);
        tvCustomer = (TextView) view.findViewById(R.id.tv_customer_detail);
        tvComplexity = (TextView) view.findViewById(R.id.tv_difficulty_detail);
        tvDateOfEnd = (TextView) view.findViewById(R.id.tv_date_of_end_detail);
        tvTextConfirm = (TextView) view.findViewById(R.id.tv_text_confirm);
        btnDone = (ImageButton) view.findViewById(R.id.imageButton_done);
        btnConfirm = (ImageButton) view.findViewById(R.id.imageButton_confirm);

        tvCustomer.setText(task.getCustomer());
        tvTaskDetail.setText(task.getDescription());
        tvComplexity.setText(task.getDifficulty());
        tvDateOfEnd.setText(task.getDateFinish());
        tvTextConfirm.setText("Завершить?");

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
