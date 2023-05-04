//package com.example.assignmenttracker;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.EditText;
//import androidx.room.Room;
//import com.example.assignmenttracker.DB.AppDataBase;
//import com.example.assignmenttracker.DB.AssignmentTrackerDAO;
//import com.example.assignmenttracker.databinding.LandingPageBinding;
//import java.util.List;
//
///**
// * @author Carlos Santiago, Fernando A. Pulido
// * @since April 23, 2023
// * Description: Android activity class that represents the landing page of an Assignment Tracker app.
// */
//
//public class LandingPage extends AppCompatActivity {
//    // Fields
//    LandingPageBinding binding;
//    TextView mainDisplay;
//    EditText assignment;
//    EditText score;
//    Button submit;
//    AssignmentTrackerDAO assignmentTrackerDAO;
//    List<AssignmentTracker> assignmentTrackerList;
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) { // called when the activity is created
//        super.onCreate(savedInstanceState);
//
//        // LandingPageBinding class is used to inflate the layout of the landing page
//        binding = LandingPageBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.landing_page);
//
////        Button backButton = findViewById(R.id.backButton);
//        ImageView imageView = findViewById(R.id.imageView);
//        TextView landingMessage = (TextView) findViewById(R.id.landingMessage);
//
//        String username = getIntent().getStringExtra("username");
//        binding.landingMessage.setText("Hello, " + username);
//
////        backButton.setOnClickListener(view -> finish());
//
//        setContentView(binding.getRoot());
//
//        // Binding
//        mainDisplay = binding.mainAssignmentTrackerDisplay;
//        assignment = binding.mainAssignmentEditText;
//        score = binding.mainScoreEditText;
//        submit = binding.mainSubmitButton;
//
//        assignmentTrackerDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
//                .allowMainThreadQueries()
//                .build()
//                .AssignmentTrackerDAO();
//
//        refreshDisplay();
//
//        submit.setOnClickListener(view -> {
//            submitAssignmentTracker();
//            refreshDisplay();
//        });
//    } // End of onCreate
//
//    // When a user clicks the submit button, the method creates a new Assignment Tracker object and
//    // inserts it into the database using the assignmentTrackerDAO object.
//    private void submitAssignmentTracker() {
//        String assignment1 = assignment.getText().toString();
//        double score2 = Double.parseDouble(score.getText().toString());
//        AssignmentTracker tracker = new AssignmentTracker(assignment1, score2);
//
//        assignmentTrackerDAO.insert(tracker);
//    }
//
//    // Used to display the current contents of the database in the mainDisplay field of the landing page.
//    private void refreshDisplay() {
//        assignmentTrackerList = assignmentTrackerDAO.getAssignmentTrackers();
//        if(!assignmentTrackerList.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            for(AssignmentTracker tracker : assignmentTrackerList) {
//                sb.append(tracker.toString());
//            }
//            mainDisplay.setText(sb.toString());
//        } else {
//            mainDisplay.setText(R.string.nothing_in_tracker_message);
//        }
//    }
//}
