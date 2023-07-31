package com.kp.socialnetwork.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.MySQLiteHelper;

public class HomePageActivity extends AppCompatActivity {
    private MySQLiteHelper mySQLiteHelper;
    Button btn;
    BottomNavigationView bottomNavigationView;
    public static int main=0x7f030004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("BBBBBBBBBBB", String.valueOf(item.getItemId()));

                if (item.getItemId() == R.id.createMenu) {
                    Log.d("AAAAAAAAAAAAA", String.valueOf(item.getItemId()));
                    showCreatePostDialog();
                    return true;
                }
                return false;
            }
        });


    }

    private void showCreatePostDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_create_post, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        Dialog dialog = builder.create();
        dialog.show();
    }
}
