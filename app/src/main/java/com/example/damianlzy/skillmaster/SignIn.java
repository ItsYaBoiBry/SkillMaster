package com.example.damianlzy.skillmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.damianlzy.skillmaster.content.ActivityHome;
import com.example.damianlzy.skillmaster.functions.Sessions;

public class SignIn extends AppCompatActivity {
    EditText etUsername, etPassword;
    TextView tvHaveAccount, tvForgotPassword;
    Button signin;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signin = findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sessions sessions = new Sessions(SignIn.this);
                sessions.StoreToken("444");
                sessions.StoreUser(1
                        , "Bryan Low"
                        , "bryanlowsk@gmail.com"
                        , "12-8-2018"
                        , "12-8-2018"
                        , "94511958"
                        , "tampines street 22"
                        , "S9830295H"
                        , "19-09-1998"
                        , "Male"
                        , "Singaporean"
                        , "Diploma"
                        , "Chinese"
                        , "Android Developer"
                        , "$2000/month");
                startActivity(new Intent(SignIn.this, ActivityHome.class));
            }
        });
    }
}
