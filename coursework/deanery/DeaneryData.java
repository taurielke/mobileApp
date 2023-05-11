package com.example.coursework.deanery;

import android.content.Context;

import com.example.coursework.deanery.DB.DeaneryDB;

import java.util.List;

public class DeaneryData {
    private DeaneryDB deaneryDB;

    public DeaneryData(Context context){
        deaneryDB = new DeaneryDB(context);
    }

    public boolean registration(Deanery deanery){
        try{
            boolean  ret = deaneryDB.registration(deanery);
            return ret;
        }
        catch(Exception ex){
            return false;
        }
    }

    public Deanery authorization(Deanery deanery){
        try{
            Deanery  ret = deaneryDB.authorization(deanery);
            return ret;
        }
        catch(Exception ex){
            return null;
        }
    }

    public List<Deanery> readAll(String login, String role, String password){
        Deanery deanery = new Deanery();
        deanery.setLogin(login);
        deanery.setPassword(password);
        deanery.setRole(role);
        return deaneryDB.readAll(deanery);
    }
}
