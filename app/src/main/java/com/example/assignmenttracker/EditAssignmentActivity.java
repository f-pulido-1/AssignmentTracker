package com.example.assignmenttracker;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
import com.example.assignmenttracker.databinding.ActivityEditAssignmentBinding;

public class EditAssignmentActivity extends AppCompatActivity {

    private static final String TRACKER_ID_KEY = "com.example.assignmenttracker.trackerIdKey";
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.assignmenttracker.PREFERENCES_KEY";
    private SharedPreferences preferences = null;
    private EditText editAssignmentAssignmentText;
    private EditText editAssignmentSubjectText;
    private EditText editAssignmentDateText;
    private Button editAssignmentUpdateButton;
    private ActivityEditAssignmentBinding binding;

    private AssignmentTrackerDAO assignmentTrackerDAO;
    private int trackerId = 1;
    private int userId = -1;
    private AssignmentTracker tracker;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        getDatabase();
        checkForUser();

        trackerId = getIntent().getIntExtra(TRACKER_ID_KEY, 1);
        tracker = assignmentTrackerDAO.getTrackerById(trackerId);
        Log.d("EditAssignmentActivity", "current trackerId= " + trackerId);
        Log.d("EditAssignmentActivity", "current tracker= " + tracker);
        user = assignmentTrackerDAO.getUserByUserId(userId);

        binding = ActivityEditAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editAssignmentAssignmentText = binding.editAssignmentAssignmentText;
        editAssignmentSubjectText = binding.editAssignmentSubjectText;
        editAssignmentDateText = binding.editAssignmentDateText;
        editAssignmentUpdateButton = binding.editAssignmentUpdateButton;

        editAssignmentAssignmentText.setText(tracker.getAssignment());
        editAssignmentSubjectText.setText(tracker.getSubject());
        editAssignmentDateText.setText(tracker.getDate());

        editAssignmentUpdateButton.setOnClickListener(view -> {
            tracker.setAssignment(editAssignmentAssignmentText.getText().toString());
            tracker.setSubject(editAssignmentSubjectText.getText().toString());
            tracker.setDate(editAssignmentDateText.getText().toString());
            assignmentTrackerDAO.update(tracker);
            Toast.makeText(this, "Updated Assignment " + editAssignmentAssignmentText, Toast.LENGTH_SHORT).show();
        });
    }

    private void getDatabase() {
        Log.d("EditAssignmentActivity", "getDatabase CALLED SUCCESSFULLY");
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().AssignmentTrackerDAO();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("EditAssignmentActivity", "onCreateOptionsMenu CALLED SUCCESSFULLY");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("EditAssignmentActivity", "onOptionsItemSelected CALLED SUCCESSFULLY");
        Intent intent;
        switch (item.getItemId()) {
            case R.id.item0:
                Toast.makeText(this, "Go Back Selected", Toast.LENGTH_SHORT).show();
                Log.d("EditAssignmentActivty", "userId=" + userId);
                intent = ToDoActivity.intentFactory(getApplicationContext(), userId);
                startActivity(intent);
                return true;
            case R.id.item1:
                Toast.makeText(this, "Edit Profile Selected", Toast.LENGTH_SHORT).show();
                intent = EditProfileActivity.intentFactory(getApplicationContext(), userId);
                startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(this, "Item 2 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Toast.makeText(this, "Logout Selected", Toast.LENGTH_SHORT).show();
                logoutUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        Log.d("EditAssignmentActivity", "logoutUser CALLED SUCCESSFULLY");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout");

        alertBuilder.setPositiveButton(getString(R.string.yes), (dialog, which) -> {
            clearUserFromIntent();
            clearUserFromPref();
            userId = -1;
            checkForUser();
        });
        alertBuilder.setNegativeButton(getString(R.string.no), (dialog, which) -> {
            //We don't really need to do anything here.
        });

        alertBuilder.create().show();
    }

    private void checkForUser() {
        Log.d("EditAssignmentActivity", "checkForUser CALLED SUCCESSFULLY");
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
        Log.d("ToDoActivity", "clearUserFromPref CALLED SUCCESSFULLY");
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromIntent() {
        Log.d("ToDoActivity", "clearUserFromIntent CALLED SUCCESSFULLY");
        addUserToPreference(-1);
    }

    private void addUserToPreference(int userId) {
        Log.d("ToDoActivity", "addUserToPreference CALLED SUCCESSFULLY");
        if (preferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        Log.d("ToDoActivity", "getPrefs CALLED SUCCESSFULLY");
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}