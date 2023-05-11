package com.example.coursework.deanery.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursework.deanery.Deanery;
import com.example.coursework.deanery.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeachersDB {
    private TeachersDB.DBHelper dbHelper;
    private DisciplinesDB disciplinesDB;

    public TeachersDB(Context context){
        dbHelper = new TeachersDB.DBHelper(context);
        disciplinesDB = new DisciplinesDB(context);
    }

    public Teacher get(Teacher teacher){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("teachers", null, "id = ?",
                new String[] {String.valueOf(teacher.getId())},
                null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int specIndex = c.getColumnIndex("spec");
            int deaneryLoginIndex = c.getColumnIndex("deaneryLogin");
            Teacher tr = new Teacher();
            tr.setId(c.getInt(idIndex));
            tr.setName(c.getString(nameIndex));
            tr.setSpec(c.getString(specIndex));
            tr.setDeaneryLogin(c.getString(deaneryLoginIndex));
            if (tr.getDeaneryLogin().equals(teacher.getDeaneryLogin())){
                dbHelper.close();
                return tr;
            }
        }
        dbHelper.close();
        return null;
    }

    public void add(Teacher teacher){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", teacher.getName());
        cv.put("spec", teacher.getSpec());
        cv.put("deaneryLogin", teacher.getDeaneryLogin());
        long teacherId = db.insert("teachers", null, cv);
        dbHelper.close();
    }

    public void update(Teacher teacher){
        if (get(teacher) == null){
            return;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", teacher.getName());
        cv.put("spec", teacher.getSpec());
        cv.put("deaneryLogin", teacher.getDeaneryLogin());
        db.update("teachers", cv, "id = ?", new String[] {String.valueOf(teacher.getId())});
        dbHelper.close();
    }

    public void delete(Teacher teacher){
        if(get(teacher) == null){
            return;
        }
        disciplinesDB.delete(teacher);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("teachers", "id = " + teacher.getId(), null);
        dbHelper.close();
    }

    public List<Teacher> readAll(Deanery deanery){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Teacher> teacherList = new ArrayList<Teacher>();
        Cursor c = db.query("teachers", null, "deaneryLogin = ?",
                new String[] {deanery.getLogin()}, null, null, null);
        if (c.moveToFirst()){
            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");
            int specIndex = c.getColumnIndex("spec");
            int deaneryLoginIndex = c.getColumnIndex("deaneryLogin");
            do{
                Teacher tr = new Teacher();
                tr.setId(c.getInt(idIndex));
                tr.setName(c.getString(nameIndex));
                tr.setSpec(c.getString(specIndex));
                tr.setDeaneryLogin(c.getString(deaneryLoginIndex));
                teacherList.add(tr);
            } while(c.moveToNext());
        }
        dbHelper.close();
        return teacherList;
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "courseTeacherDB", null, 2);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table teachers ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "spec text,"
                    + "deaneryLogin text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
