package com.kp.socialnetwork.data.table;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean isActive;
    private List<Comment> comment;
    private List<Post> post;
    private List<Activity> activity;
    private List<Friendship> friendship;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public List<Friendship> getFriendship() {
        return friendship;
    }

    public void setFriendship(List<Friendship> friendship) {
        this.friendship = friendship;
    }
}
