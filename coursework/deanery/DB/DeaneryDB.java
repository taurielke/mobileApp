package com.example.coursework.deanery.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.coursework.deanery.Deanery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeaneryDB {
    private DeaneryDB.DBHelper dbHelper;

    public DeaneryDB(Context context){
        dbHelper = new DeaneryDB.DBHelper(context);
    }

    public boolean registration(Deanery deanery){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { deanery.getLogin() };
        Cursor c = db.query("deaneries", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                dbHelper.close();
                return false;
            }
            ContentValues cv = new ContentValues();
            cv.put("login", deanery.getLogin());
            cv.put("password", deanery.getPassword());
            cv.put("role", deanery.getRole());
            long deaneryId =  db.insert("deaneries", null, cv);
            dbHelper.close();
            return true;
        }
        dbHelper.close();
        return false;
    }

    public Deanery authorization(Deanery deanery){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "login = ?";
        String[] selectionArgs = new String[] { deanery.getLogin() };
        Cursor c = db.query("deaneries", null, selection,
                selectionArgs, null, null, null);
        if (c != null){
            if (c.moveToFirst()) {
                if (Objects.equals(deanery.getLogin(), c.getString(1)) &&
                        Objects.equals(deanery.getPassword(), c.getString(2))){
                    Deanery dean = new Deanery();
                    dean.setLogin(deanery.getLogin());
                    dean.setPassword(deanery.getPassword());
                    dean.setRole(c.getString(3));
                    c.close();
                    db.close();
                    return dean;
                }
            }
            c.close();
            dbHelper.close();
            return null;
        }
        dbHelper.close();
        return null;
    }

    public List<Deanery> readAll(Deanery deanery) {
        if (!Objects.equals(deanery.getRole(), "admin")) {
            return null;
        }
        List<Deanery> retList = new ArrayList<Deanery>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query("deaneries", null, null,
                null, null, null, null);
        if (c.moveToFirst()) {
            int idIndex = c.getColumnIndex("id");
            int logIndex = c.getColumnIndex("login");
            int passIndex = c.getColumnIndex("password");
            int rolIndex = c.getColumnIndex("role");
            do {
                Deanery usr = new Deanery();
                usr.setId(c.getInt(idIndex));
                usr.setRole(c.getString(rolIndex));
                usr.setLogin(c.getString(logIndex));
                usr.setPassword(c.getString(passIndex));
                retList.add(usr);
            } while (c.moveToNext());
        }
        dbHelper.close();
        return retList;
    }
    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "courseDeaneryDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table deaneries ("
                    + "id integer primary key autoincrement,"
                    + "login text,"
                    + "password text,"
                    + "role text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
