package com.kp.socialnetwork.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kp.socialnetwork.data.MySQLiteHelper;
import com.kp.socialnetwork.data.model.Post;
import com.kp.socialnetwork.data.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserDao {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID_USER,
            MySQLiteHelper.COLUMN_USERNAME,
            MySQLiteHelper.COLUMN_EMAIL,
            MySQLiteHelper.COLUMN_PASSWORD,
            MySQLiteHelper.COLUMN_IS_ACTIVE};

    public UserDao(Context context) { dbHelper = new MySQLiteHelper(context);}

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {dbHelper.close();}


    public void createUser(String username, String email, String password, int isActive) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USERNAME, username);
        values.put(MySQLiteHelper.COLUMN_EMAIL, email);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        values.put(MySQLiteHelper.COLUMN_IS_ACTIVE, isActive);

        db.insert(MySQLiteHelper.TABLE_USER, null, values);
        db.close();
    }
    public Boolean authenticateUser(String usernameOrEmail, String password) {
        String COLUMN_PARAM;
        open();
        if (usernameOrEmail.matches(emailPattern)) {
            COLUMN_PARAM = MySQLiteHelper.COLUMN_EMAIL; //if param is email
        } else {
            COLUMN_PARAM = MySQLiteHelper.COLUMN_USERNAME; // if param is username
        }

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER, allColumns,
                                        COLUMN_PARAM + " = ? AND " +
                                                MySQLiteHelper.COLUMN_PASSWORD + " = ?",
                                                new String[]{usernameOrEmail, password}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        close();

        return count > 0;
    }

}
