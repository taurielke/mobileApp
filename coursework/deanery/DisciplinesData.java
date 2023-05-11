package com.example.coursework.deanery;

import android.content.Context;

import com.example.coursework.deanery.DB.DisciplinesDB;

import java.util.ArrayList;
import java.util.List;

public class DisciplinesData {
    private static ArrayList<Discipline> disciplines  = new ArrayList<Discipline>();
    DisciplinesDB disciplinesDB;

    public DisciplinesData(Context context, String deaneryLogin){
        disciplinesDB = new DisciplinesDB(context);
        readAll(deaneryLogin);
    }
    public Discipline getDiscipline(int id, String login){
        Discipline st = new Discipline();
        st.setId(id);
        st.setDeaneryLogin(login);
        return disciplinesDB.get(st);
    }
    public List<Discipline> findAllDisciplines(String deaneryLogin){
        return disciplines;
    }

    public void addDiscipline(String nameDiscipline, String deaneryLogin,
                              int learningPlan_id, int teacher_id){
        Discipline discipline = new Discipline();
        discipline.setDeaneryLogin(deaneryLogin);
        discipline.setNameDiscipline(nameDiscipline);
        discipline.setLearningPlan_id(learningPlan_id);
        discipline.setTeacher_id(teacher_id);
        disciplinesDB.add(discipline);
        readAll(deaneryLogin);
    }
    public void updateDiscipline(int id,String nameDiscipline, String deaneryLogin,
                                 int learningPlan_id, int teacher_id){
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setDeaneryLogin(deaneryLogin);
        discipline.setNameDiscipline(nameDiscipline);
        discipline.setLearningPlan_id(learningPlan_id);
        discipline.setTeacher_id(teacher_id);
        disciplinesDB.update(discipline);
        readAll(deaneryLogin);
    }
    public void deleteDiscipline(int id, String deaneryLogin){
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setDeaneryLogin(deaneryLogin);
        disciplinesDB.delete(discipline);
        readAll(deaneryLogin);
    }
    private void readAll(String deaneryLogin){
        Deanery dean = new Deanery();
        dean.setLogin(deaneryLogin);
        List<Discipline> stps = disciplinesDB.readAll(dean);
        disciplines.clear();
        for(Discipline discipline : stps){
            disciplines.add(discipline);
        }
        dean.setRole("admin");
        stps = disciplinesDB.readAllDeaneries(dean);
    }
    public List<Discipline> readAllDisciplines(String deaneryLogin){
        Deanery dean = new Deanery();
        dean.setLogin(deaneryLogin);
        return disciplinesDB.readAll(dean);
    }
}
