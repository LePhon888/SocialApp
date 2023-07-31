package com.kp.socialnetwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.kp.socialnetwork.PostImageAdapter;
import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.dao.PostDao;
import com.kp.socialnetwork.data.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ProfileActivity extends AppCompatActivity {

    private GridView postGridView;
    private PostDao postDao;
    private PostImageAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        postGridView = findViewById(R.id.postGridView);
        postDao = new PostDao(this);
        postDao.open();


        List<Post> listPost = postDao.getAllPosts();
        Log.d("TE", listPost.size()+"");

        List<String> imagePaths = new ArrayList<>();
        for (Post post : listPost) {
            imagePaths.add(post.getImage());
        }
        Log.d("TE", imagePaths.size()+"");
        adapter = new PostImageAdapter(this, imagePaths);
        postGridView.setAdapter(adapter);

        postGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedImage = imagePaths.get(position);
                Intent intent = new Intent(ProfileActivity.this, PostListActivity.class);
                intent.putExtra("SELECTED_IMAGE", selectedImage);
                startActivity(intent);
            }
        });

    }
}