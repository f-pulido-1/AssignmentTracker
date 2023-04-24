package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;

@Entity(tableName = AppDataBase.LANDINGPAGE_TABLE)
public class LandingPage extends AppCompatActivity {
    @PrimaryKey(autoGenerate = true)
    private int logId;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }
}
