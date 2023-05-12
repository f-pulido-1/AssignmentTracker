package com.example.assignmenttracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignmenttracker.AssignmentTracker;
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
}
