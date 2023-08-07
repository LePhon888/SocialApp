package com.kp.socialnetwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kp.socialnetwork.PostImageAdapter;
import com.kp.socialnetwork.PostListAdapter;
import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.dao.PostDao;
import com.kp.socialnetwork.data.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostListActivity extends AppCompatActivity {

    private ListView postListView;
    private PostDao postDao;
    private String selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        //Get current user id
//        int userId = Integer.parseInt(FirebaseAuth.getInstance().getCurrentUser().getUid());
        String userId = "1gPFtNBZLka3WJLVh04HBHprozj2";

        postListView = findViewById(R.id.postListView);

        postDao = new PostDao(this);
        postDao.open();
        selectedImage = getIntent().getStringExtra("SELECTED_IMAGE");

        List<Post> allPosts = postDao.getAllPosts(userId);

        postDao.close();

        PostListAdapter postListAdapter = new PostListAdapter(PostListActivity.this, R.layout.post_layout, allPosts);

        postListView.setAdapter(postListAdapter);


    }
}