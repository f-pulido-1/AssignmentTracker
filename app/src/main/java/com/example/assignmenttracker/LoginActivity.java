package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
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

public class LoginActivity extends AppCompatActivity {

    // Fields
    TextView txtSignIn;
    MaterialButton logInBtn;

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;

    private TextInputLayout usernameLayout;
    private TextInputLayout passwordLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignIn = findViewById(R.id.txtSignIn);
        logInBtn = findViewById(R.id.btnSignIn);
        usernameLayout = findViewById(R.id.edtSignInUsername);
        passwordLayout = findViewById(R.id.edtSignInPassword);

        // When logInBtn is clicked, the entered username and password are compared with users.
        // if match is found, an intent is created to launch the LandingPage, and passes the
        // username as an extra. Otherwise, a toast message is displayed to indicate that the
        // login failed.
        logInBtn.setOnClickListener(view -> {
            String username = Objects.requireNonNull(usernameLayout.getEditText()).getText().toString();
            String password = Objects.requireNonNull(passwordLayout.getEditText()).getText().toString();

            List<User> predefinedUsers = User.getPredefinedUsers();
            for(User user : predefinedUsers) {
                if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    Intent intent = new Intent(LoginActivity.this, LandingPage.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                    return;
                }
                //TODO: style the toast better (as shown in class by Dr.C)
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

        });

        // When txtSignIn is clicked, the SignUpActivity launched
        txtSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
