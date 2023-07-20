package com.kp.socialnetwork.data.model;

public class Activity {
    private int id;
    private User user;

    public Activity(User user, Post post, Category category) {
        this.user = user;
        this.post = post;
        this.category = category;
    }

    public Activity() {
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", user=" + user +
                ", post=" + post +
                ", category=" + category +
                '}';
    }

    private Post post;
    private Category category;

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
