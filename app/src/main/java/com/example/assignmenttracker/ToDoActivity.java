package com.example.assignmenttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private int userId = -1;
    private List<AssignmentTracker> trackers;
    private AssignmentTrackerDAO assignmentTrackerDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        getDatabase();

        Log.d("ToDoActivity", "onCreate CALLED SUCCESSFULLY");
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
        Log.d("ToDoActivity", "userId= " + userId);
        trackers = assignmentTrackerDAO.getTrackersByUserId(userId);
        Log.d("ToDoActivity", "trackers= " + trackers);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < trackers.size(); i++) {
            items.add(new Item(trackers.get(i).getAssignment(), trackers.get(i).getSubject(), trackers.get(i).getDate()));
        }



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));
    }

    private void getDatabase() {
        Log.d("ToDoActivity", "getDatabase CALLED SUCCESSFULLY");
        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .AssignmentTrackerDAO();
    }

}