package com.example.assignmenttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 27, 2023
 * Description: Represents an attendance tracker object.
 */
@Entity(tableName = AppDataBase.ATTENDANCE_TABLE)
public class Attendance {
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
