package com.example.assignmenttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;

@Entity(tableName = AppDataBase.STUDENTS_TABLE)
public class Student {
//    Fields
    @PrimaryKey(autoGenerate = true)
    private int studentId;

    private int classId;
    private int roll;
    private String studentName;

//    Constructor
    public Student(int studentId, int classId, int roll, String studentName) {
        this.studentId = studentId;
        this.classId = classId;
        this.roll = roll;
        this.studentName = studentName;
    }

//    Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "Students{" +
                "studentId=" + studentId +
                ", classId=" + classId +
                ", roll=" + roll +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
