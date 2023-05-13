package com.example.assignmenttracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignmenttracker.AssignmentTracker;
import com.example.assignmenttracker.Attendance;
import com.example.assignmenttracker.Classrooms;
import com.example.assignmenttracker.Status;
import com.example.assignmenttracker.Student;
import com.example.assignmenttracker.User;

import java.util.List;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 27, 2023
 * Description: Defines a DAO interface for accessing the AppDataBase.
 */

@Dao
public interface AssignmentTrackerDAO {
    // Methods - Take on one or more instances of the AssignmentTracker class as parameters
    // and performs appropriate database operations on them.
    @Insert
    void insert(AssignmentTracker... assignmentTrackers);

    @Update
    void update(AssignmentTracker... assignmentTrackers);

    @Delete
    void delete(AssignmentTracker assignmentTracker);

    // Custom methods using the @Query annotation
    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTTRACKER_TABLE)
    List<AssignmentTracker> getAssignmentTrackers(); // returns list of AssignmentTracker objects
    // in the database.

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTTRACKER_TABLE + " WHERE trackerId = :trackerId")
    AssignmentTracker getTrackerById(int trackerId); // takes integer parameter and returns

    // list of AssignmentTracker objects
    // with matching trackerId field.
    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTTRACKER_TABLE + " WHERE userId = :userId")
    List<AssignmentTracker> getTrackersByUserId(int userId);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    // Custom methods using the @Query annotation
    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE userId = :userId")
    User getUserByUserId(int userId);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);


    @Insert
    void insert(Attendance...attendance);

    @Update
    void update(Attendance...attendance);
    @Delete
    void delete(Attendance attendance);

    @Query("SELECT * FROM " + AppDataBase.ATTENDANCE_TABLE + " WHERE attendanceId = :attendanceId")
    Attendance getAttendanceByAttendanceId(int attendanceId);


//    CLASSROOMS DATABASE DAO
    @Insert
    void insert(Classrooms... classes);

    @Update
    void update(Classrooms... classes);

    @Delete
    void delete(Classrooms classes);

    @Query("SELECT * FROM " + AppDataBase.CLASSROOMS_TABLE + " WHERE classId = :classId")
    Classrooms getRoomByClassId(int classId);

    @Query("SELECT * FROM " + AppDataBase.CLASSROOMS_TABLE + " WHERE className = :className")
    List<Classrooms> getRoomsByClassName(String className);

    @Query("SELECT * FROM " + AppDataBase.CLASSROOMS_TABLE + " WHERE subjectName = :subjectName")
    List<Classrooms> getRoomsBySubjectName(String subjectName);


//    STUDENTS DATABASE DAO
    @Insert
    void insert(Student... students);

    @Update
    void update(Student... students);

    @Delete
    void delete(Student student);

    @Query("SELECT * FROM " + AppDataBase.STUDENTS_TABLE + " WHERE studentId = :studentId")
    Student getStudentById(int studentId);

    @Query("SELECT * FROM " + AppDataBase.STUDENTS_TABLE + " WHERE classId = :classId")
    List<Student> getStudentsByClassId(int classId);

    @Query("SELECT * FROM " + AppDataBase.STUDENTS_TABLE + " WHERE roll = :roll")
    List<Student> getStudentsByRoll(String roll);

    @Query("SELECT * FROM " + AppDataBase.STUDENTS_TABLE + " WHERE studentName = :studentName")
    List<Student> getStudentsByName(String studentName);


//    STATUS TABLE
    @Insert
    void insert(Status... statuses);

    @Update
    void update(Status... statuses);

    @Delete
    void delete(Status status);

    @Query("SELECT * FROM " + AppDataBase.STATUS_TABLE + " WHERE studentId = :studentId")
    Status getStatusByStudentId(int studentId);

    @Query("SELECT * FROM " + AppDataBase.STATUS_TABLE + " WHERE date = :date")
    List<Status> getStatusesByDate(String date);

    @Query("SELECT * FROM " + AppDataBase.STATUS_TABLE + " WHERE status = :status")
    List<Status> getStatusesByStatus(String status);

}
