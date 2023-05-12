package com.example.assignmenttracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 27, 2023
 * Description: Represents an attendance tracker object.
 */
@Entity(tableName = AppDataBase.ATTENDANCE_TABLE)
public class Attendance extends AppCompatActivity {

    FloatingActionButton fab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(v -> showDialog());
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = LayoutInflater.from(this).inflate(R.layout.class_dialog, null);
        builder.setView(view);

        builder.create().show();
    }

//    Fields
    @PrimaryKey(autoGenerate = true)
    private int userId;
    private String className;
    private boolean isPresent;

//    Constructor
    public Attendance(String className, int userId, boolean isPresent){
        this.className = className;
        this.userId = userId;
        this.isPresent = isPresent;
    }

//    Getters and Setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "userId = " + userId + " was present for " +
                "className='" + className + '\'' +
                ", isPresent=" + isPresent +
                '}';
    }

}
