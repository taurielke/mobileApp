package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.deanery.LearningPlan;
import com.example.coursework.deanery.LearningPlansData;

public class LearningPlanActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    LearningPlansData learningPlansData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_plan);

        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        learningPlansData = new LearningPlansData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button btnSave = findViewById(R.id.learningPlanButtonSave);
        TextView textViewName = findViewById(R.id.learningPlanEditTextName);
        TextView textViewSemester = findViewById(R.id.learningPlanEditTextSemester);

        if (id != -1){
            LearningPlan learningPlan = learningPlansData.getLearningPlan(id, login);
            if (learningPlan != null){
                textViewName.setText(learningPlan.getNameLearningPlan());
                textViewSemester.setText(String.valueOf(learningPlan.getSemester()));
            }
        }

        btnSave.setOnClickListener(v -> {
            if (textViewName.getText().toString().equals("")){
                Toast.makeText(this, "Заполните название плана обучения",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewSemester.getText().toString().equals("")){
                Toast.makeText(this, "Заполните номер семестра",
                        Toast.LENGTH_LONG).show();
                return;
            }
            int semester = Integer.parseInt(textViewSemester.getText().toString());
            String name = textViewName.getText().toString();
            if (id != -1){
                learningPlansData.updateLearningPlan(id, name, semester, login);
            }
            else {
                learningPlansData.addLearningPlan(name, semester, login);
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}