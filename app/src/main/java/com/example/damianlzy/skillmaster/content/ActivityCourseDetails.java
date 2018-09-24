package com.example.damianlzy.skillmaster.content;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
    ArrayList<String> dates;
    ArrayList<Integer> scheduleid;
    ArrayList<String> detail;
    Button btnEnglish, btnMalay, btnChinese, btnTamil, btnRegisterCourse;
    String language;
    CourseDetails courseDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        final Intent intent = getIntent();
        Courses courses = new Courses();
        Toolbar toolbar = findViewById(R.id.toolbar);
        CourseDetails course;

        schedules = new ArrayList<>();
        scheduleid = new ArrayList<>();
        dates = new ArrayList<>();
        detail = new ArrayList<>();

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
        if (intent.hasExtra(courses.course_id_key)) {
            courseid = intent.getIntExtra(courses.course_id_key, -1);
            if (courseid == -1) {
                //unable to get details
            } else {
                SendRequest request = new SendRequest();
                Links links = new Links();
                CourseDetails details = new CourseDetails();
                Sessions sessions = new Sessions(ActivityCourseDetails.this);
                try {
                    String getCourseDetails = request.execute(links.CourseDetailsLink(courseid), "", sessions.GetToken()).get();
                    Log.e("Course Details", getCourseDetails);
                    if (!getCourseDetails.equals(null)) {
                        JSONObject requestResult = new JSONObject(getCourseDetails);
                        if (requestResult.has("success")) {
                            JSONObject courseDetails = requestResult.getJSONObject("success");
                            course = new CourseDetails(courseDetails.getInt(details.cd_id_key)
                                    , courseDetails.getString(details.cd_name_key)
                                    , courseDetails.getString(details.cd_category_key)
                                    , courseDetails.getString(details.cd_instructor_key)
                                    , courseDetails.getString(details.cd_description_key)
                                    , courseDetails.getString(details.cd_course_fee_key)
                                    , courseDetails.getString(details.cd_address_key)
                                    , courseDetails.getString(details.cd_prod_picture_key)
                                    , courseDetails.getString(details.cd_status_key)
                                    , courseDetails.getJSONObject(details.cd_created_at_key)
                                    , courseDetails.getJSONObject(details.cd_updated_at_key)
                                    , courseDetails.getJSONArray(details.cd_schedule_key));

                            tvCourseTitle.setText(course.getName());
                            tvCourseInstructors.setText(course.getInstructor());
                            tvCourseCategories.setText(course.getCategory());
                            tvCourseDescription.setText(course.getDescription());
                            detail.add(course.getName());
                            detail.add(course.getInstructor());
                            detail.add(course.getDescription());
                            detail.add(course.getAddress());
                            detail.add(course.getCourse_fee());

                            courseDetail = course;
                            Glide.with(ActivityCourseDetails.this).load(course.getProd_picture()).into(ivCourseImage);
                            for (int i = 0; i < course.getSchedule().length(); i++) {
                                JSONObject schedule = course.getSchedule().getJSONObject(i);
                                String languages = schedule.getString("language");
                                String date = schedule.getString("date");
                                schedules.add(languages + "=" + dateToDateFormat(date));
                                scheduleid.add(schedule.getInt("id"));
                            }
                        }
                    } else {
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
        } else {
            //unable to get details
        }
        //default on start
        language = "";
        tvDropdownDates.setText("No available dates");
        resetlanguages();
        setdisabledbutton(btnEnglish);
        dates.clear();
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).split("=")[0].equalsIgnoreCase(btnEnglish.getText().toString())) {
                dates.add(schedules.get(i).split("=")[1]);
                SetSeleted(true, btnEnglish);
                tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                language = "english";
            }
        }

        btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDropdownDates.setText("No available dates");
                resetlanguages();
                setdisabledbutton(btnChinese);
                dates.clear();
                for (int i = 0; i < schedules.size(); i++) {
                    if (schedules.get(i).split("=")[0].equalsIgnoreCase(btnChinese.getText().toString())) {
                        dates.add(schedules.get(i).split("=")[1]);
                        SetSeleted(true, btnChinese);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                        language = "chinese";
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
                dates.clear();
                for (int i = 0; i < schedules.size(); i++) {
                    if (schedules.get(i).split("=")[0].equalsIgnoreCase(btnMalay.getText().toString())) {
                        dates.add(schedules.get(i).split("=")[1]);
                        SetSeleted(true, btnMalay);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                        language = "malay";
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
                dates.clear();
                for (int i = 0; i < schedules.size(); i++) {
                    if (schedules.get(i).split("=")[0].equalsIgnoreCase(btnEnglish.getText().toString())) {
                        dates.add(schedules.get(i).split("=")[1]);
                        SetSeleted(true, btnEnglish);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                        language = "english";
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
                dates.clear();
                for (int i = 0; i < schedules.size(); i++) {
                    if (schedules.get(i).split("=")[0].equalsIgnoreCase(btnTamil.getText().toString())) {
                        dates.add(schedules.get(i).split("=")[1]);
                        SetSeleted(true, btnTamil);
                        tvDropdownDates.setText(schedules.get(i).split("=")[1]);
                        language = "tamil";
                    }
                }
            }
        });
        tvDropdownDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tvDropdownDates.getText().toString().equalsIgnoreCase("No available dates")) {
                    CharSequence[] items = dates.toArray(new CharSequence[dates.size()]);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCourseDetails.this);
                    builder.setTitle("Available dates");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tvDropdownDates.setText(dates.get(which));
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        btnRegisterCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvDropdownDates.getText().toString().equalsIgnoreCase("No available dates")) {
                    Toast.makeText(ActivityCourseDetails.this, "Please select an available date", Toast.LENGTH_SHORT).show();
                }else{
                    int scheduleID = -1;

                    for (int i = 0; i < schedules.size(); i++) {
                        String item = schedules.get(i).split("=")[0]+"="+dateFormatToDate(schedules.get(i).split("=")[1]);
                        Log.e("looped value1",item);
                        Log.e("looped value2",language+"="+dateFormatToDate(tvDropdownDates.getText().toString()));
                        if (item.equalsIgnoreCase(language+"="+dateFormatToDate(tvDropdownDates.getText().toString()))) {
                            Log.e("selected value1",schedules.get(i).split("=")[0]+"="+dateFormatToDate(schedules.get(i).split("=")[1]));
                            Log.e("selected value2",language+"="+dateFormatToDate(tvDropdownDates.getText().toString()));
                            scheduleID = scheduleid.get(i);
                            detail.add(schedules.get(i).split("=")[0]+"="+dateFormatToDate(schedules.get(i).split("=")[1]));
                        }
                    }
                    Courses courses = new Courses();
                    Log.e("Selected Name",detail.get(0));
                    startActivity(new Intent(ActivityCourseDetails.this, ActivityCourseRegistration.class)
                            .putExtra("course_id", intent.getIntExtra(courses.course_id_key, -1))
                            .putExtra("schedule_id", scheduleID)
                            .putExtra("course_name",detail.get(0))
                            .putExtra("course_instructor",detail.get(1))
                            .putExtra("course_description",detail.get(2))
                            .putExtra("course_address",detail.get(3))
                            .putExtra("course_fee",detail.get(4))
                            .putExtra("course_schedule",detail.get(5)));
                }
            }
        });
    }
    public void SetSeleted(Boolean isSelected, Button button) {
        if (isSelected) {
            button.setBackground(getResources().getDrawable(R.drawable.language_button_selected_background));
        } else {
            button.setBackground(getResources().getDrawable(R.drawable.language_button_background));
        }
    }
    public void setdisabledbutton(Button button) {
        button.setBackground(getResources().getDrawable(R.drawable.language_button_disabled_background));
    }

    public void resetlanguages() {
        SetSeleted(false, btnEnglish);
        SetSeleted(false, btnTamil);
        SetSeleted(false, btnChinese);
        SetSeleted(false, btnMalay);
    }
    public String dateToDateFormat(String date){
        String y = date.split("-")[0];
        String m = date.split("-")[1];
        String d = date.split("-")[2];
        if(m.equalsIgnoreCase("1")||m.equalsIgnoreCase("01")){
            return String.format("%s %s %s",d,"January",y);
        }else if(m.equalsIgnoreCase("2")||m.equalsIgnoreCase("02")){
            return String.format("%s %s %s",d,"February",y);
        }else if(m.equalsIgnoreCase("3")||m.equalsIgnoreCase("03")){
            return String.format("%s %s %s",d,"March",y);
        }else if(m.equalsIgnoreCase("4")||m.equalsIgnoreCase("04")){
            return String.format("%s %s %s",d,"April",y);
        }else if(m.equalsIgnoreCase("5")||m.equalsIgnoreCase("05")){
            return String.format("%s %s %s",d,"May",y);
        }else if(m.equalsIgnoreCase("6")||m.equalsIgnoreCase("06")){
            return String.format("%s %s %s",d,"June",y);
        }else if(m.equalsIgnoreCase("7")||m.equalsIgnoreCase("07")){
            return String.format("%s %s %s",d,"July",y);
        }else if(m.equalsIgnoreCase("8")||m.equalsIgnoreCase("08")){
            return String.format("%s %s %s",d,"August",y);
        }else if(m.equalsIgnoreCase("9")||m.equalsIgnoreCase("09")){
            return String.format("%s %s %s",d,"September",y);
        }else if(m.equalsIgnoreCase("10")){
            return String.format("%s %s %s",d,"October",y);
        }else if(m.equalsIgnoreCase("11")){
            return String.format("%s %s %s",d,"November",y);
        }else if(m.equalsIgnoreCase("12")){
            return String.format("%s %s %s",d,"December",y);
        }else{
            return String.format("%s %s %s",d,m,y);
        }
    }
    public String dateFormatToDate(String date){
        String y = date.split(" ")[2];
        String m = date.split(" ")[1];
        String d = date.split(" ")[0];
        if(m.equalsIgnoreCase("January")){
            return String.format("%s-%s-%s",y,"01",d);
        }else if(m.equalsIgnoreCase("February")){
            return String.format("%s-%s-%s",y,"02",d);
        }else if(m.equalsIgnoreCase("March")){
            return String.format("%s-%s-%s",y,"03",d);
        }else if(m.equalsIgnoreCase("April")){
            return String.format("%s-%s-%s",y,"04",d);
        }else if(m.equalsIgnoreCase("May")){
            return String.format("%s-%s-%s",y,"05",d);
        }else if(m.equalsIgnoreCase("June")){
            return String.format("%s-%s-%s",y,"06",d);
        }else if(m.equalsIgnoreCase("July")){
            return String.format("%s-%s-%s",y,"07",d);
        }else if(m.equalsIgnoreCase("August")){
            return String.format("%s-%s-%s",y,"08",d);
        }else if(m.equalsIgnoreCase("September")){
            return String.format("%s-%s-%s",y,"09",d);
        }else if(m.equalsIgnoreCase("October")){
            return String.format("%s-%s-%s",y,"10",d);
        }else if(m.equalsIgnoreCase("November")){
            return String.format("%s-%s-%s",y,"11",d);
        }else if(m.equalsIgnoreCase("December")){
            return String.format("%s-%s-%s",y,"12",d);
        }else{
            return String.format("%s-%s-%s",y,m,d);
        }
    }

}
