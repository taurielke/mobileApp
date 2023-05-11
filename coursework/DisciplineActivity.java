package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.coursework.deanery.LearningPlan;
import com.example.coursework.deanery.LearningPlansData;
import com.example.coursework.deanery.Discipline;
import com.example.coursework.deanery.DisciplinesData;
import com.example.coursework.deanery.Teacher;
import com.example.coursework.deanery.TeachersData;

public class DisciplineActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    DisciplinesData disciplinesData;
    LearningPlansData learningPlansData;
    TeachersData teachersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline);

        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        disciplinesData = new DisciplinesData(this, login);
        learningPlansData = new LearningPlansData(this, login);
        teachersData = new TeachersData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button btnSave = findViewById(R.id.disciplineButtonSave);
        TextView textViewName = findViewById(R.id.disciplineEditTextName);
        Spinner spinnerLP = findViewById(R.id.learningPlanSpinner);
        Spinner spinnerT = findViewById(R.id.teacherSpinner);

        ArrayAdapter<LearningPlan> adapterLP = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, learningPlansData.findAllLearningPlans(login));
        ArrayAdapter<Teacher> adapterT = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, teachersData.findAllTeachers(login));
        adapterLP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLP.setAdapter(adapterLP);
        spinnerT.setAdapter(adapterT);

        if (id != -1){
            Discipline discipline = disciplinesData.getDiscipline(id, login);
            if (discipline != null){
                textViewName.setText(discipline.getNameDiscipline());
                for (int i = 0; i < adapterLP.getCount(); ++i){
                    if(adapterLP.getItem(i).getId() == discipline.getLearningPlan_id()){
                        spinnerLP.setSelection(i);
                        break;
                    }
                }
                for (int i = 0; i < adapterT.getCount(); ++i){
                    if(adapterT.getItem(i).getId() == discipline.getTeacher_id()){
                        spinnerT.setSelection(i);
                        break;
                    }
                }
            }
        }

        btnSave.setOnClickListener(v -> {
            if (textViewName.getText().toString().equals("")){
                Toast.makeText(this, "Заполните название дисциплины",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String name = textViewName.getText().toString();
            if (id != -1){
                disciplinesData.updateDiscipline(id, name, login,
                        adapterLP.getItem((int) spinnerLP.getSelectedItemId()).getId(),
                        adapterT.getItem((int) spinnerT.getSelectedItemId()).getId());
            }
            else {
                disciplinesData.addDiscipline(name, login,
                        adapterLP.getItem((int) spinnerLP.getSelectedItemId()).getId(),
                        adapterT.getItem((int) spinnerT.getSelectedItemId()).getId());
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}