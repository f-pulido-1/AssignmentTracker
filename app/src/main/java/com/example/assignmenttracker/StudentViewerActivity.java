package com.example.assignmenttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
import com.example.assignmenttracker.databinding.ActivityStudentViewerBinding;

import java.util.List;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since May 9, 2023
 * Description: Android activity class that implements a student viewer.
 */

public class StudentViewerActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.assignmenttracker.PREFERENCES_KEY";
    private int userId = -1;
    private SharedPreferences preferences = null;
    private ActivityStudentViewerBinding binding;
    private TextView mainStudentViewerTextView;
    private Button studentViewerBackButton;
    private AssignmentTrackerDAO assignmentTrackerDAO;
    private List<User> userList;
    private int adminUserId = -1;

    public static Intent intentFactory(Context context, int userId) {
        Log.d("StudentViewActivity", "intentFactory CALLED SUCCESSFULLY");
        Intent intent = new Intent(context, StudentViewerActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_viewer);

        getDatabase();

        adminUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        binding = ActivityStudentViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainStudentViewerTextView = binding.mainStudentViewerTextView;
        studentViewerBackButton = binding.studentViewerBackButton;

        refreshDisplay();

        studentViewerBackButton.setOnClickListener(view -> {
            Log.d("StudentViewerActivity", "STARTING AdminMainActivity");
            Log.d("StudentViewerActivity", "adminUserId=" + adminUserId);
            Intent intent = AdminMainActivity.intentFactory(getApplicationContext(), adminUserId);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("StudentViewerActivity", "onCreateOptionsMenu CALLED SUCCESSFULLY");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("StudentViewerActivity", "onOptionsItemSelected CALLED SUCCESSFULLY");
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Item 1 Selected", Toast.LENGTH_SHORT).show();
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

    private void getDatabase() {
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().AssignmentTrackerDAO();
    }

    private void refreshDisplay() {
        userList = assignmentTrackerDAO.getAllUsers();
        if (!userList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (User user : userList) {
                sb.append(user.toString());
            }
            mainStudentViewerTextView.setText(sb.toString());
        } else {
            mainStudentViewerTextView.setText("No users yet.");
        }
        mainStudentViewerTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void logoutUser() {
        Log.d("AdminMainActivity", "logoutUser CALLED SUCCESSFULLY");
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
        Log.d("AdminMainActivity", "checkForUser CALLED SUCCESSFULLY");
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
        Log.d("AdminMainActivity", "clearUserFromPref CALLED SUCCESSFULLY");
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromIntent() {
        Log.d("AdminMainActivity", "clearUserFromIntent CALLED SUCCESSFULLY");
        addUserToPreference(-1);
    }

    private void addUserToPreference(int userId) {
        Log.d("AdminMainActivity", "addUserToPreference CALLED SUCCESSFULLY");
        if (preferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        Log.d("AdminMainActivity", "getPrefs CALLED SUCCESSFULLY");
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}