package com.example.assignmenttracker.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.assignmenttracker.AssignmentTracker;

@Database(entities = {AssignmentTracker.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "AssignmentTracker.db";
    public static final String ASSIGNMENTTRACKER_TABLE = "assignmenttracker_table";

    private static volatile AppDataBase instance;

    private static final Object LOCK = new Object();

    public abstract AssignmentTrackerDAO AssignmentTrackerDAO();
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
