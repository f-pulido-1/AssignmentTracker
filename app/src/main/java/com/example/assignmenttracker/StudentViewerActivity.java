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
import android.widget.TextView;
import android.widget.Toast;

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
    private int adminUserId = -1;
    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";

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