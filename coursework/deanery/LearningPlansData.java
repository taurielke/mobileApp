package com.example.coursework.deanery;

import android.content.Context;


import com.example.coursework.deanery.DB.LearningPlansDB;

import java.util.ArrayList;
import java.util.List;

public class LearningPlansData {
    private static ArrayList<LearningPlan> learningPlans  = new ArrayList<LearningPlan>();
    LearningPlansDB learningPlansDB;

    public LearningPlansData(Context context, String userLogin){
        learningPlansDB = new LearningPlansDB(context);
        readAll(userLogin);
    }

    public LearningPlan getLearningPlan(int id, String login){
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setId(id);
        learningPlan.setDeaneryLogin(login);
        return learningPlansDB.get(learningPlan);
    }
    public List<LearningPlan> findAllLearningPlans(String deaneryLogin){
        return learningPlans;
    }

    public void addLearningPlan(String nameLearningPlan, int semester, String deaneryLogin){
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setNameLearningPlan(nameLearningPlan);
        learningPlan.setDeaneryLogin(deaneryLogin);
        learningPlan.setSemester(semester);
        learningPlansDB.add(learningPlan);
        readAll(deaneryLogin);
    }
    public void updateLearningPlan(int id, String nameLearningPlan, int semester, String deaneryLogin){
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setId(id);
        learningPlan.setNameLearningPlan(nameLearningPlan);
        learningPlan.setDeaneryLogin(deaneryLogin);
        learningPlan.setSemester(semester);
        learningPlansDB.update(learningPlan);
        readAll(deaneryLogin);
    }
    public void deleteLearningPlan(int id, String deaneryLogin){
        LearningPlan learningPlan = new LearningPlan();
        learningPlan.setId(id);
        learningPlan.setDeaneryLogin(deaneryLogin);
        learningPlansDB.delete(learningPlan);
        readAll(deaneryLogin);
    }
    private void readAll(String deaneryLogin){
        Deanery dean = new Deanery();
        dean.setLogin(deaneryLogin);
        List<LearningPlan> learningPlanList = learningPlansDB.readAll(dean);
        learningPlans.clear();
        for(LearningPlan learningPlan : learningPlanList){
            learningPlans.add(learningPlan);
        }
    }
    public List<LearningPlan> readAllLearningPlans(String deaneryLogin){
        Deanery dean = new Deanery();
        dean.setLogin(deaneryLogin);
        return learningPlansDB.readAll(dean);
    }
}
