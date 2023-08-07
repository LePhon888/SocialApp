package com.kp.socialnetwork.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kp.socialnetwork.data.MySQLiteHelper;
import com.kp.socialnetwork.data.model.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PostDao {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID_POST,
            MySQLiteHelper.COLUMN_CONTENT,
            MySQLiteHelper.COLUMN_IMAGE,
            MySQLiteHelper.COLUMN_CREATED_DATE};

    public PostDao(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {dbHelper.close();}

    public void createPost(String content, String image) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CONTENT, content);
        values.put(MySQLiteHelper.COLUMN_IMAGE, image);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String createdDate = dateFormat.format(new Date());

        values.put(MySQLiteHelper.COLUMN_CREATED_DATE, createdDate);

        long insertId = database.insert(MySQLiteHelper.TABLE_POST, null, values);
    }

    public boolean createPost(Post post) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_CONTENT, post.getContent());
        values.put(MySQLiteHelper.COLUMN_IMAGE, post.getImage());
        values.put(MySQLiteHelper.COLUMN_CREATED_DATE, post.setCreatedDate(new Post().getCreatedDate()));
        values.put(MySQLiteHelper.COLUMN_USER_ID, post.getUser().getId());


        return database.insert(MySQLiteHelper.TABLE_POST, null, values) > 0;
    }



    public void deletePost(long id){
        database.delete(MySQLiteHelper.TABLE_POST, MySQLiteHelper.COLUMN_ID_POST
                + " = " + id, null);
    }

    public Post getPost(long id) {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_POST, allColumns,
                MySQLiteHelper.COLUMN_ID_POST + " = ?", new String[]{String.valueOf(id)},
                null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
            Post post = cursorToPost(cursor);
            cursor.close();
            return post;
        }
        return null;
    }

    public Post searchPost(Post post) {
        Post tempPost  = null;
        try(Cursor cursor =database.rawQuery("Select * from " + MySQLiteHelper.TABLE_POST
                        + " where " + MySQLiteHelper.COLUMN_ID_POST  + " = ? ",
                new String[] {String.valueOf(post.getId())})){
            if (cursor.moveToFirst() ) {
                tempPost = cursorToPost(cursor);
            }
        }
        return tempPost;
    }

    public boolean updatePost(Post post) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_IMAGE, post.getImage());
        contentValues.put(MySQLiteHelper.COLUMN_CONTENT, post.getContent());
        contentValues.put(MySQLiteHelper.COLUMN_CREATED_DATE, post.setCreatedDate(new Post().getCreatedDate()));

        return database.update(MySQLiteHelper.TABLE_POST, contentValues,
                MySQLiteHelper.COLUMN_ID_POST + " = ? ", new String[]{String.valueOf(post.getId())}) > 0;
    }

    public List<Post> getAllPosts(String userId) {
        List<Post> posts = new ArrayList<>();

        String selection = MySQLiteHelper.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = database.query(MySQLiteHelper.TABLE_POST, allColumns,
                selection, selectionArgs, null,null,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Post post = cursorToPost(cursor);
            posts.add(post);
            cursor.moveToNext();
        }
        cursor.close();
        return posts;
    }
    private Post cursorToPost(Cursor cursor) {
        Post post = new Post();
        post.setId(cursor.getInt(0));
        post.setContent(cursor.getString(1));
        post.setImage(cursor.getString(2));
        post.setCreatedDate(cursor.getString(3));
        return post;
    }

}
