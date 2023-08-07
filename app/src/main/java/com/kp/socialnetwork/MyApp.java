package com.kp.socialnetwork;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.kp.socialnetwork.data.dao.PostDao;
import com.kp.socialnetwork.data.dao.UserDao;
import com.kp.socialnetwork.data.model.Post;
import com.kp.socialnetwork.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    private static MyApp instance;
    private UserDao userDao;
    private PostDao postDao;
    private User currentUser;

    public static MyApp getInstance() {
        return instance;
    }

    public static void setInstance(MyApp instance) {
        MyApp.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        FirebaseApp.initializeApp(this);

        // Dao
        userDao = new UserDao(this);
        postDao = new PostDao(this);

        // Set data
        User user = new User();
        user.setId("1gPFtNBZLka3WJLVh04HBHprozj2");
        user.setActive(false);
        user.setOnline(false);
        user.setRole(User.UserRole.ROLE_USER.getRole());
        user.setBirthday("05/08/2009");
        user.setBio("");
        user.setPassword("123456");
        user.setEmail("khuong93qt@gmail.com");
        user.setGender("MALE");
        user.setName("KHUONG");
        user.setCreatedDate("2023-08-05 20:13:10");
        user.setImageCover("https://e00-marca.uecdn.es/assets/multimedia/imagenes/2023/07/04/16884982838657.jpg");
        user.setImageAvatar("https://firebasestorage.googleapis.com/v0/b/socialmediaapp-32653.appspot.com/o/images%2Favatar%2Fstorage%2Femulated%2F0%2FDownload%2FMaeve_Wiley_Season_1_Portrait%20(1).jpg?alt=media&token=ddbf96c6-62ab-47d3-8db0-c487f37d3d5d");


        List<Post> posts = new ArrayList<>();
        Post p1 = new Post();
        p1.setContent("This is my first post");
        p1.setImage("https://e00-marca.uecdn.es/assets/multimedia/imagenes/2023/07/04/16884982838657.jpg");
        p1.setUser(user);

        posts.add(p1);

        if (userDao.getUserById(user.getId()) == null) {
            userDao.createUser(user);
            this.setCurrentUser(user);
        }
//
//        if (posts.size() > 0) {
//            posts.forEach(post -> postDao.createPost(post));
//        }

    }

    public User getCurrentUser() {
        if(currentUser != null) return currentUser;
        else return null;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    //Context = MyApp.getInstance();

    }
