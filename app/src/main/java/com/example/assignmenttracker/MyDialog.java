package com.example.assignmenttracker;
//TODO: Check if I need to make superclass: DialogFragment

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class MyDialog extends DialogFragment {

    public static final String CLASS_ADD_DIALOG = "addClass";
    public static final String STUDENT_ADD_DIALOG = "addStudent";

    private OnClickListener listener;
    public interface OnClickListener{
        void onClick(String text1, String text2);
    }
    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if(getTag().equals(CLASS_ADD_DIALOG))dialog = getAddClassDialog();
        if(getTag().equals(STUDENT_ADD_DIALOG))dialog = getAddStudentDialog();
        return dialog;
    }

    private Dialog getAddStudentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from (getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Student");

        EditText roll_edit = view.findViewById(R.id.edit01);
        EditText name_edit = view.findViewById(R.id.edit02);

        roll_edit.setHint("Roll");
        name_edit.setHint("Name");

        Button cancel = view.findViewById(R.id.cancel_button);
        Button add = view.findViewById(R.id.add_button);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {

            String roll = roll_edit.getText().toString();
            String name = name_edit.getText().toString();
            roll_edit.setText(String.valueOf(Integer.parseInt(roll)+1));

            listener.onClick(roll, name);
        });

        return builder.create();

    }


    private Dialog getAddClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from (getActivity()).inflate(R.layout.dialog, null);
        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add New Class");

        EditText class_edit = view.findViewById(R.id.edit01);
        EditText subject_edit = view.findViewById(R.id.edit02);
        class_edit.setHint("Class Name");
        subject_edit.setHint("Subject Name");

        Button cancel = view.findViewById(R.id.cancel_button);
        Button add = view.findViewById(R.id.add_button);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {

            String className = class_edit.getText().toString();
            String subjectName = subject_edit.getText().toString();

            listener.onClick(className, subjectName);
            dismiss();
        });
        return builder.create();
    }
}
