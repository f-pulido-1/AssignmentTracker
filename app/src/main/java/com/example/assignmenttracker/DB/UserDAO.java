package com.example.assignmenttracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignmenttracker.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User... users);

    @Update
    void update(User...users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE + " WHERE userId = :userId")
    List<User> getUserById(int userId);
}
