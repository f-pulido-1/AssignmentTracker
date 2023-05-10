package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
import com.example.assignmenttracker.databinding.ActivityMainBinding;
import com.example.assignmenttracker.databinding.ActivitySignUpBinding;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity that implements a sign up page.
 */

public class SignUpActivity extends AppCompatActivity {
    // Fields
    ActivitySignUpBinding binding;
    Button buttonSignUp;
    TextView textViewLogIn;
    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText passwordConfirm;

    AssignmentTrackerDAO assignmentTrackerDAO;
    private int userId = -1;
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // xml file that is displayed

        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buttonSignUp = binding.buttonSignUp;
        firstName = binding.editTextSignUpFirstName;
        lastName = binding.editTextSignUpLastName;
        username = binding.editTextSignUpUsername;
        password = binding.editTextSignUpPassword;
        textViewLogIn = binding.textViewLogIn;

        getDatabase();

        // click listener that starts the LoginActivity
        buttonSignUp.setOnClickListener(view -> {
            //TODO: add validation so that all fields must be filled out
            String firstNameValue = firstName.getText().toString();
            String lastNameValue = lastName.getText().toString();
            String usernameValue = username.getText().toString();
            String passwordValue = password.getText().toString();

            User checkUser = assignmentTrackerDAO.getUserByUsername(usernameValue);

            if (checkUser != null){
                Toast.makeText(getApplicationContext(), usernameValue + " already taken", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(userId, firstNameValue, lastNameValue, usernameValue, passwordValue, false);
                assignmentTrackerDAO.insert(newUser);

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent); // Actually starts the activity
                finish();
            }
        });

        textViewLogIn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getDatabase(){
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .AssignmentTrackerDAO();
    }
}