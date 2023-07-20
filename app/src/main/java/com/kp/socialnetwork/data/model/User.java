package com.kp.socialnetwork.data.model;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean isActive;

    public User(String username, String password, boolean isActive, List<Comment> comment, List<Post> post, List<Activity> activity, List<Friendship> friendship) {
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.comment = comment;
        this.post = post;
        this.activity = activity;
        this.friendship = friendship;
    }

    private List<Comment> comment;
    private List<Post> post;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", comment=" + comment +
                ", post=" + post +
                ", activity=" + activity +
                ", friendship=" + friendship +
                '}';
    }

    public User() {
    }

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
