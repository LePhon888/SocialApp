package com.kp.socialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kp.socialnetwork.data.MySQLiteHelper;

public class HomePageActivity extends AppCompatActivity {
    private MySQLiteHelper mySQLiteHelper;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btn = findViewById(R.id.clickbtn);
        mySQLiteHelper = new MySQLiteHelper(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySQLiteHelper.addNewUser();
            }
        });

    }
}
