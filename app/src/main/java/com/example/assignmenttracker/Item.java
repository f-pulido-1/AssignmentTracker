package com.example.assignmenttracker;

public class Item {
    // Field(s)
    String assignment;
    String subject;
    String date;

    // Constructor(s)
    public Item(String assignment, String subject, String date) {
        this.assignment = assignment;
        this.subject = subject;
        this.date = date;
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
}