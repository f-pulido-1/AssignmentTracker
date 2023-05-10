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
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
import com.example.assignmenttracker.databinding.ActivityAdminMainBinding;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since May 9, 2023
 * Description: Android activity class that implements an admin landing page.
 */

public class AdminMainActivity extends AppCompatActivity {

    // Fields
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.assignmenttracker.PREFERENCES_KEY";
    private ActivityAdminMainBinding binding;
    private AssignmentTrackerDAO assignmentTrackerDAO;
    private Button buttonLogoutAdmin;
    private Button viewAllStudentsButton;
    private TextView adminWelcomeMessage;
    private User adminUser;
    private SharedPreferences preferences = null;
    private int userId = -1;
    
    public static Intent intentFactory(Context context, int userId) {
        Log.d("AdminMainActivity", "intentFactory CALLED SUCCESSFULLY");
        Intent intent = new Intent(context, AdminMainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        getDatabase();
        checkForUser();

        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        buttonLogoutAdmin = binding.buttonLogoutAdmin;
        adminWelcomeMessage = binding.adminWelcomeMessage;
        viewAllStudentsButton = binding.viewAllStudentsButton;

        Log.d("AdminMainActivity", "BEFORE displayWelcomeMessage()");
        displayWelcomeMessage();
        Log.d("AdminMainActivity", "AFTER displayWelcomeMessage()");

        viewAllStudentsButton.setOnClickListener(view -> {
            Log.d("AdminMainActivity", "STARTING StudentViewerActivity");
            Log.d("AdminMainActivity", "Username: " + adminUser.getUsername() + " First: " + adminUser.getFirstName());
            Intent intent = new Intent(AdminMainActivity.this, StudentViewerActivity.class);
            // TODO: fix error connected to StudentViewerActivity
            intent.putExtra(USER_ID_KEY, userId);
            startActivity(intent);
        });

        buttonLogoutAdmin.setOnClickListener(view -> logoutUser());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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

    private void getDatabase() {
        Log.d("AdminMainActivity", "getDatabase CALLED SUCCESSFULLY");
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentTrackerDAO();
    }

    private void displayWelcomeMessage() {
        Log.d("AdminMainActivity", "DURING displayWelcomeMessage()");
        adminUser = assignmentTrackerDAO.getUserByUserId(userId);
        String firstName = adminUser.getFirstName();
        String lastName = adminUser.getLastName();
        adminWelcomeMessage.setText("Hello, " + firstName + " " + lastName + ". You have admin controls.");

    }

    private void logoutUser() {
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
        // Do we have a user in the intent?
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        // Do we have a user in the preferences?
        if (userId != -1) {
            return;
        }

        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

        userId = preferences.getInt(USER_ID_KEY,-1);

        if (userId != -1) {
            return;
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void clearUserFromPref() {
        getIntent().putExtra(USER_ID_KEY,-1);
    }

    private void clearUserFromIntent() {
        addUserToPreference(-1);
    }

    private void addUserToPreference(int userId) {
        if (preferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}