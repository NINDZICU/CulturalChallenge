package com.kpfu.itis.culturalchallenge.service;

import android.content.Context;
import android.widget.Toast;

import com.kpfu.itis.culturalchallenge.adapters.TasksRecyclerAdapter;
import com.kpfu.itis.culturalchallenge.api.art.ArtApi;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.entities.Tasks;
import com.kpfu.itis.culturalchallenge.entities.User;

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

    public ApiService(Context context) {
        this.context = context;
    }

    public List<Task> getMyTasks(String login, TasksRecyclerAdapter adapter) {
        List<Task> mTasks = new ArrayList<>();
        artApi.getMyTasks(login).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    adapter.setTasks(tasks);
                }, throwable -> {
                    Toast.makeText(context, "Throw " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    System.out.println("getMYTASKS " + throwable.getMessage());
                });

        return mTasks;
    }

    public void saveUser(String login, String name, String city) {
        artApi.saveUser(login, name, city).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                }, throwable ->
                        Toast.makeText(context, "Throw " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public List<Task> getAllTasks(String city, TasksRecyclerAdapter adapter) {
        List<Task> mTasks = new ArrayList<>();
        artApi.getAllTasks(city).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tasks -> {
                    adapter.setTasks(tasks);
                }, throwable -> {
                    Toast.makeText(context, "Throw ALLTasks" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    System.out.println("getAllTasks "+throwable.getMessage());

                });
        return mTasks;
    }

    public void addTask(String address, String login,  String dateFinish, String name, String description,
                        String difficulty, String city){
        artApi.addTask(address, login,  dateFinish, name, description, difficulty, city).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                }, throwable ->
                        Toast.makeText(context, "Throw " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void acceptTask(String login, Integer idTask) {
        artApi.acceptTask(login, idTask).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                }, throwable ->
                        Toast.makeText(context, "Throw " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void successTask(String login, Integer idTask) {
        artApi.successTask(login, idTask).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                }, throwable ->
                        Toast.makeText(context, "Throw " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }

    public void failTask(String login, Integer idTask) {
        artApi.failTask(login, idTask).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(code -> {
                }, throwable ->
                        Toast.makeText(context, "Throw " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
    }


}
