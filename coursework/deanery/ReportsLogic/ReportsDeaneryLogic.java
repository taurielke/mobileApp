package com.example.coursework.deanery.ReportsLogic;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;


import com.example.coursework.deanery.LearningPlansData;
import com.example.coursework.deanery.Deanery;
import com.example.coursework.deanery.DeaneryData;
import com.example.coursework.deanery.Discipline;
import com.example.coursework.deanery.DisciplinesData;
import com.example.coursework.deanery.TeachersData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportsDeaneryLogic {
    DisciplinesData disciplinesData;
    TeachersData teachersData;
    LearningPlansData learningPlansData;
    DeaneryData deaneryData;

    public ReportsDeaneryLogic(Context context, String deaneryLogin){
        disciplinesData = new DisciplinesData(context, deaneryLogin);
        teachersData = new TeachersData(context, deaneryLogin);
        learningPlansData = new  LearningPlansData(context, deaneryLogin);
        deaneryData = new DeaneryData(context);
    }

    public List<AllDeaneriesUnit> getAllDeaneriesData(String login, String role, String password){
        List<Deanery> deaneries = deaneryData.readAll(login, role, password);
        List<AllDeaneriesUnit> deanList = new ArrayList<AllDeaneriesUnit>();
        for (Deanery dean : deaneries){
            AllDeaneriesUnit deaneryUnit = new AllDeaneriesUnit();
            deaneryUnit.setLogin(dean.getLogin());
            deaneryUnit.setPassword(dean.getPassword());
            deaneryUnit.setRole(dean.getRole());
            deanList.add(deaneryUnit);
        }
        return deanList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<DisciplinesLearningPlans> getDisciplinesByLearningPlans(String login){
        List<Discipline> disciplines = disciplinesData.findAllDisciplines(login);
        disciplines.sort(Comparator.comparing(Discipline::getLearningPlan_id));
        List<DisciplinesLearningPlans> retList = new ArrayList<DisciplinesLearningPlans>();
        for(int i = 0; i < disciplines.size(); ++i){
            DisciplinesLearningPlans dlp = new DisciplinesLearningPlans();
            if (i == 0 || disciplines.get(i).getLearningPlan_id() != disciplines.get(i - 1).getLearningPlan_id()){
                dlp.setLearningPlan(learningPlansData.getLearningPlan(disciplines.get(i).getLearningPlan_id(), login).toString());
            }
            dlp.setDiscipline(disciplines.get(i).toString());
            retList.add(dlp);
        }
        return retList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<DisciplinesTeachers> getDisciplinesByTeachers(String login){
        List<Discipline> disciplines = disciplinesData.findAllDisciplines(login);
        disciplines.sort(Comparator.comparing(Discipline::getTeacher_id));
        List<DisciplinesTeachers> retList = new ArrayList<DisciplinesTeachers>();
        for(int i = 0; i < disciplines.size(); ++i){
            DisciplinesTeachers dt = new DisciplinesTeachers();
            if (i == 0 || disciplines.get(i).getTeacher_id() != disciplines.get(i - 1).getTeacher_id()){
                dt.setTeacher(teachersData.getTeacher(disciplines.get(i).getTeacher_id(), login).toString());
            }
            dt.setDiscipline(disciplines.get(i).toString());
            retList.add(dt);
        }
        return retList;
    }
}
