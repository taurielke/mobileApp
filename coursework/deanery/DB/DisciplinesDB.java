package com.example.coursework.deanery.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursework.deanery.LearningPlan;
import com.example.coursework.deanery.Deanery;
import com.example.coursework.deanery.Discipline;
import com.example.coursework.deanery.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisciplinesDB {
    private DisciplinesDB.DBHelper dbHelper;

    public DisciplinesDB(Context context){
        dbHelper = new DisciplinesDB.DBHelper(context);
    }

    public Discipline get(Discipline discipline){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("disciplines", null, "id = ?",
                new String[] {String.valueOf(discipline.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameDiscipline");
            int deaneryLoginIndex = c.getColumnIndex("deaneryLogin");
            int learningPlanIdIndex = c.getColumnIndex("learningPlan_id");
            int teacherIdIndex = c.getColumnIndex("teacher_id");
            Discipline st = new Discipline();
            st.setId(c.getInt(idIndex));
            st.setNameDiscipline(c.getString(nameIndex));
            st.setDeaneryLogin(c.getString(deaneryLoginIndex));
            st.setLearningPlan_id(c.getInt(learningPlanIdIndex));
            st.setTeacher_id(c.getInt(teacherIdIndex));
            if (st.getDeaneryLogin().equals(discipline.getDeaneryLogin())){
                dbHelper.close();
                return st;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Discipline discipline){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nameDiscipline", discipline.getNameDiscipline());
        cv.put("deaneryLogin", discipline.getDeaneryLogin());
        cv.put("learningPlan_id", discipline.getLearningPlan_id());
        cv.put("teacher_id", discipline.getTeacher_id());
        long disciplineId = db.insert("disciplines", null, cv);
        dbHelper.close();
    }

    public void update(Discipline discipline){
        if (get(discipline) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nameDiscipline", discipline.getNameDiscipline());
        cv.put("deaneryLogin", discipline.getDeaneryLogin());
        cv.put("learningPlan_id", discipline.getLearningPlan_id());
        cv.put("teacher_id", discipline.getTeacher_id());
        db.update("disciplines", cv, "id = ?", new String[] {String.valueOf(discipline.getId())});
        dbHelper.close();
    }

    public void delete(Discipline discipline){
        if(get(discipline) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("disciplines", "id = " + discipline.getId(), null);
        dbHelper.close();
    }

    public void delete(LearningPlan learningPlan){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("disciplines", "learningPlan_id = " + learningPlan.getId(), null);
        dbHelper.close();
    }

    public void delete(Teacher teacher){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("disciplines", "learningPlan_id = " + teacher.getId(), null);
        dbHelper.close();
    }

    public List<Discipline> readAll(Deanery deanery){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Discipline> retList = new ArrayList<Discipline>();
        Cursor c = db.query("disciplines", null, "deaneryLogin = ?",
                new String[] {deanery.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameDiscipline");
            int deaneryLoginIndex = c.getColumnIndex("deaneryLogin");
            int learningPlanIdIndex = c.getColumnIndex("learningPlan_id");
            int teacherIdIndex = c.getColumnIndex("teacher_id");
            do{
                Discipline st = new Discipline();
                st.setId(c.getInt(idIndex));
                st.setNameDiscipline(c.getString(nameIndex));
                st.setDeaneryLogin(c.getString(deaneryLoginIndex));
                st.setLearningPlan_id(c.getInt(learningPlanIdIndex));
                st.setTeacher_id(c.getInt(teacherIdIndex));
                retList.add(st);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }

    public List<Discipline> readAllDeaneries(Deanery deanery){
        if (!Objects.equals(deanery.getRole(), "admin")){
            return readAll(deanery);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Discipline> retList = new ArrayList<Discipline>();
        Cursor c = db.query("disciplines", null, null, null,
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("nameDiscipline");
            int deaneryLoginIndex = c.getColumnIndex("deaneryLogin");
            int learningPlanIdIndex = c.getColumnIndex("learningPlan_id");
            int teacherIdIndex = c.getColumnIndex("teacher_id");
            do{
                Discipline st = new Discipline();
                st.setId(c.getInt(idIndex));
                st.setNameDiscipline(c.getString(nameIndex));
                st.setDeaneryLogin(c.getString(deaneryLoginIndex));
                st.setLearningPlan_id(c.getInt(learningPlanIdIndex));
                st.setTeacher_id(c.getInt(teacherIdIndex));
                retList.add(st);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }



    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "courseDisciplineDB", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table disciplines ("
                    + "id integer primary key autoincrement,"
                    + "nameDiscipline text,"
                    + "deaneryLogin text,"
                    + "learningPlan_id integer,"
                    + "teacher_id integer" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
