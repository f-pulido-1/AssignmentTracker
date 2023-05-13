package com.example.assignmenttracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import java.util.Calendar;

public class EditAssignmentActivity extends AppCompatActivity {

    private static final String TRACKER_ID_KEY = "com.example.assignmenttracker.trackerIdKey";
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.assignmenttracker.PREFERENCES_KEY";
    private SharedPreferences preferences = null;
    private EditText editAssignmentAssignmentText;
    private EditText editAssignmentSubjectText;
    private Button editAssignmentDateButton;
    private DatePickerDialog datePickerDialog;
    private Button editAssignmentUpdateButton;
    private Button getEditAssignmentDeleteButton;
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
        initDatePicker();

        trackerId = getIntent().getIntExtra(TRACKER_ID_KEY, 1);
        tracker = assignmentTrackerDAO.getTrackerById(trackerId);
        Log.d("EditAssignmentActivity", "current trackerId= " + trackerId);
        Log.d("EditAssignmentActivity", "current tracker= " + tracker);
        user = assignmentTrackerDAO.getUserByUserId(userId);

        binding = ActivityEditAssignmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editAssignmentAssignmentText = binding.editAssignmentAssignmentText;
        editAssignmentSubjectText = binding.editAssignmentSubjectText;
        editAssignmentDateButton = binding.editAssignmentDateButton;
        editAssignmentUpdateButton = binding.editAssignmentUpdateButton;
        getEditAssignmentDeleteButton = binding.editAssignmentDeleteButton;

        editAssignmentAssignmentText.setText(tracker.getAssignment());
        editAssignmentSubjectText.setText(tracker.getSubject());
        editAssignmentDateButton.setText(tracker.getDate());

        editAssignmentUpdateButton.setOnClickListener(view -> {
            tracker.setAssignment(editAssignmentAssignmentText.getText().toString());
            tracker.setSubject(editAssignmentSubjectText.getText().toString());
            tracker.setDate(editAssignmentDateButton.getText().toString());
            assignmentTrackerDAO.update(tracker);
            Toast.makeText(this, "Updated Assignment " + editAssignmentAssignmentText.getText(), Toast.LENGTH_SHORT).show();
            Intent intent = ToDoActivity.intentFactory(getApplicationContext(), userId);
            startActivity(intent);
        });

        getEditAssignmentDeleteButton.setOnClickListener(view -> {
            assignmentTrackerDAO.delete(tracker);
            Toast.makeText(this, "Deleted Assignment " + editAssignmentAssignmentText.getText(), Toast.LENGTH_SHORT).show();
            Intent intent = ToDoActivity.intentFactory(getApplicationContext(), userId);
            startActivity(intent);
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            editAssignmentDateButton.setText(date);
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1) return "JAN";
        if (month == 2) return "FEB";
        if (month == 3) return "MAR";
        if (month == 4) return "APR";
        if (month == 5) return "MAY";
        if (month == 6) return "JUN";
        if (month == 7) return "JUL";
        if (month == 8) return "AUG";
        if (month == 9) return "SEP";
        if (month == 10) return "OCT";
        if (month == 11) return "NOV";
        if (month == 12) return "DEC";
        return "JAN";
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
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}