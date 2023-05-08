package com.example.assignmenttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.assignmenttracker.DB.AppDataBase;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 27, 2023
 * Description: Represents an assignment tracker object.
 */

// @Entity tells Room to treat this class as an entity and create a database table based on it.
    // It includes parameter tableName that specifies the name of the table to be created.
@Entity(tableName = AppDataBase.ASSIGNMENTTRACKER_TABLE)
public class AssignmentTracker {
    // Fields
    @PrimaryKey(autoGenerate = true) // @PrimaryKey is used to mark trackerId as the primary key of table.
    private int trackerId;

    private String assignment;
    private double score;
    private int userId;


    // Constructor(s)
    public AssignmentTracker(String assignment, double score, int userId) {
        this.assignment = assignment;
        this.score = score;
        this.userId = userId;
    }

    // Getters & Setters
    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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
        return "AssignmentTracker{" +
                "trackerId=" + trackerId +
                ", assignment='" + assignment + '\'' +
                ", score=" + score +
                ", userId=" + userId +
                '}';
    }
}
