package com.example.coursework.deanery;

public class Teacher {
    private int id;
    private String name;
    private String spec;
    private String deaneryLogin;

    @Override
    public String toString(){
        return String.format("%s", name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getDeaneryLogin() {
        return deaneryLogin;
    }

    public void setDeaneryLogin(String deaneryLogin) {
        this.deaneryLogin = deaneryLogin;
    }
}
