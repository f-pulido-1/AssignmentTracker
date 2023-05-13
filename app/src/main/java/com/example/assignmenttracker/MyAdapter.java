package com.example.assignmenttracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.assignmenttracker.DB.AppDataBase;
import com.example.assignmenttracker.DB.AssignmentTrackerDAO;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final String USER_ID_KEY = "com.example.assignmenttracker.userIdKey";
    private static final String TRACKER_ID_KEY = "com.example.assignmenttracker.trackerIdKey";
    private final List<AssignmentTracker> trackers;
    Context context;
    List<Item> items;
    private int userId;
    private AssignmentTrackerDAO assignmentTrackerDAO;

    public MyAdapter(Context context, List<Item> items, List<AssignmentTracker> trackers, int userId) {
        this.context = context;
        this.items = items;
        this.trackers = trackers;
        this.userId = userId;
        this.assignmentTrackerDAO = Room.databaseBuilder(context, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().build().AssignmentTrackerDAO();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("MyAdapter", "onBindViewHolder WORKING");
        holder.assignmentView.setText(items.get(position).getAssignment());
        holder.subjectView.setText(items.get(position).getSubject());
        holder.dateView.setText(items.get(position).getDate());

        int currentTrackerId = trackers.get(position).getTrackerId();

        holder.toDoEditButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EditAssignmentActivity.class);
            Log.d("MyAdapter", "EDIT BUTTON CLICKED, trackerId=" + currentTrackerId);
            intent.putExtra(TRACKER_ID_KEY, currentTrackerId);
            intent.putExtra(USER_ID_KEY, userId);
            view.getContext().startActivity(intent);
        });

        holder.toDoDoneButton.setOnClickListener(view -> {
            // Handle done button click
            AssignmentTracker tracker = assignmentTrackerDAO.getTrackerById(currentTrackerId);
            assignmentTrackerDAO.delete(tracker);
            Toast.makeText(view.getContext(), "Assignment completed!", Toast.LENGTH_SHORT).show();
            Intent intent = ToDoActivity.intentFactory(view.getContext(), userId);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
