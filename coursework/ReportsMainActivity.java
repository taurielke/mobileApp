package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;


public class ReportsMainActivity extends AppCompatActivity {

    String login = "";
    String role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_main);

        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        role = sPref.getString("role", "");

        Button teachers = findViewById(R.id.reportsButtonTeachers);
        Button disciplines = findViewById(R.id.reportsButtonDisciplines);
        Button allUsers = findViewById(R.id.reportsButtonAllUsers);

       if (!Objects.equals(role, "admin")){
          allUsers.setVisibility(View.INVISIBLE);
       }

        allUsers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportDeaneriesActivity.class);
            startActivity(intent);
        });
        disciplines.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportDisciplinesLearningPlansActivity.class);
            startActivity(intent);
        });
        teachers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReportDisciplinesTeachersActivity.class);
            startActivity(intent);
        });
    }
}