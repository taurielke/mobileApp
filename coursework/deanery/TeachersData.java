package com.example.coursework.deanery;

import android.content.Context;

import com.example.coursework.deanery.DB.TeachersDB;

import java.util.ArrayList;
import java.util.List;

public class TeachersData {
    private static ArrayList<Teacher> teachers  = new ArrayList<Teacher>();
    TeachersDB teachersDB;

    public TeachersData(Context context, String deaneryLogin){
        teachersDB = new TeachersDB(context);
        readAll(deaneryLogin);
    }
    public Teacher getTeacher(int id, String login){
        Teacher tr = new Teacher();
        tr.setId(id);
        tr.setDeaneryLogin(login);
        return teachersDB.get(tr);
    }
    public List<Teacher> findAllTeachers(String deaneryLogin){
        return teachers;
    }

    public void addTeacher(String name, String deaneryLogin, String spec){
        Teacher teacher = new Teacher();
        teacher.setDeaneryLogin(deaneryLogin);
        teacher.setName(name);
        teacher.setSpec(spec);
        teachersDB.add(teacher);
        readAll(deaneryLogin);
    }
    public void updateTeacher(int id, String name, String deaneryLogin, String spec){
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setDeaneryLogin(deaneryLogin);
        teacher.setName(name);
        teacher.setSpec(spec);
        teachersDB.update(teacher);
        readAll(deaneryLogin);
    }
    public void deleteTeacher(int id, String deaneryLogin){
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setDeaneryLogin(deaneryLogin);
        teachersDB.delete(teacher);
        readAll(deaneryLogin);
    }
    private void readAll(String deaneryLogin){
        Deanery dean = new Deanery();
        dean.setLogin(deaneryLogin);
        List<Teacher> trs = teachersDB.readAll(dean);
        teachers.clear();
        for(Teacher teacher : trs){
            teachers.add(teacher);
        }
    }
    public List<Teacher> readAllTeachers(String deaneryLogin){
        Deanery dean = new Deanery();
        dean.setLogin(deaneryLogin);
        return teachersDB.readAll(dean);
    }
}
