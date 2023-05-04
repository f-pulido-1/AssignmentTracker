package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
import com.example.assignmenttracker.databinding.ActivityMainBinding;

import java.util.List;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 23, 2023
 * Description: Android activity that allows users to navigate to either the login or sign up page.
 */

public class MainActivity extends AppCompatActivity {
    // Fields
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.assignmenttracker.PREFERENCES_KEY";

    ActivityMainBinding binding;
    TextView mainDisplay;
    EditText assignment;
    EditText score;
    Button submit;
    AssignmentTrackerDAO assignmentTrackerDAO;
    List<AssignmentTracker> assignmentTrackerList;
    private int userId = -1;


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

        refreshDisplay();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAssignmentTracker();
                refreshDisplay();
            }
        });
}

    private void submitAssignmentTracker() {
        String assignmentText = assignment.getText().toString();
        double scoreValue = Double.parseDouble(score.getText().toString());
        AssignmentTracker tracker = new AssignmentTracker(assignmentText, scoreValue, userId);
        assignmentTrackerDAO.insert(tracker);
    }

    private void refreshDisplay() {
        assignmentTrackerList = assignmentTrackerDAO.getAssignmentTrackers();
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
        if (userId == -1) {
            List<User> users = assignmentTrackerDAO.getAllUsers();
            if (users.size() <= 0) {
                User defaultUser = new User("testuser1", "testuser1", false);
                User altUser = new User("admin2", "admin2", true);
                assignmentTrackerDAO.insert(defaultUser, altUser);
                userId = defaultUser.getUserId(); // Set the user ID to the ID of the newly inserted user
            } else {
                userId = users.get(0).getUserId(); // Set the user ID to the ID of the first user in the database
            }
            // Store the user ID in the preferences
            SharedPreferences.Editor editor = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE).edit();
            editor.putInt(USER_ID_KEY, userId);
            editor.apply();
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
}