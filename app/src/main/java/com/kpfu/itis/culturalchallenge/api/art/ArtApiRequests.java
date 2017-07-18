package com.kpfu.itis.culturalchallenge.api.art;

import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.entities.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rage on 12.07.2017.
 */

public interface ArtApiRequests {

    @GET("tasks/get")
    Observable<List<Task>> getAllTasks();

    @GET("user/friends")
    Observable<List<User>> getFriends(@Query("login") String login);

    @GET("tasks/getMy")
    Observable<List<Task>> getMyTasks(@Query("login") String login);

    @POST("user/reg")
    Observable<String> saveUser(@Query("login") String login, @Query("name") String name, @Query("city") String city);

    @GET("tasks/getAll")
    Observable<List<Task>> getAllTasks(@Query("city") String city);

    @GET("tasks/getAllAndr")
    Observable<List<Task>> getAllTasksAndr(@Query("city") String city, @Query("login") String login);

    @POST("tasks/add")
    Observable<Integer> addTask(@Query("address")String address,@Query("login") String login, @Query("dateFinish") String dateFinish,
                                @Query("name") String name, @Query("description")String description, @Query("difficulty") String difficulty,
                                @Query("city") String city);

    @POST("tasks/acceptTask")
    Observable<Integer> acceptTask(@Query("login") String login, @Query("idTask") Integer id);

    @GET("tasks/successTask")
    Observable<Integer> successTask(@Query("login") String login, @Query("idTask") Integer idTask);

    @GET("tasks/failTask")
    Observable<Integer> failTask(@Query("login") String login, @Query("idTask") Integer idTask);

    @GET("user/getByID")
    Observable<User> getUserById(@Query("login") String login);
}
