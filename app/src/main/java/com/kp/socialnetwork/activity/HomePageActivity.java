package com.kp.socialnetwork.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
                if (item.getItemId() == R.id.createMenu) {
                    Intent intent = new Intent(HomePageActivity.this, CreateOrUpdatePostActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


    }

}
