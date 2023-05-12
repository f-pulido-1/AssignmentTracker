package com.example.assignmenttracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignmenttracker.DB.AppDataBase;

public class AttendanceActivity extends AppCompatActivity {

    private EditText className, subjectName;
    private Button addAttendanceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_dialog);

        className = findViewById(R.id.class_edit);
        subjectName = findViewById(R.id.subject_edit);
        addAttendanceButton = findViewById(R.id.add_button);

        addAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markAttendance();
            }
        });
    }

    private void markAttendance() {
        String className = this.className.getText().toString();
        String userIdText = subjectName.getText().toString();

        if (className.isEmpty() || userIdText.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = Integer.parseInt(userIdText);
        boolean isPresent = true; // You can modify this based on your logic

        Attendance attendance = new Attendance(className, userId, isPresent);
        saveAttendance(attendance);
    }

    private void saveAttendance(Attendance attendance) {
        AppDataBase.getInstance(this).AssignmentTrackerDAO().insert(attendance);
        Toast.makeText(this, "Attendance saved", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    private void clearFields() {
        className.setText("");
        subjectName.setText("");
    }
}
