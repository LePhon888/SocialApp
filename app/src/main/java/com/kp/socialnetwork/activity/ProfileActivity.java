package com.kp.socialnetwork.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kp.socialnetwork.PostImageAdapter;
import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.FirebaseHelper;
import com.kp.socialnetwork.data.MySQLiteHelper;
import com.kp.socialnetwork.data.dao.PostDao;
import com.kp.socialnetwork.data.dao.UserDao;
import com.kp.socialnetwork.data.model.Post;
import com.kp.socialnetwork.data.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private GridView postGridView;
    private PostDao postDao;
    private UserDao userDao;
    private PostImageAdapter adapter;
    private TextView textViewUsername;

    private TextView textViewUsernameHeader;
    private CircleImageView avatar;

private ImageButton btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Create Post button
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(item -> {
            Intent intent = new Intent(ProfileActivity.this, CreateOrUpdatePostActivity.class);
            startActivity(intent);
        });




        postGridView = findViewById(R.id.postGridView);
        avatar = findViewById(R.id.avatar);
        textViewUsername = findViewById(R.id.textViewUsername);
        textViewUsernameHeader = findViewById(R.id.textViewUsernameHeader);
        loadImage();

        //Get current user id
//        int userId = Integer.parseInt(FirebaseAuth.getInstance().getCurrentUser().getUid());




        //Click each image on girdview in profile user






   }


   private void loadImage(){
       //Load avatar and username
       String userId = "1gPFtNBZLka3WJLVh04HBHprozj2";
       userDao = new UserDao(this);
       userDao.open();
       User u = userDao.getUserById("1gPFtNBZLka3WJLVh04HBHprozj2");
       Picasso.get().load(u.getImageAvatar()).into(avatar);
       textViewUsername.setText(u.getName());
       textViewUsernameHeader.setText(u.getName());
       userDao.close();

       //Load gridview image post
       postDao = new PostDao(this);
       postDao.open();
       List<Post> listPost = new ArrayList<>();
       listPost.addAll(postDao.getAllPosts(userId));
       Log.d("listPostlistPostlistPostlistPost", String.valueOf(listPost.size()));
       List<String> imagePaths = new ArrayList<>();
       for (Post post : listPost) {
           imagePaths.add(post.getImage());
       }
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

    @Override
    protected void onResume() {
        super.onResume();
        loadImage();
    }
}