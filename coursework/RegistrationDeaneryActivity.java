package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.deanery.Deanery;
import com.example.coursework.deanery.DeaneryData;

public class RegistrationDeaneryActivity extends AppCompatActivity {
    DeaneryData deaneryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_deanery);
        deaneryData = new DeaneryData(this);
        Button reg = findViewById(R.id.registrationButton);
        TextView login = findViewById(R.id.regEditTextLogin);
        TextView password = findViewById(R.id.regEditTextPassword);
        reg.setOnClickListener(v -> {
            String log = login.getText().toString();
            String pas = password.getText().toString();
            if (log.equals("") || pas.equals("") ||
                    log.length() < 5 || pas.length() < 5){
                Toast.makeText(this, "Введите логин и пароль с минимальной длиной 5",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Deanery deanery = new Deanery();
                deanery.setLogin(log);
                deanery.setPassword(pas);
                deanery.setRole("user");
                boolean ret = deaneryData.registration(deanery);
                if (ret){
                    Intent data = new Intent();
                    setResult(Activity.RESULT_OK, data);
                    Toast.makeText(this, "Регистрация успешна",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(this, "Пользователь с таким логином уже существует",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}