package com.example.coursework.deanery;

public class Discipline {
    private int id;
    private String nameDiscipline;
    private String deaneryLogin;
    private int learningPlan_id;
    private int teacher_id;

    @Override
    public String toString(){
        return String.format("%s", nameDiscipline);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameDiscipline() {
        return nameDiscipline;
    }

    public void setNameDiscipline(String nameDiscipline) {
        this.nameDiscipline = nameDiscipline;
    }

    public String getDeaneryLogin() {
        return deaneryLogin;
    }

    public void setDeaneryLogin(String deaneryLogin) {
        this.deaneryLogin = deaneryLogin;
    }

    public int getLearningPlan_id() {
        return learningPlan_id;
    }

    public void setLearningPlan_id(int learningPlan_id) {
        this.learningPlan_id = learningPlan_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }
}
