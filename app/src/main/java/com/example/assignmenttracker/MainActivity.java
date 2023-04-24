package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mainLoginButton;
    private Button mainSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLoginButton = findViewById(R.id.mainLoginButton);
        mainSignUpButton = findViewById(R.id.mainSignUpButton);
        mainLoginButton.setOnClickListener(view -> openLoginPage());


        mainSignUpButton.setOnClickListener(view -> openSignUpPage());
    }


    public void openLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openSignUpPage(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }



}