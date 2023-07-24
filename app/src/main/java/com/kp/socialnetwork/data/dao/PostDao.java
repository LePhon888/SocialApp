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

    public PostDao(Context context) { dbHelper = new MySQLiteHelper(context);}

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

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

//        Cursor c = database.rawQuery("SELECT * FROM Post", null);
//
//        while (c.moveToNext()) {
//            Post post = new Post();
//            post.setId(c.getInt(0));
//            post.setContent(c.getString(1));
//            post.setImage(c.getString(2));
//            post.setCreatedDate(c.getString(3));
//            post.setUser(null);
//
//            posts.add(post);
//        }
//        c.close();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_POST, allColumns,
                null, null, null,null,null);

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
