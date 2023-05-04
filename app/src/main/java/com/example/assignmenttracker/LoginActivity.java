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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.List;
import java.util.Objects;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity class that implements a login screen.
 */

//public class LoginActivity extends AppCompatActivity {
//
//    // Fields
//    TextView txtSignIn;
//    MaterialButton logInBtn;
//
//    private TextInputEditText usernameEditText;
//    private TextInputEditText passwordEditText;
//
//    private TextInputLayout usernameLayout;
//    private TextInputLayout passwordLayout;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        txtSignIn = findViewById(R.id.txtSignIn);
//        logInBtn = findViewById(R.id.btnSignIn);
//        usernameLayout = findViewById(R.id.edtSignInUsername);
//        passwordLayout = findViewById(R.id.edtSignInPassword);
//
//        // When logInBtn is clicked, the entered username and password are compared with users.
//        // if match is found, an intent is created to launch the LandingPage, and passes the
//        // username as an extra. Otherwise, a toast message is displayed to indicate that the
//        // login failed.
//        logInBtn.setOnClickListener(view -> {
//            String username = Objects.requireNonNull(usernameLayout.getEditText()).getText().toString();
//            String password = Objects.requireNonNull(passwordLayout.getEditText()).getText().toString();
//
//            List<User> predefinedUsers = User.getPredefinedUsers();
//            for(User user : predefinedUsers) {
//                if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
//                    Intent intent = new Intent(LoginActivity.this, LandingPage.class);
//                    intent.putExtra("username", username);
//                    startActivity(intent);
//                    finish();
//                    return;
//                }
//                //TODO: style the toast better (as shown in class by Dr.C)
//                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//        // When txtSignIn is clicked, the SignUpActivity launched
//        txtSignIn.setOnClickListener(view -> {
//            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//            startActivity(intent);
//        });
//    }
//}


public class LoginActivity extends AppCompatActivity {

    // Fields
    private EditText usernameField;
    private EditText passwordField;
    private Button button;
    private AssignmentTrackerDAO assignmentTrackerDAO;
    private String username;
    private String password;
    private User user;
    private TextView txtSignIn;

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
        usernameField = findViewById(R.id.editTextLoginUsername);
        passwordField = findViewById(R.id.editTextLoginPassword);
        button = findViewById(R.id.buttonLogin);
        txtSignIn = findViewById(R.id.textViewSignIn);

        button.setOnClickListener(v -> {
            getValuesFromDisplay();
            if (checkForUserInDatabase()) {
                if (!validatePassword()) {
                    Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.intentFactory(getApplicationContext(), user.getUserId());
                    startActivity(intent);
                }
            }
        });

        // When txtSignIn is clicked, the SignUpActivity launched
        txtSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
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
        if (user == null) {
            Toast.makeText(this, "No user " + user + " found", Toast.LENGTH_SHORT).show();
        }
        return true;
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
