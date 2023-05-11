package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainDeaneryActivity extends AppCompatActivity {

    String login = "";
    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_deanery);


        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        role = sPref.getString("role", "");


        Button teachers = findViewById(R.id.mainButtonTeacher);
        Button learningPlans = findViewById(R.id.mainButtonLearningPlan);
        Button disciplines = findViewById(R.id.mainButtonDiscipline);
        Button reports = findViewById(R.id.mainButtonReports);

        teachers.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeachersActivity.class);
            startActivity(intent);
        });
        learningPlans.setOnClickListener(v -> {
            Intent intent = new Intent(this, LearningPlansActivity.class);
            startActivity(intent);
        });
        disciplines.setOnClickListener(v -> {
            Intent intent = new Intent(this, DisciplinesActivity.class);
            startActivity(intent);
        });
        reports.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportsMainActivity.class);
            startActivity(intent);
        });
    }
}