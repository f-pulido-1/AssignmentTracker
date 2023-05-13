package com.example.assignmenttracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.assignmenttracker.DB.AppDataBase;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 29, 2023
 * Description: Represents a user of the app.
 */

@Entity(tableName = AppDataBase.CLASSROOMS_TABLE)
public class Classrooms {
//    Fields
    @PrimaryKey(autoGenerate = true)
    private int classId;

    private String className;
    private String subjectName;

//    Constructor
    public Classrooms(int classId, String className, String subjectName) {
        this.classId = classId;
        this.className = className;
        this.subjectName = subjectName;
    }

//    Getters and Setters

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Classrooms{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

}
