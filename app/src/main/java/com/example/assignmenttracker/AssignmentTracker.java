package com.example.assignmenttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 27, 2023
 * Description: Represents an assignment tracker object.
 */

@Entity(tableName = AppDataBase.ASSIGNMENTTRACKER_TABLE)
public class AssignmentTracker {
    // Field(s)
    @PrimaryKey(autoGenerate = true)
    // @PrimaryKey is used to mark trackerId as the primary key of table.
    private int trackerId;

    private String assignment;
    private String subject;
    private String date;
    private int userId;

    // Constructor(s)
    public AssignmentTracker(String assignment, String subject, String date, int userId) {
        this.assignment = assignment;
        this.subject = subject;
        this.date = date;
        this.userId = userId;
    }

    // Getters & Setters
    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Assignment:" + assignment + "\nSubject: " + subject + "\nDue: " + date + "\n\n";
    }
}
