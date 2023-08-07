package com.kp.socialnetwork.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;


import java.util.Properties;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_COMMENT = "comments";
    public static final String TABLE_USER = "users";
    public static final String TABLE_POST = "posts";
    public static final String TABLE_ACTIVITY = "activities";
    public static final String TABLE_FRIENDSHIP = "friendships";

    // Comments table column names
    public static final String COLUMN_ID_COMMENT = "comment_id";
    public static final String COLUMN_CONTENT_COMMENT = "content_comment";


    // Users table column names
    // User
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_IS_ACTIVE = "is_active";
    public static final String COLUMN_USER_IMAGE_AVATAR = "image_avatar";
    public static final String COLUMN_USER_IMAGE_COVER = "image_cover";
    public static final String COLUMN_USER_BIO = "bio";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_BIRTHDAY = "birthday";
    public static final String COLUMN_USER_CREATED_DATE = "created_date";
    public static final String COLUMN_USER_USER_ROLE = "user_role";
    public static final String COLUMN_USER_IS_ONLINE = "is_online";
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " TEXT PRIMARY KEY NOT NULL, "
            + COLUMN_USER_NAME + " TEXT NOT NULL, "
            + COLUMN_USER_GENDER + " TEXT NOT NULL, "
            + COLUMN_USER_BIRTHDAY + " TEXT NOT NULL, "
            + COLUMN_USER_EMAIL + " TEXT NOT NULL, "
            + COLUMN_USER_IMAGE_AVATAR +" TEXT , "
            + COLUMN_USER_IMAGE_COVER + " TEXT, "
            + COLUMN_USER_BIO +  " " + "TEXT, "
            + COLUMN_USER_CREATED_DATE + " " + "TEXT, "
            + COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_USER_USER_ROLE + " TEXT NOT NULL, "
            + COLUMN_USER_IS_ONLINE + " INTEGER, "
            + COLUMN_USER_IS_ACTIVE + " INTEGER);";

    // Posts table column names
    public static final String COLUMN_ID_POST = "post_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CREATED_DATE = "created_date";

    // Activities table column names
    public static final String COLUMN_ID_ACTIVITY = "activity_id";
    public static final String COLUMN_CATEGORY = "category";

    // Friendships table column names
    public static final String COLUMN_ID_FRIENDSHIP = "friendship_id";

    private static final String DATABASE_NAME = "social_network.db";
    private static final int DATABASE_VERSION = 1;

    // Comments table creation sql statement
    private static final String CREATE_TABLE_COMMENT = "CREATE TABLE " + TABLE_COMMENT + "("
            + COLUMN_ID_COMMENT + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +  COLUMN_CONTENT_COMMENT + " TEXT NOT NULL);";

    // Users table creation sql statement
//    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
//            + COLUMN_ID_USER + " TEXT PRIMARY KEY , "
//            + COLUMN_USERNAME + " TEXT NOT NULL, "
//            + COLUMN_AVATAR + " TEXT, "
//            + COLUMN_EMAIL + " TEXT NOT NULL, "
//            + COLUMN_PASSWORD + " TEXT NOT NULL, "
//            + COLUMN_IS_ACTIVE + " INTEGER);";

    // Posts table creation sql statement
    private static final String CREATE_TABLE_POST = "CREATE TABLE " + TABLE_POST + "("
            + COLUMN_ID_POST + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CONTENT + " TEXT NOT NULL, "
            + COLUMN_IMAGE + " TEXT, "
            + COLUMN_CREATED_DATE + " TEXT, "
            + COLUMN_USER_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    // Activities table creation sql statement
    private static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY + "("
            + COLUMN_ID_ACTIVITY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY + " TEXT NOT NULL, "
            + COLUMN_USER_ID + " INTEGER, "
            + COLUMN_ID_POST + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), "
            + "FOREIGN KEY(" + COLUMN_ID_POST + ") REFERENCES " + TABLE_POST + "(" + COLUMN_ID_POST + "));";

    // Friendships table creation sql statement
//    private static final String CREATE_TABLE_FRIENDSHIP = "CREATE TABLE " + TABLE_FRIENDSHIP + "("
//            + COLUMN_ID_FRIENDSHIP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + "id_user1" + " INTEGER, "
//            + "id_user2" + " INTEGER, "
//            + "FOREIGN KEY(" + "id_user1" + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "), "
//            + "FOREIGN KEY(" + "id_user2" + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "));";

    // Create Database
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    // Create multiple tables
    @Override
    public void onCreate(SQLiteDatabase database) {
        // Create all tables
        database.execSQL(CREATE_TABLE_COMMENT);
        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(CREATE_TABLE_POST);
        database.execSQL(CREATE_TABLE_ACTIVITY);
//        database.execSQL(CREATE_TABLE_FRIENDSHIP);
    }

    // Upgrade version database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        // Drop all tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDSHIP);
        // Recreate all tables
        onCreate(db);
    }

    public void addNewUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
//        contentValue.put(COLUMN_USERNAME, "aaa");
//        contentValue.put(COLUMN_EMAIL, "aaa@gmail.com");
//        contentValue.put(COLUMN_PASSWORD, "aaa");
//        contentValue.put(COLUMN_IS_ACTIVE, "1");

        db.insert(TABLE_USER, null, contentValue);
        db.close();

    }


}
