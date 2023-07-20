package com.kp.socialnetwork.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kp.socialnetwork.R;
import com.kp.socialnetwork.data.MySQLiteHelper;
import com.kp.socialnetwork.data.dao.UserDao;
import com.kp.socialnetwork.data.model.User;

public class SignUpActivity extends AppCompatActivity {

    private TextView txtLogin;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    private Button signupBtn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtLogin = findViewById(R.id.txtLogin);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.comfirmPasswordEditText);
        signupBtn = findViewById(R.id.signUpBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if(username.equals("") || email.equals("") || password.equals("") || confirmPassword.equals(""))
                    Toast.makeText(SignUpActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else if (!email.matches(emailPattern)) {
                    Toast.makeText(SignUpActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Confirm Password have to similar to Password", Toast.LENGTH_SHORT).show();
                } else {
                    UserDao userDao = new UserDao(SignUpActivity.this);
                    userDao.createUser(username, email, password, 1);
                    Toast.makeText(SignUpActivity.this, "Sign up account is successful ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}