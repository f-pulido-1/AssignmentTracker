package com.example.assignmenttracker.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.assignmenttracker.AssignmentTracker;

import java.util.List;

@Dao
public interface AssignmentTrackerDAO {
    @Insert
    void insert(AssignmentTracker... assignmentTrackers);

    @Update
    void update(AssignmentTracker... assignmentTrackers);

    @Delete
    void delete(AssignmentTracker assignmentTracker);

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTTRACKER_TABLE)
    List<AssignmentTracker> getAssignmentTrackers();

    @Query("SELECT * FROM " + AppDataBase.ASSIGNMENTTRACKER_TABLE + " WHERE trackerId = :trackerId")
    List<AssignmentTracker> getTrackerById(int trackerId);

}
