package com.kp.socialnetwork.data.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Post {
    private int id;
    private String content;
    private String image;
    private String createdDate;
    private User user;
    private List<Comment> comment;

    {
        this.createdDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public Post(String content, String image, User user) {
        this.content = content;
        this.image = image;
        this.user = user;
    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", image='" + image + '\'' +
                ", createdDate=" + getCreatedDate() +
                ", user=" + user +
                ", comment=" + comment +
                ", activity=" + activity +
                '}';
    }


    private List<Activity> activity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedDate() {
        return createdDate;
    }


    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Activity> getActivity() {
        return activity;
    }

    public void setActivity(List<Activity> activity) {
        this.activity = activity;
    }

    public String setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return createdDate;
    }
}
