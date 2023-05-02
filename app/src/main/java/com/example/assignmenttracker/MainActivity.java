package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity that allows users to navigate to either the login or sign up page.
 */

public class MainActivity extends AppCompatActivity {
    // Fields
    private Button mainLoginButton;
    private Button mainSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // The main layout file (activity_main.xml) is inflated

        // Two buttons are retrieved using the findViewById
        mainLoginButton = findViewById(R.id.mainLoginButton);
        mainSignUpButton = findViewById(R.id.mainSignUpButton);

        // Two click listeners are then set for each button and they open their corresponding activity using Intent object
        mainLoginButton.setOnClickListener(view -> openLoginPage());
        mainSignUpButton.setOnClickListener(view -> openSignUpPage());
    }

    // Intent methods used to start a new activity by passing the current Activity context and the
    // Class object of the activity to be started
    public void openLoginPage(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openSignUpPage(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}