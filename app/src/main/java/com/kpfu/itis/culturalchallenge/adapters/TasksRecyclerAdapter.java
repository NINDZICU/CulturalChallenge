package com.kpfu.itis.culturalchallenge.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.MainActivity;
import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.api.ArtApi;
import com.kpfu.itis.culturalchallenge.entities.MyTasks;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.entities.Tasks;
import com.kpfu.itis.culturalchallenge.fragments.TaskDetailFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anatoly on 11.07.2017.
 */

public class TasksRecyclerAdapter extends RecyclerView.Adapter<TasksRecyclerAdapter.TasksViewHolder>{
    private List<Task> mTasks;
    private Context context;
    private MainActivity mainActivity;

    public TasksRecyclerAdapter(Context context, Activity activity){
        this.context = context;
        this.mainActivity = (MainActivity) activity;
        mTasks = new ArrayList<>();
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
        final Task task =  mTasks.get(position);
        holder.tvCustomer.setText(task.getCustomer());
        holder.tvTask.setText(task.getName());
        holder.tvDeadline.setText(task.getDateFinish());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDetailFragment fragment = new TaskDetailFragment().newInstance(task);
                mainActivity.getFragmentManager().beginTransaction()
                        .add(R.id.task_detail_frame, fragment, TaskDetailFragment.class.getName()).commit();
            }
        });
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
    }


    public void setmTasks(List<Task> mTasks) {
        this.mTasks = mTasks;
        System.out.println(mTasks);
        notifyDataSetChanged();
    }
}
