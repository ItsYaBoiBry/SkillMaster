package com.example.damianlzy.skillmaster.content;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.damianlzy.skillmaster.R;
import com.example.damianlzy.skillmaster.functions.Sessions;
import com.example.damianlzy.skillmaster.model.CourseDetails;
import com.example.damianlzy.skillmaster.model.User;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityCourseRegistration extends AppCompatActivity {

    Toolbar toolbar;
    EditText etFullName, etMobile, etEmail, etAddress, etNRIC, etNationality, etEducationLevel, etRace, etOccupation, etSalary;
    TextView etGender, etDob;
    Button btnSignup;
    RelativeLayout signupPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_registration);



        final Sessions sessions = new Sessions(ActivityCourseRegistration.this);
        etFullName = findViewById(R.id.et_full_name);
        etMobile = findViewById(R.id.et_mobile);
        etEmail = findViewById(R.id.et_email);

        etAddress = findViewById(R.id.et_address);
        etNRIC = findViewById(R.id.et_nric);
        etNationality = findViewById(R.id.et_nationality);
        etEducationLevel = findViewById(R.id.et_education_level);
        etRace = findViewById(R.id.et_race);
        etOccupation = findViewById(R.id.et_occupation);
        etSalary = findViewById(R.id.et_salary);
        etGender = findViewById(R.id.et_gender);
        etDob = findViewById(R.id.et_dob);
        User user = sessions.GetUser();
        etFullName.setText(user.getUser_name());
        etMobile.setText(user.getUser_mobile());
        etEmail.setText(user.getUser_email());
        etAddress.setText(user.getUser_address());
        etNRIC.setText(user.getUser_nric());
        etNationality.setText(user.getUser_nationality());
        etRace.setText(user.getUser_race());
        etOccupation.setText(user.getUser_occupation());
        etSalary.setText(user.getUser_salary());
        etGender.setText(user.getUser_gender());
        etDob.setText(user.getUser_dob());
        etEducationLevel.setText(user.getUser_education_level());
        btnSignup = findViewById(R.id.btn_signup);
        signupPage = findViewById(R.id.signup_page);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });
        etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectGender();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyBoard();
                Log.e("Click", "Signup Clicked");
                if (validate()) {
//                    Links links = new Links();
//                    SendRequest sendRequest = new SendRequest();
//                    try {
//                        JSONObject params = new JSONObject();
//                        params.put("email",etEmail.getText().toString().trim());
//                        params.put("name",etFullName.getText().toString().trim());
//                        params.put("mobile",etMobile.getText().toString().trim());
//                        params.put("address",etAddress.getText().toString().trim());
//                        params.put("nric",etNRIC.getText().toString().trim());
//                        params.put("dob",etDob.getText().toString().trim());
//                        params.put("gender",etGender.getText().toString().trim());
//                        params.put("nationality",etNationality.getText().toString().trim());
//                        params.put("education_level",etEducationLevel.getText().toString().trim());
//                        params.put("race",etRace.getText().toString().trim());
//                        params.put("occupation",etOccupation.getText().toString().trim());
//                        params.put("salary",etSalary.getText().toString().trim());
//                        Intent intent = getIntent();
//
//                        params.put("course_id",intent.getIntExtra("course_id", -1));
//                        params.put("schedule_id",intent.getIntExtra("schedule_id", -1));
//                        String request = sendRequest.execute(links.registerCourseLink(),"",sessions.GetToken()).get();
//                        Log.e("REGISTER COURSE:",request);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    User u = new User();
                    u.setUser_email(etEmail.getText().toString());
                    u.setUser_name(etFullName.getText().toString());
                    u.setUser_mobile(etMobile.getText().toString());
                    u.setUser_address(etAddress.getText().toString());
                    u.setUser_nric(etNRIC.getText().toString());
                    u.setUser_dob(etDob.getText().toString());
                    u.setUser_gender(etGender.getText().toString());
                    u.setUser_nationality(etNationality.getText().toString());
                    u.setUser_education_level(etEducationLevel.getText().toString());
                    u.setUser_race(etRace.getText().toString());
                    u.setUser_occupation(etOccupation.getText().toString());
                    u.setUser_salary(etSalary.getText().toString());
                    Intent intent = getIntent();
                    int course_id = intent.getIntExtra("course_id", -1);
                    int schedule_id = intent.getIntExtra("schedule_id", -1);
                    Intent i = getIntent();
                    startActivity(new Intent(ActivityCourseRegistration.this, ActivityPayment.class)
                            .putExtra("user",u)
                            .putExtra("course_id",course_id)
                            .putExtra("schedule_id",schedule_id)
                            .putExtra("course_name", i.getStringExtra("course_name"))
                            .putExtra("course_instructor", i.getStringExtra("course_instructor"))
                            .putExtra("course_description", i.getStringExtra("course_description"))
                            .putExtra("course_address", i.getStringExtra("course_address"))
                            .putExtra("course_fee", i.getStringExtra("course_fee"))
                            .putExtra("course_schedule", i.getStringExtra("course_schedule")));
                } else {
                    Snackbar snackbar = Snackbar
                            .make(signupPage, "Please fll in ALL details", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    public boolean validate() {
        boolean check = true;
        if (!hasText(etFullName)) check = false;
        if (!hasText(etMobile)) check = false;
        if (!hasText(etEmail)) check = false;
        if (!hasText(etAddress)) check = false;
        if (!hasText(etNRIC)) check = false;
        if (!hasText(etNationality)) check = false;
        if (!hasText(etEducationLevel)) check = false;
        if (!hasText(etRace)) check = false;
        if (!hasText(etOccupation)) check = false;
        if (!hasText(etSalary)) check = false;
        if (!CheckDOB(etDob)) check = false;
        if (!CheckGender(etGender)) check = false;
        return check;
    }

    public static boolean hasText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckDOB(TextView textView) {
        if (textView.getText().toString().equalsIgnoreCase("Date of birth")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean CheckGender(TextView textView) {
        if (textView.getText().toString().equalsIgnoreCase("Gender")) {
            return false;
        } else {
            return true;
        }
    }

    private void SelectGender() {
        final CharSequence[] items = {"Male", "Female", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityCourseRegistration.this);
        builder.setTitle("Choose Gender");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Male")) {
                    etGender.setText("Male");
                    dialog.dismiss();
                } else if (items[which].equals("Female")) {
                    etGender.setText("Female");
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void SelectDate() {
        DatePickerDialog datePickerDialog;
        int year;
        int month;
        int dayOfMonth;
        Calendar calendar;
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(ActivityCourseRegistration.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if (String.valueOf(month).length() == 1) {
                            etDob.setText(String.format("%d-0%d-%d", year, month + 1, day));
                        } else {
                            etDob.setText(String.format("%d-%d-%d", year, month + 1, day));
                        }

                    }
                }, year, month, dayOfMonth);
        datePickerDialog.updateDate(year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
