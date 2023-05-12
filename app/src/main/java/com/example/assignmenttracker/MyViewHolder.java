package com.example.assignmenttracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView assignmentView, subjectView, dateView;
    public MyViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
        super(itemView);
        assignmentView = itemView.findViewById(R.id.assignment);
        subjectView = itemView.findViewById(R.id.subject);
        dateView = itemView.findViewById(R.id.date);
    }
}
