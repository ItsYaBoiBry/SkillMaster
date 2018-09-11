package com.example.damianlzy.skillmaster.content;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.damianlzy.skillmaster.R;
import com.example.damianlzy.skillmaster.functions.SendRequest;
import com.example.damianlzy.skillmaster.functions.Sessions;
import com.example.damianlzy.skillmaster.model.CourseDetails;
import com.example.damianlzy.skillmaster.model.Courses;
import com.example.damianlzy.skillmaster.model.Links;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ActivityCourseDetails extends AppCompatActivity {
    TextView tvCourseTitle, tvCourseInstructors, tvCourseCategories, tvCourseDescription, tvDropdownDates;
    ImageView ivCourseImage;
    ArrayList<String> schedules;
    ArrayList<Integer> scheduleid;
    Button btnEnglish,btnMalay,btnChinese,btnTamil,btnRegisterCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        final Intent intent= getIntent();
        Courses courses = new Courses();
        Toolbar toolbar = findViewById(R.id.toolbar);
        schedules = new ArrayList<>();
        scheduleid =new ArrayList<>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvCourseTitle = findViewById(R.id.tv_course_title);
        tvCourseInstructors = findViewById(R.id.tv_course_instructors);
        tvCourseCategories = findViewById(R.id.tv_course_categories);
        tvCourseDescription = findViewById(R.id.tv_course_description);
        tvCourseTitle = findViewById(R.id.tv_course_title);
        ivCourseImage = findViewById(R.id.iv_course_image);
        tvDropdownDates = findViewById(R.id.dropdown_dates);

        btnEnglish = findViewById(R.id.btnEnglish);
        btnMalay = findViewById(R.id.btnMalay);
        btnChinese = findViewById(R.id.btnChinese);
        btnTamil = findViewById(R.id.btnTamil);

        btnRegisterCourse = findViewById(R.id.btn_register_course);



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ivCourseImage = findViewById(R.id.iv_course_image);
        int courseid;
        if(intent.hasExtra(courses.course_id_key)){
            courseid = intent.getIntExtra(courses.course_id_key,-1);
            if(courseid==-1){
                //unable to get details
            }else{
                SendRequest request = new SendRequest();
                Links links = new Links();
                CourseDetails details = new CourseDetails();
                Sessions sessions = new Sessions(ActivityCourseDetails.this);
                try {
                    String getCourseDetails = request.execute(links.CourseDetailsLink(courseid),"",sessions.GetToken()).get();
                    Log.e("Course Details",getCourseDetails);
                    if(!getCourseDetails.equals(null)){
                        JSONObject requestResult = new JSONObject(getCourseDetails);
                        if(requestResult.has("success")){
                            JSONObject courseDetails = requestResult.getJSONObject("success");
                            CourseDetails course = new CourseDetails(courseDetails.getInt(details.cd_id_key)
                                    ,courseDetails.getString(details.cd_name_key)
                                    ,courseDetails.getString(details.cd_category_key)
                                    ,courseDetails.getString(details.cd_instructor_key)
                                    ,courseDetails.getString(details.cd_description_key)
                                    ,courseDetails.getString(details.cd_course_fee_key)
                                    ,courseDetails.getString(details.cd_address_key)
                                    ,courseDetails.getString(details.cd_prod_picture_key)
                                    ,courseDetails.getString(details.cd_status_key)
                                    ,courseDetails.getJSONObject(details.cd_created_at_key)
                                    ,courseDetails.getJSONObject(details.cd_updated_at_key)
                                    ,courseDetails.getJSONArray(details.cd_schedule_key));

                            tvCourseTitle.setText(course.getName());
                            tvCourseInstructors.setText(course.getInstructor());
                            tvCourseCategories.setText(course.getCategory());
                            tvCourseDescription.setText(course.getDescription());
                            Glide.with(ActivityCourseDetails.this).load(course.getProd_picture()).into(ivCourseImage);
                            for(int i=0;i<course.getSchedule().length();i++){
                                JSONObject schedule = course.getSchedule().getJSONObject(i);
                                String language = schedule.getString("language");
                                String date = schedule.getString("date");
                                schedules.add(language+"="+date);
                                scheduleid.add(schedule.getInt("id"));
                            }
                        }

                    }else{
                        //unable to get details
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else{
            //unable to get details
        }
        //default on start

        tvDropdownDates.setText("No available dates");
        resetlanguages();
        setdisabledbutton(btnEnglish);
        for(int i=0;i<schedules.size();i++){
            Log.e("Language",schedules.get(i).split("$")[0]);
            if(schedules.get(i).split("=")[0].equalsIgnoreCase(btnEnglish.getText().toString())){
                SetSeleted(true,btnEnglish);
                tvDropdownDates.setText(schedules.get(i).split("=")[1]);
            }
        }


        btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDropdownDates.setText("No available dates");
                resetlanguages();
                setdisabledbutton(btnChinese);
                for(int i=0;i<schedules.size();i++){
                    if(schedules.get(i).split("=")[0].equalsIgnoreCase(btnChinese.getText().toString())){
                        SetSeleted(true,btnChinese);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                    }
                }
            }
        });
        btnMalay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDropdownDates.setText("No available dates");
                resetlanguages();
                setdisabledbutton(btnMalay);
                for(int i=0;i<schedules.size();i++){
                    if(schedules.get(i).split("=")[0].equalsIgnoreCase(btnMalay.getText().toString())){
                        SetSeleted(true,btnMalay);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                    }
                }
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDropdownDates.setText("No available dates");
                resetlanguages();
                setdisabledbutton(btnEnglish);
                for(int i=0;i<schedules.size();i++){
                    if(schedules.get(i).split("=")[0].equalsIgnoreCase(btnEnglish.getText().toString())){
                        SetSeleted(true,btnEnglish);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                    }
                }
            }
        });
        btnTamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDropdownDates.setText("No available dates");
                resetlanguages();
                setdisabledbutton(btnTamil);
                for(int i=0;i<schedules.size();i++){
                    if(schedules.get(i).split("=")[0].equalsIgnoreCase(btnTamil.getText().toString())){
                        SetSeleted(true,btnTamil);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                    }
                }
            }
        });
        btnRegisterCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvDropdownDates.getText().toString().equalsIgnoreCase("No available dates")){
                    Toast.makeText(ActivityCourseDetails.this, "Please select an available date", Toast.LENGTH_SHORT).show();
                }
                int scheduleID=-1;
                for(int i=0;i<schedules.size();i++){
                    if(schedules.get(i).split("=")[1].equalsIgnoreCase(tvDropdownDates.getText().toString())){
                        scheduleID = scheduleid.get(i);
                    }

                }
                Courses courses = new Courses();
                startActivity(new Intent(ActivityCourseDetails.this,ActivityCourseRegistration.class)
                        .putExtra("course_id",intent.getIntExtra(courses.course_id_key,-1))
                        .putExtra("schedule_id",scheduleID));
            }
        });


    }
    public void SetSeleted(Boolean isSelected, Button button){
        if(isSelected){
            button.setBackground(getResources().getDrawable(R.drawable.language_button_selected_background));
        }else{
            button.setBackground(getResources().getDrawable(R.drawable.language_button_background));
        }
    }
    public void setdisabledbutton(Button button){
        button.setBackground(getResources().getDrawable(R.drawable.language_button_disabled_background));
    }
    public void resetlanguages(){
        SetSeleted(false,btnEnglish);
        SetSeleted(false,btnTamil);
        SetSeleted(false,btnChinese);
        SetSeleted(false,btnMalay);
    }

}
