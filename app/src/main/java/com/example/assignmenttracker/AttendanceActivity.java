package com.example.assignmenttracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {

    FloatingActionButton fab;

    RecyclerView recyclerViewClasses;
    ClassAdapter classAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ClassItem> classItems = new ArrayList<>();

    EditText class_edit;
    EditText subject_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(v -> showDialog());

        recyclerViewClasses = findViewById(R.id.recyclerViewClasses);
        recyclerViewClasses.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewClasses.setLayoutManager(layoutManager);
        classAdapter = new ClassAdapter(this, classItems);
        recyclerViewClasses.setAdapter(classAdapter);

    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from (this).inflate(R.layout.dialog, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        class_edit = view.findViewById(R.id.edit01);
        subject_edit = view.findViewById(R.id.edit02);

        Button cancel = view.findViewById(R.id.cancel_button);
        Button add = view.findViewById(R.id.add_button);

        cancel.setOnClickListener(v -> dialog.dismiss());
        add.setOnClickListener(v -> {
            addClass();
            dialog.dismiss();
        });


    }

    private void addClass() {
        String className = class_edit.getText().toString();
        String subjectName = subject_edit.getText().toString();
        classItems.add(new ClassItem(className, subjectName));
        classAdapter.notifyDataSetChanged();
    }

}
