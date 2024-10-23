package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activity.HotelActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usernameEditText = findViewById(R.id.etUsername);
        EditText passwordEditText = findViewById(R.id.etPassword);
        Button loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                validateLogin(username, password);
            }
        });
    }

    private void validateLogin(String username, String password) {
        // Hardcoded credentials for simplicity
        if (username.equals("admin") && password.equals("admin123")) {

            // Redirect to Admin Dashboard
            startActivity(new Intent(this, HotelActivity.class));
        } else {
            // Show an error message
            Toast.makeText(this, "Login Fail ", Toast.LENGTH_SHORT).show();
        }
    }
}
