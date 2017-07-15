package com.kpfu.itis.culturalchallenge.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.fragments.TaskDetailFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Anatoly on 11.07.2017.
 */

public class TasksRecyclerAdapter extends RecyclerView.Adapter<TasksRecyclerAdapter.TasksViewHolder>{
    private List<Task> mTasks;
    private TaskListener mTaskListener;

    public TasksRecyclerAdapter(){
        mTasks = Collections.emptyList();
    }

    public void setTaskListener(TaskListener taskListener) {
        mTaskListener = taskListener;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_home,
                parent,
                false
        );
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        holder.bind(mTasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder{
        TextView tvCustomer;
        TextView tvTask;
        TextView tvDeadline;


        public TasksViewHolder(View itemView) {
            super(itemView);
            tvCustomer = (TextView) itemView.findViewById(R.id.tv_customer);
            tvTask = (TextView) itemView.findViewById(R.id.tv_task);
            tvDeadline = (TextView) itemView.findViewById(R.id.tv_deadline);
        }

        void bind(Task task){
            tvCustomer.setText(task.getCustomer());
            tvTask.setText(task.getName());
            tvDeadline.setText(task.getDateFinish());
            if(mTaskListener !=null){
                mTaskListener.onTaskClick(task);
            }
        }
    }

    public interface TaskListener{
        void onTaskClick(Task task);
    }


    public void setTasks(List<Task> mTasks) {
        this.mTasks = mTasks;
        notifyDataSetChanged();
    }
}
