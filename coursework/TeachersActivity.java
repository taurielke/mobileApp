package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.coursework.deanery.Teacher;
import com.example.coursework.deanery.TeachersData;

public class TeachersActivity extends AppCompatActivity {

    String login = "";
    TeachersData teachersData;
    ArrayAdapter<Teacher> adapter;
    ListView listViewTeachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        teachersData = new TeachersData(this, login);

        listViewTeachers = findViewById(R.id.teachersListView);
        Button add = findViewById(R.id.teachersButtonAdd);
        Button upd = findViewById(R.id.teachersButtonChange);
        Button del = findViewById(R.id.teachersButtonDelete);

        adapter = new ArrayAdapter<Teacher>(this, android.R.layout.simple_list_item_single_choice,
                teachersData.findAllTeachers(login));
        listViewTeachers.setAdapter(adapter);
        listViewTeachers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, TeacherActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int teacher = -1;
            SparseBooleanArray sparseBooleanArray = listViewTeachers.getCheckedItemPositions();
            for (int i = 0; i < listViewTeachers.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    teacher = adapter.getItem(i).getId();
                }
            }
            if (teacher == -1){
                return;
            }
            Intent intent = new Intent(this, TeacherActivity.class);
            intent.putExtra("Id", teacher);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewTeachers.clearChoices();
        });
        del.setOnClickListener(v -> {
            int teacher = -1;
            SparseBooleanArray sparseBooleanArray = listViewTeachers.getCheckedItemPositions();
            for (int i = 0; i < listViewTeachers.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    teacher = adapter.getItem(i).getId();
                }
            }
            if (teacher != -1) {
                int finalTeacher = teacher;
                teachersData.deleteTeacher(finalTeacher, login);
                listViewTeachers.clearChoices();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }
}