package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity that implements a sign up page.
 */

public class SignUpActivity extends AppCompatActivity {
    // Fields
    Button txtSignUp;
    EditText firstName;
    EditText lastName;
    EditText username;
    EditText password;
    EditText passwordConfirm;

    AssignmentTrackerDAO assignmentTrackerDAO;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // xml file that is displayed

        txtSignUp = findViewById(R.id.btnSignUp);
        firstName = findViewById(R.id.edtSignUpFirstName);
        lastName = findViewById(R.id.edtSignUpLastName);
        username = findViewById(R.id.editSignUpUsername);
        password = findViewById(R.id.edtSignInPassword);
        passwordConfirm = findViewById(R.id.edtSignUpConfirmPassword);

        getDatabase();


        // click listener that starts the LoginActivity
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                TODO: Make a user object
//                TODO: Insert into database

                String firstNameValue = firstName.getText().toString();
                String lastNameValue = lastName.getText().toString();
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();
                String passwordConfirmValue = passwordConfirm.getText().toString();

                User checkUser = assignmentTrackerDAO.getUserByUsername(usernameValue);

                if(checkUser != null){
                    Toast.makeText(getApplicationContext(), "Username already taken", Toast.LENGTH_SHORT).show();
                }else{
                    User newUser = new User(firstNameValue, lastNameValue, usernameValue, passwordValue, false);
                    assignmentTrackerDAO.insert(newUser);

                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent); // Actually starts the activity
                    finish();
                }

            }
        });
    }

    private void getDatabase(){
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().AssignmentTrackerDAO();
    }
}