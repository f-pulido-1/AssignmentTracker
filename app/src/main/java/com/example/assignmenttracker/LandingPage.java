package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LandingPage extends AppCompatActivity {

    private Button backButton;
    private Button landingAddAssignmentBtn;
    private Button landingEnterGradeBtn;
    private TextView landingMessage;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);
        backButton = findViewById(R.id.backButton);
        landingAddAssignmentBtn = findViewById(R.id.landingAddAssignmentBtn);
        landingEnterGradeBtn = findViewById(R.id.landingEnterGradeBtn);
        landingMessage = findViewById(R.id.landingMessage);
        imageView = findViewById(R.id.imageView);

        String username = getIntent().getStringExtra("username");
        landingMessage.setText("Welcome, " + username + "!");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
