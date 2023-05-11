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

import com.example.coursework.deanery.LearningPlan;
import com.example.coursework.deanery.LearningPlansData;

public class LearningPlansActivity extends AppCompatActivity {
    String login = "";
    LearningPlansData learningPlansData;
    ArrayAdapter<LearningPlan> adapter;
    ListView listViewLearningPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_plans);

        SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        login = sPref.getString("login", "");
        learningPlansData = new LearningPlansData(this, login);

        listViewLearningPlans = findViewById(R.id.learningPlansListView);
        Button add = findViewById(R.id.learningPlansButtonAdd);
        Button upd = findViewById(R.id.learningPlansButtonChange);
        Button del = findViewById(R.id.learningPlansButtonDelete);

        adapter = new ArrayAdapter<LearningPlan>(this, android.R.layout.simple_list_item_single_choice,
                learningPlansData.findAllLearningPlans(login));
        listViewLearningPlans.setAdapter(adapter);
        listViewLearningPlans.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();

        add.setOnClickListener(v -> {
            Intent intent = new Intent(this, LearningPlanActivity.class);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
        });
        upd.setOnClickListener(v -> {
            int learningPlan = -1;
            SparseBooleanArray sparseBooleanArray = listViewLearningPlans.getCheckedItemPositions();
            for (int i = 0; i < listViewLearningPlans.getCount(); ++i){
                if(sparseBooleanArray.get(i) == true){
                    learningPlan = adapter.getItem(i).getId();
                }
            }
            if (learningPlan == -1){
                return;
            }
            Intent intent = new Intent(this, LearningPlanActivity.class);
            intent.putExtra("Id", learningPlan);
            startActivityForResult(intent, 99);
            adapter.notifyDataSetChanged();
            listViewLearningPlans.clearChoices();
        });
        del.setOnClickListener(v -> {
            int learningPlan = -1;
            SparseBooleanArray sparseBooleanArray = listViewLearningPlans.getCheckedItemPositions();
            for (int i = 0; i < listViewLearningPlans.getCount(); ++i) {
                if (sparseBooleanArray.get(i) == true) {
                    learningPlan = adapter.getItem(i).getId();
                }
            }
            if (learningPlan != -1) {
                int finalLearningPlan = learningPlan;
                learningPlansData.deleteLearningPlan(finalLearningPlan, login);
                listViewLearningPlans.clearChoices();
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