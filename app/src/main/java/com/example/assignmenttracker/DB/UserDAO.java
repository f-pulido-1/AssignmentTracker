package com.example.assignmenttracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.assignmenttracker.User;
import java.util.List;

/**
 * @author Carlos Santiago, Fernando A. Pulido
 * @since April 29, 2023
 * Description: Defines a DAO interface for accessing the AppDataBase.
 */

@Dao
public interface UserDAO {
    // Methods - Take on one or more instances of the AssignmentTracker class as parameters
            // and performs appropriate database operations on them.
    @Insert
    void insert(User... users);

    @Update
    void update(User...users);

    @Delete
    void delete(User user);

    // Custom methods using the @Query annotation
    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getUsers(); // returns list of all User objects in the database

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE userId = :userId")
    List<User> getUserById(int userId); // takes an integer parameter and returns a list of all
                                            // User objects with a matching userId field
}
