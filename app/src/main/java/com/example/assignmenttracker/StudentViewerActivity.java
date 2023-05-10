package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

    private ActivityStudentViewerBinding binding;
    private TextView mainStudentViewerTextView;
    private Button studentViewerBackButton;
    private AssignmentTrackerDAO assignmentTrackerDAO;
    private List<User> userList;
    private User adminUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_viewer);

        getDatabase();

        binding = ActivityStudentViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainStudentViewerTextView = binding.mainStudentViewerTextView;
        studentViewerBackButton = binding.studentViewerBackButton;

        refreshDisplay();

        studentViewerBackButton.setOnClickListener(view -> {
            Log.d("StudentViewerActivity", "STARTING AdminMainActivity");
            Log.d("StudentViewerActivity", "Username: " + adminUser.getUsername() + " First: " + adminUser.getFirstName());
            Intent intent = new Intent(StudentViewerActivity.this, AdminMainActivity.class);
            //TODO: fix error where when going back and forth between adminMain and studentviewer causes app to crash
            startActivity(intent);
        });
    }

    private void getDatabase() {
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentTrackerDAO();
    }

    private void refreshDisplay() {
        userList = assignmentTrackerDAO.getAllUsers();
        if(!userList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for(User user : userList) {
                sb.append(user.toString());
            }
            mainStudentViewerTextView.setText(sb.toString());
        } else {
            mainStudentViewerTextView.setText("No users yet.");
        }
    }
}