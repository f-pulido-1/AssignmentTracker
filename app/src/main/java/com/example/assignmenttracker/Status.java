package com.example.assignmenttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;

import java.util.Date;

@Entity(tableName = AppDataBase.STATUS_TABLE)
public class Status {
//    Fields
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int studentId;
    private Date date;
    private Character status;

//    Constructor

    public Status(int id, int studentId, Date date, Character status) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.status = status;
    }

//    Setters and Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
