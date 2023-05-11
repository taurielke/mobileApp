package com.example.coursework.deanery;

public class LearningPlan {
    private int id;
    private String nameLearningPlan;
    private int semester;
    private String deaneryLogin;

    @Override
    public String toString(){
        return String.format("План обучения для %s, %d семестр", nameLearningPlan, semester);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameLearningPlan() {
        return nameLearningPlan;
    }

    public void setNameLearningPlan(String nameLearningPlan) {
        this.nameLearningPlan = nameLearningPlan;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getDeaneryLogin() {
        return deaneryLogin;
    }

    public void setDeaneryLogin(String deaneryLogin) {
        this.deaneryLogin = deaneryLogin;
    }
}
