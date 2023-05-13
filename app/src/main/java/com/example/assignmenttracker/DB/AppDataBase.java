package com.example.assignmenttracker.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.assignmenttracker.AssignmentTracker;
import com.example.assignmenttracker.Attendance;
import com.example.assignmenttracker.Classrooms;
import com.example.assignmenttracker.Status;
import com.example.assignmenttracker.Student;
import com.example.assignmenttracker.User;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 27, 2023
 * Description: Defines a Room database for an android app that tracks assignments.
 */

// Declaration specifies the entities and version of the database.
@Database(entities = {User.class, AssignmentTracker.class,
                        Attendance.class, Classrooms.class,
                        Student.class, Status.class}, version = 10)
public abstract class AppDataBase extends RoomDatabase {
    // Fields - Contains the database name, table names, and instance of the AppDataBase
    public static final String DATABASE_NAME = "AssignmentTracker.db";
    public static final String ASSIGNMENTTRACKER_TABLE = "assignmenttracker_table";
    public static final String USER_TABLE = "user_table";

    public static final String ATTENDANCE_TABLE = "attendance_table";
    public static final String CLASSROOMS_TABLE = "classes_table";
    public static final String STUDENTS_TABLE = "students_table";
    public static final String STATUS_TABLE = "status_table";
    // is always up to date
    private static final Object LOCK = new Object(); // LOCK is used for thread safety
    private static volatile AppDataBase instance; // Volatile keyword ensures that the instance variable

    public static AppDataBase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }

    // Methods - Used to define the DAO for the entities. It provides methods for accessing and manipulating the entities.
    public abstract AssignmentTrackerDAO AssignmentTrackerDAO();
}
