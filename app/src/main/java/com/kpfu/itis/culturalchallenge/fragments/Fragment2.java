package com.kpfu.itis.culturalchallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpfu.itis.culturalchallenge.MainActivity;
import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.entities.Tasks;
import com.kpfu.itis.culturalchallenge.providers.SharedPreferencesProvider;
import com.kpfu.itis.culturalchallenge.service.ApiService;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rage on 14.07.2017.
 */

public class Fragment2 extends Fragment {

    public static Fragment2 getInstance() {
        return new Fragment2();
    }

    @BindView(R.id.all_tasks_recycler_view)
    RecyclerView tasksRecyclerView;

    private ApiService apiService;
    private TasksRecyclerAdapter taskAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tasks, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);


        apiService = new ApiService(getContext().getApplicationContext());
        taskAdapter = new TasksRecyclerAdapter();
        taskAdapter.setTaskListener(task -> {
            TaskDetailFragment fragment = new TaskDetailFragment().newInstance(task);
            fragment.setTaskListener(task1 -> {
                apiService.acceptTask(SharedPreferencesProvider.getInstance(getContext()).getVkId(), task1.getId());
                ((MainActivity) getActivity()).notifyDataSetChanged();
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            });
            fragment.setTaskListenerDone(task1 -> {
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            });
            fragment.setTextConfirm("Вы готовы принять вызов?");
            getChildFragmentManager().beginTransaction()
                    .add(R.id.all_task_detail_frame, fragment, TaskDetailFragment.class.getName()).commit();
        });
        List<Task> mTasks = apiService.getAllTasksAndr("kazan", SharedPreferencesProvider.getInstance(getContext()).getVkId(), taskAdapter);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksRecyclerView.setAdapter(taskAdapter);

    }

    public void notifyDataSetChanged() {
        apiService.getAllTasksAndr("kazan", SharedPreferencesProvider.getInstance(getContext()).getVkId(), taskAdapter);

    }


}
