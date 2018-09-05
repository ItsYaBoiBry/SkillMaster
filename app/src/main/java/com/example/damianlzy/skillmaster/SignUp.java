package com.example.damianlzy.skillmaster;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    TextView tvPP, tvTNC;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvPP = findViewById(R.id.tvPP);
        tvTNC = findViewById(R.id.tvTNC);

        tvPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                LayoutInflater li = LayoutInflater.from(SignUp.this);
                final View gtnc = li.inflate(R.layout.pp_dialog, null);
                builder.setCancelable(true);
                builder.setView(gtnc);
                Button btnDone = gtnc.findViewById(R.id.btnDone);
                final AlertDialog dialog  = builder.create();
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        tvTNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                LayoutInflater li = LayoutInflater.from(SignUp.this);
                final View gtnc = li.inflate(R.layout.pp_dialog, null);
                builder.setCancelable(true);
                builder.setView(gtnc);
                Button btnDone = gtnc.findViewById(R.id.btnDone);
                final AlertDialog dialog  = builder.create();
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
