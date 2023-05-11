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

import com.example.coursework.deanery.Discipline;
import com.example.coursework.deanery.DisciplinesData;

public class DisciplinesActivity extends AppCompatActivity {

    String login = "";
    DisciplinesData disciplinesData;
    ArrayAdapter<Discipline> adapter;
    ListView listViewDisciplines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplines);

        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        disciplinesData = new DisciplinesData(this, login);

        listViewDisciplines = findViewById(R.id.disciplinesListView);
        Button btnAdd = findViewById(R.id.disciplinesButtonAdd);
        Button btnUpd = findViewById(R.id.disciplinesButtonChange);
        Button btnDel = findViewById(R.id.disciplinesButtonDelete);

        adapter = new ArrayAdapter<Discipline>(this,
                android.R.layout.simple_list_item_single_choice,
                disciplinesData.findAllDisciplines(login));
        listViewDisciplines.setAdapter(adapter);
        listViewDisciplines.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, DisciplineActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });

        btnUpd.setOnClickListener(v -> {
            int discipline = -1;
            SparseBooleanArray sparseBooleanArray = listViewDisciplines.getCheckedItemPositions();
            for (int i = 0; i < listViewDisciplines.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    discipline = adapter.getItem(i).getId();
                }
            }
            if (discipline == -1){
                return;
            }
            Intent intent = new Intent(this, DisciplineActivity.class);
            intent.putExtra("Id", discipline);
            startActivityForResult(intent, 99); ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            adapter.notifyDataSetChanged();
            listViewDisciplines.clearChoices();
        });

        btnDel.setOnClickListener(v -> {
            int discipline = -1;
            SparseBooleanArray sparseBooleanArray = listViewDisciplines.getCheckedItemPositions();
            for (int i = 0; i < listViewDisciplines.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    discipline = adapter.getItem(i).getId();
                }
            }
            if (discipline != -1) {
                int finalDiscipline = discipline;
                disciplinesData.deleteDiscipline(finalDiscipline, login);
                listViewDisciplines.clearChoices();
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