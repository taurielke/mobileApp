package com.example.coursework.deanery.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.coursework.deanery.LearningPlan;
import com.example.coursework.deanery.Deanery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LearningPlansDB {
    private LearningPlansDB.DBHelper dbHelper;
    private DisciplinesDB disciplinesDB;

    public LearningPlansDB(Context context){
        dbHelper = new LearningPlansDB.DBHelper(context);
        disciplinesDB = new DisciplinesDB(context);
    }

    public LearningPlan get(LearningPlan learningPlan){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("learningPlans", null, "id = ?",
                new String[] {String.valueOf(learningPlan.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameLearningPlan");
            int semesterIndex = c.getColumnIndex("semester");
            int deaneryLoginIndex = c.getColumnIndex("deaneryLogin");
            LearningPlan lp = new LearningPlan();
            lp.setId(c.getInt(idIndex));
            lp.setNameLearningPlan(c.getString(nameIndex));
            lp.setSemester(c.getInt(semesterIndex));
            lp.setDeaneryLogin(c.getString(deaneryLoginIndex));
            if (lp.getDeaneryLogin().equals(lp.getDeaneryLogin())){
                dbHelper.close();
                return lp;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(LearningPlan learningPlan){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nameLearningPlan", learningPlan.getNameLearningPlan());
        cv.put("semester", learningPlan.getSemester());
        cv.put("deaneryLogin", learningPlan.getDeaneryLogin());
        long learningPlanId = db.insert("learningPlans", null, cv);
        dbHelper.close();
    }

    public void update(LearningPlan learningPlan){
        if (get(learningPlan) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nameLearningPlan", learningPlan.getNameLearningPlan());
        cv.put("semester", learningPlan.getSemester());
        cv.put("deaneryLogin", learningPlan.getDeaneryLogin());
        db.update("learningPlans", cv, "id = ?", new String[] {String.valueOf(learningPlan.getId())});
        dbHelper.close();
    }

    public void delete(LearningPlan learningPlan){
        if (get(learningPlan) == null){
            return;
        }
        disciplinesDB.delete(learningPlan);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("learningPlans", "id = " + learningPlan.getId(), null);
        dbHelper.close();
    }

    public List<LearningPlan> readAll(Deanery deanery){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<LearningPlan> retList = new ArrayList<LearningPlan>();
        Cursor c = db.query("learningPlans", null, "deaneryLogin = ?",
                new String[] {deanery.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameLearningPlan");
            int semesterIndex = c.getColumnIndex("semester");
            int deaneryLoginIndex = c.getColumnIndex("deaneryLogin");
            do{
                LearningPlan learningPlan = new LearningPlan();
                learningPlan.setId(c.getInt(idIndex));
                learningPlan.setNameLearningPlan(c.getString(nameIndex));
                learningPlan.setSemester(c.getInt(semesterIndex));
                learningPlan.setDeaneryLogin(c.getString(deaneryLoginIndex));
                retList.add(learningPlan);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "semesterLearningPlanDB", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table learningPlans ("
                    + "id integer primary key autoincrement,"
                    + "nameLearningPlan text,"
                    + "semester integer,"
                    + "deaneryLogin text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
