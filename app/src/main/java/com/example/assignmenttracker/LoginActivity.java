package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

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
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }

        });

        txtSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
