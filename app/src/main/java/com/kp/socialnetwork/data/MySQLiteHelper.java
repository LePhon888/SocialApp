package com.kp.socialnetwork.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_COMMENT = "comments";
    public static final String TABLE_USER = "users";
    public static final String TABLE_POST = "posts";
    public static final String TABLE_ACTIVITY = "activities";
    public static final String TABLE_FRIENDSHIP = "friendships";

    // Comments table column names
    public static final String COLUMN_ID_COMMENT = "comment_id";
    public static final String COLUMN_COMMENT = "comment";

    // Users table column names
    public static final String COLUMN_ID_USER = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IS_ACTIVE = "is_active";

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
            + COLUMN_COMMENT + " TEXT NOT NULL);";

    // Users table creation sql statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USERNAME + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_IS_ACTIVE + " INTEGER);";

    // Posts table creation sql statement
    private static final String CREATE_TABLE_POST = "CREATE TABLE " + TABLE_POST + "("
            + COLUMN_ID_POST + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CONTENT + " TEXT NOT NULL, "
            + COLUMN_IMAGE + " TEXT, "
            + COLUMN_CREATED_DATE + " INTEGER, "
            + COLUMN_ID_USER + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "));";

    // Activities table creation sql statement
    private static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY + "("
            + COLUMN_ID_ACTIVITY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY + " TEXT NOT NULL, "
            + COLUMN_ID_USER + " INTEGER, "
            + COLUMN_ID_POST + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "), "
            + "FOREIGN KEY(" + COLUMN_ID_POST + ") REFERENCES " + TABLE_POST + "(" + COLUMN_ID_POST + "));";

    // Friendships table creation sql statement
    private static final String CREATE_TABLE_FRIENDSHIP = "CREATE TABLE " + TABLE_FRIENDSHIP + "("
            + COLUMN_ID_FRIENDSHIP + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ID_USER + " INTEGER, "
            + COLUMN_ID_USER + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "), "
            + "FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + TABLE_USER + "(" + COLUMN_ID_USER + "));";

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
        database.execSQL(CREATE_TABLE_FRIENDSHIP);
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
        contentValue.put(COLUMN_USERNAME, "AAA");
        contentValue.put(COLUMN_PASSWORD, "BBB");
        contentValue.put(COLUMN_IS_ACTIVE, "1");

        db.insert(TABLE_USER, null, contentValue);
        db.close();

    }



}
