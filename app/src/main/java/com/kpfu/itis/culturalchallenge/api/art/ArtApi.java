package com.kpfu.itis.culturalchallenge.api.art;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.kpfu.itis.culturalchallenge.entities.Task;
import com.kpfu.itis.culturalchallenge.entities.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rage on 12.07.2017.
 */

public class ArtApi {

    //Поменять надо на свой IP
    private static final String BASE_URL = "http://192.168.1.3:8080/";
    private ArtApiRequests mArtApiRequests;

    public ArtApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
        mArtApiRequests = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ArtApiRequests.class);
    }

    public Observable<List<Task>> getAllTasks() {
        return mArtApiRequests.getAllTasks();
    }

    public Observable<List<User>> getFriendsByLogin(String login) {
        return mArtApiRequests.getFriends(login);
    }

    public Observable<List<Task>> getMyTasks(String login) {
        return mArtApiRequests.getMyTasks(login);
    }

    public Observable<List<Task>> getAllTasks(String city) {
        return mArtApiRequests.getAllTasks(city);
    }

    public Observable<List<Task>> getAllTasksAndr(String city, String login) {
        return mArtApiRequests.getAllTasksAndr(city, login);
    }

    public Observable<String> saveUser(String login, String name, String city) {
        return mArtApiRequests.saveUser(login, name, city);
    }

    public Observable<Integer> acceptTask(String login, Integer idTask) {
        return mArtApiRequests.acceptTask(login, idTask);
    }

    public Observable<Integer> successTask(String login, Integer idTask) {
        return mArtApiRequests.successTask(login, idTask);
    }

    public Observable<Integer> failTask(String login, Integer idTask) {
        return mArtApiRequests.failTask(login, idTask);
    }

    public Observable<User> getUser(String login){
        return mArtApiRequests.getUserById(login);
    }

    public Observable<Integer> addTask(String address, String login,  String dateFinish, String name, String description,
                                       String difficulty, String city){
       return mArtApiRequests.addTask(address, login,  dateFinish, name, description, difficulty, city);
    }

}
