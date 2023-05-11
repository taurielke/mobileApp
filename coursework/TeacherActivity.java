package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.deanery.LearningPlan;
import com.example.coursework.deanery.LearningPlansData;
import com.example.coursework.deanery.Teacher;
import com.example.coursework.deanery.TeachersData;

public class TeacherActivity extends AppCompatActivity {

    String login = "";
    String role = "";
    int id = -1;
    TeachersData teachersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        teachersData = new TeachersData(this, login);
        role = sPref.getString("role", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("Id", -1);

        Button save = findViewById(R.id.teacherButtonSave);
        TextView textViewName = findViewById(R.id.teacherEditName);
        TextView textViewSpec = findViewById(R.id.teacherEditSpec);

        if (id != -1){
            Teacher teacher = teachersData.getTeacher(id, login);
            if (teacher != null){
                textViewName.setText(teacher.getName());
                textViewSpec.setText(teacher.getSpec());
            }
        }

        save.setOnClickListener(v -> {
            if (textViewName.getText().equals("")){
                Toast.makeText(this, "Заполните ФИО преподавателя",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if (textViewSpec.getText().equals("")){
                Toast.makeText(this, "Заполните специализацию",
                        Toast.LENGTH_LONG).show();
                return;
            }
            String name = textViewName.getText().toString();
            String spec = textViewSpec.getText().toString();
            if (id != -1){
                teachersData.updateTeacher(id, name, login, spec);
            }
            else {
                teachersData.addTeacher(name, login, spec);
            }
            Intent data = new Intent();
            setResult(Activity.RESULT_OK, data);
            finish();
        });
    }
}