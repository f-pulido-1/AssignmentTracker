package com.example.assignmenttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since May 10, 2023
 * Description: Android activity that implements an edit profile page.
 */
public class EditProfileActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.assignmenttracker.PREFERENCES_KEY";
    private AssignmentTrackerDAO assignmentTrackerDAO;
    private int userId = -1;
    private ActivityEditProfileBinding binding;
    private EditText editProfileFirstNameText;
    private EditText editProfileLastNameText;
    private EditText editProfileUsernameText;
    private EditText editProfilePasswordText;
    private Button editProfileUpdateButton;
    private Button editProfileBackButton;
    private Button editProfileDeleteButton;
    private SharedPreferences preferences = null;
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
        editProfileBackButton = binding.editProfileBackButton;
        editProfileDeleteButton = binding.editProfileDeleteButton;

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

            if (checkUser != null && !Objects.equals(checkUser.getUsername(), user.getUsername())) {
                Toast.makeText(getApplicationContext(), usernameValue + " already taken", Toast.LENGTH_SHORT).show();
            } else {
                user.setFirstName(firstNameValue);
                user.setLastName(lastNameValue);
                user.setUsername(usernameValue);
                user.setPassword(passwordValue);
                assignmentTrackerDAO.update(user);
                Toast.makeText(getApplicationContext(), "Your profile has updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editProfileBackButton.setOnClickListener(view -> {
            Intent intent;
            if (user.isAdmin()) {
                intent = AdminMainActivity.intentFactory(getApplicationContext(), userId);
            } else {
                intent = MainActivity.intentFactory(getApplicationContext(), userId);
            }
            startActivity(intent);
        });

        editProfileDeleteButton.setOnClickListener(view ->  {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

            alertBuilder.setMessage("Are you sure you want to delete this profile?");

            alertBuilder.setPositiveButton(getString(R.string.yes),
                    (dialog, which) -> {
                        assignmentTrackerDAO.delete(user);
                        Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                    });
            alertBuilder.setNegativeButton(getString(R.string.no),
                    (dialog, which) -> {
                        //We don't really need to do anything here.
                    });

            alertBuilder.create().show();
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
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Edit Profile Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
                logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        Log.d("EditProfile", "logoutUser CALLED SUCCESSFULLY");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout");

        alertBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, which) -> {
                    clearUserFromIntent();
                    clearUserFromPref();
                    userId = -1;
                    checkForUser();
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                (dialog, which) -> {
                    //We don't really need to do anything here.
                });

        alertBuilder.create().show();
    }


    private void checkForUser() {
        Log.d("EditProfileActivity", "checkForUser CALLED SUCCESSFULLY");
        // Do we have a user in the intent?
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        // Do we have a user in the preferences?
        if (userId != -1) {
            return;
        }

        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

        userId = preferences.getInt(USER_ID_KEY, -1);

        if (userId != -1) {
            return;
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void clearUserFromPref() {
        Log.d("EditProfileActivity", "clearUserFromPref CALLED SUCCESSFULLY");
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromIntent() {
        Log.d("EditProfileActivity", "clearUserFromIntent CALLED SUCCESSFULLY");
        addUserToPreference(-1);
    }

    private void addUserToPreference(int userId) {
        Log.d("EditProfileActivity", "addUserToPreference CALLED SUCCESSFULLY");
        if (preferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        Log.d("EditProfileActivity", "getPrefs CALLED SUCCESSFULLY");
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}