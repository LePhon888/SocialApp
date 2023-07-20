package com.kp.socialnetwork.data.model;

import java.util.List;

public class Comment {
    private int id;
    private String content;
    private List<Comment> comment;
    private Post post;
    private User user;

    public Comment(String content, List<Comment> comment, Post post, User user) {
        this.content = content;
        this.comment = comment;
        this.post = post;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", comment=" + comment +
                ", post=" + post +
                ", user=" + user +
                '}';
    }

    public Comment() {

    }

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

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
