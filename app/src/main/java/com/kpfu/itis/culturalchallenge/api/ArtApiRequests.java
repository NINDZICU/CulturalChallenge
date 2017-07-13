package com.kpfu.itis.culturalchallenge.api;

import com.kpfu.itis.culturalchallenge.api.pojo.task.Task;
import com.kpfu.itis.culturalchallenge.api.pojo.task.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rage on 12.07.2017.
 */

public interface ArtApiRequests {

    @GET("tasks/get")
    Observable<List<Task>> getAllTasks();

    @GET("user/friends")
    Observable<List<User>> getFriends(@Query("login") String login);

}
