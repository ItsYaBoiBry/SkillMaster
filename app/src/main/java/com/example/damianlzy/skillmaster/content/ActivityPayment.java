package com.example.damianlzy.skillmaster.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.damianlzy.skillmaster.R;
import com.example.damianlzy.skillmaster.functions.SendRequest;
import com.example.damianlzy.skillmaster.functions.Sessions;
import com.example.damianlzy.skillmaster.model.Links;
import com.example.damianlzy.skillmaster.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ActivityPayment extends AppCompatActivity {
    TextView tvTitle, tvFees, tvDetails;
    Button btnConfirm;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        tvTitle = findViewById(R.id.tv_course_title);
        tvDetails = findViewById(R.id.tv_course_details);
        tvFees = findViewById(R.id.tv_course_fees);
        btnConfirm = findViewById(R.id.btn_confirm);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent i = getIntent();
        Log.e("course_id id", i.getIntExtra("course_id", -1) + "");
        Log.e("schedule_id id", i.getIntExtra("schedule_id", -1) + "");
        Log.e("course_name", i.getStringExtra("course_name"));
        Log.e("course_instructor", i.getStringExtra("course_instructor"));
        Log.e("course_description", i.getStringExtra("course_description"));
        Log.e("course_address", i.getStringExtra("course_address"));
        Log.e("course_fee", i.getStringExtra("course_fee"));
        Log.e("course_schedule", i.getStringExtra("course_schedule"));
        final User user = (User) i.getSerializableExtra("user");
        Log.e("user name", user.getUser_name());
        tvTitle.setText(i.getStringExtra("course_name"));
        tvDetails.setText(String.format("Speaker(s):     %s\n\nDate:     %s\n\nAddress:     %s", i.getStringExtra("course_instructor"), i.getStringExtra("course_schedule").split("=")[1], i.getStringExtra("course_address")));
        tvFees.setText(String.format("Course Fees: $%s", i.getStringExtra("course_fee")));
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Links links = new Links();
                SendRequest sendRequest = new SendRequest();
                try {
                    JSONObject params = new JSONObject();
                    params.put("email", user.getUser_email());
                    params.put("name", user.getUser_name());
                    params.put("mobile", user.getUser_mobile());
                    params.put("address", user.getUser_address());
                    params.put("nric", user.getUser_nric());
                    params.put("dob", user.getUser_dob());
                    params.put("gender", user.getUser_gender());
                    params.put("nationality", user.getUser_nationality());
                    params.put("education_level", user.getUser_education_level());
                    params.put("race", user.getUser_race());
                    params.put("occupation", user.getUser_occupation());
                    params.put("salary", user.getUser_salary());
                    Intent intent = getIntent();
                    Sessions sessions = new Sessions(ActivityPayment.this);
                    params.put("course_id", intent.getIntExtra("course_id", -1));
                    params.put("schedule_id", intent.getIntExtra("schedule_id", -1));
                    String request = sendRequest.execute(links.registerCourseLink(), "", sessions.GetToken()).get();
                    Log.e("REGISTER COURSE:", request);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
