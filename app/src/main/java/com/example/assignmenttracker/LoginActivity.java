package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity class that implements a login screen.
 */

public class LoginActivity extends AppCompatActivity {

    // Fields
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
     EditText usernameField;
     EditText passwordField;
     Button button;
     AssignmentTrackerDAO assignmentTrackerDAO;
     String username;
     String password;
     User user;
     TextView txtSignIn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LoginActivity", "onCreate CALLED SUCCESSFULLY");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getDatabase();
        wireUpDisplay();
    }

    private void wireUpDisplay() {
        Log.d("LoginActivity", "wireUpDisplay CALLED SUCCESSFULLY");
        usernameField = findViewById(R.id.editTextSignUpUsername);
        passwordField = findViewById(R.id.editTextSignUpPassword);
        button = findViewById(R.id.buttonSignUp);
        txtSignIn = findViewById(R.id.textViewLogIn);

        button.setOnClickListener(v -> {
            getValuesFromDisplay();
            if (checkForUserInDatabase()) {
                if (!validatePassword()) {
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else {
                    // take user to MainActivty OR AdminMainActivity, depending on their isAdmin value
                    Intent intent;
                    if (user.isAdmin()) {
                        intent = AdminMainActivity.intentFactory(getApplicationContext(), user.getUserId());
                    } else {
                        intent = MainActivity.intentFactory(getApplicationContext(), user.getUserId());
                    }
                    startActivity(intent);
                }
            }
        });

        // When txtSignIn is clicked, the SignUpActivity launched
        txtSignIn.setOnClickListener(view -> {
            Log.d("LoginActivity", "txtSignIn buttonClicked");
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.putExtra(USER_ID_KEY, user.getUserId());
            startActivity(intent);
        });
    }

    private boolean validatePassword() {
        Log.d("LoginActivity", "validatePassword CALLED SUCCESSFULLY");
        return user.getPassword().equals(password);
    }

    private boolean checkForUserInDatabase() {
        Log.d("LoginActivity", "checkForUserInDatabase CALLED SUCCESSFULLY");
        user = assignmentTrackerDAO.getUserByUsername(username);
        Log.d("LoginActivity", "checkForUserInDatabase user = " + user);
        if (user != null) {
//            Toast.makeText(this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
            return true; // Is this the bug?
        }
        Log.d("LoginActivity", "checkForUserInDatabase FOUND NULL USER");
        Toast.makeText(this, "No user " + username + " found", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void getValuesFromDisplay() {
        Log.d("LoginActivity", "getValuesFromDisplay CALLED SUCCESSFULLY");
        username = usernameField.getText().toString();
        password = passwordField.getText().toString();
    }

    private void getDatabase() {
        Log.d("LoginActivity", "getDatabase CALLED SUCCESSFULLY");
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentTrackerDAO();
    }

    public static Intent intentFactory(Context context) {
        Log.d("LoginActivity", "intentFactory CALLED SUCCESSFULLY");
        return new Intent(context, LoginActivity.class);
    }
}
