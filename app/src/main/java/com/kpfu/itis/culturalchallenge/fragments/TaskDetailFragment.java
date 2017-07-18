package com.kpfu.itis.culturalchallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.entities.Task;

/**
 * Created by Anatoly on 11.07.2017.
 */

public class TaskDetailFragment extends Fragment {
    private TaskListener mTaskListener;
    private TaskListener mTaskListenerDone;


    private TextView tvTaskDetail;
    private TextView tvComplexity;
    private TextView tvDateOfEnd;
    private TextView tvTextConfirm;
    private TextView tvCustomer;
    private ImageButton btnDone;
    private ImageButton btnConfirm;

    private String textConfirm = "";

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
        View view = inflater.inflate(R.layout.done, container, false);
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

        tvCustomer.setText(task.getCustomer().getName());
        tvTextConfirm.setText(textConfirm);
        tvTaskDetail.setText(task.getDescription());
        tvComplexity.setText(task.getDifficulty());
        tvDateOfEnd.setText(task.getDateFinish());

        btnDone.setOnClickListener(v-> {
            if(mTaskListenerDone != null){
                getActivity().getSupportFragmentManager().beginTransaction().remove(TaskDetailFragment.this).commit();
//                mTaskListenerDone.onTaskClick(task);
            }
        });
        btnConfirm.setOnClickListener(v -> {
            if (mTaskListener != null) {
                mTaskListener.onTaskClick(task);
            }
        });
    }

    public void setTextConfirm(String textConfirm){
        this.textConfirm = textConfirm;
}
    public void setTaskListener(TaskListener taskListener) {
        mTaskListener = taskListener;
    }

    public void setTaskListenerDone(TaskListener taskListenerDone) {
        mTaskListenerDone = taskListenerDone;
    }


    public interface TaskListener{
        void onTaskClick(Task task);
    }
}
