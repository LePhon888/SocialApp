package com.kp.socialnetwork.data.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kp.socialnetwork.data.MySQLiteHelper;
import com.kp.socialnetwork.data.model.Activity;
import com.kp.socialnetwork.data.model.Comment;
import com.kp.socialnetwork.data.model.Friendship;
import com.kp.socialnetwork.data.model.Post;
import com.kp.socialnetwork.data.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDao {
    private SQLiteDatabase db;
    private MySQLiteHelper dbHelper;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public UserDao(Context context) {
        dbHelper = new MySQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }
    public void close() {dbHelper.close();}

    public boolean createUser(User user) {
        db.beginTransaction();
        boolean result = false;
        try {
            Log.d("createUser : ", "LOCAL");
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_USER_ID, user.getId().trim());
            values.put(MySQLiteHelper.COLUMN_USER_NAME, user.getName());
            values.put(MySQLiteHelper.COLUMN_USER_GENDER, user.getGender());
            values.put(MySQLiteHelper.COLUMN_USER_BIRTHDAY, user.getBirthday());
            values.put(MySQLiteHelper.COLUMN_USER_PASSWORD, user.getPassword()); // already hashed in register
            values.put(MySQLiteHelper.COLUMN_USER_EMAIL, user.getEmail());
            values.put(MySQLiteHelper.COLUMN_USER_BIO, user.getBio() != null ? user.getBio() : "");
            values.put(MySQLiteHelper.COLUMN_USER_IMAGE_AVATAR, user.getImageAvatar() != null ? user.getImageAvatar() : "");
            values.put(MySQLiteHelper.COLUMN_USER_IMAGE_COVER, user.getImageCover() != null ? user.getImageCover() : "");
            values.put(MySQLiteHelper.COLUMN_USER_CREATED_DATE, user.getCreatedDate());
            values.put(MySQLiteHelper.COLUMN_USER_IS_ACTIVE, user.isActive());
            values.put(MySQLiteHelper.COLUMN_USER_IS_ONLINE, user.isOnline());
            values.put(MySQLiteHelper.COLUMN_USER_USER_ROLE, user.getRole());
            result = db.insert(MySQLiteHelper.TABLE_USER, null, values) > 0;
            db.setTransactionSuccessful();
        } catch (Exception exception) {
            Log.d("CreateUser", exception.getMessage());
        } finally {
            db.endTransaction();
        }
        return result;
    }
    public Boolean authenticateUser(String usernameOrEmail, String password) {
//        String COLUMN_PARAM;
//        open();
//        if (usernameOrEmail.matches(emailPattern)) {
//            COLUMN_PARAM = MySQLiteHelper.COLUMN_EMAIL; //if param is email
//        } else {
//            COLUMN_PARAM = MySQLiteHelper.COLUMN_USERNAME; // if param is username
//        }
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, allColumns,
//                                        COLUMN_PARAM + " = ? AND " +
//                                                MySQLiteHelper.COLUMN_PASSWORD + " = ?",
//                                                new String[]{usernameOrEmail, password}, null, null, null);
//        int count = cursor.getCount();
//        cursor.close();
//        close();
//
//        return count > 0;
        return true;
    }

//    @SuppressLint("Range")
//    public User getUserById(String userId) {
//        User u = null;
//
//        String selection = MySQLiteHelper.COLUMN_ID_USER + " = ?";
//        String[] selectionArgs = {String.valueOf(userId)};
//
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, allColumns,
//                selection, selectionArgs, null, null, null);
//
//        try {
//            if (cursor != null && cursor.moveToFirst()) {
//                // Store column indices
//                int columnIndexUserId = cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID_USER);
//                int columnIndexUsername = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USERNAME);
//                int columnIndexAvatar = cursor.getColumnIndex(MySQLiteHelper.COLUMN_AVATAR);
//                int columnIndexEmail = cursor.getColumnIndex(MySQLiteHelper.COLUMN_EMAIL);
//                int columnIndexPassword = cursor.getColumnIndex(MySQLiteHelper.COLUMN_PASSWORD);
//                int columnIndexIsActive = cursor.getColumnIndex(MySQLiteHelper.COLUMN_IS_ACTIVE);
//
//                // Read data using column indices
//                u = new User(
//                        cursor.getString(columnIndexUserId),
//                        cursor.getString(columnIndexUsername),
//                        cursor.getString(columnIndexAvatar),
//                        cursor.getString(columnIndexEmail),
//                        cursor.getString(columnIndexPassword),
//                        cursor.getInt(columnIndexIsActive) == 1 ? true : false,
//                        null, null, null, null
//                );
//            } else {
//                // Cursor is empty, user not found
//                Log.e("UserDao", "User with ID " + userId + " not found.");
//            }
//        } catch (Exception e) {
//            Log.e("UserDao", "Error reading user data from cursor: " + e.getMessage());
//        } finally {
//            // Close the cursor after fetching data.
//            if (cursor != null) {
//                cursor.close();
//            }
//        }
//        return u;
//    }
public User getUserById(String id) {
    User user = null;
    try(Cursor cursor = db.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_USER
                    + " WHERE " + MySQLiteHelper.COLUMN_USER_ID + " = ?",
            new String[]{id})) {
        //if user is exists
        if (cursor.moveToFirst()) {
            user = getUserByCursor(cursor);
        }
    }
    return user;
}


    public User getUserByCursor(Cursor cursor) {
        // id - name - gender - birthday - email - imgAvatar - imgCover - bio - createdDate - password - isActive
        User user = new User();

        // index columns
        int id = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_ID);
        int name = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_NAME);
        int birthday = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_BIRTHDAY);
        int gender = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_GENDER);
        int email = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_EMAIL);
        int avatar = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_IMAGE_AVATAR);
        int cover = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_IMAGE_COVER);
        int bio = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_BIO);
        int createdDate = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_CREATED_DATE);
        int password = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_PASSWORD);
        int isActive = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_IS_ACTIVE);
        int role = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_USER_ROLE);
        int isOnline = cursor.getColumnIndex(MySQLiteHelper.COLUMN_USER_IS_ONLINE);

        // set data
        if (id != -1) user.setId(cursor.getString(id));
        if (name != -1) user.setName(cursor.getString(name));
        if (birthday != -1) user.setBirthday(cursor.getString(birthday));
        if (gender != -1) user.setGender(cursor.getString(gender));
        if (email != -1) user.setEmail(cursor.getString(email));
        if (avatar != -1) user.setImageAvatar(cursor.getString(avatar));
        if (cover != -1) user.setImageCover(cursor.getString(cover));
        if (bio != -1) user.setBio(cursor.getString(bio));
        if (createdDate != -1) user.setCreatedDate(cursor.getString(createdDate));
        if (password != -1) user.setPassword(cursor.getString(password));
        if (isActive != -1) user.setActive(cursor.getInt(isActive) == 1);
        if (role != -1) user.setRole(cursor.getString(role));
        if (isOnline != -1) user.setOnline(cursor.getInt(isOnline) == 1);

        return user;
    }
}

