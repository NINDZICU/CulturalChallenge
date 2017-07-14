package com.kpfu.itis.culturalchallenge.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.api.ArtApi;
import com.kpfu.itis.culturalchallenge.entities.MyTasks;
import com.kpfu.itis.culturalchallenge.entities.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anatoly on 14.07.2017.
 */

public class ApiService {

    private Context context;
    private ArtApi artApi = new ArtApi();

    public ApiService(Context context){
        this.context = context;
    }

    public List<Task> getMyTasks(String login, TasksRecyclerAdapter adapter){
         List<Task> mTasks = new ArrayList<>();
        artApi.getMyTasks(login).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                   adapter.setmTasks(tasks);
                },throwable ->
                        Toast.makeText(context,"Throw "+throwable.getMessage(),Toast.LENGTH_SHORT).show());
        return mTasks;
    }
    public void saveUser(String login, String name, String city){
        artApi.saveUser(login, name, city).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                },throwable ->
                        Toast.makeText(context,"Throw "+throwable.getMessage(),Toast.LENGTH_SHORT).show());
    }
}
