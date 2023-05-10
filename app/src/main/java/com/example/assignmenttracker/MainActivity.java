package com.example.assignmenttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
import com.example.assignmenttracker.databinding.ActivityMainBinding;

import java.util.List;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity class that implements a user landing page.
 */

public class MainActivity extends AppCompatActivity {
    // Fields
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.assignmenttracker.PREFERENCES_KEY";
    private ActivityMainBinding binding;
    private TextView mainDisplay;
    private EditText assignment;
    private EditText score;
    private Button submit;
    private TextView mainWelcomeMessage;
    private AssignmentTrackerDAO assignmentTrackerDAO;
    private List<AssignmentTracker> assignmentTrackerList;
    private int userId = -1;
    private SharedPreferences preferences = null;
    private Button buttonLogout;
    User user;
    String firstName;
    String lastName;


    public static Intent intentFactory(Context context, int userId) {
        Log.d("MainActivity", "intentFactory CALLED SUCCESSFULLY");
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate CALLED SUCCESSFULLY");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // The main layout file (activity_main.xml) is inflated

        getDatabase();
        checkForUser();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainDisplay = binding.mainAssignmentTrackerDisplay;
        assignment = binding.mainAssignmentEditText;
        score = binding.mainScoreEditText;
        submit = binding.mainSubmitButton;
        buttonLogout = binding.buttonLogout;
        mainWelcomeMessage = binding.mainWelcomeMessage;

        displayWelcomeMessage();

        refreshDisplay();

        submit.setOnClickListener(view -> {
            submitAssignmentTracker();
            refreshDisplay();
        });

        buttonLogout.setOnClickListener(view -> {
            logoutUser();
        });
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("MainActivity", "onCreateOptionsMenu CALLED SUCCESSFULLY");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("MainActivity", "onOptionsItemSelected CALLED SUCCESSFULLY");
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

    private void displayWelcomeMessage() {
        Log.d("MainActivity", "displayWelcomeMessage CALLED SUCCESSFULLY");
        user = assignmentTrackerDAO.getUserByUserId(userId);
        if (user != null) {
            firstName = user.getFirstName();
            lastName = user.getLastName();
            mainWelcomeMessage.setText("Hello, " + firstName + " " + lastName + "!");
        }
    }

    private void submitAssignmentTracker() {
        Log.d("MainActivity", "submitAssignmentTracker CALLED SUCCESSFULLY");
        String assignmentText = assignment.getText().toString();
        double scoreValue = Double.parseDouble(score.getText().toString());
        AssignmentTracker tracker = new AssignmentTracker(assignmentText, scoreValue, userId);
        assignmentTrackerDAO.insert(tracker);
    }

    private void refreshDisplay() {
        Log.d("MainActivity", "refreshDisplay CALLED SUCCESSFULLY");
        assignmentTrackerList = assignmentTrackerDAO.getTrackersByUserId(userId);
        if(!assignmentTrackerList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(AssignmentTracker tracker : assignmentTrackerList) {
                sb.append(tracker.toString());
            }
            mainDisplay.setText(sb.toString());
        } else {
            mainDisplay.setText(R.string.nothing_in_tracker_message);
        }
    }

    private void checkForUser() {
        Log.d("MainActivity", "checkForUser CALLED SUCCESSFULLY");
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

        // Do we have any users at all?
//        if (userId == -1) {
//            List<User> users = assignmentTrackerDAO.getAllUsers();
//            if (users.size() <= 0) {
//                User defaultUser = new User("Mike", "Wazowski", "testuser1", "testuser1", false);
//                User altUser = new User("James", "Sullivan", "admin2", "admin2",true);
//                assignmentTrackerDAO.insert(defaultUser, altUser);
////                userId = defaultUser.getUserId(); // Set the user ID to the ID of the newly inserted user
//            }
//            else {
//                userId = users.get(0).getUserId(); // Set the user ID to the ID of the first user in the database
//            }
//            // Store the user ID in the preferences
//            SharedPreferences.Editor editor = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
//            editor.putInt(USER_ID_KEY, userId);
//            editor.apply();
//        }
        List<User> users = assignmentTrackerDAO.getAllUsers();
        if (users.size() <= 0) {
            User defaultUser = new User("Mike", "Wazowski", "testuser1", "testuser1", false);
            User altUser = new User("James", "Sullivan", "admin2", "admin2", true);
            assignmentTrackerDAO.insert(defaultUser, altUser);
        }
        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getDatabase() {
        Log.d("MainActivity", "getDatabase CALLED SUCCESSFULLY");
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentTrackerDAO();
    }

    private void logoutUser() {
        Log.d("MainActivity", "logoutUser CALLED SUCCESSFULLY");
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout");

        alertBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, which) -> {
                    clearUserFromIntent();
                    clearUserFromPref();
                    userId=-1;
                    checkForUser();
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                (dialog, which) -> {
                    //We don't really need to do anything here.
                });

        alertBuilder.create().show();
    }

    private void clearUserFromPref() {
        Log.d("MainActivity", "clearUserFromPref CALLED SUCCESSFULLY");
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromIntent() {
        Log.d("MainActivity", "clearUserFromIntent CALLED SUCCESSFULLY");
        addUserToPreference(-1);
    }

    private void addUserToPreference(int userId) {
        Log.d("MainActivity", "addUserToPreference CALLED SUCCESSFULLY");
        if (preferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void getPrefs() {
        Log.d("MainActivity", "getPrefs CALLED SUCCESSFULLY");
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}