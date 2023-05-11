package com.example.coursework.deanery.ReportsLogic;

public class DisciplinesLearningPlans {
    private String learningPlan;
    private String discipline;

    public DisciplinesLearningPlans(){
        learningPlan = "";
        discipline = "";
    }

    public String getLearningPlan() {
        return learningPlan;
    }

    public void setLearningPlan(String learningPlan) {
        this.learningPlan = learningPlan;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }
}
