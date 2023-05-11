package com.example.assignmenttracker.DB;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.assignmenttracker.AssignmentTracker;
import com.example.assignmenttracker.User;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 27, 2023
 * Description: Defines a Room database for an android app that tracks assignments.
 */

// Declaration specifies the entities and version of the database.
@Database(entities = {User.class, AssignmentTracker.class}, version = 4)
public abstract class AppDataBase extends RoomDatabase {
    // Fields - Contains the database name, table names, and instance of the AppDataBase
    public static final String DATABASE_NAME = "AssignmentTracker.db";
    public static final String ASSIGNMENTTRACKER_TABLE = "assignmenttracker_table";
    public static final String USER_TABLE = "user_table";
    private static volatile AppDataBase instance; // Volatile keyword ensures that the instance variable
                                                    // is always up to date
    private static final Object LOCK = new Object(); // LOCK is used for thread safety

    // Methods - Used to define the DAO for the entities. It provides methods
    // for accessing and manipulating the entities.
    public abstract AssignmentTrackerDAO AssignmentTrackerDAO();
//    public abstract UserDAO UserDAO();
    // getInstance is used to get the instance of the AppDataBase class. Follows singleton pattern to
    // ensure that only one instance of the database is created throughout lifetime of app.
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
}
