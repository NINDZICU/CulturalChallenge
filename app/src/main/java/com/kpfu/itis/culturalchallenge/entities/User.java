package com.kpfu.itis.culturalchallenge.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Anatoly on 08.07.2017.
 */
public class User {
    private Integer id;
    private String login;
    private String password;
    private String name;
    private String city;
    private int level;
    private int exp;
    private Set<MyTasks> myTasks;
    private Set<Tasks> tasks = new HashSet<>();
    private Set<User> friends;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<MyTasks> getMyTasks() {
        return myTasks;
    }

    public void setMyTasks(Set<MyTasks> myTasks) {
        this.myTasks = myTasks;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

}
