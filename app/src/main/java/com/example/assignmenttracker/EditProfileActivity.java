package com.example.assignmenttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
import com.example.assignmenttracker.databinding.ActivityEditProfileBinding;
import com.example.assignmenttracker.databinding.ActivityMainBinding;

import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private AssignmentTrackerDAO assignmentTrackerDAO;
    private int userId = -1;
    private ActivityEditProfileBinding binding;
    private EditText editProfileFirstNameText;
    private EditText editProfileLastNameText;
    private EditText editProfileUsernameText;
    private EditText editProfilePasswordText;
    private Button editProfileUpdateButton;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getDatabase();

        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editProfileFirstNameText = binding.editProfileFirstNameText;
        editProfileLastNameText = binding.editProfileLastNameText;
        editProfileUsernameText = binding.editProfileUsernameText;
        editProfilePasswordText = binding.editProfilePasswordText;
        editProfileUpdateButton = binding.editProfileUpdateButton;

        user = assignmentTrackerDAO.getUserByUserId(userId);

        editProfileFirstNameText.setText(user.getFirstName());
        editProfileLastNameText.setText(user.getLastName());
        editProfileUsernameText.setText(user.getUsername());
        editProfilePasswordText.setText(user.getPassword());

        editProfileUpdateButton.setOnClickListener(view -> {
            String firstNameValue = editProfileFirstNameText.getText().toString();
            String lastNameValue = editProfileLastNameText.getText().toString();
            String usernameValue = editProfileUsernameText.getText().toString();
            String passwordValue = editProfilePasswordText.getText().toString();

            User checkUser = assignmentTrackerDAO.getUserByUsername(usernameValue);

            if (checkUser != null && !Objects.equals(checkUser.getUsername(), user.getUsername())){
                Toast.makeText(getApplicationContext(), usernameValue + " already taken", Toast.LENGTH_SHORT).show();
            } else {
                user = new User(userId, firstNameValue, lastNameValue, usernameValue, passwordValue, false);
                assignmentTrackerDAO.update(user);
                Toast.makeText(getApplicationContext(), "Your profile has updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDatabase() {
        Log.d("EditProfileActivity", "getDatabase CALLED SUCCESSFULLY");
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentTrackerDAO();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("EditProfileActivity", "onCreateOptionsMenu CALLED SUCCESSFULLY");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("EditProfileActivity", "onOptionsItemSelected CALLED SUCCESSFULLY");
        switch(item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Item 3 Selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}