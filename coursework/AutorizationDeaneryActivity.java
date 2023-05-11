package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.deanery.Deanery;
import com.example.coursework.deanery.DeaneryData;

public class AutorizationDeaneryActivity extends AppCompatActivity {

    DeaneryData deaneryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization_deanery);

        deaneryData = new DeaneryData(this);
        Button aut = findViewById(R.id.autorizationAut);
        Button reg = findViewById(R.id.autorizationReg);
        TextView login = findViewById(R.id.textViewLogin);
        TextView password = findViewById(R.id.textViewPassword);

        aut.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("")){
                Toast.makeText(this, "Введите логин и пароль",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Deanery deanery = new Deanery();
                deanery.setLogin(log);
                deanery.setPassword(pas);
                Deanery ret = deaneryData.authorization(deanery);
                if (ret == null){
                    Toast.makeText(this, "Неверный логин или пароль, или такого пользователя не существует",
                            Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences sPref = getSharedPreferences("Deanery", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString("login", ret.getLogin());
                    ed.putString("role", ret.getRole());
                    ed.commit();
                    Intent intent = new Intent(this, MainDeaneryActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        reg.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrationDeaneryActivity.class);
            startActivity(intent);
        });
    }
}