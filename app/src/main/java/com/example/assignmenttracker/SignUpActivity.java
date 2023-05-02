package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity that implements a sign up page.
 */

public class SignUpActivity extends AppCompatActivity {
    // Fields
    TextView txtSignUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // xml file that is displayed

        txtSignUp = findViewById(R.id.txtSignUp);

        // click listener that starts the LoginActivity
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent); // Actually starts the activity
                finish();
            }
        });
    }
}