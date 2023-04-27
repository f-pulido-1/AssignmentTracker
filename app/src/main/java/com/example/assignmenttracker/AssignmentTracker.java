package com.example.assignmenttracker;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.example.assignmenttracker.DB.AppDataBase;

@Entity(tableName = AppDataBase.ASSIGNMENTTRACKER_TABLE)
public class AssignmentTracker {
    @PrimaryKey(autoGenerate = true)
    private int trackerId;

    // Fields
    private String assignment;
    private double score;


    // Constructor(s)
    public AssignmentTracker(String assignment, double score) {
        this.assignment = assignment;
        this.score = score;
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

    // Other
    @Override
    public String toString() {
        return "AssignmentTracker{" +
                "trackerId=" + trackerId +
                ", assignment='" + assignment + '\'' +
                ", score=" + score +
                '}';
    }
}
