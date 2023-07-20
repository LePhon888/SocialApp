package com.kp.socialnetwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.dao.UserDao;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameOrEmailEditText;

    private EditText passwordEditText;
    private Button loginBtn;
    private UserDao userDao;
    private TextView txtSignUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameOrEmailEditText = findViewById(R.id.usernameOrEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginBtn = findViewById(R.id.loginBtn);
        txtSignUp = findViewById(R.id.txtSignUp);
        userDao = new UserDao(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameOrEmail = usernameOrEmailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(usernameOrEmailEditText.equals("") || password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else {
                    Boolean isAuthenticated = userDao.authenticateUser(usernameOrEmail, password);
                    if (isAuthenticated == true) {
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        try {
                            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e("LoginActivity", "Error starting HomePageActivity: " + e.getMessage());
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}