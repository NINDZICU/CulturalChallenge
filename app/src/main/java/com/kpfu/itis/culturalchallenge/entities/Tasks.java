package com.kpfu.itis.culturalchallenge.entities;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Anatoly on 08.07.2017.
 */
public class Tasks implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String dateFinish;
    private String difficulty;
    private String customer;

    private String address;
    private String latitude;
    private String longitude;


    private Set<User> users;

    public Tasks(){}

    public Tasks(String name, String description, String dateFinish, String difficulty, String customer) {
        this.name = name;
        this.description = description;
        this.dateFinish = dateFinish;
        this.difficulty = difficulty;
        this.customer = customer;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(String dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
